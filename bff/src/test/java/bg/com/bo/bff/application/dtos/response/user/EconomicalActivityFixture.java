package bg.com.bo.bff.application.dtos.response.user;

public class EconomicalActivityFixture {
    public static EconomicalActivity withDefault() {
        return EconomicalActivity.builder()
                .id("123")
                .description("Description")
                .build();
    }

    public static EconomicalActivity economicActivity() {
        return EconomicalActivity.builder()
                .id("1401")
                .description("Agricultura/Ganaderia")
                .build();
    }

    public static EconomicalActivity incomeLevel() {
        return EconomicalActivity.builder()
                .id("1")
                .description("MENOS DE $600")
                .build();
    }
    public static EconomicalActivity incomeSource() {
        return EconomicalActivity.builder()
                .id("D")
                .description("Dependiente")
                .build();
    }

    public static EconomicalActivity jobTitle() {
        return EconomicalActivity.builder()
                .id("AR1")
                .description("GERENTE")
                .build();
    }
}
