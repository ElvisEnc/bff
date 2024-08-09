package bg.com.bo.bff.providers.models.enums.middleware.third.account;

import bg.com.bo.bff.providers.models.interfaces.middleware.IMiddlewareError;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ThirdAccountMiddlewareError implements IMiddlewareError {
    MDWACTM_0001(HttpStatus.INTERNAL_SERVER_ERROR, "INTERNAL_SERVER_ERROR", "MDWACTM_0001", "Ocurrio un error al agregar la cuenta"),
    MDWACTM_002(HttpStatus.NOT_ACCEPTABLE, "NOT_ACCEPTABLE", "MDWACTM-002", "No se encontraron cuentas"),
    MDWACTM_004(HttpStatus.NOT_FOUND, "NOT_ACCOUNT", "MDWACTM-004", "No se encontraro la cuenta para eliminar."),
    MDWACTM_0006(HttpStatus.BAD_REQUEST, "NOT_ACCOUNT", "MDWACTM_0006", "No se encontró la cuenta."),
    MDWACTM_0007(HttpStatus.NOT_FOUND, "NOT_ACCOUNT", "MDWACTM-0007", "La cuenta está bloqueada."),
    MDWACTM_0008(HttpStatus.NOT_ACCEPTABLE, "NOT_ACCEPTABLE", "MDWACTM_0008", "La cuenta es incorrecta, no es cuenta de terceros."),
    MDWACTM_0009(HttpStatus.NOT_ACCEPTABLE, "NOT_ACCEPTABLE", "MDWACTM_0009", "La cuenta está cancelada."),
    MDWACTM_0010(HttpStatus.NOT_ACCEPTABLE, "NOT_ACCEPTABLE", "MDWACTM_0010", "La cuenta ya se encuentra registrada."),
    MDWACTM_015(HttpStatus.BAD_REQUEST, "BAD_REQUEST", "MDWACTM-015", "Params must be the same for CHANNEL 2"),
    MDWACTM_016(HttpStatus.BAD_REQUEST, "BAD_REQUEST", "MDWACTM-016", "Params must not be the same for CHANNEL 6"),
    MDWACTM_017(HttpStatus.NOT_FOUND, "NOT_ACCOUNT", "MDWACTM-017", "No se encontró la cuenta con los datos otorgados."),
    MDWACTM_1002(HttpStatus.NOT_FOUND, "NOT_ACCOUNT", "MDWACTM-1002", "No se encontraro registros"),
    MDWRLIB_0003(HttpStatus.NOT_FOUND, "NOT_ACCOUNT", "MDWRLIB-0003", "La cuenta no ha sido encontrada."),
    MDWACTM_2004(HttpStatus.BAD_REQUEST, "BAD_REQUEST", "MDWACTM-2004", "La cuenta no ha sido encontrada."),
    MDWACTM_2007(HttpStatus.BAD_REQUEST, "BAD_REQUEST", "MDWACTM-2007", "La cuenta ya está registrada."),
    MDWACTM_2005(HttpStatus.NOT_ACCEPTABLE, "NOT_ACCEPTABLE", "MDWACTM-2005", "La cuenta está cancelada"),
    MDWACTM_2006(HttpStatus.NOT_ACCEPTABLE, "NOT_ACCEPTABLE", "MDWACTM-2006", "La cuenta está bloqueada"),
    MDWACTM_2001(HttpStatus.NOT_ACCEPTABLE, "NOT_ACCEPTABLE", "MDWACTM-2001", "Ocurrio un error al agregar la cuenta"),
    MDWACTM_2002(HttpStatus.NOT_ACCEPTABLE, "NOT_ACCEPTABLE", "MDWACTM-2002", "Ocurrio un error al agregar la cuenta"),
    MDWACTM_2003(HttpStatus.BAD_REQUEST, "BAD_REQUEST", "MDWACTM-2003", "La cuenta es incorrecta."),
    MDWACTM_2008(HttpStatus.NOT_ACCEPTABLE, "NOT_ACCEPTABLE", "MDWACTM-2008", "Ocurrio un error al agregar la cuenta"),
    MDWACTM_3001(HttpStatus.BAD_REQUEST, "BAD_REQUEST", "MDWACTM-3001", "La cuenta no ha sido encontrada"),
    MDWACTM_3002(HttpStatus.BAD_REQUEST, "BAD_REQUEST", "MDWACTM-3002", "La cuenta no ha sido encontrada"),
    MDWACTM_3003(HttpStatus.INTERNAL_SERVER_ERROR, "INTERNAL_SERVER_ERROR", "MDWACTM-3003", "Ocurrio un error al eliminar la cuenta.");


    private final HttpStatus httpCode;
    private final String code;
    private final String codeMiddleware;
    private final String message;
}
