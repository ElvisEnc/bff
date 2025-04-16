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




}
