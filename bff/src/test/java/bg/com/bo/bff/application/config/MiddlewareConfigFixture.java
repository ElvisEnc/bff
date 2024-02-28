package bg.com.bo.bff.application.config;

import bg.com.bo.bff.application.config.MiddlewareConfig;

public class MiddlewareConfigFixture {

    public static MiddlewareConfig withDefault() {
        return MiddlewareConfig.builder()
                .client_transfer("1231212321")
                .url_base("https://url")
                .token_path("/path")
                .build();
    }
}