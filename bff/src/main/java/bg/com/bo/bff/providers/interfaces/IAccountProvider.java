package bg.com.bo.bff.providers.interfaces;

import bg.com.bo.bff.models.dtos.accounts.AccountListResponse;
import bg.com.bo.bff.models.dtos.middleware.ClientMWToken;

import java.io.IOException;

public interface IAccountProvider {
    ClientMWToken generateAccountAccessToken() throws IOException;

    AccountListResponse getAccounts(String token, String personId, String documentNumber) throws IOException;

}
