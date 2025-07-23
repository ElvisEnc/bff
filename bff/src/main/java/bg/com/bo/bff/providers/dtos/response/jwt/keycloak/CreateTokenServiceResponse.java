package bg.com.bo.bff.providers.dtos.response.jwt.keycloak;

import bg.com.bo.bff.models.TokenDataServiceResponse;

@lombok.Data
@lombok.NoArgsConstructor
public class CreateTokenServiceResponse {
    private TokenDataServiceResponse tokenData;
    private StatusCode statusCode;

    public enum StatusCode {
        FAILED, INVALID_DATA, SUCCESS
    }
}
