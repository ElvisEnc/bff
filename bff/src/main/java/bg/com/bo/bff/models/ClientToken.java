package bg.com.bo.bff.models;

import java.util.Date;

@lombok.Data
public class ClientToken {
    String accessToken;
    String tokenType;
    Integer expiresIn;
    String scope;
    String jti;
    Date requiredAt;
}
