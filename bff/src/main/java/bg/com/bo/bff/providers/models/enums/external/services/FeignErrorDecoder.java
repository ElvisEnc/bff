package bg.com.bo.bff.providers.models.enums.external.services;

import feign.Response;
import feign.Util;
import feign.codec.ErrorDecoder;
import lombok.extern.slf4j.Slf4j;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import bg.com.bo.bff.application.exceptions.GenericException;
import bg.com.bo.bff.providers.models.external.services.interfaces.IExternalError;

import java.io.IOException;
import java.util.Map;

@Slf4j
public class FeignErrorDecoder<T extends Enum<T> & IExternalError> implements ErrorDecoder {

    private final Class<T> errorEnumClass;
    private final ObjectMapper objectMapper;

    public FeignErrorDecoder(Class<T> errorEnumClass, ObjectMapper objectMapper) {
        this.errorEnumClass = errorEnumClass;
        this.objectMapper = objectMapper;
    }

    @Override
    public Exception decode(String methodKey, Response response) {
        try {
            if (response.body() != null) {
                String body = Util.toString(response.body().asReader());
                Map<?, ?> errorMap = objectMapper.readValue(body, Map.class);

                if (errorMap.containsKey("errorDetailResponse")) {
                    Object[] details = ((Object[]) ((Map<?, ?>) errorMap).get("errorDetailResponse"));
                    if (details.length > 0) {
                        Map<?, ?> errorDetail = (Map<?, ?>) details[0];
                        String code = (String) errorDetail.get("code");

                        for (T error : errorEnumClass.getEnumConstants()) {
                            if (error.getCode().equalsIgnoreCase(code)) {
                                return new GenericException(error);
                            }
                        }
                    }
                }
            }
        } catch (IOException e) {
            log.error("Error decoding response for method {}: {}", methodKey, e.getMessage());
        }

        return new GenericException("Error desconocido al invocar servicio externo", HttpStatus.INTERNAL_SERVER_ERROR);
    }
}