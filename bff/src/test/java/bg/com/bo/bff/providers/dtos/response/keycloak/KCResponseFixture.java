package bg.com.bo.bff.providers.dtos.response.keycloak;

public class KCResponseFixture {
    public static IntrospectTokenKCResponse introspectWithActiveToken() {
        return IntrospectTokenKCResponse.builder()
                .active(true)
                .build();
    }

    public static IntrospectTokenKCResponse introspectWithInactiveToken() {
        return IntrospectTokenKCResponse.builder()
                .active(false)
                .build();
    }

    public static ErrorKCResponse errorWithInvalidRequest() {
        return ErrorKCResponse.builder()
                .error(ErrorKCResponse.Error.INVALID_REQUEST.getCode())
                .build();
    }
}
