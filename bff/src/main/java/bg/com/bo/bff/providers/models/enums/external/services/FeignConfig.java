package bg.com.bo.bff.providers.models.enums.external.services;

import bg.com.bo.bff.providers.models.external.services.interfaces.IExternalError;
import com.fasterxml.jackson.databind.ObjectMapper;
import feign.codec.ErrorDecoder;
import org.springframework.context.annotation.Bean;

public class FeignConfig<T extends Enum<T> & IExternalError> {

    private final Class<T> errorEnumClass;

    public FeignConfig(Class<T> errorEnumClass) {
        this.errorEnumClass = errorEnumClass;
    }

    @Bean
    public ErrorDecoder errorDecoder(ObjectMapper objectMapper) {
        return new FeignErrorDecoder<>(errorEnumClass, objectMapper);
    }
}