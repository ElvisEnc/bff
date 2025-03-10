package bg.com.bo.bff.application.dtos.response.certifications;

import java.util.Arrays;
import java.util.List;

public class CertificationResponseFixture {

    public static List<CertificationTypesResponse> withDefaults() {
        return Arrays.asList(
                CertificationTypesResponse.builder()
                        .requestCode(123)
                        .typeCode(321)
                        .description("Description")
                        .build(),
                CertificationTypesResponse.builder()
                        .requestCode(123)
                        .typeCode(321)
                        .description("Description")
                        .build()
        );
    }

    public static List<CertificationAccountsResponse> withDefaultsAccounts() {
        return Arrays.asList(
                CertificationAccountsResponse.builder()
                        .accountId("1735286")
                        .accountNumber("1052398524")
                        .clientName("PERSONA NATURAL")
                        .product("GANAMAS M/E")
                        .descState("SIN BLOQUEO")
                        .descHandle("INDIVIDUAL")
                        .descCurrency("$us")
                        .currentBalance("49999228.89")
                        .availableBalance("49999228.89")
                        .pledgedDFunds("0")
                        .depnoconf("0")
                        .currencyCode("2225")
                        .currency("DOLARES AMERICANOS")
                        .descAccountType("Caja de Ahorros")
                        .accountTypeCode("3")
                        .build()
        );
    }
}
