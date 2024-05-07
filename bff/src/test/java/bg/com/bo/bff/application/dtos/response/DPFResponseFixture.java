package bg.com.bo.bff.application.dtos.response;

import java.util.Arrays;

public class DPFResponseFixture {
    public static DPFListResponse withDefault() {
        return DPFListResponse.builder()
                .data(Arrays.asList(
                        DPFDataResponse.builder()
                                .numDPF("311042")
                                .numDpfBGA("BGA-201-311040-0-0")
                                .clientName("INSTITUCION FINANCIERA")
                                .capital("50000")
                                .interes("6000")
                                .total("206000")
                                .codeCurrency("068")
                                .dischargeDate("2016-01-01")
                                .expirationDate("2026-04-15")
                                .term("5412")
                                .rate("3.5")
                                .paymentFrequency("AL VENCIMIENTO")
                                .build(),
                        DPFDataResponse.builder()
                                .numDPF("311044")
                                .numDpfBGA("BGA-201-311044-0-0")
                                .clientName("INSTITUCION FINANCIERA")
                                .capital("200000")
                                .interes("6166.67")
                                .total("206166.67")
                                .codeCurrency("068")
                                .dischargeDate("2023-04-28")
                                .expirationDate("2024-05-02")
                                .term("370")
                                .rate("3")
                                .paymentFrequency("AL VENCIMIENTO")
                                .build()
                ))
                .build();
    }
}