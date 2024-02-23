package bg.com.bo.bff.model.dtos.accounts;

import bg.com.bo.bff.model.Account;

import java.util.List;

@lombok.Data
public class AccountListResponse {
    private List<Account> data;
}
