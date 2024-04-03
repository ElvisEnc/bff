package bg.com.bo.bff.models.jwt;

@lombok.Data
public class JwtRefresh {
    private JwtHeader header;
    private JwtPayload payload;
}
