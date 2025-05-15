package bg.com.bo.bff.application.dtos.response.transfers.programming;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

public class ProgrammedTransfersResponseFixture {

    public static List<ProgrammedTransfersResponse> withDefaults() {
        return Arrays.asList(
                ProgrammedTransfersResponse.builder()
                        .transferId(518)
                        .personId(13649136)
                        .originJtsOid(7129136)
                        .destinationAccount("1310398352")
                        .amount(BigDecimal.valueOf(10))
                        .currency(0)
                        .transferType("TER")
                        .description("almuerzo anabelia")
                        .frequencyCode("DIARIA")
                        .initDate("2025-01-24")
                        .endDate("2025-01-27")
                        .userRegistry("13649136")
                        .registryDate("2025-01-23T20:49:15.000+00:00")
                        .userModification(null)
                        .modificationDate(null)
                        .tzLock(0)
                        .destinationJtsOid(4857514)
                        .listCode(0)
                        .codEif("1018")
                        .destinationFounds(null)
                        .originFounds(null)
                        .reason(null)
                        .status("3")
                        .destinationName("TERRAZAS COSSIO ANA BELIA")
                        .bankName("Banco Ganadero S.A.")
                        .uifControl("N")
                        .feeNumber(4)
                        .build(),
                ProgrammedTransfersResponse.builder()
                        .transferId(519)
                        .personId(13649136)
                        .originJtsOid(7129136)
                        .destinationAccount("1310398352")
                        .amount(BigDecimal.valueOf(10))
                        .currency(0)
                        .transferType("TER")
                        .description("almuerzo anabelia")
                        .frequencyCode("DIARIA")
                        .initDate("2025-01-24")
                        .endDate("2025-01-27")
                        .userRegistry("13649136")
                        .registryDate("2025-01-23T20:49:15.000+00:00")
                        .userModification(null)
                        .modificationDate(null)
                        .tzLock(0)
                        .destinationJtsOid(4857514)
                        .listCode(0)
                        .codEif("1018")
                        .destinationFounds(null)
                        .originFounds(null)
                        .reason(null)
                        .status("3")
                        .destinationName("TERRAZAS COSSIO ANA BELIA")
                        .bankName("Banco Ganadero S.A.")
                        .uifControl("N")
                        .feeNumber(4)
                        .build()
        );
    }

}
