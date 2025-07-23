package bg.com.bo.bff.providers.dtos.response.certificates;

import bg.com.bo.bff.providers.dtos.response.certifications.CertificatesAccountsListMWResponse;

import java.util.Arrays;

public class CertificatesAccountsListMWResponseFixture {

    public static CertificatesAccountsListMWResponse withDefaults() {
        return CertificatesAccountsListMWResponse.builder()
                .data(Arrays.asList(
                        CertificatesAccountsListMWResponse.AccountMDW.builder()
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
                ))
                .build();
    }

    public static CertificatesAccountsListMWResponse withEmptyData() {
        return CertificatesAccountsListMWResponse.builder()
                .data(null)
                .build();
    }

}
