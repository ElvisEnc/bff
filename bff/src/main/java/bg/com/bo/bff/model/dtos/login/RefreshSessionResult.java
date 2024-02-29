package bg.com.bo.bff.model.dtos.login;

import bg.com.bo.bff.model.exceptions.NotValidValueForFactoryException;

@lombok.Data
@lombok.NoArgsConstructor
public class RefreshSessionResult {
    private String accessToken;
    private String refreshToken;
    private StatusCode statusCode;

    public enum StatusCode {
        SUCCESS,
        INVALID_DATA
    }

    public static RefreshSessionResult create(StatusCode statusCode) {
        switch (statusCode) {
            case INVALID_DATA:
                RefreshSessionResult result = new RefreshSessionResult();
                result.setStatusCode(statusCode);
                return result;
            default:
                throw new NotValidValueForFactoryException("Valor no valido para la creación de RefreshSessionResult.");
        }
    }
}
