package bg.com.bo.bff.model.jwt;

@lombok.Data
public class JwtAccess {
    private JwtHeader header;
    private JwtPayload payload;
}
