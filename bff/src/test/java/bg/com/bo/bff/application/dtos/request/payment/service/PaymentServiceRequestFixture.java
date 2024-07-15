package bg.com.bo.bff.application.dtos.request.payment.service;

import java.util.List;

public class PaymentServiceRequestFixture {
    public static ValidateAffiliateCriteriaRequest withDefaultValidateAffiliateCriteria() {
        return ValidateAffiliateCriteriaRequest.builder()
                .year(2023)
                .criteriaId("28")
                .criteriaIdAbbreviation("3")
                .searchFields(List.of(ValidateAffiliateCriteriaRequest.SearchField.builder()
                        .code("28")
                        .value("73166120")
                        .build()))
                .build();
    }
}
