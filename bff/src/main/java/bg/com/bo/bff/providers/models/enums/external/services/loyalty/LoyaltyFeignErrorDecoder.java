package bg.com.bo.bff.providers.models.enums.external.services.loyalty;

import bg.com.bo.bff.application.exceptions.GenericException;
import bg.com.bo.bff.providers.dtos.response.generic.ApiErrorResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import feign.Response;
import feign.codec.ErrorDecoder;
import org.apache.commons.io.IOUtils;

import java.nio.charset.StandardCharsets;

public class LoyaltyFeignErrorDecoder implements ErrorDecoder {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public Exception decode(String methodKey, Response response) {
        try {
            String json = IOUtils.toString(response.body().asInputStream(), StandardCharsets.UTF_8);

            ApiErrorResponse errorResponse = objectMapper.readValue(json, ApiErrorResponse.class);
            String message = errorResponse.getMensaje();

            LoyaltyError mappedError = findLoyaltyErrorByCode(message);

            return new GenericException(mappedError); // o HandledException(mappedError)
        } catch (Exception e) {
            return new GenericException(LoyaltyError.GENERIC_ERROR); // fallback
        }
    }

    private LoyaltyError findLoyaltyErrorByCode(String message) {
        for (LoyaltyError err : LoyaltyError.values()) {
            if (err.getMsgError().equalsIgnoreCase(message)) {
                return err;
            }
        }
        return LoyaltyError.GENERIC_ERROR;
    }
}
