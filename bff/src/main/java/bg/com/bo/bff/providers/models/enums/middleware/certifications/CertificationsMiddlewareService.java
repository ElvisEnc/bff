package bg.com.bo.bff.providers.models.enums.middleware.certifications;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CertificationsMiddlewareService {

    GET_CERTIFICATIONS_TYPE("/bs/v1/certs-types/persons/%s/application/%s"),
    GET_ACCOUNTS("/bs/v1/accounts/persons/%s"),
    GET_PREFERRED_EXCHANGE_RATE("/bs/v1/pref-exchange/persons/%s"),
    GET_CERTIFICATION_HISTORY("/bs/v1/historic-certs/persons/%s"),
    GET_CONFIGS("/bs/v1/config"),
    GET_CERTIFICATION_PRICE("/bs/v1/certs-price"),
    SAVE_CERTIFICATE_REQUEST("/bs/v1/save-request")

    ;

    private final String serviceURL;
}
