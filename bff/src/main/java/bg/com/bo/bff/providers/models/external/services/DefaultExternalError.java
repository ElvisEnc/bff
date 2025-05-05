package bg.com.bo.bff.providers.models.external.services;

import bg.com.bo.bff.commons.enums.CategoryError;
import bg.com.bo.bff.commons.enums.ConstantMessages;
import bg.com.bo.bff.providers.models.external.services.interfaces.IExternalError;
import org.springframework.http.HttpStatus;

@lombok.Getter
@lombok.AllArgsConstructor
public enum DefaultExternalError implements IExternalError {

    BAD_REQUEST(HttpStatus.BAD_REQUEST, "BAD_REQUEST", "BAD_REQUEST", "Ocurrió un error", "Error",CategoryError.MW_GENERIC_FAIL_RESPONSE.getCategoryId()),
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "UNAUTHORIZED", "Usuario no autenticado.", "Usuario no autenticado.", "Usuario no autenticado", CategoryError.AUTH.getCategoryId()),
    ACCESS_DENIED(HttpStatus.UNAUTHORIZED, "ACCESS_DENIED", "Unauthorized", "Error token expirado", "Access token expired", CategoryError.AUTH.getCategoryId()),
    INVALID_URL(HttpStatus.NOT_IMPLEMENTED, "INVALID_URL", null, "Error url inválido", "URL inválido", CategoryError.INVALID_REQUEST_MW_ERROR.getCategoryId()),
    DEFAULT(HttpStatus.INTERNAL_SERVER_ERROR, "INTERNAL_SERVER_ERROR", null, "Tuvimos un problema interno. Inténtalo nuevamente.", ConstantMessages.GENERIC.getTitle(), CategoryError.NO_MAPPED_MW_ERROR.getCategoryId()),
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
