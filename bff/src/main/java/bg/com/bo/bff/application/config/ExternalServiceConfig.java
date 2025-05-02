package bg.com.bo.bff.application.config;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ExternalServiceConfig {

    private String urlExternalBase;
}
