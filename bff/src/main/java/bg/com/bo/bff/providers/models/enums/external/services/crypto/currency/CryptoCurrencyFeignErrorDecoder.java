package bg.com.bo.bff.providers.models.enums.external.services.crypto.currency;

import bg.com.bo.bff.application.exceptions.GenericException;
import bg.com.bo.bff.providers.dtos.response.generic.ApiErrorResponse;
import bg.com.bo.bff.providers.models.enums.external.services.loyalty.LoyaltyError;
import com.fasterxml.jackson.databind.ObjectMapper;
import feign.Response;
import feign.codec.ErrorDecoder;
import org.apache.commons.io.IOUtils;

import java.nio.charset.StandardCharsets;

public class CryptoCurrencyFeignErrorDecoder implements ErrorDecoder {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public Exception decode(String methodKey, Response response) {
        try {
            String json = IOUtils.toString(response.body().asInputStream(), StandardCharsets.UTF_8);

            ApiErrorResponse errorResponse = objectMapper.readValue(json, ApiErrorResponse.class);
            String message = errorResponse.getMensaje();

            CryptoCurrencyError mappedError = findLoyaltyErrorByCode(message);

            return new GenericException(mappedError); // o HandledException(mappedError)
        } catch (Exception e) {
            return new GenericException(CryptoCurrencyError.ERROR_EXCHANGE); // fallback
        }
    }

    private CryptoCurrencyError findLoyaltyErrorByCode(String message) {
        for (CryptoCurrencyError err : CryptoCurrencyError.values()) {
            if (err.getMsgError().equalsIgnoreCase(message)) {
                return err;
            }
        }
        return CryptoCurrencyError.ERROR_EXCHANGE;
    }
}
