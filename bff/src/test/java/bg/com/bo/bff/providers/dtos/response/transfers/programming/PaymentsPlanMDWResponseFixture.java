package bg.com.bo.bff.providers.dtos.response.transfers.programming;

import java.util.Arrays;

public class PaymentsPlanMDWResponseFixture {

    public static PaymentsPlanMDWResponse withDefaults() {
        return PaymentsPlanMDWResponse.builder()
                .data(
                        Arrays.asList(
                                PaymentsPlanMDWResponse.PaymentPlanObj.builder()
                                        .paymentId("918")
                                        .progTransferId("0")
                                        .trackingProcess("tracking")
                                        .registerDate("27-01-2025")
                                        .amount("10.0")
                                        .currency("0")
                                        .status("1")
                                        .processError("")
                                        .voucher("")
                                        .accountingEntry("999053443")
                                        .accountEntryDate("27-01-2025")
                                        .branch("0")
                                        .tzLock("0")
                                        .orderCode("order code")
                                        .batchNumber("batch number")
                                        .ordinal("4")
                                        .build(),
                                PaymentsPlanMDWResponse.PaymentPlanObj.builder()
                                        .paymentId("919")
                                        .progTransferId("0")
                                        .trackingProcess("tracking")
                                        .registerDate("27-01-2025")
                                        .amount("10.0")
                                        .currency("0")
                                        .status("1")
                                        .processError("")
                                        .voucher("")
                                        .accountingEntry("999053443")
                                        .accountEntryDate("27-01-2025")
                                        .branch("0")
                                        .tzLock("0")
                                        .orderCode("order code")
                                        .batchNumber("batch number")
                                        .ordinal("5")
                                        .build()
                        )
                )
                .build();
    }

}
