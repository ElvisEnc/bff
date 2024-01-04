package bg.com.bo.bff.services.interfaces;

import bg.com.bo.bff.model.AccountListResponse;
import bg.com.bo.bff.model.ClientToken;

import java.io.IOException;

public interface IAccountMiddlewareService {
    ClientToken generateAccountAccessToken() throws IOException;

    AccountListResponse getAccounts(String token, String personId, String documentNumber) throws IOException;

}
