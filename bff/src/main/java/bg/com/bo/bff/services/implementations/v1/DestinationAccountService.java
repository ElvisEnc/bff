package bg.com.bo.bff.services.implementations.v1;

import bg.com.bo.bff.application.dtos.request.AddThirdAccountRequest;
import bg.com.bo.bff.application.dtos.request.DeleteThirdAccountRequest;
import bg.com.bo.bff.application.dtos.response.GenericResponse;
import bg.com.bo.bff.providers.dtos.requests.AddThirdAccountBasicRequest;
import bg.com.bo.bff.providers.interfaces.IDestinationAccountProvider;
import bg.com.bo.bff.providers.interfaces.IThirdAccountProvider;
import bg.com.bo.bff.services.interfaces.IDestinationAccountService;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Map;

@Service
public class DestinationAccountService implements IDestinationAccountService {
    private final IDestinationAccountProvider destinationAccountProvider;

    private final IThirdAccountProvider thirdAccountProvider;

    public DestinationAccountService(IDestinationAccountProvider destinationAccountProvider, IThirdAccountProvider thirdAccountProvider) {
        this.destinationAccountProvider = destinationAccountProvider;
        this.thirdAccountProvider = thirdAccountProvider;
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
        return destinationAccountProvider
                .addThirdAccount(
                        thirdAccountProvider.generateAccessToken().getAccessToken(),
                        addThirdAccountBasicRequest,
                        parameters
                );
    }

    @Override
    public GenericResponse delete(String personId, int identifier, String deviceId, String deviceIp, DeleteThirdAccountRequest request) throws IOException {
        return thirdAccountProvider.delete(personId, identifier, request.getAccountId(), deviceId, deviceIp);
    }

}
