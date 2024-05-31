package bg.com.bo.bff.providers.dtos.response.accounts;

import lombok.ToString;

@lombok.Data
@ToString
public class AccountListMWMetadata {
    private Integer totalRecords;
    private Integer totalPag;
}
