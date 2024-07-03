package bg.com.bo.bff.providers.dtos.request.payment.services.mw;

public class PaymentServicesMWRequestFixture {
    public static DeleteAffiliateServiceMWRequest withDefaultDeleteAffiliateServiceMWRequest(){
        return DeleteAffiliateServiceMWRequest.builder()
                .personId("123456")
                .affiliationCode("1234")
                .build();
    }
}
