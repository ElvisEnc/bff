package bg.com.bo.bff.model.dtos.middleware.accounts;

import bg.com.bo.bff.model.Account;
import lombok.ToString;

import java.util.List;

@lombok.Data
@ToString
public class AccountListMWResponse {
    private List<Account> data;
    private AccountListMWMetadata meta;
}
