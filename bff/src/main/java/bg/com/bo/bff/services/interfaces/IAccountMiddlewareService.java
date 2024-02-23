package bg.com.bo.bff.services.interfaces;

import bg.com.bo.bff.model.dtos.accounts.AccountListResponse;
import bg.com.bo.bff.model.dtos.middleware.ClientMWToken;

import java.io.IOException;

public interface IAccountMiddlewareService {
    ClientMWToken generateAccountAccessToken() throws IOException;

    AccountListResponse getAccounts(String token, String personId, String documentNumber) throws IOException;

}
