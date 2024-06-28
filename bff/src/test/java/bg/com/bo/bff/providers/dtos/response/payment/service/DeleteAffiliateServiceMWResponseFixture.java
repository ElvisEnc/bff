package bg.com.bo.bff.providers.dtos.response.payment.service;

public class DeleteAffiliateServiceMWResponseFixture {
    public static DeleteAffiliateServiceMWResponse withDefault(){
        return new DeleteAffiliateServiceMWResponse(
                new DeleteAffiliateServiceMWResponse.Response("1234")
        );
    }
}
