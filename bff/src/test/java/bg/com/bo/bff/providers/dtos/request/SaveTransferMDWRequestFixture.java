package bg.com.bo.bff.providers.dtos.request;

import bg.com.bo.bff.providers.dtos.request.transfers.programming.SaveTransferMDWRequest;

public class SaveTransferMDWRequestFixture {

    public static SaveTransferMDWRequest withDefaults() {
        return SaveTransferMDWRequest.builder()
                .personId("123456")
                .originJtsOid("321654")
                .destinationAccountNumber("72669117")
                .amount("10")
                .currency("0")
                .transferType("YOLO")
                .description("PRUEBA")
                .frequencyCode("3")
                .initDate("2025-04-14")
                .endDate("2025-12-14")
                .userRegistry("TOP1")
                .destinationJtsOid("8355606")
                .listCode("0")
                .codEif("N")
                .destinationFounds("DESTINATION")
                .originFounds("ORIGIN")
                .reason("REGISTRO DE PRUEBA")
                .destinationName("USUARIO")
                .bankName("BGA")
                .uifControl("N")
                .build();
    }

}
