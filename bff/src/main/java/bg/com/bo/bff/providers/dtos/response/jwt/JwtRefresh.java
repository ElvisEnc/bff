package bg.com.bo.bff.providers.dtos.response.jwt;

@lombok.Data
public class JwtRefresh {
    private JwtHeader header;
    private JwtPayload payload;
}
