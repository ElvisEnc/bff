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
    GET_DEBTS("/bs/v1/payment/debts"),
    GET_SERVICES("/bs/v1/sub-categories/%s/cities/%s"),
    DELETE_AFFILIATE_SERVICE("/bs/v1/affiliations/delete"),
    GET_AFFILIATE_CRITERIA("/bs/v2/search-criteria?serviceCode=%s");
    private final String serviceURL;
}

