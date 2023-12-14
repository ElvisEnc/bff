package bg.com.bo.bff.model;

import lombok.ToString;

import java.util.List;

@lombok.Data
@ToString
public class AccountListMWResponse {
    private List<Account> data;
    private AccountListMWMetadata meta;
}
