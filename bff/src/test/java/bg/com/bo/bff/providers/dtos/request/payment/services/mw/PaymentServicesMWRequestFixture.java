package bg.com.bo.bff.providers.dtos.request.payment.services.mw;

import java.util.List;

public class PaymentServicesMWRequestFixture {
    public static DeleteAffiliateServiceMWRequest withDefaultDeleteAffiliateServiceMWRequest(){
        return DeleteAffiliateServiceMWRequest.builder()
                .personId("123456")
                .affiliationCode("1234")
                .build();
    }

    public static ValidateAffiliateCriteriaMWRequest withDefaultValidateAffiliateCriteria(){
        return ValidateAffiliateCriteriaMWRequest.builder()
                .serviceCode(1234)
                .year(2021)
                .searchCriteriaId("123456")
                .searchCriteriaIdAbbreviation("1234")
                .personId(123456)
                .searchFields(
                        List.of(
                                ValidateAffiliateCriteriaMWRequest.SearchField.builder()
                                        .code("28")
                                        .value("123456")
                                        .build()
                        )
                )
                .build();
    }
}
