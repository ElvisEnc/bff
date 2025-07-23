package bg.com.bo.bff.providers.dtos.response.jwt;

@lombok.Data
public class JwtAccess {
    private JwtHeader header;
    private JwtPayload payload;
}
