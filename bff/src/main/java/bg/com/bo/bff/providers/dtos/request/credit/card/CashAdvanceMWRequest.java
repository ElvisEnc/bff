package bg.com.bo.bff.providers.dtos.request.credit.card;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CashAdvanceMWRequest {
    private String personId;
    private String companyId;
    private String cmsAccountNumber;
    private String cmsCardNumber;
    private String accountId;
    private BigDecimal amount;
    private String description;
}
