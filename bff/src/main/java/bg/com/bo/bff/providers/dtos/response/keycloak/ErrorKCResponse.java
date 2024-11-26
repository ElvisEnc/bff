package bg.com.bo.bff.providers.dtos.response.keycloak;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.http.HttpStatus;

@lombok.Data
@lombok.Builder
@lombok.AllArgsConstructor
@lombok.NoArgsConstructor
public class ErrorKCResponse {

    private String error;
    @JsonProperty("error_description")
    private String errorDescription;

    @lombok.Getter
    @lombok.AllArgsConstructor
    public enum Error {
        UNAUTHORIZED_CLIENT("unauthorized_client", HttpStatus.UNAUTHORIZED),
        INVALID_CLIENT("invalid_client", HttpStatus.UNAUTHORIZED),
        INVALID_REQUEST("invalid_request", HttpStatus.BAD_REQUEST),
        UNSUPPORTED_GRANT_TYPE("unsupported_grant_type", HttpStatus.BAD_REQUEST),
        INVALID_GRANT("invalid_grant", HttpStatus.BAD_REQUEST),
        NOT_FOUND_404("not_found", HttpStatus.NOT_FOUND),
        NOT_AVAILABLE("not_available", HttpStatus.SERVICE_UNAVAILABLE);

        private final String code;
        private final HttpStatus statusCode;
    }
}
