package bg.com.bo.bff.application.dtos.request.loyalty;


import java.util.List;

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

    public static LoyaltyStatementRequest withDefaultStatement() {
        return LoyaltyStatementRequest.builder()
                .startDate("2021-09-10")
                .endDate("2023-09-10")
                .build();
    }

    public static LoyaltyImageRequest withDefaultImage() {
        return LoyaltyImageRequest.builder()
                .filePaths(List.of(
                        LoyaltyImageRequest.FilePath.builder()
                                .file("default/image/path.jpg")
                                .build()
                ))
                .build();
    }


}
