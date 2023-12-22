package bg.com.bo.bff.model;

@lombok.Data
public class ClientToken {
           String accessToken;
           String tokenType;
           Integer expiresIn;
           String scope;
           String jti;
}
