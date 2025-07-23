package bg.com.bo.bff.providers.dtos.request.payment.services.mw;

public class ConceptsMWRequestFixture {

    public static ConceptsMWRequest withDefaults(){
        return ConceptsMWRequest.builder()
                .personId("54321")
                .affiliationCode("1234556")
                .serviceCode("123")
                .build();
    }

}
