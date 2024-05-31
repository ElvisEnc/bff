package bg.com.bo.bff.providers.dtos.response;

import bg.com.bo.bff.models.dtos.TransactionLimit;
import bg.com.bo.bff.providers.dtos.TransactionLimitListMWResponse;

public class TransactionLimitListMWResponseFixture {
    public static TransactionLimitListMWResponse  withDefault(){
        return new TransactionLimitListMWResponse(
                 TransactionLimit.builder()
                         .identifier("123445")
                         .transactionPermitDay(10)
                         .availableTransaction(1000)
                         .availableTransactionGroup("2")
                         .currencyCod("13")
                         .type("I")
                         .build()
        );
    }
}
