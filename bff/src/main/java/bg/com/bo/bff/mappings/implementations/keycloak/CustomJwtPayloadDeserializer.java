package bg.com.bo.bff.mappings.implementations.keycloak;

import bg.com.bo.bff.models.jwt.JwtPayload;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import io.jsonwebtoken.io.DeserializationException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.ArrayList;

class CustomJwtPayloadDeserializer extends StdDeserializer<JwtPayload> {
    private static final Logger logger = LogManager.getLogger(CustomJwtPayloadDeserializer.class.getName());

    protected CustomJwtPayloadDeserializer() {
        this(null);
    }

    protected CustomJwtPayloadDeserializer(Class<?> vc) {
        super(vc);
    }


    @Override
    public JwtPayload deserialize(JsonParser parser, DeserializationContext deserializationContext) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();

        ObjectCodec codec = parser.getCodec();
        JsonNode node = codec.readTree(parser);

        JwtPayload payload = objectMapper.readValue(node.toString(), JwtPayload.class);

        try {
            JsonNode authorization = node.get("authorization");
            JsonNode permissions = authorization.get("permissions");
            JsonNode value = permissions.get(0);
            JsonNode claims = value.get("claims");

            JsonNode personIds = claims.get("personId");
            payload.setPersonId(personIds.get(0).asText());

            JsonNode sids = claims.get("sid");
            payload.setSid(sids.get(0).asText());

            JsonNode rolesAsNodes = claims.get("roles");
            ArrayList<String> roles = new ArrayList<>();
            for (JsonNode jsonNode: rolesAsNodes){
                roles.add(jsonNode.asText());
            }
            payload.setRoles(roles);

            return payload;
        } catch (Exception e) {
            String message = String.format("Hubo un error al deserealizar con el deserializer %s.", this.getClass().getName());
            logger.error(message);
            logger.error(e);
            throw new DeserializationException(message);
        }
    }
}
