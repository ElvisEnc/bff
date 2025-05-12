package bg.com.bo.bff.models;

import com.fasterxml.jackson.annotation.JsonAlias;

@lombok.Data
public class ClientToken {
    @JsonAlias({"access_token", "accessToken"})
    String accessToken;
    @JsonAlias({"token_type", "tokenType"})
    String tokenType;
    @JsonAlias({"expires_in", "expiresIn"})
    Integer expiresIn;
    String scope;
    String jti;
    String requiredAt;
    String expiredAt;
}
