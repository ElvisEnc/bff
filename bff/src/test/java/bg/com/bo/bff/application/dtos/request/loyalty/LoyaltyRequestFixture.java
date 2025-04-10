package bg.com.bo.bff.application.dtos.request.loyalty;


public class LoyaltyRequestFixture {

    public static RegisterSubscriptionRequest withDefaultRegisterSubscription() {
        return RegisterSubscriptionRequest.builder()
                .email("test@test.com")
                .build();
    }

    public static RegisterRedeemVoucherRequest withDefaultRegisterRedeemVoucher() {
        return RegisterRedeemVoucherRequest.builder()
                .idBenefit("defaultBenefitId")
                .typeBenefit("CONSUMO")
                .information(RegisterRedeemVoucherRequest.InformationVoucher.builder()
                        .beneficiaryName("John Doe")
                        .beneficiaryDocument("12345678")
                        .beneficiaryRelationship("Familiar")
                        .build())
                .build();
    }

}
