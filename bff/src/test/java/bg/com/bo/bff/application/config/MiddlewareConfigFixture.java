package bg.com.bo.bff.application.config;

public class MiddlewareConfigFixture {

    public static MiddlewareConfig withDefault() {
        return MiddlewareConfig.builder()
                .clientTransfer("1231212321")
                .urlBase("http://url")
                .tokenPath("/path")
                .build();
    }
}