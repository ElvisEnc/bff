package bg.com.bo.bff.providers.dtos.request.loyalty;

import bg.com.bo.bff.application.dtos.request.loyalty.RegisterRedeemVoucherRequest;
import bg.com.bo.bff.application.dtos.request.loyalty.RegisterSubscriptionRequest;

import java.util.List;

public class LoyaltySERequestFixture {

    public static RegisterSubscriptionRequest withDefaultRegisterSubscription() {
        return RegisterSubscriptionRequest.builder()
                .email("test@test.com")
                .build();
    }

    public static LoyaltyRegisterSubscriptionRequest withDefaultRegisterSubscriptionSE() {
        return LoyaltyRegisterSubscriptionRequest.builder()
                .signatureDigital(true)
                .idPerson("123")
                .codeCampaign("1")
                .jtsOidAccountNumber("1234")
                .email("test@test.com")
                .subscriptionOrigin("GANAMOVIL")
                .build();
    }

    public static LoyaltyRegisterRedeemVoucherRequest withDefaultRegisterRedeemVoucherSE() {
        return LoyaltyRegisterRedeemVoucherRequest.builder()
                .codigoCampana("campana001")
                .idCodigoSistema("sistema123")
                .idBeneficio("beneficio456")
                .tipoBeneficio("TIPO1")
                .informacion(LoyaltyRegisterRedeemVoucherRequest.Information.builder()
                        .nombreBeneficiario("Juan Pérez")
                        .documentoBeneficiario("12345678")
                        .parentesco("Hijo")
                        .build())
                .build();
    }

    public static RegisterRedeemVoucherRequest withDefaultRegisterRedeemVoucher() {
        return RegisterRedeemVoucherRequest.builder()
                .idBenefit("BENEF-001")
                .typeBenefit("DESCUENTO")
                .information(RegisterRedeemVoucherRequest.InformationVoucher.builder()
                        .beneficiaryName("Juan Pérez")
                        .beneficiaryDocument("12345678")
                        .beneficiaryRelationship("Hijo")
                        .build())
                .build();
    }

    public static RegisterRedeemVoucherRequest withDefaultRegisterRedeemVoucherNull() {
        return RegisterRedeemVoucherRequest.builder()
                .idBenefit("BENEF-002")
                .typeBenefit("BONO")
                .information(null)
                .build();
    }

    public static LoyaltyStatementPointRequest withDefaultStatementPoint() {
        return LoyaltyStatementPointRequest.builder()
                .codigoPersona("2")
                .codigoCampana("1")
                .fechaInicial("2023-09-10")
                .fechaFinal("2023-09-10")
                .build();
    }

    public static LoyaltyGetImagesRequest  withDefaultImage() {
        return LoyaltyGetImagesRequest.builder()
                .rutas(List.of(
                        LoyaltyGetImagesRequest.Ruta.builder()
                                .filePath("https://example.com/default-image.jpg")
                                .build()
                ))
                .build();
    }

    public static LoyaltyPersonCampRequest  withDefaultPersonCamp() {
        return LoyaltyPersonCampRequest.builder()
                .idPersona("123")
                .codigoCampana("1")
                .build();
    }


}
