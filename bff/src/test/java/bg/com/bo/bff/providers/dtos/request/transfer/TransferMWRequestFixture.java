package bg.com.bo.bff.providers.dtos.request.transfer;

import bg.com.bo.bff.application.dtos.response.transfer.Pcc01Data;
import bg.com.bo.bff.providers.dtos.response.transfer.Pcc01MWResponse;

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

    public static Pcc01MWResponse withDefaultPcc01MWResponse() {
        Pcc01MWResponse response = new Pcc01MWResponse();
        response.setData(new Pcc01Data("S"));
        return response;
    }
}