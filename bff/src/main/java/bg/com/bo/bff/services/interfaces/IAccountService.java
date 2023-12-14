package bg.com.bo.bff.services.interfaces;

import bg.com.bo.bff.model.AccountListResponse;

import java.io.IOException;

public interface IAccountService {
    AccountListResponse getAccounts(String personId) throws IOException;
}
