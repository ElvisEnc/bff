package bg.com.bo.bff.models.jwt;

@lombok.Data
public class JwtAccess {
    private JwtHeader header;
    private JwtPayload payload;
}
