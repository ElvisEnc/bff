package bg.com.bo.bff.providers.models.enums.external.services;

import feign.Response;
import feign.codec.ErrorDecoder;
import io.micrometer.core.instrument.util.IOUtils;
import lombok.extern.slf4j.Slf4j;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import bg.com.bo.bff.application.exceptions.GenericException;
import bg.com.bo.bff.providers.models.external.services.interfaces.IExternalError;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

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
            if (response.body() == null) {
                return defaultError();
            }
            String body = IOUtils.toString(response.body().asInputStream(), StandardCharsets.UTF_8);
            ExternalErrorResponse errorResponse = objectMapper.readValue(body, ExternalErrorResponse.class);
            if (errorResponse.getErrorDetailResponse() != null && !errorResponse.getErrorDetailResponse().isEmpty()) {
                String code = errorResponse.getErrorDetailResponse().get(0).getCode();

                for (T error : errorEnumClass.getEnumConstants()) {
                    if (error.getCode().equalsIgnoreCase(code)) {
                        return new GenericException(error);
                    }
                }
            }
        } catch (IOException e) {
            log.error("Error decoding response for method {}: {}", methodKey, e.getMessage());
        }
        return defaultError();
    }

    private GenericException defaultError() {
        return new GenericException("Error desconocido al invocar servicio externo", HttpStatus.INTERNAL_SERVER_ERROR);
    }

}