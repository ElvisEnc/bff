package bg.com.bo.bff.services.implementations.v1;

import bg.com.bo.bff.application.dtos.request.own.account.UpdateTransactionLimitRequest;
import bg.com.bo.bff.application.dtos.response.generic.GenericResponse;
import bg.com.bo.bff.application.dtos.response.own.account.GetTransactionLimitResponse;
import bg.com.bo.bff.application.dtos.response.own.account.TransactionLimitData;
import bg.com.bo.bff.providers.dtos.response.own.account.mw.TransactionLimitListMWResponse;
import bg.com.bo.bff.application.dtos.response.own.account.AccountListResponse;
import bg.com.bo.bff.providers.dtos.response.own.account.mw.ClientMWToken;
import bg.com.bo.bff.providers.dtos.request.own.account.mw.UpdateTransactionLimitMWRequest;
import bg.com.bo.bff.providers.interfaces.IAccountProvider;
import bg.com.bo.bff.services.interfaces.IAccountService;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Map;

@Service
public class AccountService implements IAccountService {

    private final IAccountProvider iAccountMiddlewareService;

    public AccountService(IAccountProvider iAccountMiddlewareService) {
        this.iAccountMiddlewareService = iAccountMiddlewareService;
    }

    public AccountListResponse getAccounts(String personId, String documentNumber) throws IOException {
        ClientMWToken clientToken = iAccountMiddlewareService.generateAccountAccessToken();
        String token = clientToken.getAccessToken();
        return iAccountMiddlewareService.getAccounts(token, personId, documentNumber);
    }

    @Override
    public GenericResponse updateTransactionLimit(String personId, String accountId, UpdateTransactionLimitRequest request, Map<String, String> parameter) throws IOException {
        UpdateTransactionLimitMWRequest requestMW = UpdateTransactionLimitMWRequest.builder()
                .availableTransaction(request.getAmountLimit())
                .transactionPermitDay(request.getCountLimit())
                .build();
        return iAccountMiddlewareService.updateTransactionLimit(personId, accountId, requestMW, parameter);

    }

    @Override
    public GetTransactionLimitResponse getTransactionLimit(String personId, String accountId, Map<String, String> parameter) throws IOException {
        TransactionLimitListMWResponse result = iAccountMiddlewareService.getTransactionLimit(personId, accountId, parameter);
       return new GetTransactionLimitResponse(
                TransactionLimitData.builder()
                        .amountLimit(result.getData().getAvailableTransaction())
                        .countLimit(result.getData().getTransactionPermitDay())
                        .build()
        );

    }
}
