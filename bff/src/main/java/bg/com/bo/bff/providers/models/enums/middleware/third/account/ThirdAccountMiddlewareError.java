package bg.com.bo.bff.providers.models.enums.middleware.third.account;

import bg.com.bo.bff.commons.enums.CategoryError;
import bg.com.bo.bff.commons.enums.ConstantMessages;
import bg.com.bo.bff.providers.models.interfaces.middleware.IMiddlewareError;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ThirdAccountMiddlewareError implements IMiddlewareError {

    MDWACTM_0001(HttpStatus.NOT_ACCEPTABLE, "ERROR_PROCEDURE", "MDWACTM-0001", "No se pudo agregar la cuenta. Inténtalo nuevamente.", "Ocurrió un problema", CategoryError.MW_SPECIFIC_INTERNAL_FAILURE.getCategoryId()),
    MDWACTM_0003(HttpStatus.NOT_IMPLEMENTED, "INVALID_VERSION", "MDWACTM-0003", "La versión es inválida.", "Versión inválida", CategoryError.NO_HEADER_PARAMS_MW_ERROR.getCategoryId()),
    MDWACTM_0004(HttpStatus.NOT_ACCEPTABLE, "ERROR_PROCEDURE", "MDWACTM-0004", "No se pudo agregar la cuenta. Inténtalo nuevamente.", "Ocurrió un problema", CategoryError.MW_SPECIFIC_INTERNAL_FAILURE.getCategoryId()),
    MDWACTM_0005(HttpStatus.CONFLICT, "ACCOUNT_REGISTERED", "MDWACTM-0005", "La cuenta ya se encuentra registrada.", "Cuenta registrada", CategoryError.MW_GENERIC_FAIL_RESPONSE.getCategoryId()),
    MDWACTM_0006(HttpStatus.CONFLICT, "INVALID_ACCOUNT", "MDWACTM-0006", "No se encontró la cuenta especificada. Revisa los datos e intenta nuevamente.", "Cuenta no encontrada", CategoryError.MW_GENERIC_FAIL_RESPONSE.getCategoryId()),
    MDWACTM_0007(HttpStatus.CONFLICT, "ACCOUNT_BLOCKED", "MDWACTM-0007", "La cuenta que intentas agendar no está habilitada para recibir depósitos.", "Cuenta no disponible", CategoryError.MW_GENERIC_FAIL_RESPONSE.getCategoryId()),
    MDWACTM_0008(HttpStatus.CONFLICT, "INVALID_ACCOUNT", "MDWACTM-0008", "No puedes adicionar tu propia cuenta.", "Agregar cuenta", CategoryError.MW_GENERIC_FAIL_RESPONSE.getCategoryId()),
    MDWACTM_0009(HttpStatus.CONFLICT, "ACCOUNT_KO", "MDWACTM-0009", "La cuenta especificada ha sido cancelada y no está disponible para operaciones.", "Cuenta cancelada", CategoryError.MW_GENERIC_FAIL_RESPONSE.getCategoryId()),
    MDWACTM_0010(HttpStatus.CONFLICT, "ACCOUNT_REGISTERED", "MDWACTM-0010", "La cuenta ya se encuentra registrada.", "Cuenta registrada", CategoryError.MW_GENERIC_FAIL_RESPONSE.getCategoryId()),
    MDWACTM_0011(HttpStatus.NOT_ACCEPTABLE, "ERROR_PROCEDURE", "MDWACTM-0011", "No se pudo agregar la cuenta. Inténtalo nuevamente.", "Ocurrió un problema", CategoryError.MW_SPECIFIC_INTERNAL_FAILURE.getCategoryId()),

    MDWACTM_1001(HttpStatus.NOT_IMPLEMENTED, "INVALID_VERSION", "MDWACTM-1001", "La versión es inválida.", "Versión inválida", CategoryError.NO_HEADER_PARAMS_MW_ERROR.getCategoryId()),
    MDWACTM_1002(HttpStatus.NOT_ACCEPTABLE, "REGISTERS_NOT_FOUND", "MDWACTM-1002", "No se encontraron registros relacionados.", "Datos no encontrados", CategoryError.MW_GENERIC_INTERNAL_FAILURE.getCategoryId()),

    MDWACTM_2001(HttpStatus.NOT_ACCEPTABLE, "ERROR_PROCEDURE", "MDWACTM-2001", "No se pudo agregar la cuenta billetera. Inténtalo nuevamente.", "Ocurrió un problema", CategoryError.MW_SPECIFIC_INTERNAL_FAILURE.getCategoryId()),
    MDWACTM_2002(HttpStatus.NOT_ACCEPTABLE, "ERROR_PROCEDURE", "MDWACTM-2002", "No se pudo agregar la cuenta billetera. Inténtalo nuevamente", "Ocurrió un problema", CategoryError.MW_SPECIFIC_INTERNAL_FAILURE.getCategoryId()),
    MDWACTM_2003(HttpStatus.CONFLICT, "INVALID_WALLET_ACCOUNT", "MDWACTM-2003", "No se encontró la cuenta billetera especificada. Revisa los datos e intenta nuevamente.", "Cuenta no encontrada", CategoryError.MW_GENERIC_FAIL_RESPONSE.getCategoryId()),
    MDWACTM_2004(HttpStatus.CONFLICT, "INVALID_ACCOUNT", "MDWACTM-2004", "No se encontró la cuenta especificada. Revisa los datos e intenta nuevamente.", "Cuenta no encontrada", CategoryError.MW_GENERIC_FAIL_RESPONSE.getCategoryId()),
    MDWACTM_2005(HttpStatus.CONFLICT, "ACCOUNT_KO", "MDWACTM-2005", "La cuenta especificada ha sido cancelada y no está disponible para operaciones.", "Cuenta cancelada", CategoryError.MW_GENERIC_FAIL_RESPONSE.getCategoryId()),
    MDWACTM_2006(HttpStatus.CONFLICT, "ACCOUNT_KO", "MDWACTM-2006", "La cuenta especificada no permite créditos y no está disponible para operaciones.", "Cuenta inválida", CategoryError.MW_GENERIC_FAIL_RESPONSE.getCategoryId()),
    MDWACTM_2007(HttpStatus.CONFLICT, "ACCOUNT_REGISTERED", "MDWACTM-2007", "La cuenta ya se encuentra registrada.", "Cuenta registrada", CategoryError.MW_GENERIC_FAIL_RESPONSE.getCategoryId()),
    MDWACTM_2008(HttpStatus.NOT_ACCEPTABLE, "ERROR_PROCEDURE", "MDWACTM-2008", "No se pudo agregar la cuenta billetera. Inténtalo nuevamente.", "Ocurrió un problema", CategoryError.MW_SPECIFIC_INTERNAL_FAILURE.getCategoryId()),

    MDWACTM_3001(HttpStatus.NOT_ACCEPTABLE, "ERROR_PROCEDURE", "MDWACTM-3001", "No se pudo obtener el registro de la cuenta. Inténtalo nuevamente.", "Ocurrió un problema", CategoryError.MW_SPECIFIC_INTERNAL_FAILURE.getCategoryId()),
    MDWACTM_3002(HttpStatus.NOT_ACCEPTABLE, "ERROR_PROCEDURE", "MDWACTM-3002", "Tuvimos un problema interno. Inténtalo nuevamente.", "Ocurrió un problema", CategoryError.MW_GENERIC_INTERNAL_FAILURE.getCategoryId()),
    MDWACTM_3003(HttpStatus.NOT_ACCEPTABLE, "ERROR_PROCEDURE", "MDWACTM-3003", "No se pudo eliminar la cuenta billetera. Inténtalo nuevamente.", "Ocurrió un problema", CategoryError.MW_SPECIFIC_INTERNAL_FAILURE.getCategoryId()),

    MDWACTM_002(HttpStatus.NOT_ACCEPTABLE, "ACCOUNT_NOT_FOUND", "MDWACTM-002", "No se encontraron cuentas disponibles.", "No hay cuentas disponibles", CategoryError.MW_SPECIFIC_INTERNAL_FAILURE.getCategoryId()),
    MDWACTM_004(HttpStatus.CONFLICT, "INVALID_DATA", "MDWACTM-004", "No se encontró una cuenta relacionada a la solicitud.", "Datos no encontrados", CategoryError.MW_GENERIC_FAIL_RESPONSE.getCategoryId()),
    MDWACTM_009(HttpStatus.CONFLICT, "INVALID_DATA", "MDWACTM-009", "Los datos ingresados no existen. Verificalos e intenta nuevamente.", "Destinatario no encontrado", CategoryError.MW_GENERIC_FAIL_RESPONSE.getCategoryId()),
    MDWACTM_015(HttpStatus.NOT_IMPLEMENTED, "INVALID_PARAMS", "MDWACTM-015", "Los parámetros deben ser los mismos para el canal 2.", "Parámetros inválidos", CategoryError.NO_HEADER_PARAMS_MW_ERROR.getCategoryId()),
    MDWACTM_016(HttpStatus.NOT_IMPLEMENTED, "INVALID_PARAMS", "MDWACTM-016", "Los parámetros deben ser diferentes para el canal 6.", "Parámetros inválidos", CategoryError.NO_HEADER_PARAMS_MW_ERROR.getCategoryId()),
    MDWACTM_017(HttpStatus.CONFLICT, "INVALID_DATA", "MDWACTM-017", "No se encontró la cuenta con los datos proporcionados.", "Cuenta no encontrada", CategoryError.MW_GENERIC_FAIL_RESPONSE.getCategoryId()),

    ERROR_ADD_ACCOUNT(HttpStatus.NOT_ACCEPTABLE, "ERROR_PROCEDURE", null, "No se pudo agregar la cuenta. Revisa los datos e intenta nuevamente.", "Error al agregar cuenta", CategoryError.MW_SPECIFIC_INTERNAL_FAILURE.getCategoryId());

    private final HttpStatus httpCode;
    private final String code;
    private final String codeMiddleware;
    private final String message;
    private final String title;
    private final int getCategoryId;
}
