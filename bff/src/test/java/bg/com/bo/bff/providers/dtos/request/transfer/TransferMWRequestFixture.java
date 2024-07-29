package bg.com.bo.bff.providers.dtos.request.transfer;

import java.math.BigDecimal;

public class TransferMWRequestFixture {
    public static TransferMWRequest withDefault() {
        return TransferMWRequest.builder()
                .ownerAccount(TransferMWRequest.OwnAccount.builder()
                        .schemeName("personId")
                        .personId("123456")
                        .build()
                )
                .debtorAccount(TransferMWRequest.Account.builder()
                        .schemeName("accountId")
                        .identification("123456")
                        .build()
                )
                .creditorAccount(TransferMWRequest.Account.builder()
                        .schemeName("accountId")
                        .identification("123456")
                        .build()
                )
                .instructedAmount(TransferMWRequest.Amount.builder()
                        .currency("068")
                        .amount(BigDecimal.valueOf(5.0))
                        .build()
                )
                .supplementaryData(TransferMWRequest.SupplementaryData.builder()
                        .description("description")
                        .sourceOfFunds("sourceOfFunds")
                        .destinationOfFunds("destinationOfFunds")
                        .build()
                )
                .build();
    }
}