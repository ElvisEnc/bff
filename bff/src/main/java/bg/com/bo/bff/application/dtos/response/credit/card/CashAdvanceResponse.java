package bg.com.bo.bff.application.dtos.response.credit.card;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CashAdvanceResponse {
    private String voucher;
    private String voucherDate;
    private String voucherTime;
    private String amountCommission;
    private String amountPaid;
    private String exchangeRate;
    private String swExchangeRate;
}
