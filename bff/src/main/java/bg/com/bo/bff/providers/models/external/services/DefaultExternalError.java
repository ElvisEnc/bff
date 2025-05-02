package bg.com.bo.bff.providers.models.external.services;

import bg.com.bo.bff.commons.enums.CategoryError;
import bg.com.bo.bff.providers.models.external.services.interfaces.IExternalError;
import org.springframework.http.HttpStatus;

@lombok.Getter
@lombok.AllArgsConstructor
public enum DefaultExternalError implements IExternalError {

    BAD_REQUEST(HttpStatus.BAD_REQUEST, "BAD_REQUEST", "BAD_REQUEST", "Ocurrió un error", "Error",CategoryError.MW_GENERIC_FAIL_RESPONSE.getCategoryId()),
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "UNAUTHORIZED", "Sin autorización", "Usuario no autenticado.", "Usuario no autenticado", CategoryError.AUTH.getCategoryId()),
    ;


    private final HttpStatus httpCode;
    private final String code;
    private final String getMsgError;
    private final String message;
    private final String title;
    private final int categoryId;

    @Override
    public String getMsgError() {
        return this.getMsgError;
    }
}
