package bg.com.bo.bff.providers.dtos.response.own.account.mw;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class TransactionLimit {
    private String identifier;
    private Integer transactionPermitDay;
    private Integer availableTransaction;
    private String availableTransactionGroup;
    private String currencyCod;
    private String type;
}
