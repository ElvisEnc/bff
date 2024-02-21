package bg.com.bo.bff.config;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class MiddlewareConfig {
    private String url_base;

    private String token_path;

    private String client_transfer;
}
