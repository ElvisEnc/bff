package bg.com.bo.bff.application.dtos.response.user;

public class MaritalStatusFixture {
    public static MaritalStatus withDefault() {
        return MaritalStatus.builder()
                .id("1")
                .description("casado")
                .build();
    }
}