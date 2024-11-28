package bg.com.bo.bff.services.implementations.v1;

import bg.com.bo.bff.application.dtos.request.destination.account.*;
import bg.com.bo.bff.application.dtos.response.destination.account.*;
import bg.com.bo.bff.application.dtos.response.generic.GenericResponse;
import bg.com.bo.bff.application.exceptions.GenericException;
import bg.com.bo.bff.commons.constants.CacheConstants;
import bg.com.bo.bff.commons.enums.destination.account.AccountType;
import bg.com.bo.bff.commons.enums.destination.account.DestinationAccountBG;
import bg.com.bo.bff.commons.enums.destination.account.DestinationAccountType;
import bg.com.bo.bff.commons.filters.AccountNameFilter;
import bg.com.bo.bff.commons.filters.PageFilter;
import bg.com.bo.bff.mappings.providers.account.IThirdAccountsMapper;
import bg.com.bo.bff.mappings.services.DestinationAccountServiceMapper;
import bg.com.bo.bff.providers.dtos.request.ach.account.mw.AddAchAccountBasicRequest;
import bg.com.bo.bff.providers.dtos.request.ach.account.mw.DeleteAchAccountMWRequest;
import bg.com.bo.bff.providers.dtos.request.third.account.mw.AddThirdAccountBasicRequest;
import bg.com.bo.bff.providers.dtos.request.third.account.mw.AddWalletAccountBasicRequest;
import bg.com.bo.bff.providers.dtos.request.third.account.mw.DeleteThirdAccountMWRequest;
import bg.com.bo.bff.providers.dtos.response.ach.account.mw.AchAccountsMWResponse;
import bg.com.bo.bff.providers.dtos.response.ach.account.mw.BanksMWResponse;
import bg.com.bo.bff.providers.dtos.response.ach.account.mw.BranchOfficeMWResponse;
import bg.com.bo.bff.providers.dtos.response.third.account.mw.ThirdAccountsMWResponse;
import bg.com.bo.bff.providers.interfaces.IAchAccountProvider;
import bg.com.bo.bff.providers.interfaces.IThirdAccountProvider;
import bg.com.bo.bff.mappings.providers.account.IAchAccountsMapper;
import bg.com.bo.bff.providers.models.enums.middleware.ach.account.AchAccountMiddlewareError;
import bg.com.bo.bff.providers.models.enums.middleware.ach.account.AchAccountMiddlewareResponse;
import bg.com.bo.bff.providers.models.enums.middleware.third.account.ThirdAccountMiddlewareError;
import bg.com.bo.bff.providers.models.enums.middleware.third.account.ThirdAccountMiddlewareResponse;
import bg.com.bo.bff.providers.models.middleware.DefaultMiddlewareError;
import bg.com.bo.bff.services.interfaces.IDestinationAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;

@Service
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class DestinationAccountService implements IDestinationAccountService {
    private final IThirdAccountProvider thirdAccountProvider;
    private final IAchAccountProvider achAccountProvider;
    private final DestinationAccountServiceMapper serviceMapper;
    private final IAchAccountsMapper achMapper;
    private final IThirdAccountsMapper thirdMapper;
    @Autowired
    private DestinationAccountService self;

    public DestinationAccountService(IThirdAccountProvider thirdAccountProvider, IAchAccountProvider achAccountProvider, DestinationAccountServiceMapper serviceMapper, IAchAccountsMapper achMapper, IThirdAccountsMapper thirdMapper) {
        this.thirdAccountProvider = thirdAccountProvider;
        this.achAccountProvider = achAccountProvider;
        this.serviceMapper = serviceMapper;
        this.achMapper = achMapper;
        this.thirdMapper = thirdMapper;
    }

    @Override
    public AddAccountResponse addThirdAccount(String personId, AddThirdAccountRequest request, Map<String, String> parameters) throws IOException {
        AddThirdAccountBasicRequest addThirdAccountBasicRequest = AddThirdAccountBasicRequest.builder()
                .personId(personId)
                .companyPersonId(personId)
                .toAccountNumber(request.getToAccountNumber())
                .reference(request.getReference())
                .isFavorite(request.getIsFavorite())
                .build();
        return thirdAccountProvider
                .addThirdAccount(
                        addThirdAccountBasicRequest,
                        parameters
                );
    }

    @Override
    public AddAccountResponse addWalletAccount(String personId, AddWalletAccountRequest request, Map<String, String> parameter) throws IOException {
        AddWalletAccountBasicRequest addWalletAccountBasicRequest = AddWalletAccountBasicRequest.builder()
                .personId(personId)
                .companyPersonId(personId)
                .toAccountNumber(request.getToAccountNumber())
                .reference(request.getReference())
                .isFavorite(request.getIsFavorite())
                .build();
        return thirdAccountProvider
                .addWalletAccount(
                        addWalletAccountBasicRequest,
                        parameter
                );
    }

    @Override
    public AddAccountResponse addAchAccount(String personId, AddAchAccountRequest addAchAccountRequest, Map<String, String> parameters) throws IOException {
        AddAchAccountBasicRequest addAchAccountBasicRequest = achMapper.mapperRequest(personId, addAchAccountRequest);
        return achAccountProvider.addAchAccount(addAchAccountBasicRequest, parameters);
    }

    @Override
    public GenericResponse addQRAccount(String personId, String bankType, AddQRAccountRequest addQRAccountRequest, Map<String, String> parameters) throws IOException {
        return switch (DestinationAccountBG.valueOf(bankType.toUpperCase())) {
            case THIRD -> {
                AddThirdAccountBasicRequest thirdRequest = thirdMapper.mapToThirdRequest(personId, addQRAccountRequest);
                AddAccountResponse response = thirdAccountProvider.addThirdAccount(thirdRequest, parameters);
                if (response.getId() != null)
                    yield GenericResponse.instance(ThirdAccountMiddlewareResponse.SUCCESS_ADD_ACCOUNT);
                else throw new GenericException(ThirdAccountMiddlewareError.ERROR_ADD_ACCOUNT);
            }
            case WALLET -> {
                AddWalletAccountBasicRequest walletRequest = thirdMapper.mapToWalletRequest(personId, addQRAccountRequest);
                AddAccountResponse response = thirdAccountProvider.addWalletAccount(walletRequest, parameters);
                if (response.getId() != null)
                    yield GenericResponse.instance(ThirdAccountMiddlewareResponse.SUCCESS_ADD_ACCOUNT);
                else throw new GenericException(ThirdAccountMiddlewareError.ERROR_ADD_ACCOUNT);
            }
            case ACH -> {
                AddAchAccountBasicRequest achRequest = achMapper.mapToAchRequest(personId, addQRAccountRequest);
                AddAccountResponse response = achAccountProvider.addAchAccount(achRequest, parameters);
                if (response.getId() != null)
                    yield GenericResponse.instance(AchAccountMiddlewareResponse.SUCCESS_ADD_ACCOUNT);
                else throw new GenericException(AchAccountMiddlewareError.ERROR_ADD_ACCOUNT);
            }
            default -> throw new IllegalArgumentException("Invalid bank type: " + bankType);
        };
    }

    @Override
    public BanksResponse getBanks(Map<String, String> parameter) throws IOException {
        BanksMWResponse result = achAccountProvider.getBanks(parameter);
        return new BanksResponse(result.getData().stream().map(Bank::fromMWBank).toList());
    }

    @Override
    public BranchOfficeResponse getBranchOffice(String bankCode, Map<String, String> parameter) throws IOException {
        BranchOfficeMWResponse mWResponse = achAccountProvider.getAllBranchOfficeBank(bankCode, parameter);
        return achMapper.mapToBranchOfficeResponse(mWResponse);
    }

    @Override
    public AccountTypeListResponse accountTypes() {
        List<AccountType> values = Arrays.stream(AccountType.values()).toList();
        return AccountTypeListResponse.builder()
                .data(serviceMapper.convert(values))
                .build();
    }

    @Override
    public GenericResponse deleteThirdAccount(String personId, long identifier, DeleteAccountRequest request, Map<String, String> parameters) throws IOException {
        DeleteThirdAccountMWRequest mwRequest = thirdMapper.mapperRequest(personId, identifier, request.getAccountNumber());
        return thirdAccountProvider.deleteThirdAccount(mwRequest, parameters);
    }

    @Override
    public GenericResponse deleteWalletAccount(String personId, long identifier, DeleteAccountRequest request, Map<String, String> parameters) throws IOException {
        return thirdAccountProvider.deleteWalletAccount(personId, identifier, request.getAccountNumber(), parameters);
    }

    @Override
    public GenericResponse deleteAchAccount(String personId, long identifier, DeleteAccountRequest request, Map<String, String> parameter) throws IOException {
        return achAccountProvider.deleteAchAccount(personId, identifier, request.getAccountNumber(), parameter);
    }

    @Override
    public DestinationAccountResponse getDestinationAccounts(String personId, DestinationAccountRequest request, Map<String, String> parameter) throws IOException {
        Boolean needAllRecords = (request.getPagination() == null || request.getPagination().getPage() == 1) && (request.getName() == null || request.getName().isEmpty());
        List<DestinationAccount> allAccounts = self.getListDestinationAccount(personId, parameter, needAllRecords);
        DestinationAccountResponse response = DestinationAccountResponse.builder()
                .totalAccounts(allAccounts.size())
                .build();

        String searchByName = request.getName();
        if (searchByName != null && searchByName.length() > 2)
            allAccounts = new AccountNameFilter(searchByName).apply(allAccounts);

        if (request.getPagination() != null) {
            int page = request.getPagination().getPage();
            int pageSize = request.getPagination().getPageSize();
            allAccounts = new PageFilter(page, pageSize).apply(allAccounts);
        }
        response.setData(allAccounts);
        response.setTotal(allAccounts.size());
        return response;
    }

    @Caching(cacheable = {@Cacheable(value = CacheConstants.DESTINATION_ACCOUNTS, key = "#personId", condition = "#isInitial == false")},
            put = {@CachePut(value = CacheConstants.DESTINATION_ACCOUNTS, key = "#personId", condition = "#isInitial == true")})
    public List<DestinationAccount> getListDestinationAccount(String personId, Map<String, String> parameter, Boolean isInitial) throws IOException {
        ThirdAccountsMWResponse thirdAccountsResponse = thirdAccountProvider.getThirdAccounts(personId, parameter);
        ThirdAccountsMWResponse walletAccountsResponse = thirdAccountProvider.getWalletAccounts(personId, parameter);
        AchAccountsMWResponse achAccountsMWResponse = achAccountProvider.getAchAccounts(personId, parameter);
        List<DestinationAccount> allAccounts = new ArrayList<>();
        allAccounts.addAll(thirdMapper.convertThirdAccountToDestinationAccount(thirdAccountsResponse, DestinationAccountType.CUENTA_TERCERO.getCode(), DestinationAccountBG.BG.getName()));
        allAccounts.addAll(thirdMapper.convertThirdAccountToDestinationAccount(walletAccountsResponse, DestinationAccountType.BILLETERA.getCode(), DestinationAccountBG.YOLO.getName()));
        allAccounts.addAll(achMapper.convertAchAccountToDestinationAccount(achAccountsMWResponse));

        allAccounts.sort(Comparator.comparing(DestinationAccount::getClientName, String.CASE_INSENSITIVE_ORDER));
        return allAccounts;
    }

    @Override
    public ValidateAccountResponse getValidateDestinationAccounts(String accountNumber, String clientName, Map<String, String> parameter) throws IOException {
        return thirdAccountProvider.validateAccount(accountNumber, clientName, parameter);
    }

    @Override
    public DestinationAccount getAccount(String personId, String accountType, String accountId, Map<String, String> parameter) throws IOException {
        List<DestinationAccount> accounts = retrieveAccounts(personId, accountType, parameter);
        return accounts.stream()
                .filter(account -> account.getId().equals(Long.valueOf(accountId)))
                .findFirst()
                .orElseThrow(() -> new GenericException(ThirdAccountMiddlewareError.MDWACTM_3001));
    }

    private List<DestinationAccount> retrieveAccounts(String personId, String accountType, Map<String, String> parameter) throws IOException {
        return switch (accountType) {
            case "1" -> thirdMapper.convertThirdAccountToDestinationAccount(
                    thirdAccountProvider.getThirdAccounts(personId, parameter),
                    DestinationAccountType.CUENTA_TERCERO.getCode(),
                    DestinationAccountBG.BG.getName()
            );
            case "2" -> achMapper.convertAchAccountToDestinationAccount(
                    achAccountProvider.getAchAccounts(personId, parameter)
            );
            case "3" -> thirdMapper.convertThirdAccountToDestinationAccount(
                    thirdAccountProvider.getWalletAccounts(personId, parameter),
                    DestinationAccountType.BILLETERA.getCode(),
                    DestinationAccountBG.YOLO.getName()
            );
            default -> throw new GenericException(DefaultMiddlewareError.BAD_REQUEST);
        };
    }
}