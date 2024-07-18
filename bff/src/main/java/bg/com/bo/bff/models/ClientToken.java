package bg.com.bo.bff.models;

import java.time.LocalDateTime;

@lombok.Data
public class ClientToken {
    String accessToken;
    String tokenType;
    Integer expiresIn;
    String scope;
    String jti;
    String requiredAt;
    String expiredAt;
}
