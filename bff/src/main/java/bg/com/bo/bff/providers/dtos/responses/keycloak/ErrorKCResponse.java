package bg.com.bo.bff.providers.dtos.responses.keycloak;

import com.fasterxml.jackson.annotation.JsonProperty;

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
        UNAUTHORIZED_CLIENT("unauthorized_client"),
        INVALID_CLIENT("invalid_client"),
        INVALID_REQUEST("invalid_request"),
        UNSUPPORTED_GRANT_TYPE("unsupported_grant_type"),
        INVALID_GRANT("invalid_grant"),
        NOT_FOUND_404("not_found");

        private final String code;
    }
}
