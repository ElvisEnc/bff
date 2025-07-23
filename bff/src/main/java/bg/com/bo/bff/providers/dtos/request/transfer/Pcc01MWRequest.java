package bg.com.bo.bff.providers.dtos.request.transfer;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Pcc01MWRequest {
    private String currency;
    private BigDecimal amount;
    private String accountId;
    private String personId;
}
