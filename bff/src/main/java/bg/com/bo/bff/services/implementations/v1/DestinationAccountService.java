package bg.com.bo.bff.services.implementations.v1;

import bg.com.bo.bff.application.dtos.Bank;
import bg.com.bo.bff.application.dtos.request.AddAchAccountRequest;
import bg.com.bo.bff.application.dtos.request.AddThirdAccountRequest;
import bg.com.bo.bff.application.dtos.request.AddWalletAccountRequest;
import bg.com.bo.bff.application.dtos.request.DeleteThirdAccountRequest;
import bg.com.bo.bff.application.dtos.request.destination.account.DestinationAccountRequest;
import bg.com.bo.bff.application.dtos.response.AccountTypeListResponse;
import bg.com.bo.bff.application.dtos.response.BanksResponse;
import bg.com.bo.bff.application.dtos.response.GenericResponse;
import bg.com.bo.bff.application.dtos.response.BranchOfficeResponse;
import bg.com.bo.bff.application.dtos.response.destination.account.DestinationAccount;
import bg.com.bo.bff.application.dtos.response.destination.account.DestinationAccountResponse;
import bg.com.bo.bff.commons.constants.CacheConstants;
import bg.com.bo.bff.commons.enums.AccountType;
import bg.com.bo.bff.commons.enums.DestinationAccountBG;
import bg.com.bo.bff.commons.enums.DestinationAccountType;
import bg.com.bo.bff.commons.filters.AccountNameFilter;
import bg.com.bo.bff.commons.filters.PageFilter;
import bg.com.bo.bff.mappings.services.DestinationAccountServiceMapper;
import bg.com.bo.bff.models.ThirdAccount;
import bg.com.bo.bff.models.ThirdAccountListResponse;
import bg.com.bo.bff.models.dtos.BanksMWResponse;
import bg.com.bo.bff.providers.dtos.requests.AddAchAccountBasicRequest;
import bg.com.bo.bff.providers.dtos.requests.AddThirdAccountBasicRequest;
import bg.com.bo.bff.providers.dtos.requests.AddWalletAccountBasicRequest;
import bg.com.bo.bff.providers.dtos.responses.BranchOfficeMWResponse;
import bg.com.bo.bff.providers.dtos.responses.account.ach.AchAccountMW;
import bg.com.bo.bff.providers.dtos.responses.account.ach.AchAccountMWResponse;
import bg.com.bo.bff.providers.interfaces.IAchAccountProvider;
import bg.com.bo.bff.providers.interfaces.IThirdAccountProvider;
import bg.com.bo.bff.providers.mappings.destination.account.IDestinationAccountMapper;
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
    private final DestinationAccountServiceMapper mapper;
    private final IDestinationAccountMapper iDestinationAccountMapper;
    @Autowired
    private DestinationAccountService self;

    public DestinationAccountService(IThirdAccountProvider thirdAccountProvider, IAchAccountProvider achAccountProvider, DestinationAccountServiceMapper mapper, IDestinationAccountMapper iDestinationAccountMapper) {
        this.thirdAccountProvider = thirdAccountProvider;
        this.achAccountProvider = achAccountProvider;
        this.mapper = mapper;
        this.iDestinationAccountMapper = iDestinationAccountMapper;
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
                        thirdAccountProvider.generateAccessToken().getAccessToken(),
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
                        thirdAccountProvider.generateAccessToken().getAccessToken(),
                        addWalletAccountBasicRequest,
                        parameter
                );
    }

    @Override
    public GenericResponse addAchAccount(String personId, AddAchAccountRequest addAchAccountRequest, Map<String, String> parameters) throws IOException {

        AddAchAccountBasicRequest addAchAccountBasicRequest = AddAchAccountBasicRequest.builder()
                .personId(personId)
                .companyPersonId(personId)
                .isFavorite(addAchAccountRequest.getIsFavorite())
                .isEnabled(addAchAccountRequest.getIsEnabled())
                .reference(addAchAccountRequest.getReference())
                .destinationAccountNumber(addAchAccountRequest.getDestinationAccountNumber())
                .destinationBankCode(addAchAccountRequest.getDestinationBankCode())
                .destinationBranchOfficeCode(addAchAccountRequest.getDestinationBranchOfficeCode())
                .destinationAccountTypeCode(addAchAccountRequest.getDestinationAccountTypeCode())
                .destinationHolderName(addAchAccountRequest.getDestinationHolderName())
                .destinationIDNumber(addAchAccountRequest.getDestinationIDNumber())
                .email(addAchAccountRequest.getEmail())
                .build();

        return achAccountProvider
                .addAchAccount(
                        achAccountProvider.generateAccessToken().getAccessToken(),
                        addAchAccountBasicRequest,
                        parameters
                );
    }

    @Override
    public BanksResponse getBanks() throws IOException {
        BanksMWResponse result = achAccountProvider.getBanks();
        return new BanksResponse(result.getData().stream().map(Bank::fromMWBank).toList());
    }

    @Override
    public BranchOfficeResponse getBranchOffice(Integer bankCode) throws IOException {
        BranchOfficeMWResponse mWResponse = achAccountProvider.getAllBranchOfficeBank(bankCode);
        return iDestinationAccountMapper.mapToBranchOfficeResponse(mWResponse);
    }

    @Override
    public AccountTypeListResponse accountTypes() {
        List<AccountType> values = Arrays.stream(AccountType.values()).toList();
        return AccountTypeListResponse.builder()
                .data(mapper.convert(values))
                .build();
    }

    @Override
    public GenericResponse delete(String personId, int identifier, String deviceId, String deviceIp, DeleteThirdAccountRequest request) throws IOException {
        return thirdAccountProvider.delete(personId, identifier, request.getAccountId(), deviceId, deviceIp);
    }

    @Override
    public GenericResponse deleteWalletAccount(String personId, int identifier, int accountNumber, String deviceId, String deviceIp) throws IOException {
        return thirdAccountProvider.deleteWalletAccount(personId, identifier, accountNumber, deviceId, deviceIp);
    }


    @Override
    public GenericResponse deleteAchAccount(String personId, int identifier, String deviceId, String deviceIp) throws IOException {
        return achAccountProvider.deleteAchAccount(personId, identifier, deviceId, deviceIp);
    }

    @Override
    public DestinationAccountResponse getDestinationAccounts(Integer personId, DestinationAccountRequest request, Map<String, String> parameter) throws IOException {
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
    public List<DestinationAccount> getListDestinationAccount(Integer personId, Map<String, String> parameter, Boolean isInitial) throws IOException {
        String tokenThirdAccounts = thirdAccountProvider.generateAccessToken().getAccessToken();
        ThirdAccountListResponse thirdAccountsResponse = thirdAccountProvider.getThirdAccounts(personId, tokenThirdAccounts, parameter);
        ThirdAccountListResponse walletAccountsResponse = thirdAccountProvider.getWalletAccounts(personId, tokenThirdAccounts, parameter);
        AchAccountMWResponse achAccountMWResponse = achAccountProvider.getAchAccounts(personId, parameter);
        List<DestinationAccount> allAccounts = new ArrayList<>();

        for (ThirdAccount thirdAccount : thirdAccountsResponse.getData()) {
            allAccounts.add(iDestinationAccountMapper.convertThirdAccountToDestinationAccount(thirdAccount, DestinationAccountType.CUENTA_TERCERO.getCode(), DestinationAccountBG.BG.getName()));
        }

        for (ThirdAccount walletAccount : walletAccountsResponse.getData()) {
            allAccounts.add(iDestinationAccountMapper.convertThirdAccountToDestinationAccount(walletAccount, DestinationAccountType.BILLETERA.getCode(), DestinationAccountBG.YOLO.getName()));
        }

        for (AchAccountMW achAccount : achAccountMWResponse.getData()) {
            allAccounts.add(iDestinationAccountMapper.convertAchAccountToDestinationAccount(achAccount));
        }

        allAccounts.sort(Comparator.comparing(DestinationAccount::getClientName, String.CASE_INSENSITIVE_ORDER));
        return allAccounts;
    }
}
