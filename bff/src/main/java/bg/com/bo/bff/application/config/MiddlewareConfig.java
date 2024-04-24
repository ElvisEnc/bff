package bg.com.bo.bff.application.config;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class MiddlewareConfig {
    private String urlBase;

    private String tokenPath;

    private String clientLogin;

    private String clientOwnManager;

    private String clientTransfer;

    private String clientThirdAccount;

    private String clientAchAccount;
}
