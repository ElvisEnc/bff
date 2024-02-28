package bg.com.bo.bff.providers.dtos.responses.accounts;

import bg.com.bo.bff.models.Account;
import lombok.ToString;

import java.util.List;

@lombok.Data
@ToString
public class AccountListMWResponse {
    private List<Account> data;
    private AccountListMWMetadata meta;
}
