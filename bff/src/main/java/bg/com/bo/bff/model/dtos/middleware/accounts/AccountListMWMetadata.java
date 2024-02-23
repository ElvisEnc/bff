package bg.com.bo.bff.model.dtos.middleware.accounts;

import lombok.ToString;

@lombok.Data
@ToString
public class AccountListMWMetadata {
    private Integer totalRecords;
    private Integer totalPag;
}
