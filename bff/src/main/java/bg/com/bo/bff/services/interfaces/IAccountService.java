package bg.com.bo.bff.services.interfaces;

import bg.com.bo.bff.model.dtos.accounts.AccountListResponse;

import java.io.IOException;

public interface IAccountService {
    AccountListResponse getAccounts(String personId, String documentNumber) throws IOException;
}
