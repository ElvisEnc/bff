package bg.com.bo.bff.model.dtos.login;

import bg.com.bo.bff.model.dtos.TokenDataServiceResponse;

@lombok.Data
@lombok.NoArgsConstructor
public class CreateTokenServiceResponse {
    private TokenDataServiceResponse tokenData;
    private StatusCode statusCode;

    public enum StatusCode {
        FAILED, INVALID_DATA, SUCCESS
    }
}
