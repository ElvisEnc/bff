package bg.com.bo.bff.mappings.services.keycloak;

import bg.com.bo.bff.models.UserContact;
import bg.com.bo.bff.models.jwt.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import io.jsonwebtoken.Jwts;
import org.springframework.stereotype.Component;

import java.math.BigInteger;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.RSAPublicKeySpec;
import java.util.Base64;
import java.util.Map;

@Component
public class KeyCloakJsonMapper {

    public JwtHeader convertToJwtHeader(String json) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(json, JwtHeader.class);
    }

    /**
     * Convierte un JWT encodeado en un objeto JwtAccess. Internamente valida si esta correctamente firmado.
     *
     * @param token   el JWT en base64.
     * @param keyList key con la que fue firmado.
     * @return JwtAccess con los datos del token provisto.
     * @throws JsonProcessingException  si hubo un error al convertir del json desencodeado a las partes del Access JWT.
     * @throws NoSuchAlgorithmException si ningún Provider admite una implementación KeyFactorySpi para el algoritmo
     *                                  especificado.
     * @throws InvalidKeySpecException  si la especificación de la key dada no es apropiada para que el KeyFactory
     *                                  produzca una clave pública.
     */
    public JwtAccess convertToJwtAccess(String token, Map<String, JwtKey> keyList) throws JsonProcessingException, NoSuchAlgorithmException, InvalidKeySpecException {
        String[] chunks = token.split("\\.");

        String headerEncoded = chunks[0];
        String payloadEncoded = chunks[1];

        Base64.Decoder decoder = Base64.getUrlDecoder();

        String header = new String(decoder.decode(headerEncoded));
        String payload = new String(decoder.decode(payloadEncoded));

        JwtHeader jwtHeader = this.convertToJwtHeader(header);

        //Se obtiene la key con la que se firmo el token.
        JwtKey key = keyList.get(jwtHeader.getId());

        BigInteger modulus = new BigInteger(1, Base64.getUrlDecoder().decode(key.getRsaPublicModulus()));
        BigInteger exponent = new BigInteger(1, Base64.getUrlDecoder().decode(key.getRsaPublicExponent()));
        RSAPublicKeySpec keySpec = new RSAPublicKeySpec(modulus, exponent);
        KeyFactory kf = KeyFactory.getInstance(key.getKeyType());
        PublicKey pk = kf.generatePublic(keySpec);

        // Se valida que el token este firmado correctamente.
        Jwts.parser().verifyWith(pk).build().parse(token);

        JwtAccess jwtAccess = new JwtAccess();
        jwtAccess.setHeader(jwtHeader);
        jwtAccess.setPayload(this.convertToJwtPayload(payload));

        return jwtAccess;
    }

    /**
     * Convierte un JWT encodeado en un objeto JwtRefresh.
     *
     * @param token   el JWT en base64.
     * @return JwtAccess con los datos del token provisto.
     * @throws JsonProcessingException  si hubo un error al convertir del json desencodeado a las partes del Refresh JWT.
     */
    public JwtRefresh convertToJwtRefresh(String token) throws JsonProcessingException {
        String[] chunks = token.split("\\.");

        String headerEncoded = chunks[0];
        String payloadEncoded = chunks[1];

        Base64.Decoder decoder = Base64.getUrlDecoder();

        String header = new String(decoder.decode(headerEncoded));
        String payload = new String(decoder.decode(payloadEncoded));

        JwtHeader jwtHeader = this.convertToJwtHeader(header);

        JwtRefresh jwtRefresh = new JwtRefresh();
        jwtRefresh.setHeader(jwtHeader);
        jwtRefresh.setPayload(this.convertToJwtPayload(payload));

        return jwtRefresh;
    }

    private JwtPayload convertToJwtPayload(String json) throws JsonProcessingException {
        SimpleModule module = new SimpleModule();
        module.addDeserializer(JwtPayload.class, new CustomJwtPayloadDeserializer());
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(module);
        return mapper.readValue(json, JwtPayload.class);
    }
}
