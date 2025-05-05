package bg.com.bo.bff.providers.models.enums.external.services.crypto.currency;

import bg.com.bo.bff.commons.enums.CategoryError;
import bg.com.bo.bff.providers.models.external.services.interfaces.IExternalError;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum CryptoCurrencyError implements IExternalError {
    ACCESS_DENIED(HttpStatus.UNAUTHORIZED, "ACCESS_DENIED", "Unauthorized", "Error token expirado", "Access token expired", CategoryError.AUTH.getCategoryId()),
    USER_REGISTERED(HttpStatus.NOT_ACCEPTABLE, "USER_REGISTERED", "Usuario tiene una cuenta cripto", "Usuario ya tiene una cuenta en GanaCripto", "Usuario ya registrado", CategoryError.MW_SPECIFIC_FAIL_RESPONSE.getCategoryId()),
    USER_NOT_EXIST(HttpStatus.NOT_ACCEPTABLE, "USER_NOT_EXIST", "No existe el código de persona", "El código de persona no existe", "Usuario no existente", CategoryError.MW_SPECIFIC_FAIL_RESPONSE.getCategoryId()),
    ;

    private final HttpStatus httpCode;
    private final String code;
    private final String msgError;
    private final String message;
    private final String title;
    private final int categoryId;
}
