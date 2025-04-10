package bg.com.bo.bff.providers.models.enums.middleware.nps;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum NpsMiddlewareService {

    REGISTER_NPS("/bs/v1/register-nps?codPerson=%s&idDevice=%s"),
    RESPONSE_NPS("/bs/v1/response-nps");

    private final String serviceURL;
}
