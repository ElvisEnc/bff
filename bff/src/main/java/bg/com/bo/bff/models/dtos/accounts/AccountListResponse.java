package bg.com.bo.bff.models.dtos.accounts;

import bg.com.bo.bff.models.Account;

import java.util.List;

@lombok.Data
public class AccountListResponse {
    private List<Account> data;
}
