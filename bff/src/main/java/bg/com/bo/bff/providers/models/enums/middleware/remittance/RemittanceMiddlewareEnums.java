package bg.com.bo.bff.providers.models.enums.middleware.remittance;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum RemittanceMiddlewareEnums {
    CODE_LANGUAGE("1", "Código de lenguaje"),
    GANAMOVIL("1", "Es el dato si ingresa desde GM");

    private final String code;
    private final String description;
}
