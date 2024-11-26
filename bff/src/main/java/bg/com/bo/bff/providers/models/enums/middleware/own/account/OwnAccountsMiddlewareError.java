package bg.com.bo.bff.providers.models.enums.middleware.own.account;

import bg.com.bo.bff.providers.models.interfaces.middleware.IMiddlewareError;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum OwnAccountsMiddlewareError implements IMiddlewareError {
    MDWACM_002(HttpStatus.NOT_FOUND, "NOT_FOUND", "MDWACM-002", "No se encontraron registros.", "Registros no encontrados"),
    MDWACM_008(HttpStatus.NOT_FOUND, "NOT_FOUND", "MDWACM-008", "No se encontraron registros.", "Registros no encontrados"),
    MDWACM_012(HttpStatus.BAD_REQUEST, "DATA_INVALID", "MDWACM-012", "Los datos proporcionados son inválidos.", "Datos inválidos"),
    MDWACM_013(HttpStatus.BAD_REQUEST, "BAD_REQUEST", "MDWACM-013", "La cuenta especificada no existe.", "Cuenta no encontrada"),
    MDWACM_027(HttpStatus.BAD_REQUEST, "DATA_INVALID", "MDWACM-027", "Se excedió la cantidad máxima permitida de límites.", "Cantidad de límites"),
    MDWACM_028(HttpStatus.SERVICE_UNAVAILABLE, "SERVICE_UNAVAILABLE", "MDWACM-028", "Tuvimos un problema interno. Inténtalo nuevamente.", "Ocurrio un problema");

    private final HttpStatus httpCode;
    private final String code;
    private final String codeMiddleware;
    private final String message;
    private final String title;
}
