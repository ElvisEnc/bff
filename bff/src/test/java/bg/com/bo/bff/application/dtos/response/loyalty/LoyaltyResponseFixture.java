package bg.com.bo.bff.application.dtos.response.loyalty;

import bg.com.bo.bff.application.dtos.response.generic.GenericResponse;
import bg.com.bo.bff.providers.dtos.response.loyalty.LoyaltySystemCodeResponse;

import java.math.BigDecimal;
import java.util.List;

public class LoyaltyResponseFixture {

    public static LoyaltySystemCodeResponse withDefaultSystemCode() {
        return LoyaltySystemCodeResponse.builder()
                .codeSystem(1)
                .build();
    }
    public static LoyaltyPointResponse withDefaultSumPoint() {
        return LoyaltyPointResponse.builder()
                .points(1)
                .build();
    }

    public static GenericResponse withDefaultGeneric() {
        return GenericResponse.builder()
                .code("OK")
                .message("OK")
                .title("OK")
                .build();
    }

    public static LoyaltyRedeemVoucherResponse withDefaultRedeemVoucher() {
        return LoyaltyRedeemVoucherResponse.builder()
                .codeVoucher("codeVoucher")
                .idCampaign("idCampaign")
                .holderName("holderName")
                .documentHolder("documentHolder")
                .beneficiaryName("beneficiaryName")
                .beneficiaryDocument("beneficiaryDocument")
                .idPerson("idPerson")
                .codePerson("codePerson")
                .dateCreation("dateCreation")
                .dateVoucher("dateVoucher")
                .valueVoucher(100.0)
                .expirationDate("test")
                .idBenefit("test")
                .name("test")
                .description("test")
                .banner("test")
                .note("test")
                .status("test")
                .trade(withDefaultLoyaltyTrade())
                .typeValue("test")
                .redeemVoucher(withDefaultLoyaltyRedeemVoucher())
                .build();
    }

    public static LoyaltyRedeemVoucherResponse.LoyaltyTrade withDefaultLoyaltyTrade() {
        return LoyaltyRedeemVoucherResponse.LoyaltyTrade.builder()
                .identifierTrade("codeVoucher")
                .nameTrade("nameTrade")
                .descriptionTrade("holderName")
                .logoTrade("holderName")
                .category(withDefaultLoyaltyCategory())
                .build();
    }

    public static LoyaltyRedeemVoucherResponse.LoyaltyCategory withDefaultLoyaltyCategory() {
        return LoyaltyRedeemVoucherResponse.LoyaltyCategory.builder()
                .identifierCategory("codeVoucher")
                .nameCategory("nameTrade")
                .iconCategory("holderName")
                .build();
    }

    public static LoyaltyRedeemVoucherResponse.LoyaltyRedeemVoucher withDefaultLoyaltyRedeemVoucher() {
        return LoyaltyRedeemVoucherResponse.LoyaltyRedeemVoucher.builder()
                .valueRedeemVoucher("codeVoucher")
                .typeValueRedeemVoucher("nameTrade")
                .build();
    }

    public static LoyaltyLevelResponse withDefaultLevel() {
        return LoyaltyLevelResponse.builder()
                .identifier("default-id")
                .name("Nivel 1")
                .minimumScore(0)
                .maximumScore(100)
                .idCampaign("campaign-001")
                .idLevelNext("nivel-2")
                .build();
    }

    public static LoyaltyStatementResponse withDefaultStatement() {
        return LoyaltyStatementResponse.builder()
                .action("ACUMULACION")
                .comment("Compra en tienda física")
                .dateCreation("2025-04-10T15:30:00")
                .origin("TIENDA_FISICA")
                .campaignScore(150.0)
                .build();
    }

    public static LoyaltyGeneralInfoResponse withDefaultGeneralInfo() {
        return LoyaltyGeneralInfoResponse.builder()
                .codeSystem(2)
                .points(new BigDecimal("2915136"))
                .pointsPeriod(new BigDecimal("2996121"))
                .level(LoyaltyLevelResponse.builder()
                        .identifier("a5b374c7-f068-4ced-a68b-882b8080f850")
                        .name("Diamante")
                        .minimumScore(140001)
                        .maximumScore(0)
                        .idCampaign("700143bf-4b1c-4771-b367-273200e97668")
                        .idLevelNext(null)
                        .build())
                .levels(List.of(
                        LoyaltyLevelResponse.builder()
                                .identifier("c8676e5b-b4e4-4534-815c-520168cbb2de")
                                .name("Bronce")
                                .minimumScore(0)
                                .maximumScore(4000)
                                .idCampaign("700143bf-4b1c-4771-b367-273200e97668")
                                .idLevelNext("28b8f759-821b-4e47-a21b-0ed1de042088")
                                .build(),
                        LoyaltyLevelResponse.builder()
                                .identifier("28b8f759-821b-4e47-a21b-0ed1de042088")
                                .name("Plata")
                                .minimumScore(4001)
                                .maximumScore(40000)
                                .idCampaign("700143bf-4b1c-4771-b367-273200e97668")
                                .idLevelNext("a535e863-90ce-4d22-afba-06ecc332ad23")
                                .build()
                ))
                .build();
    }

    public static LoyaltyInitialPointsResponse withDefaultInitialPoints() {
        LoyaltyInitialPointsResponse response = new LoyaltyInitialPointsResponse();
        response.setPoints(1000);
        response.setDatePoints("2025-04-14");
        response.setPointsLoyalty(3500);
        return response;
    }

    public static LoyaltyImageResponse withDefaultImage() {
        return LoyaltyImageResponse.builder()
                .identifier(1)
                .idImageMongo("Nivel 1")
                .filename("test")
                .fileType("test")
                .fileContent("test")
                .pathImage("test")
                .build();
    }

    public static LoyaltyCategoryPromotionResponse withDefaultCategoryPromotion() {
        return LoyaltyCategoryPromotionResponse.builder()
                .identifier("1")
                .name("test")
                .text("test")
                .link("test")
                .routeImageThumbnail("test")
                .build();
    }

    public static LoyaltyTermsConditionsResponse withDefaultTermsConditions(){
        return LoyaltyTermsConditionsResponse.builder()
                .contractName("TÉRMINOS Y CONDICIONES PROGRAMA DE LEALTAD PERMANENTE")
                .contract("Es responsabilidad del cliente leer y aceptar los términos.")
                .informationPerson(
                        LoyaltyTermsConditionsResponse.PersonData.builder()
                                .documentNumber("0000000")
                                .documentType("CI")
                                .namePerson("Nombre Ejemplo")
                                .build()
                )
                .build();
    }

    public static LoyaltyPromotionResponse withDefaultPromotion() {
        return LoyaltyPromotionResponse.builder()
                .identifier("c1b2e134-6365-45de-a27e-2db697d8184f")
                .namePromotion("Promo Baby Corps")
                .text("Promoción Baby Corps")
                .link("https://store.babycorpbolivia.com/")
                .imagePath("buscarImagen/567")
                .image(
                        LoyaltyImageResponse.builder()
                                .identifier(567)
                                .idImageMongo(null)
                                .filename("BabyCorp")
                                .fileType("png")
                                .fileContent("iVBORw0KGg")
                                .pathImage("buscarImagen/567")
                                .build()
                )
                .build();
    }

    public static LoyaltyStoreFeaturedResponse withDefaultStoreFeatured() {
        return LoyaltyStoreFeaturedResponse.builder()
                .identifier("store123")
                .name("Comercio Ejemplo")
                .description("Un comercio destacado con excelentes productos.")
                .logo("https://example.com/logo.png")
                .isFeatured(1)
                .categoryId("cat001")
                .category(LoyaltyStoreFeaturedResponse.Category.builder()
                        .categoryId("cat001")
                        .nameCategory("Tecnología")
                        .iconCategory("https://example.com/icono-tecnologia.png")
                        .build())
                .isActive(1)
                .cheaper(0)
                .build();
    }

    public static LoyaltyGenericVoucherTransactionResponse withDefaultQrTransactions() {
        return LoyaltyGenericVoucherTransactionResponse.builder()
                .identifier("VCHR-001")
                .voucherCode("ABCD1234")
                .campaignId("CMP-2025")
                .holderName("Juan Pérez")
                .holderDocument("12345678")
                .beneficiaryName("Ana López")
                .beneficiaryDocument("87654321")
                .personId("PERS-1001")
                .personCode("CDE-9001")
                .creationDate("2025-04-01T10:00:00Z")
                .redemptionDate("2025-04-10T15:30:00Z")
                .redemptionValue(500)
                .expirationDate("2025-05-01T23:59:59Z")
                .benefitId("BEN-5001")
                .name("Vale de Consumo Abril")
                .description("Vale aplicable a comercios afiliados en el mes de abril.")
                .banner("https://example.com/banner.png")
                .note("Canje válido solo en tiendas físicas.")
                .status("REDEEMED")
                .voucherType("consumo")
                .store(LoyaltyStoreFeaturedResponse.builder()
                        .identifier("STR-001")
                        .name("Supermercado Ejemplo")
                        .description("Supermercado con productos variados")
                        .logo("https://example.com/logo-super.png")
                        .isFeatured(1)
                        .categoryId("CAT-01")
                        .category(LoyaltyStoreFeaturedResponse.Category.builder()
                                .categoryId("CAT-01")
                                .nameCategory("Alimentos")
                                .iconCategory("https://example.com/icon-alimentos.png")
                                .build())
                        .isActive(1)
                        .cheaper(1)
                        .build())
                .voucherConsumption(LoyaltyGenericVoucherTransactionResponse.VoucherConsumption.builder()
                        .valueVoucher("500")
                        .valueType("Bs")
                        .build())
                .build();
    }

    public static LoyaltyVoucherTransactionsResponse withDefaultVoucherTransactions() {
        LoyaltyVoucherTransactionsResponse response = new LoyaltyVoucherTransactionsResponse();

        response.setIdentifier("VCH-2025-0001");
        response.setVoucherCode("VCH-CONS-9821");
        response.setCampaignId("CAMP-APR-2025");
        response.setHolderName("Carlos Mendoza");
        response.setHolderDocument("100200300");
        response.setBeneficiaryName("Lucía Mendoza");
        response.setBeneficiaryDocument("200300400");
        response.setPersonId("PER-1025");
        response.setPersonCode("USR-789");
        response.setCreationDate("2025-04-01T09:00:00Z");
        response.setRedemptionValue(1000);
        response.setExpirationDate("2025-06-01T23:59:59Z");
        response.setBenefitId("BENEF-0425");
        response.setName("Vale de Consumo Hogar");
        response.setDescription("Vale canjeable en productos del hogar en tiendas afiliadas.");
        response.setBanner("https://cdn.example.com/vouchers/banner-hogar.png");
        response.setRedeemed(1);
        response.setRedemptionDate("2025-04-15T14:20:00Z");
        response.setManagerId("MG-309");
        response.setVoucherCost(500);
        response.setVoucherType("CONSUMO");
        response.setAssumedPercentage(70);
        response.setNote("Uso exclusivo en locales físicos");
        response.setStatus("REDEEMED");

        response.setStore(LoyaltyStoreFeaturedResponse.builder()
                .identifier("STORE-456")
                .name("Casa & Hogar")
                .description("Tienda especializada en artículos para el hogar")
                .logo("https://cdn.example.com/logos/casayhogar.png")
                .isFeatured(1)
                .categoryId("CAT-HOME")
                .category(LoyaltyStoreFeaturedResponse.Category.builder()
                        .categoryId("CAT-HOME")
                        .nameCategory("Hogar")
                        .iconCategory("https://cdn.example.com/icons/hogar.png")
                        .build())
                .isActive(1)
                .cheaper(0)
                .build());

        response.setVoucherConsumption(LoyaltyGenericVoucherTransactionResponse.VoucherConsumption.builder()
                .valueVoucher("1000")
                .valueType("Bs")
                .build());

        return response;
    }

}
