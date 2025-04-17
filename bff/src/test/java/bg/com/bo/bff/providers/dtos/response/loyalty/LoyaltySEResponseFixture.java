package bg.com.bo.bff.providers.dtos.response.loyalty;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class LoyaltySEResponseFixture {

    public static LoyaltySystemCodeServerResponse withDefaultSystemCode() {
        return LoyaltySystemCodeServerResponse.builder()
                .codeSystem(1)
                .build();
    }

    public static LoyaltyPointServerResponse withDefaultSumPoint() {
        return LoyaltyPointServerResponse.builder()
                .point(1)
                .build();
    }

    public static LoyaltyRegisterSubscriptionResponse withDefaultRegisterSubscription() {
        return LoyaltyRegisterSubscriptionResponse.builder()
                .code(201)
                .message("Suscripcion realizada correctamente.")
                .build();
    }

    public static LoyaltyRegisterSubscriptionResponse withDefaultRegisterSubscriptionExist() {
        return LoyaltyRegisterSubscriptionResponse.builder()
                .code(400)
                .message("La persona ya se encuentra inscrita a esta campaña.")
                .build();
    }

    public static LoyaltyRegisterSubscriptionResponse withDefaultRegisterSubscriptionExistEmail() {
        return LoyaltyRegisterSubscriptionResponse.builder()
                .code(400)
                .message("El email ya se encuentra registrado.")
                .build();
    }

    public static LoyaltyRegisterSubscriptionResponse withDefaultRegisterSubscriptionException() {
        return LoyaltyRegisterSubscriptionResponse.builder()
                .code(406)
                .message("Error")
                .build();
    }

    public static LoyaltyRegisterRedeemVoucherResponse withDefaultRegisterRedeemVoucher() {
        return LoyaltyRegisterRedeemVoucherResponse.builder()
                .identifier("123456")
                .codeVoucher("VALE-001")
                .idCampaign("CAMP-2025")
                .holderName("Carlos Mendoza")
                .documentHolder("98765432")
                .beneficiaryName("Ana López")
                .beneficiaryDocument("12345678")
                .idPerson("PERS-001")
                .codePerson("CODPERS-001")
                .dateCreation("2025-04-09T10:00:00")
                .dateVoucher("2025-04-10T12:00:00")
                .valueVoucher(100.0)
                .expirationDate("2025-12-31T23:59:59")
                .idBenefit("BENEF-001")
                .name("Vale de Consumo")
                .description("Vale válido para compras en comercio adherido")
                .banner("https://example.com/banner.jpg")
                .note("Usar antes de la fecha de expiración")
                .status("ACTIVO")
                .typeValue("CONSUMO")
                .trade(LoyaltyRegisterRedeemVoucherResponse.LoyaltyTrade.builder()
                        .identifierTrade("TRD-001")
                        .nameTrade("Supermercado El Ahorro")
                        .descriptionTrade("Cadena de supermercados")
                        .logoTrade("https://example.com/logo.png")
                        .category(LoyaltyRegisterRedeemVoucherResponse.LoyaltyCategory.builder()
                                .identifierCategory("CAT-01")
                                .nameCategory("Alimentos")
                                .iconCategory("https://example.com/icono.png")
                                .build())
                        .build())
                .redeemVoucher(LoyaltyRegisterRedeemVoucherResponse.LoyaltyRedeemVoucher.builder()
                        .valueRedeemVoucher("100")
                        .typeValueRedeemVoucher("PESOS")
                        .build())
                .build();
    }

    public static LoyaltyGetLevelResponse withDefaultLoyaltyGetLevel() {
        return LoyaltyGetLevelResponse.builder()
                .identifier("LEVEL001")
                .name("Bronce")
                .minimumScore(0)
                .maximumScore(9)
                .idCampaign("CAMP-2025")
                .idLevelNext("LEVEL-002")
                .build();
    }

    public static LoyaltyGetInitialPointsVamosResponse withDefaultInitialPoints() {
        return LoyaltyGetInitialPointsVamosResponse.builder()
                .pointsVamos(0)
                .datePointsVamos("13/04/2025")
                .pointsLoyalty(0)
                .build();
    }

    public static LoyaltyStatusResponse withDefaultSubscription() {
        return LoyaltyStatusResponse.builder()
                .status(true)
                .build();
    }

    public static LoyaltyStatusResponse withDefaultSubscriptionFalse() {
        return LoyaltyStatusResponse.builder()
                .status(false)
                .build();
    }

    public static List<LoyaltyStatementPointsResponse> withDefaultStatementPoints() {
        return List.of(
                LoyaltyStatementPointsResponse.builder()
                        .action("Carga")
                        .comment("Carga de puntos por compra")
                        .dateCreation("2025-04-10")
                        .origin("POS - Tienda 01")
                        .campaignScore(150.0)
                        .build(),
                LoyaltyStatementPointsResponse.builder()
                        .action("Ajuste")
                        .comment("Ajuste manual de puntos")
                        .dateCreation("2025-04-12")
                        .origin("Atención al cliente")
                        .campaignScore(50.0)
                        .build()
        );
    }

    public static LoyaltyGeneralInformationResponse withDefaultGeneralInformationData() {
        return LoyaltyGeneralInformationResponse.builder()
                .codeSystem(1)
                .level(LoyaltyGetLevelResponse.builder()
                        .identifier("L1")
                        .name("Nivel 1")
                        .minimumScore(100)
                        .maximumScore(200)
                        .idCampaign("C1")
                        .idLevelNext("L2")
                        .build())
                .levels(Arrays.asList(
                        LoyaltyGetLevelResponse.builder()
                                .identifier("L2")
                                .name("Nivel 2")
                                .minimumScore(200)
                                .maximumScore(300)
                                .idCampaign("C2")
                                .idLevelNext("L3")
                                .build()
                ))
                .points(BigDecimal.valueOf(150))
                .pointsPeriod(BigDecimal.valueOf(50))
                .build();
    }

    public static LoyaltyGeneralInformationResponse withDefaultGeneralInformation() {
        return LoyaltyGeneralInformationResponse.builder()
                .codeSystem(0)
                .level(null)
                .levels(Collections.emptyList())
                .points(BigDecimal.ZERO)
                .pointsPeriod(BigDecimal.ZERO)
                .build();
    }

    public static LoyaltyGetImageResponse withDefaultImage() {
        return LoyaltyGetImageResponse.builder()
                .identifier(0)
                .idImageMongo("test")
                .filename("test")
                .fileType("test")
                .fileContent("test")
                .pathImage("test")
                .build();
    }

    public static LoyaltyGetImageResponse withDefaultImageData() {
        return LoyaltyGetImageResponse.builder()
                .identifier(123)
                .idImageMongo("mongo123")
                .filename("archivo.jpg")
                .fileType("jpg")
                .fileContent("contenido")
                .pathImage("ruta/imagen")
                .build();
    }

    public static LoyaltyGetCategoryPromotionResponse withDefaultCategoryPromotion() {
        return LoyaltyGetCategoryPromotionResponse.builder()
                .identifier("123")
                .name("test")
                .text("test")
                .link("test")
                .routeImageThumbnail("test")
                .build();
    }

    public static LoyaltyGetTermsConditionsResponse withDefaultTermsConditions() {
        return LoyaltyGetTermsConditionsResponse.builder()
                .contractName("Contrato Programa VAMOS")
                .contract("Este es el contenido del contrato del programa VAMOS...")
                .informationPerson(
                        LoyaltyGetTermsConditionsResponse.Person.builder()
                                .documentNumber("12345678")
                                .documentType("CI")
                                .namePerson("Juan Pérez")
                                .build()
                )
                .build();
    }

    public static LoyaltyGetPromotionResponse withDefaultPromotion() {
        return LoyaltyGetPromotionResponse.builder()
                .identifier("c1b2e1344f")
                .namePromotion("Promo")
                .text("Promoción")
                .link("https://store.babycorpbolivia.com/")
                .imagePath("test")
                .image(LoyaltyGetImageResponse.builder()
                        .identifier(156)
                        .idImageMongo(null)
                        .filename("test")
                        .fileType("png")
                        .fileContent("test")
                        .pathImage("buscarImagen/567")
                        .build())
                .build();
    }
    public static LoyaltyGetPromotionResponse withDefaultPromotionNull() {
        return LoyaltyGetPromotionResponse.builder()
                .identifier("abc123")
                .namePromotion("test")
                .text("test")
                .link("https://test.com")
                .imagePath("ruta/test")
                .image(null)
                .build();
    }





}
