package bg.com.bo.bff.services.v1;

import bg.com.bo.bff.model.dtos.accounts.AccountListResponse;
import bg.com.bo.bff.model.dtos.middleware.ClientMWToken;
import bg.com.bo.bff.services.interfaces.IAccountMiddlewareService;
import bg.com.bo.bff.services.interfaces.IAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class AccountService implements IAccountService {
    @Autowired
    private IAccountMiddlewareService iAccountMiddlewareService;

    public AccountListResponse getAccounts(String personId, String documentNumber) throws IOException {
        ClientMWToken clientToken = iAccountMiddlewareService.generateAccountAccessToken();
        String token = clientToken.getAccessToken();
        return iAccountMiddlewareService.getAccounts(token, personId, documentNumber);
    }
}
