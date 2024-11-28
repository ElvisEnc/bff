package bg.com.bo.bff.providers.models.enums.middleware.third.account;

import bg.com.bo.bff.providers.models.interfaces.middleware.IMiddlewareError;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ThirdAccountMiddlewareError implements IMiddlewareError {

    MDWACTM_002(HttpStatus.NOT_ACCEPTABLE, "NOT_ACCEPTABLE", "MDWACTM-002", "No se encontraron cuentas disponibles.", "No hay cuentas disponibles"),
    MDWACTM_004(HttpStatus.NOT_FOUND, "NOT_ACCOUNT", "MDWACTM-004", "No se encontró la cuenta especificada para eliminar.", "Cuenta no encontrada"),
    MDWACTM_009(HttpStatus.CONFLICT, "INVALID_DATA", "MDWACTM-009", "Los datos ingresados no existen. Verificalos e intenta nuevamente.", "Destinatario no encontrado"),
    MDWACTM_015(HttpStatus.BAD_REQUEST, "BAD_REQUEST", "MDWACTM-015", "Los parámetros proporcionados deben ser iguales para el canal 2.", "Error de parámetros"),
    MDWACTM_016(HttpStatus.BAD_REQUEST, "BAD_REQUEST", "MDWACTM-016", "Los parámetros deben ser distintos para el canal 6.", "Error de parámetros"),
    MDWACTM_017(HttpStatus.NOT_FOUND, "NOT_ACCOUNT", "MDWACTM-017", "No se encontró la cuenta con los datos proporcionados.", "Cuenta no encontrada"),
    MDWACTM_0001(HttpStatus.INTERNAL_SERVER_ERROR, "INTERNAL_SERVER_ERROR", "MDWACTM-0001", "Ocurrió un error al agregar la cuenta. Por favor, revisa los datos e intenta nuevamente.", "Error al agregar cuenta"),
    MDWRLIB_0003(HttpStatus.NOT_FOUND, "NOT_ACCOUNT", "MDWRLIB-0003", "No se encontró la cuenta solicitada.", "Cuenta no encontrada"),
    MDWACTM_0006(HttpStatus.CONFLICT, "INVALID_DATA", "MDWACTM-0006", "No se encontró la cuenta especificada. Revisa los datos e intenta nuevamente.", "Cuenta no encontrada"),
    MDWACTM_0007(HttpStatus.NOT_FOUND, "NOT_ACCOUNT", "MDWACTM-0007", "La cuenta está bloqueada. Comunícate con soporte para más detalles.", "Cuenta bloqueada"),
    MDWACTM_0008(HttpStatus.NOT_ACCEPTABLE, "NOT_ACCEPTABLE", "MDWACTM-0008", "La cuenta ingresada es incorrecta, no corresponde a una cuenta de terceros.", "Cuenta incorrecta"),
    MDWACTM_0009(HttpStatus.NOT_ACCEPTABLE, "NOT_ACCEPTABLE", "MDWACTM-0009", "La cuenta especificada ha sido cancelada y no está disponible para operaciones.", "Cuenta cancelada"),
    MDWACTM_0010(HttpStatus.BAD_REQUEST, "BAD_REQUEST", "MDWACTM-0010", "La cuenta ya se encuentra registrada.", "Cuenta registrada"),
    MDWACTM_1002(HttpStatus.CONFLICT, "INVALID_DATA", "MDWACTM-1002", "No se encontraron registros asociados a la cuenta.", "Datos inválidos"),
    MDWACTM_2004(HttpStatus.BAD_REQUEST, "BAD_REQUEST", "MDWACTM-2004", "No se pudo encontrar la cuenta con los datos proporcionados.", "Cuenta no encontrada"),
    MDWACTM_2007(HttpStatus.CONFLICT, "INVALID_DATA", "MDWACTM-2007", "La cuenta ya se encuentra registrada.", "Cuenta registrada"),
    MDWACTM_2005(HttpStatus.NOT_ACCEPTABLE, "NOT_ACCEPTABLE", "MDWACTM-2005", "La cuenta especificada ha sido cancelada.", "Cuenta cancelada"),
    MDWACTM_2006(HttpStatus.NOT_ACCEPTABLE, "NOT_ACCEPTABLE", "MDWACTM-2006", "La cuenta está bloqueada. Contacta a soporte.", "Cuenta bloqueada"),
    MDWACTM_2001(HttpStatus.NOT_ACCEPTABLE, "NOT_ACCEPTABLE", "MDWACTM-2001", "Ocurrió un error al agregar la cuenta. Intenta nuevamente más tarde.", "Error al agregar cuenta"),
    MDWACTM_2002(HttpStatus.NOT_ACCEPTABLE, "NOT_ACCEPTABLE", "MDWACTM-2002", "Ocurrió un error al agregar la cuenta. Revisa los datos e intenta nuevamente.", "Error al agregar cuenta"),
    MDWACTM_2003(HttpStatus.BAD_REQUEST, "BAD_REQUEST", "MDWACTM-2003", "La cuenta especificada es incorrecta. Por favor, verifica los datos proporcionados.", "Cuenta incorrecta"),
    MDWACTM_2008(HttpStatus.NOT_ACCEPTABLE, "NOT_ACCEPTABLE", "MDWACTM-2008", "Ocurrió un error al agregar la cuenta. Intenta nuevamente.", "Error al agregar cuenta"),
    MDWACTM_3001(HttpStatus.BAD_REQUEST, "BAD_REQUEST", "MDWACTM-3001", "No se encontró la cuenta solicitada.", "Cuenta no encontrada"),
    MDWACTM_3002(HttpStatus.CONFLICT, "INVALID_DATA", "MDWACTM-3002", "No se encontró la cuenta con los datos proporcionados.", "Cuenta no encontrada"),
    MDWACTM_3003(HttpStatus.INTERNAL_SERVER_ERROR, "INTERNAL_SERVER_ERROR", "MDWACTM-3003", "Ocurrió un error al eliminar la cuenta. Intenta nuevamente.", "Error al eliminar cuenta"),
    ERROR_ADD_ACCOUNT(HttpStatus.BAD_REQUEST, "BAD_REQUEST", null, "Ocurrió un error al agregar la cuenta. Revisa los datos e intenta nuevamente.", "Error al agregar cuenta");

    private final HttpStatus httpCode;
    private final String code;
    private final String codeMiddleware;
    private final String message;
    private final String title;
}
