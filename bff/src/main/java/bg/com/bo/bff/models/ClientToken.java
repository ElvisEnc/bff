package bg.com.bo.bff.models;

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
