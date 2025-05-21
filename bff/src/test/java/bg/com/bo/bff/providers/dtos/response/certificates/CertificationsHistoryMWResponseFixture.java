package bg.com.bo.bff.providers.dtos.response.certificates;

import bg.com.bo.bff.providers.dtos.response.certifications.CertificationsHistoryMWResponse;

import java.util.Arrays;

public class CertificationsHistoryMWResponseFixture {

    public static CertificationsHistoryMWResponse withDefaults() {
        return CertificationsHistoryMWResponse.builder()
                .data(
                        Arrays.asList(
                                CertificationsHistoryMWResponse.HistoricMW.builder()
                                        .requestNumber("1268870")
                                        .day("21")
                                        .month("Ene")
                                        .year("2022")
                                        .title("Certificado Cuentas")
                                        .docNumber("CCU1268870-SC2022")
                                        .state("Entregado")
                                        .mail("carlos.g.cruz.a3@gmail.com")
                                        .build(),
                                CertificationsHistoryMWResponse.HistoricMW.builder()
                                        .requestNumber("1268870")
                                        .day("21")
                                        .month("Ene")
                                        .year("2022")
                                        .title("Certificado Cuentas")
                                        .docNumber("CCU1268870-SC2022")
                                        .state("Entregado")
                                        .mail("carlos.g.cruz.a3@gmail.com")
                                        .build()
                        )
                )
                .build();
    }

    public static CertificationsHistoryMWResponse withEmptyData() {
        return CertificationsHistoryMWResponse.builder()
                .data(null)
                .build();
    }

}
