package bg.com.bo.bff.commons.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum AppError {
    // Login
    MDWLM_009(HttpStatus.UNAUTHORIZED, "ERROR_INTERN", "MDWLM-009", "User not found"),
    MDWLM_010(HttpStatus.UNAUTHORIZED, "PERSON_NOT_FOUND", "MDWLM-010", "Device or Person not enrolled"),
    MDWLM_011(HttpStatus.UNAUTHORIZED, "DATA_INVALID", "MDWLM-011", "Invalid data"),
    MDWLM_012(HttpStatus.UNAUTHORIZED, "INVALID_DATA", "MDWLM-012", "Estimado cliente, sus datos son incorrectos"),
    MDWLM_013(HttpStatus.NOT_ACCEPTABLE, "NOT_ACCEPTABLE", "MDWLM-013", "Error en el procesamiento de parametros"),
    MDWLM_018(HttpStatus.UNAUTHORIZED, "EXPIRED_PASSWORD_LM", "MDWLM-018", "The password has expired, you must change it"),
    MDWLM_019(HttpStatus.UNAUTHORIZED, "EXPIRED_PASSWORD_DAYS_LM", "MDWLM-019", "The password will expire in X days"),
    MDWLM_020(HttpStatus.UNAUTHORIZED, "PASSWORD_CHANGE_LM", "MDWLM-020", "You must change the password"),
    MDWLM_23(HttpStatus.UNAUTHORIZED, "BLOCKED_USER", "MDWLM-23", "Estimado cliente, has superado los intentos máximos, necesitamos validar su identidad"),
    MDWLM_24(HttpStatus.UNAUTHORIZED, "NOT_ENROLLED", "MDWLM-24", "Estimado cliente, está intentando iniciar sesión en un nuevo dispositivo, necesitamos validar su identidad"),
    MDWLM_25(HttpStatus.UNAUTHORIZED, "RESTRICTED_USER", "MDWLM-25", "Estimado cliente, necesitamos por favor que visite nuestras oficinas para validar su identidad"),

    MDWRLIB_0003(HttpStatus.NOT_FOUND, "NOT_FOUND", "MDWRLIB-0003", "Error en los datos"),

    // Logout
    MDWRLIB_0001(HttpStatus.BAD_REQUEST, "NOT_ENROLLED", "MDWRLIB-0001", "Error en los headers. Channel"),
    MDWRLIB_0011(HttpStatus.BAD_REQUEST, "NOT_ENROLLED", "MDWRLIB-0011", "Error en los headers. ApplicationId"),
    MDWPGL_404(HttpStatus.NOT_FOUND, "NOT_ENROLLED", "MDWPGL-404", "Not found"),
    MDWPGL_405(HttpStatus.METHOD_NOT_ALLOWED, "NOT_ENROLLED", "MDWPGL-405", "Method not allowed"),
    MDWRLIB_003(HttpStatus.NOT_ACCEPTABLE, "NOT_ENROLLED", "MDWRLIB-003", "Error en los datos"),
    KEY_CLOAK_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "INTERNAL_SERVER_ERROR", "KEY_CLOAK_ERROR", "Error interno."),

    // Extractos
    MDWPGL_400(HttpStatus.BAD_REQUEST, "BAD_REQUEST", "MDWPGL-400", "Error en los DATOS"),
    MDWACM_008(HttpStatus.NOT_FOUND, "NOT_FOUND", "MDWACM-008", "Sin registros"),
    MDWACM_013(HttpStatus.BAD_REQUEST, "BAD_REQUEST", "MDWACM-013", "La cuenta no existe"),
    MDWACM_002(HttpStatus.NOT_FOUND, "NOT_FOUND", "MDWACM-002", "Sin registros"),

    // Agendar Cuentas
    MDWRACTM_004(HttpStatus.BAD_REQUEST, "DATA_INVALID", "MDWRACTM-004", "Datos Invalidos"),
    MDWAAM_002(HttpStatus.BAD_REQUEST, "DATA_INVALID", "MDWAAM-002", "Datos Invalidos"),

    // Branch Office Bank
    MDWAAM002(HttpStatus.BAD_REQUEST, "BAD_REQUEST", "MDWAAM002", "Datos Invalidos"),
    MDWAAM_001(HttpStatus.NOT_FOUND, "NOT_FOUND", "MDWAAM-001", "No tiene datos"),
    MDWRLIB_0009(HttpStatus.FORBIDDEN, "FORBIDDEN", "MDWRLIB-0009", "Forbidden"),

    // Cuentas Terceros
    MDWRACTM_002(HttpStatus.NOT_ACCEPTABLE, "NOT_ACCEPTABLE", "MDWRACTM-002", "No se encontraron cuentas"),
    MDWRLIB_0012(HttpStatus.BAD_REQUEST, "BAD_REQUEST", "MDWRLIB-0012", "Channel not implemented yet"),
    MDWRACTM_015(HttpStatus.BAD_REQUEST, "BAD_REQUEST", "MDWRACTM-015", "Params must be the same for CHANNEL 2"),
    MDWRACTM_016(HttpStatus.BAD_REQUEST, "BAD_REQUEST", "MDWRACTM-016", "Params must not be the same for CHANNEL 6"),
    MDWRACTM_017(HttpStatus.NOT_FOUND, "NOT_ACCOUNT", "MDWRACTM-017", "No se encontró la cuenta con los datos otorgados."),


    // Cuentas Ach
    MDWHDR_01(HttpStatus.BAD_REQUEST, "BAD_REQUEST", "MDWHDR-01", "Invalid Header channel"),
    MDWHDR_02(HttpStatus.BAD_REQUEST, "BAD_REQUEST", "MDWHDR-02", "Channel not implemented yet"),
    MDWAAM_006(HttpStatus.BAD_REQUEST, "BAD_REQUEST", "MDWAAM-006", "Params must be the same for CHANNEL 2"),
    MDWAAM_010(HttpStatus.NOT_ACCEPTABLE, "NOT_ACCEPTABLE", "MDWAAM-010", "No tiene datos"),
    MDWAAM_004(HttpStatus.NOT_ACCEPTABLE, "NOT_ACCEPTABLE", "MDWAAM-004", "No tiene datos"),

    //DPFs
    MDWDPF_002(HttpStatus.NOT_ACCEPTABLE, "NOT_ACCEPTABLE", "MDWDPF-002", "No se encontraron registros."),
    MDWRLIB_0013(HttpStatus.BAD_REQUEST, "BAD_REQUEST", "MDWRLIB-0013", "Error en los headers. DeviceId"),

    //Transferencias
    MDWTRM_001(HttpStatus.BAD_REQUEST, "SOURCE_ACCOUNT_KO", "MDWTRM-001", "No se pudo obtener datos de la cuenta de origen"),
    MDWTRM_002(HttpStatus.BAD_REQUEST, "SOURCE_ACCOUNT_KO", "MDWTRM-002", "La cuenta de origen no se encuentra operativa"),
    MDWTRM_003(HttpStatus.BAD_REQUEST, "TARGET_ACCOUNT_NOT_FOUND", "MDWTRM-003", "Error al obtener datos de la cuenta de destino"),
    MDWTRM_004(HttpStatus.BAD_REQUEST, "TARGET_ACCOUNT_KO", "MDWTRM-004", "La cuenta de destino no se encuentra operativa"),
    MDWTRM_005(HttpStatus.BAD_REQUEST, "SAME_ACCOUNT", "MDWTRM-005", "La cuenta de origen y de destino no pueden ser la misma"),
    MDWTRM_006(HttpStatus.BAD_REQUEST, "NOT_FOUND_AVAILABLE", "MDWTRM-006", "No se pudo realizar la conversión del monto"),
    MDWTRM_007(HttpStatus.BAD_REQUEST, "ERROR_ITF", "MDWTRM-007", "No se pudo realizar el cálculo del ITF"),
    MDWTRM_008(HttpStatus.BAD_REQUEST, "NO_FUNDS_AVAILABLE", "MDWTRM-008", "La cuenta no tiene fondos suficientes para realizar la transferencia"),
    MDWTRM_009(HttpStatus.BAD_REQUEST, "VALIDATE_LIMITS", "MDWTRM-009", "No se pudo validar los limites"),
    MDWTRM_010(HttpStatus.BAD_REQUEST, "LIMIT_KO", "MDWTRM-010", "No se pudo realizar la transferencia, límites superados"),
    MDWTRM_011(HttpStatus.BAD_REQUEST, "VALIDATE_COMBINATION", "MDWTRM-011", "Se requieren permisos de firmantes para realizar la transferencia"),
    MDWTRM_012(HttpStatus.BAD_REQUEST, "TRANSFER_KO", "MDWTRM-012", "La moneda es distinta a la de la cuenta a debitar y a la de acreditar"),
    MDWTRM_013(HttpStatus.BAD_REQUEST, "NOT_FOUND_AVAILABLE", "MDWTRM-013", "No se pudo obtener los datos del préstamo"),
    MDWTRM_014(HttpStatus.BAD_REQUEST, "PAYMENT_LOAND_ERROR", "MDWTRM-014", "Se produjo un error al procesar el pago del préstamo"),
    MDWTRM_015(HttpStatus.BAD_REQUEST, "NOT_FOUND_RECEIPT", "MDWTRM-015", "Hubo un problema al recuperar los datos del bono"),
    MDWTRM_016(HttpStatus.BAD_REQUEST, "VOUCHER_LOAN_PAYMENT_KO", "MDWTRM-016", "Se produjo un error al recuperar los datos del comprobante de préstamo"),
    MDWTRM_017(HttpStatus.BAD_REQUEST, "VOUCHER_LOAN_PAYMENT_KO", "MDWTRM-017", "Se produjo un error al recuperar los datos del comprobante de préstamo"),
    MDWTRM_018(HttpStatus.BAD_REQUEST, "INVALID_CURRENCY", "MDWTRM-018", "Problemas con el tipo de la moneda"),
    MDWTRM_019(HttpStatus.INTERNAL_SERVER_ERROR, "INTERNAL_SERVER_ERROR", "MDWTRM-019", "No se pudo realizar la transferencia"),
    MDWTRM_020(HttpStatus.BAD_REQUEST, "TRANSFER_DUPLICATE", "MDWTRM-020", "Esta transacción ya ha sido realizada anteriormente"),
    MDWTRM_021(HttpStatus.BAD_REQUEST, "TRANSFER_DUPLICATE", "MDWTRM-021", "No se pudo verificar la transacción duplicada"),
    MDWTRM_022(HttpStatus.BAD_REQUEST, "TRANSFER_DUPLICATE", "MDWTRM-022", "No se pudo verificar la transacción duplicada"),
    MDWTRM_023(HttpStatus.BAD_REQUEST, "TRANSACTION_NOT_ALLOWED", "MDWTRM-023", "No se pudo realizar la transacción, no esta permitida"),
    MDWTRM_024(HttpStatus.BAD_REQUEST, "LIST_KO", "MDWTRM-024", "No se pudo obtener la lista de cuentas"),
    MDWTRM_026(HttpStatus.BAD_REQUEST, "SOURCE_OR_TARGET_ACCOUNT_KO", "MDWTRM-026", "La cuenta de origen o destino no se encuentra operativa"),
    MDWTRM_032(HttpStatus.BAD_REQUEST, "OWNER_ACCOUNT_KO", "MDWTRM-032", "La cuenta de origen no se encuentra operativa"),
    MDWTRM_033(HttpStatus.BAD_REQUEST, "CRIPTO_FOUND", "MDWTRM-033", "La transferencia no puede ser procesada, por validación de criptomoneda"),
    MDWTRM_037(HttpStatus.BAD_REQUEST, "BLACKLIST_FOUND", "MDWTRM-037", "La cuenta de origen se encuentra en una lista negra interna"),
    MDWTRM_040(HttpStatus.BAD_REQUEST, "LIMIT_KO", "MDWTRM-040", "No se pudo realizar la transferencia, actualiza el límite diario"),
    MDWTRM_041(HttpStatus.BAD_REQUEST, "LIMIT_KO", "MDWTRM-041", "No se pudo realizar la transferencia, actualiza el límite diario"),
    MDWTRM_042(HttpStatus.BAD_REQUEST, "LIMIT_KO", "MDWTRM-042", "No se pudo validar los limites por día de la cuenta"),
    MDWTRM_400(HttpStatus.BAD_REQUEST, "INVALID_CURRENCY", "MDWPGL-400", "Problemas con el tipo de la moneda"),
    MDWTRM_PENDING(HttpStatus.OK, "TRANSFER_PENDING", "MDWPGL-PENDING", "La transferencia está pendiente de aprobación por el titular. Por favor, espera la confirmación"),

    //QR

    MDWGQM_001(HttpStatus.BAD_REQUEST, "GENERATE_QR_IMAGE", "MDWGQM-001", "Hubo un error al generar el qr, revise los datos e intente nuevamente."),
    MDWGQM_002(HttpStatus.BAD_REQUEST, "GENERATE_QR_IMAGE_BASE_64", "MDWGQM-002", "Hubo un error al generar el qr, revise los datos e intente nuevamente."),
    MDWGQM_003(HttpStatus.BAD_REQUEST, "DECODE_IMAGE_BASE_64", "MDWGQM-003", "Hubo un error al generar el qr, revise los datos e intente nuevamente."),
    MDWGQM_012(HttpStatus.BAD_REQUEST, "ERROR_ENCRYPT", "MDWGQM-012", "Hubo un error al generar el qr, revise los datos e intente nuevamente."),
    MDWGQM_015(HttpStatus.BAD_REQUEST, "ERROR_DECRYPT", "MDWGQM-015", "Hubo un error al generar el qr, revise los datos e intente nuevamente."),
    MDWGQM_007(HttpStatus.BAD_REQUEST, "ERROR_DATA_ACCOUNT_ORIGIN", "MDWGQM-007", "Hubo un error al obtener los datos de la cuenta origen, revise los datos e intente nuevamente."),
    MDWGQM_009(HttpStatus.INTERNAL_SERVER_ERROR, "INTERNAL_SERVER_ERROR", "MDWGQM-009", "Internal server error."),
    MDWGQM_010(HttpStatus.INTERNAL_SERVER_ERROR, "INTERNAL_SERVER_ERROR", "MDWGQM-010", "Internal server error."),
    MDWGQM_013(HttpStatus.BAD_REQUEST, "ACCOUNT_CURRENCY_ORIGIN_DIFFERENT", "MDWGQM-013", "La moneda de la cuenta origen es distinta."),
    MDWGQM_014(HttpStatus.BAD_REQUEST, "FORMAT_IS_NOT_VALID_ENTITY", "MDWGQM-014", "El formato de la entidad bancaria no es válido o no existe."),
    MDWGQM_011(HttpStatus.BAD_REQUEST, "ERROR_DATE", "MDWGQM-011", "La fecha otogada no es correcta."),
    MDWGQM_004(HttpStatus.BAD_REQUEST, "CONVERTING_DATE_FORMAT", "MDWGQM-004", "El formato de la fecha no es correcta."),
    MDWGQM_008(HttpStatus.BAD_REQUEST, "SOURCE_ACCOUNT_KO", "MDWGQM-008", "La cuenta de origen no se encuentra operativa"),

    // Genericos
    BAD_REQUEST(HttpStatus.BAD_REQUEST, "BAD_REQUEST", "BAD_REQUEST", "Error en los parametros"),
    MDWACM_012(HttpStatus.BAD_REQUEST, "DATA_INVALID", "MDWACM-012", "Datos Invalidos"),
    MDWACM_027(HttpStatus.BAD_REQUEST, "DATA_INVALID", "MDWACM-027", "Error cantidad de limites maximo"),
    DEFAULT(HttpStatus.INTERNAL_SERVER_ERROR, "INTERNAL_SERVER_ERROR", "DEFAULT", "Error interno");

    private final HttpStatus httpCode;
    private final String code;
    private final String codeMiddleware;
    private final String message;

    public static AppError findByCode(String description) {

        for (AppError constant : AppError.values()) {
            if (constant.getCodeMiddleware().equals(description)) {
                return constant;
            }
        }
        return AppError.DEFAULT;
    }
}
