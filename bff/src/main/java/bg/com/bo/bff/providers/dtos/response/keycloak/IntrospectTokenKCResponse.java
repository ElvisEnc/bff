package bg.com.bo.bff.providers.dtos.response.keycloak;

@lombok.Getter
@lombok.Builder
@lombok.AllArgsConstructor
public class IntrospectTokenKCResponse {

    private Result result;
    private boolean active;

    private IntrospectTokenKCResponse() {
    }

    private IntrospectTokenKCResponse(Result result) {
        this.result = result;
    }

    public static IntrospectTokenKCResponse instance(Result result) {
        return new IntrospectTokenKCResponse(result);
    }

    @lombok.Getter
    @lombok.AllArgsConstructor
    public enum Result {
        SUCCESS,
        INVALID_TOKEN,
        EXPIRED_TOKEN
    }
}
