package bg.com.bo.bff.application.dtos.response.loyalty;

import bg.com.bo.bff.application.dtos.response.generic.GenericResponse;
import bg.com.bo.bff.providers.dtos.response.loyalty.LoyaltySystemCodeResponse;

public class LoyaltyResponseFixture {

    public static LoyaltySystemCodeResponse withDefaultSystemCode() {
        return LoyaltySystemCodeResponse.builder()
                .codeSystem(1)
                .build();
    }
    public static LoyaltySumPointResponse withDefaultSumPoint() {
        return LoyaltySumPointResponse.builder()
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

    public static LoyaltyLevel withDefaultLevel() {
        return LoyaltyLevel.builder()
                .identifier("default-id")
                .name("Nivel 1")
                .minimumScore(0)
                .maximumScore(100)
                .idCampaign("campaign-001")
                .idLevelNext("nivel-2")
                .build();
    }


}
