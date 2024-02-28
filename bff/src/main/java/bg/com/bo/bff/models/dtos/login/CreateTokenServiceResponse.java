package bg.com.bo.bff.models.dtos.login;

import bg.com.bo.bff.models.dtos.TokenDataServiceResponse;

@lombok.Data
@lombok.NoArgsConstructor
public class CreateTokenServiceResponse {
    private TokenDataServiceResponse tokenData;
    private StatusCode statusCode;

    public enum StatusCode {
        FAILED, INVALID_DATA, SUCCESS
    }
}
