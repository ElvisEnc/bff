package bg.com.bo.bff.application.dtos.response.dpf;

import java.util.Arrays;

public class DpfResponseFixture {
    public static DpfListResponse withDefaultDpfListResponse() {
        return DpfListResponse.builder()
                .data(Arrays.asList(
                        DpfDataResponse.builder()
                                .numDPF("311042")
                                .numDpfBGA("BGA-201-311040-0-0")
                                .clientName("INSTITUCION FINANCIERA")
                                .capital("50000")
                                .interes("6000")
                                .total("206000")
                                .codeCurrency("068")
                                .dischargeDate("2016-01-01")
                                .expirationDate("15/04/2026")
                                .term("5412")
                                .rate("3.5")
                                .paymentFrequency("AL VENCIMIENTO")
                                .build(),
                        DpfDataResponse.builder()
                                .numDPF("311044")
                                .numDpfBGA("BGA-201-311044-0-0")
                                .clientName("INSTITUCION FINANCIERA")
                                .capital("200000")
                                .interes("6166.67")
                                .total("206166.67")
                                .codeCurrency("068")
                                .dischargeDate("2023-04-28")
                                .expirationDate("02/05/2024")
                                .term("370")
                                .rate("3")
                                .paymentFrequency("AL VENCIMIENTO")
                                .build()
                ))
                .build();
    }
}