package bg.com.bo.bff.providers.dtos.request.debit.card;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DCAccountsOrderMWRequest {
    private String accountId1;
    private String accountId2;
    private String accountId3;
    private String accountId4;
    private String pciId;
    private String personId;
}
