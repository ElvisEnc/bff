package bg.com.bo.bff.application.dtos.response.loyalty;

import bg.com.bo.bff.application.dtos.response.generic.GenericResponse;
import bg.com.bo.bff.providers.dtos.response.loyalty.LoyaltySystemCodeResponse;

import java.math.BigDecimal;
import java.util.Collections;
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

    public static LoyaltyTradeCategoryResponse withDefaultLoyaltyCategory() {
        return LoyaltyTradeCategoryResponse.builder()
                .categoryId("codeVoucher")
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
                .category(withDefaultLoyaltyCategory())
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
                        .category(withDefaultLoyaltyCategory())
                        .isActive(1)
                        .cheaper(1)
                        .build())
                .voucherConsumption(LoyaltyVoucherConsumptionResponse.builder()
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
                .category(withDefaultLoyaltyCategory())
                .isActive(1)
                .cheaper(0)
                .build());

        response.setVoucherConsumption(LoyaltyVoucherConsumptionResponse.builder()
                .valueVoucher("1000")
                .valueType("Bs")
                .build());

        return response;
    }

    public static LoyaltyTradeCategoryListResponse withDefaultTradeCategories() {

        return LoyaltyTradeCategoryListResponse.builder().data(
                Collections.singletonList(LoyaltyTradeCategoryResponse
                        .builder()
                        .nameCategory("BIMBO")
                        .iconCategory("MyIcon")
                        .categoryId("123").build()
                )
        ).build();
    }

    public static LoyaltyFeaturedMerchantListResponse withDefaultGetFeaturedMerchants() {
        return LoyaltyFeaturedMerchantListResponse.builder()
                .data(
                        Collections.singletonList(
                                LoyaltyFeaturedMerchant.builder()
                                        .identifier("123123")
                                        .name("El Solar")
                                        .description("Negocio el Solar.")
                                        .logo("Logo el Solar")
                                        .cheapest(420)
                                        .category(
                                                LoyaltyTradeCategoryResponse.builder()
                                                        .categoryId("123123")
                                                        .nameCategory("Restaurantes")
                                                        .iconCategory("Icono Restaurante").build()

                                        ).build()
                        )
                )
                .build();
    }

    public static LoyaltyCityListResponse withDefaultGetCityList() {
        return LoyaltyCityListResponse.builder()
                .data(
                        Collections.singletonList(
                                LoyaltyCityListResponse.LoyaltyCity.builder()
                                        .name("SantaCruz")
                                        .identifier("123123")
                                        .build()
                        )
                )
                .build();
    }

    public static LoyaltyQrTransactionResponse withDefaultGetVoucherDetail() {
        return LoyaltyQrTransactionResponse.builder()
                .identifier("123456")
                .voucherCode("COD123456")
                .campignId("98765")
                .holderName("Juan Luis Guerra")
                .documentHolder("12345678")
                .recipientName("Mario Villa")
                .recipientDocument("87654321")
                .personId("12312313")
                .personCode("PERSON-2025")
                .registerDate("2024-04-01")
                .redemptionDate("2024-04-10")
                .redemptionValue(5000)
                .expirationDate("2024-12-31")
                .benefitId("BENEFICIO-001")
                .voucherName("Vale de descuento")
                .description("Este vale otorga un 50% de descuento en productos seleccionados.")
                .banner("Banner exclusivo.")
                .note("Usar antes de fin de 2026.")
                .voucherType("DESCUENTO")
                .voucherStatus("ACTIVO")
                .merchant(LoyaltyFeaturedMerchant.builder()
                        .identifier("GANADERO")
                        .name("BANCO BGA")
                        .description("Comercio dedicado a la banca.")
                        .logo("Logo BGA")
                        .cheapest(1500)
                        .category(LoyaltyTradeCategoryResponse.builder()
                                .categoryId("CAT-001")
                                .nameCategory("Banca privada")
                                .iconCategory("icono BGA")
                                .build()
                        )
                        .build())
                .travelVoucher(LoyaltyQrTransactionResponse.TravelVoucher.builder()
                        .relationship("Hijo")
                        .travelVoucherType("INTERPROVINCIAL")
                        .origin("Lima")
                        .destination("Cusco")
                        .build())
                .build();
    }

    public static LoyaltyMerchantVoucherCategoryResponse withDefaultGetMerchantCampaignVouchers() {
        return LoyaltyMerchantVoucherCategoryResponse.builder()
                .redemptionVoucher(new LoyaltyMerchantVoucherCategoryResponse.Voucher[]{
                        buildVoucher(
                                "Consumo-001",
                                "Vale de consumo",
                                "Descuento para consumo", "1000", "CONSUMO")
                })
                .travelVoucher(new LoyaltyMerchantVoucherCategoryResponse.Voucher[]{
                        buildVoucher(
                                "PASAJE-001",
                                "Vale de Pasaje",
                                "Pasaje interprovincial",
                                "2000",
                                "PASAJE")
                })
                .productVoucher(new LoyaltyMerchantVoucherCategoryResponse.Voucher[]{
                        buildVoucher(
                                "PRODUCTOS-001",
                                "Vale de Productos",
                                "Canje de producto", "3000",
                                "PRODUCTOS")
                })
                .discountVoucher(new LoyaltyMerchantVoucherCategoryResponse.Voucher[]{
                        buildVoucher(
                                "DESCUENTO-001", "Vale Descuento Generales",
                                "500 Bs en toda la tienda",
                                "500",
                                "DESCUENTO")
                })
                .build();
    }

    private static LoyaltyMerchantVoucherCategoryResponse.Voucher buildVoucher(
            String id, String name, String desc, String value, String type
    ) {
        return LoyaltyMerchantVoucherCategoryResponse.Voucher.builder()
                .identifier(id)
                .name(name)
                .description(desc)
                .redemptionValue(value)
                .typeVoucher(type)
                .banner("Banner de vouchers" + id.toLowerCase() + ".png")
                .merchantId("2312001")
                .build();
    }

    public static LoyaltyVoucherTransactedListResponse withDefaultGetVoucherTransactedList() {
        return LoyaltyVoucherTransactedListResponse.builder().data(
                    Collections.singletonList(LoyaltyRedeemVoucherResponse.builder()
                    .identifier("1231323")
                    .codeVoucher("Cod123")
                    .idCampaign("3221132")
                    .holderName("John Travolta")
                    .documentHolder("123456789")
                    .beneficiaryName("Jane Travolta")
                    .beneficiaryDocument("987654321")
                    .idPerson("12343")
                    .codePerson("COD12345")
                    .dateCreation("2025-04-22")
                    .dateVoucher("2025-04-23")
                    .valueVoucher(100.0)
                    .expirationDate("2025-12-31")
                    .idBenefit("4564545")
                    .name("Voucher para consumo")
                    .description("Este voucher es con proposito de consumo.")
                    .banner("Banner voucher")
                    .note("Solo valido en tiendas que participan.")
                    .status("ACTIVE")
                    .trade(LoyaltyRedeemVoucherResponse.LoyaltyTrade.builder()
                        .identifierTrade("12313245")
                        .nameTrade("DISMAC")
                        .descriptionTrade("Muy buena tienda para articulos originales")
                        .logoTrade("Logo Dismac")
                        .category(LoyaltyTradeCategoryResponse.builder()
                            .categoryId("31232")
                            .nameCategory("Electronica")
                            .iconCategory("Icono Electronica")
                            .build())
                        .build())
                    .typeValue("CONSUMMO")
                    .redeemVoucher(LoyaltyRedeemVoucherResponse.LoyaltyRedeemVoucher.builder()
                        .valueRedeemVoucher("100.0")
                        .typeValueRedeemVoucher("Dolares")
                        .build())
                    .build()))
            .build();
    }

}
