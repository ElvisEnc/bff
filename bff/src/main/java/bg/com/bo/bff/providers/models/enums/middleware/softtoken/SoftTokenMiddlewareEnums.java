package bg.com.bo.bff.providers.models.enums.middleware.softtoken;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum SoftTokenMiddlewareEnums {

    CODE_LANGUAGE(1, "Código de lenguaje");

    private final Integer code;
    private final String description;
}