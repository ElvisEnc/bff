package bg.com.bo.bff.providers.dtos.response.own.account.mw;

@lombok.Data
public class ClientMWToken {
    String accessToken;
    String tokenType;
    Integer expiresIn;
    String scope;
    String jti;
}
