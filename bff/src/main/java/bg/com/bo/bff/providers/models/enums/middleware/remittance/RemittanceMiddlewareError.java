package bg.com.bo.bff.providers.models.enums.middleware.remittance;

import bg.com.bo.bff.commons.enums.CategoryError;
import bg.com.bo.bff.providers.models.interfaces.middleware.IMiddlewareError;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum RemittanceMiddlewareError implements IMiddlewareError {
    RM_030(HttpStatus.CONFLICT, "NOT_ACCEPTABLE", "RM-030", "No se encontraron registros para la petición.", "Datos no encontrados", CategoryError.MW_GENERIC_FAIL_RESPONSE.getCategoryId()),
    RM_031(HttpStatus.CONFLICT, "INVALID_ACCOUNT", "RM-031", "No es posible utilizar esta cuenta para realizar esta transacción.", "Cuenta inactiva", CategoryError.MW_GENERIC_FAIL_RESPONSE.getCategoryId()),
    RM002(HttpStatus.CONFLICT, "BAD_REQUEST", "RM002", "No se encontraron registros para la petición.", "Datos no encontrados", CategoryError.MW_GENERIC_FAIL_RESPONSE.getCategoryId());

    private final HttpStatus httpCode;
    private final String code;
    private final String codeMiddleware;
    private final String message;
    private final String title;
    private final int categoryId;
}
