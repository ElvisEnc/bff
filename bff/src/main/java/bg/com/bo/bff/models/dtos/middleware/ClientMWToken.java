package bg.com.bo.bff.models.dtos.middleware;

@lombok.Data
public class ClientMWToken {
    String accessToken;
    String tokenType;
    Integer expiresIn;
    String scope;
    String jti;
}
