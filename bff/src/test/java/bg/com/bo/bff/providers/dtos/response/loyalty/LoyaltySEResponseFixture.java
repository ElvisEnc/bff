package bg.com.bo.bff.providers.dtos.response.loyalty;


public class LoyaltySEResponseFixture {

    public static LoyaltySystemCodeServerResponse withDefaultSystemCode() {
        return LoyaltySystemCodeServerResponse.builder()
                .codeSystem(1)
                .build();
    }

    public static LoyaltySumPointServerResponse withDefaultSumPoint() {
        return LoyaltySumPointServerResponse.builder()
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

}
