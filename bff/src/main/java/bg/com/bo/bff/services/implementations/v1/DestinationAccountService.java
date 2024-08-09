package bg.com.bo.bff.services.implementations.v1;

import bg.com.bo.bff.application.dtos.request.destination.account.AddAchAccountRequest;
import bg.com.bo.bff.application.dtos.request.destination.account.AddThirdAccountRequest;
import bg.com.bo.bff.application.dtos.request.destination.account.AddWalletAccountRequest;
import bg.com.bo.bff.application.dtos.request.destination.account.AddQRAccountRequest;
import bg.com.bo.bff.application.dtos.request.destination.account.DestinationAccountRequest;
import bg.com.bo.bff.application.dtos.response.destination.account.*;
import bg.com.bo.bff.application.dtos.response.generic.GenericResponse;
import bg.com.bo.bff.commons.constants.CacheConstants;
import bg.com.bo.bff.commons.enums.AccountType;
import bg.com.bo.bff.commons.enums.DestinationAccountBG;
import bg.com.bo.bff.commons.enums.DestinationAccountType;
import bg.com.bo.bff.commons.filters.AccountNameFilter;
import bg.com.bo.bff.commons.filters.PageFilter;
import bg.com.bo.bff.mappings.providers.account.IThirdAccountsMapper;
import bg.com.bo.bff.mappings.services.DestinationAccountServiceMapper;
import bg.com.bo.bff.providers.dtos.request.ach.account.mw.AddAchAccountBasicRequest;
import bg.com.bo.bff.providers.dtos.request.ach.account.mw.DeleteAchAccountMWRequest;
import bg.com.bo.bff.providers.dtos.request.third.account.mw.AddThirdAccountBasicRequest;
import bg.com.bo.bff.providers.dtos.request.third.account.mw.AddWalletAccountBasicRequest;
import bg.com.bo.bff.providers.dtos.request.third.account.mw.DeleteThirdAccountMWRequest;
import bg.com.bo.bff.providers.dtos.response.ach.account.mw.AchAccountMW;
import bg.com.bo.bff.providers.dtos.response.ach.account.mw.AchAccountsMWResponse;
import bg.com.bo.bff.providers.dtos.response.ach.account.mw.BanksMWResponse;
import bg.com.bo.bff.providers.dtos.response.ach.account.mw.BranchOfficeMWResponse;
import bg.com.bo.bff.providers.dtos.response.third.account.mw.ThirdAccountsMWResponse;
import bg.com.bo.bff.providers.interfaces.IAchAccountProvider;
import bg.com.bo.bff.providers.interfaces.IThirdAccountProvider;
import bg.com.bo.bff.mappings.providers.account.IAchAccountsMapper;
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
    private final IAchAccountsMapper mapper;
    private final IThirdAccountsMapper thirdMapper;
    @Autowired
    private DestinationAccountService self;

    public DestinationAccountService(IThirdAccountProvider thirdAccountProvider, IAchAccountProvider achAccountProvider, DestinationAccountServiceMapper serviceMapper, IAchAccountsMapper mapper, IThirdAccountsMapper thirdMapper) {
        this.thirdAccountProvider = thirdAccountProvider;
        this.achAccountProvider = achAccountProvider;
        this.serviceMapper = serviceMapper;
        this.mapper = mapper;
        this.thirdMapper = thirdMapper;
    }

    @Override
    public GenericResponse addThirdAccount(String personId, AddThirdAccountRequest request, Map<String, String> parameters) throws IOException {
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
    public GenericResponse addWalletAccount(String personId, AddWalletAccountRequest request, Map<String, String> parameter) throws IOException {
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
    public GenericResponse addAchAccount(String personId, AddAchAccountRequest addAchAccountRequest, Map<String, String> parameters) throws IOException {
        AddAchAccountBasicRequest addAchAccountBasicRequest = mapper.mapperRequest(personId, addAchAccountRequest);
        return achAccountProvider.addAchAccount(addAchAccountBasicRequest, parameters);
    }

    @Override
    public GenericResponse addQRAccount(String personId, String bankType, AddQRAccountRequest addQRAccountRequest, Map<String, String> parameters) throws IOException {
        return switch (DestinationAccountBG.valueOf(bankType.toUpperCase())) {
            case THIRD -> {
                AddThirdAccountBasicRequest thirdRequest = thirdMapper.mapToThirdRequest(personId, addQRAccountRequest);
                yield thirdAccountProvider.addThirdAccount(
                        thirdRequest,
                        parameters
                );
            }
            case WALLET -> {
                AddWalletAccountBasicRequest walletRequest = thirdMapper.mapToWalletRequest(personId, addQRAccountRequest);
                yield thirdAccountProvider.addWalletAccount(
                        walletRequest,
                        parameters
                );
            }
            case ACH -> {
                AddAchAccountBasicRequest achRequest = mapper.mapToAchRequest(personId, addQRAccountRequest);
                yield achAccountProvider.addAchAccount(achRequest, parameters);
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
        return mapper.mapToBranchOfficeResponse(mWResponse);
    }

    @Override
    public AccountTypeListResponse accountTypes() {
        List<AccountType> values = Arrays.stream(AccountType.values()).toList();
        return AccountTypeListResponse.builder()
                .data(serviceMapper.convert(values))
                .build();
    }

    @Override
    public GenericResponse deleteThirdAccount(String personId, long identifier, long accountNumber, Map<String, String> parameters) throws IOException {
        DeleteThirdAccountMWRequest mwRequest = thirdMapper.mapperRequest(personId, identifier, accountNumber);
        return thirdAccountProvider.deleteThirdAccount(mwRequest, parameters);
    }

    @Override
    public GenericResponse deleteWalletAccount(String personId, long identifier, long accountNumber, Map<String, String> parameters) throws IOException {
        return thirdAccountProvider.deleteWalletAccount(personId, identifier, accountNumber, parameters);
    }

    @Override
    public GenericResponse deleteAchAccount(String personId, long identifier, Map<String, String> parameter) throws IOException {
        DeleteAchAccountMWRequest requestData = mapper.mapperRequest(personId, identifier);
        return achAccountProvider.deleteAchAccount(requestData, parameter);
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

        for (ThirdAccountsMWResponse.ThirdAccountMW thirdAccount : thirdAccountsResponse.getData()) {
            allAccounts.add(thirdMapper.convertThirdAccountToDestinationAccount(thirdAccount, DestinationAccountType.CUENTA_TERCERO.getCode(), DestinationAccountBG.BG.getName()));
        }

        for (ThirdAccountsMWResponse.ThirdAccountMW walletAccount : walletAccountsResponse.getData()) {
            allAccounts.add(thirdMapper.convertThirdAccountToDestinationAccount(walletAccount, DestinationAccountType.BILLETERA.getCode(), DestinationAccountBG.YOLO.getName()));
        }

        for (AchAccountMW achAccount : achAccountsMWResponse.getData()) {
            allAccounts.add(mapper.convertAchAccountToDestinationAccount(achAccount));
        }

        allAccounts.sort(Comparator.comparing(DestinationAccount::getClientName, String.CASE_INSENSITIVE_ORDER));
        return allAccounts;
    }

    @Override
    public ValidateAccountResponse getValidateDestinationAccounts(String accountNumber, String clientName, Map<String, String> parameter) throws IOException {
        return thirdAccountProvider.validateAccount(accountNumber, clientName, parameter);
    }
}