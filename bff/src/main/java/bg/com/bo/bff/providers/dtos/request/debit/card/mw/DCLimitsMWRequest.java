package bg.com.bo.bff.providers.dtos.request.debit.card.mw;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DCLimitsMWRequest {
    private String numberOperations;
    private String personId;
    private String expirationDate;
    private String pciId;
    private BigDecimal amount;
}
