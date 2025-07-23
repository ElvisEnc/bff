package bg.com.bo.bff.providers.dtos.response.credit.card.mw;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CashAdvanceMWResponse {
    private String voucher;
    private String voucherDate;
    private String voucherTime;
    private String amountCommission;
    private String amountPaid;
    private String exchangeRate;
    private String swExchangeRate;
}
