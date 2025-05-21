package bg.com.bo.bff.application.dtos.response.transfers.programming;

import java.util.Arrays;
import java.util.List;

public class PaymentsPlanResponseFixture {

    public static List<PaymentsPlanResponse> withDefaults() {
        return Arrays.asList(
                PaymentsPlanResponse.builder()
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
                PaymentsPlanResponse.builder()
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
        );
    }

}
