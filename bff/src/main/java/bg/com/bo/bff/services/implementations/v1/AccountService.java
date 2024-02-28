package bg.com.bo.bff.services.implementations.v1;

import bg.com.bo.bff.models.dtos.accounts.AccountListResponse;
import bg.com.bo.bff.models.dtos.middleware.ClientMWToken;
import bg.com.bo.bff.providers.interfaces.IAccountProvider;
import bg.com.bo.bff.services.interfaces.IAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class AccountService implements IAccountService {
    @Autowired
    private IAccountProvider iAccountMiddlewareService;

    public AccountListResponse getAccounts(String personId, String documentNumber) throws IOException {
        ClientMWToken clientToken = iAccountMiddlewareService.generateAccountAccessToken();
        String token = clientToken.getAccessToken();
        return iAccountMiddlewareService.getAccounts(token, personId, documentNumber);
    }
}
