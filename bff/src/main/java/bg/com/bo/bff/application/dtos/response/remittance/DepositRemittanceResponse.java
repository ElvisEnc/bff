package bg.com.bo.bff.application.dtos.response.remittance;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DepositRemittanceResponse {

    private int accountingEntry;
    private String time;
    private String description;
    private String remittanceName;
    private BigDecimal amountReceived;
    private String currencyCode;
    private BigDecimal exchangeRate;
    private BigDecimal commission;
    private BigDecimal amountPaid;
}
