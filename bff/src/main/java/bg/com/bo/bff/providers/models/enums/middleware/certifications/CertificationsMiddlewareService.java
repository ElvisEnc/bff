package bg.com.bo.bff.providers.models.enums.middleware.certifications;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CertificationsMiddlewareService {

    GET_CERTIFICATIONS_TYPE("/bs/v1/certs-types/persons/%s/application/%s");

    private final String serviceURL;
}
