package bg.com.bo.bff.models.jwt;

import java.io.Serializable;

@lombok.Data
@lombok.NoArgsConstructor
public class JwtKey implements Serializable {
    private String id;
    private String keyType;
    private String algorithm;
    private String publicKeyUse;
    private String rsaPublicModulus;
    private String rsaPublicExponent;
}
