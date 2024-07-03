package bg.com.bo.bff.providers.dtos.response.dpf.mw;

import bg.com.bo.bff.providers.dtos.response.generic.ApiErrorResponse;
import bg.com.bo.bff.providers.dtos.response.generic.ErrorDetailResponse;
import bg.com.bo.bff.providers.models.enums.middleware.DPFMiddlewareError;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DpfMWResponseFixture {
    public static DpfMWResponse withDefaultDpfMWResponse() {
        return DpfMWResponse.builder()
                .data(Arrays.asList(DpfMWResponse.DPFManagerMWData.builder()
                                .accountDpf("7892112")
                                .numDpf("311042")
                                .personNumber("56412")
                                .numDpfBGA("BGA-201-311042-0-0")
                                .ordinal("0")
                                .numCertificate("0")
                                .codeCurrency("068")
                                .highDate("2023-04-28")
                                .term("360")
                                .expirationDate("2024-04-22")
                                .rate("3")
                                .capital("200000")
                                .interest("6000")
                                .total("206000")
                                .managementCod("I")
                                .payFormInterestCod(" ")
                                .currencyDescription("BOLIVIANOS")
                                .currencyAbbreviation("Bs")
                                .statusCod(" ")
                                .statusDescription("SIN BLOQUEO")
                                .paymentMethod("AL VENCIMIENTO")
                                .drivingAccountDpf("INDIVIDUAL")
                                .productCod("86997")
                                .productDescription("DPF DESMA. INSTITUCIONALES - VCTO - NOMINATIVO M/N")
                                .clientCod("205")
                                .clientName("INSTITUCION FINANCIERA")
                                .plazaCod("2")
                                .plazaDescription("LA PAZ")
                                .build(),
                        DpfMWResponse.DPFManagerMWData.builder()
                                .accountDpf("7892136")
                                .numDpf("311044")
                                .personNumber("56412")
                                .numDpfBGA("BGA-201-311044-0-0")
                                .ordinal("0")
                                .numCertificate("0")
                                .codeCurrency("068")
                                .highDate("2023-04-28")
                                .term("370")
                                .expirationDate("2024-05-02")
                                .rate("3")
                                .capital("200000")
                                .interest("6166.67")
                                .total("206166.67")
                                .managementCod("I")
                                .payFormInterestCod(" ")
                                .currencyDescription("BOLIVIANOS")
                                .currencyAbbreviation("Bs")
                                .statusCod(" ")
                                .statusDescription("SIN BLOQUEO")
                                .paymentMethod("AL VENCIMIENTO")
                                .drivingAccountDpf("INDIVIDUAL")
                                .productCod("86997")
                                .productDescription("DPF DESMA. INSTITUCIONALES - VCTO - NOMINATIVO M/N")
                                .clientCod("205")
                                .clientName("INSTITUCION FINANCIERA")
                                .plazaCod("2")
                                .plazaDescription("LA PAZ")
                                .build())
                )
                .build();
    }

    public static ApiErrorResponse withDefaultApiErrorResponse() {
        List<ErrorDetailResponse> list = new ArrayList<>();
        ErrorDetailResponse errorDetailResponse = ErrorDetailResponse.builder()
                .code(DPFMiddlewareError.MDWDPF_002.getCodeMiddleware())
                .description("Sarasa")
                .build();

        list.add(errorDetailResponse);

        return ApiErrorResponse.builder()
                .errorDetailResponse(list)
                .build();
    }
}