package bg.com.bo.bff.providers.models.enums.middleware.payment.services;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum PaymentServicesMiddlewareServices {
    GET_SUBCATEGORIES("/bs/v1/categories/%s/sub-categories"),
    GET_SUBCATEGORY_CITIES("/bs/v1/sub-categories/%s/cities");
    private final String serviceURL;
}

