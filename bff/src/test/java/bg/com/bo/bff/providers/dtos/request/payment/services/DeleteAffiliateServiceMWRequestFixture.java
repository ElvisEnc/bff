package bg.com.bo.bff.providers.dtos.request.payment.services;

public class DeleteAffiliateServiceMWRequestFixture {
    public static DeleteAffiliateServiceMWRequest withDefault(){
        return DeleteAffiliateServiceMWRequest.builder()
                .personId("123456")
                .affiliationCode("1234")
                .build();
    }
}
