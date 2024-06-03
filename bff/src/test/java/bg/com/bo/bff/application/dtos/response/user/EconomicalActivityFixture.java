package bg.com.bo.bff.application.dtos.response.user;

public class EconomicalActivityFixture {
    public static EconomicalActivity withDefault() {
        return EconomicalActivity.builder()
                .id("123")
                .description("Description")
                .build();
    }
}