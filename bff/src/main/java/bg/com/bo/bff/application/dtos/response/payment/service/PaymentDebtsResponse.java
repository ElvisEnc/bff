package bg.com.bo.bff.application.dtos.response.payment.service;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PaymentDebtsResponse {
    private String status;
    private String maeId;
    private String nroTransaction;
    private PaymentDebtsDetail receiptDetail;
}
