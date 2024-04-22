package bg.com.bo.bff.services.implementations.v1;

import bg.com.bo.bff.application.dtos.request.AddAchAccountRequest;
import bg.com.bo.bff.application.dtos.request.AddThirdAccountRequest;
import bg.com.bo.bff.application.dtos.request.AddWalletAccountRequest;
import bg.com.bo.bff.application.dtos.request.DeleteThirdAccountRequest;
import bg.com.bo.bff.application.dtos.response.AccountTypeListResponse;
import bg.com.bo.bff.application.dtos.response.GenericResponse;
import bg.com.bo.bff.commons.enums.AccountType;
import bg.com.bo.bff.mappings.services.DestinationAccountServiceMapper;
import bg.com.bo.bff.providers.dtos.requests.AddAchAccountBasicRequest;
import bg.com.bo.bff.providers.dtos.requests.AddThirdAccountBasicRequest;
import bg.com.bo.bff.providers.dtos.requests.AddWalletAccountBasicRequest;
import bg.com.bo.bff.providers.interfaces.IAchAccountProvider;
import bg.com.bo.bff.providers.interfaces.IThirdAccountProvider;
import bg.com.bo.bff.services.interfaces.IDestinationAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Service
public class DestinationAccountService implements IDestinationAccountService {
    private final IThirdAccountProvider thirdAccountProvider;
    private final IAchAccountProvider achAccountProvider;
    private final DestinationAccountServiceMapper mapper;

    @Autowired
    public DestinationAccountService(IThirdAccountProvider thirdAccountProvider, IAchAccountProvider achAccountProvider, DestinationAccountServiceMapper mapper) {
        this.thirdAccountProvider = thirdAccountProvider;
        this.achAccountProvider = achAccountProvider;
        this.mapper = mapper;
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
    public GenericResponse delete(String personId, int identifier, String deviceId, String deviceIp, DeleteThirdAccountRequest request) throws IOException {
        return thirdAccountProvider.delete(personId, identifier, request.getAccountId(), deviceId, deviceIp);
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
    public AccountTypeListResponse accountTypes() {
        List<AccountType> values = Arrays.stream(AccountType.values()).toList();
        return AccountTypeListResponse.builder()
                .data(mapper.convert(values))
                .build();
    }
}
