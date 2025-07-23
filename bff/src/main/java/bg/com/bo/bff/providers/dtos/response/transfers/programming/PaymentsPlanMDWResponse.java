package bg.com.bo.bff.providers.dtos.response.transfers.programming;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PaymentsPlanMDWResponse {

    private List<PaymentPlanObj> data;

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class PaymentPlanObj{
        private String paymentId;
        private String progTransferId;
        private String trackingProcess;
        private String registerDate;
        private String amount;
        private String currency;
        private String status;
        private String processError;
        private String voucher;
        private String accountingEntry;
        private String accountEntryDate;
        private String branch;
        private String tzLock;
        private String orderCode;
        private String batchNumber;
        private String ordinal;
    }

}
