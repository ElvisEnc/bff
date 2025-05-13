package bg.com.bo.bff.application.dtos.response.certifications;

import java.math.BigDecimal;
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

    public static List<CertificationPrefExchRateResponse> withDefaultsPrefExchRate() {
        return Arrays.asList(
                CertificationPrefExchRateResponse.builder()
                        .buyRateUFV("2.58274")
                        .buyRateEUR("6.91238")
                        .sellRateEur("8.55139")
                        .buyRate("6.85000")
                        .sellRate("6.97000")
                        .client("0")
                        .build(),
                CertificationPrefExchRateResponse.builder()
                        .buyRateUFV("2.58274")
                        .buyRateEUR("6.91238")
                        .sellRateEur("8.55139")
                        .buyRate("6.85000")
                        .sellRate("6.97000")
                        .client("0")
                        .build()
        );
    }

    public static List<CertificationHistoryResponse> withDefaultsHistory() {
        return Arrays.asList(
                CertificationHistoryResponse.builder()
                        .requestNumber("1268870")

                        .title("Certificado Cuentas")
                        .docNumber("CCU1268870-SC2022")
                        .state("Entregado")
                        .mail("carlos.g.cruz.a3@gmail.com")
                        .build(),
                CertificationHistoryResponse.builder()
                        .requestNumber("1268870")
                        .date("21/02/2021")
                        .title("Certificado Cuentas")
                        .docNumber("CCU1268870-SC2022")
                        .state("Entregado")
                        .mail("carlos.g.cruz.a3@gmail.com")
                        .build()
        );
    }

    public static CertificationConfigResponse withDefaultsConfig() {
        return CertificationConfigResponse.builder()
                .certPrice(new BigDecimal(123))
                .message("message")
                .build();
    }

    public static CertificationPriceResponse withDefaultsPrice() {
        return CertificationPriceResponse.builder()
                .chargeFeeId(1)
                .eventId(8)
                .amount(123)
                .currencyDes("BS")
                .currencyCode(1)
                .rangeFrom(2)
                .rangeTo(2)
                .rangeType("text")
                .build();
    }

    public static SaveCertificationResponse withDefaultsSave(){
        return SaveCertificationResponse.builder()
                .certPrice("50.0")
                .requestDate("2025-01-01")
                .requestTime("10:10")
                .fromCurrency("068")
                .originAccount("123456")
                .clientAccountName("NOMBRE")
                .email("test@bg.com.bo")
                .certDescription("DESCRIPTION")
                .dateRange("FROM 1 TO 2 MONTHS")
                .requestNumber("321654987")
                .build();
    }

}
