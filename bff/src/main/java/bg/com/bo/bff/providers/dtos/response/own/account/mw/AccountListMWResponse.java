package bg.com.bo.bff.providers.dtos.response.own.account.mw;

import bg.com.bo.bff.application.dtos.response.own.account.Account;
import lombok.ToString;

import java.util.List;

@lombok.Data
@ToString
public class AccountListMWResponse {
    private List<Account> data;
    private AccountListMWMetadata meta;
}
