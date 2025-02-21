package bg.com.bo.bff.providers.models.enums.middleware.payment.services;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum PaymentServicesMiddlewareServices {
    GET_SUBCATEGORIES("/bs/v1/categories/%s/sub-categories"),
    GET_CATEGORIES("/bs/v1/categories"),
    GET_SUBCATEGORY_CITIES("/bs/v1/sub-categories/%s/cities"),
    GET_AFFILIATIONS_SERVICES("/bs/v1/affiliations-services/persons/%d"),
    DEBTS("/bs/v1/debts/consultation"),
    POST_PAYMENTS_DEBTS("/bs/v1/payments/debts"),
    GET_SERVICES("/bs/v1/sub-categories/%s/cities/%s"),
    POST_SERVICE_AFFILIATION("/bs/v1/affiliations-services"),
    DELETE_AFFILIATE_SERVICE("/bs/v1/affiliations/delete"),
    GET_AFFILIATE_CRITERIA("/bs/v1/search-criteria?serviceCode=%s"),
    VALIDATE_AFFILIATE_CRITERIA("/bs/v1/affiliations/validate"),
    GET_LIST_SERVICES("/bs/v1/services");
    private final String serviceURL;
}

