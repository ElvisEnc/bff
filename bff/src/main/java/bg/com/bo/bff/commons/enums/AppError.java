package bg.com.bo.bff.commons.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum AppError {
    // Login
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

    MDWLM_008(HttpStatus.UNAUTHORIZED, "INVALID_DATA", "MDWLM-008", "Estimado cliente, sus datos son incorrectos"), // alias, DNI
    MDWLM_009(HttpStatus.UNAUTHORIZED, "INVALID_DATA", "MDWLM-009", "Estimado cliente, sus datos son incorrectos"), // personId
    MDWLM_007(HttpStatus.UNAUTHORIZED, "INVALID_DATA", "MDWLM-007", "Type authentication invalid"),
    MDWLM_12(HttpStatus.UNAUTHORIZED, "INVALID_DATA", "MDWLM-12", "Estimado cliente, sus datos son incorrectos"), // contraseña
    MDWLM_021(HttpStatus.UNAUTHORIZED, "PASSWORD_CHANGE_LM", "MDWLM-021", "Estimado cliente, Debe cambiar la contraseña"),
    MDWRLIB_0003(HttpStatus.NOT_FOUND, "NOT_FOUND", "MDWRLIB-0003", "Error en los datos"),

    // Logout
    MDWRLIB_0001(HttpStatus.BAD_REQUEST, "BAD_REQUEST", "MDWRLIB-0001", "Error en los headers. Channel"),
    MDWRLIB_0011(HttpStatus.BAD_REQUEST, "BAD_REQUEST", "MDWRLIB-0011", "Error en los headers. ApplicationId"),
    MDWPGL_404(HttpStatus.NOT_FOUND, "NOT_FOUND", "MDWPGL-404", "Not found"),
    MDWPGL_405(HttpStatus.METHOD_NOT_ALLOWED, "METHOD_NOT_ALLOWED", "MDWPGL-405", "Method not allowed"),
    MDWRLIB_003(HttpStatus.NOT_ACCEPTABLE, "BAD_REQUEST", "MDWRLIB-003", "Error en los datos otorgados"),
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
    MDWACTM_002(HttpStatus.NOT_ACCEPTABLE, "NOT_ACCEPTABLE", "MDWACTM-002", "No se encontraron cuentas"),
    MDWRLIB_0012(HttpStatus.BAD_REQUEST, "BAD_REQUEST", "MDWRLIB-0012", "Channel not implemented yet"),
    MDWACTM_015(HttpStatus.BAD_REQUEST, "BAD_REQUEST", "MDWACTM-015", "Params must be the same for CHANNEL 2"),
    MDWACTM_016(HttpStatus.BAD_REQUEST, "BAD_REQUEST", "MDWACTM-016", "Params must not be the same for CHANNEL 6"),
    MDWACTM_017(HttpStatus.NOT_FOUND, "NOT_ACCOUNT", "MDWACTM-017", "No se encontró la cuenta con los datos otorgados."),


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
    MDWTRM_016(HttpStatus.BAD_REQUEST, "SOURCE_ACCOUNT_KO", "MDWTRM-016", "La cuenta de origen no puede realizar débitos"),
    MDWTRM_017(HttpStatus.BAD_REQUEST, "TRANSFER_KO", "MDWTRM-017", "Se produjo un error al validar la transferencia"),
    MDWTRM_018(HttpStatus.BAD_REQUEST, "INVALID_CURRENCY", "MDWTRM-018", "Problemas con el tipo de la moneda"),
    MDWTRM_019(HttpStatus.INTERNAL_SERVER_ERROR, "INTERNAL_SERVER_ERROR", "MDWTRM-019", "No se pudo realizar la transferencia"),
    MDWTRM_020(HttpStatus.BAD_REQUEST, "TRANSFER_DUPLICATE", "MDWTRM-020", "Esta transacción ya ha sido realizada anteriormente"),
    MDWTRM_021(HttpStatus.BAD_REQUEST, "TRANSFER_DUPLICATE", "MDWTRM-021", "No se pudo verificar la transacción duplicada"),
    MDWTRM_022(HttpStatus.BAD_REQUEST, "TRANSFER_DUPLICATE", "MDWTRM-022", "No se pudo verificar la transacción duplicada"),
    MDWTRM_023(HttpStatus.BAD_REQUEST, "TRANSACTION_NOT_ALLOWED", "MDWTRM-023", "No se pudo realizar la transacción, no esta permitida"),
    MDWTRM_024(HttpStatus.BAD_REQUEST, "LIST_KO", "MDWTRM-024", "No se pudo obtener la lista de cuentas"),
    MDWTRM_026(HttpStatus.BAD_REQUEST, "FUNDS_REQUIRED", "MDWTRM-026", "El origen y destino de fondos son requeridos"),
    MDWTRM_032(HttpStatus.BAD_REQUEST, "OWNER_ACCOUNT_KO", "MDWTRM-032", "La cuenta de origen no se encuentra operativa"),
    MDWTRM_033(HttpStatus.BAD_REQUEST, "CRIPTO_FOUND", "MDWTRM-033", "La transferencia no puede ser procesada, por validación de criptomoneda"),
    MDWTRM_034(HttpStatus.BAD_REQUEST, "TRACKING_ERROR", "MDWTRM-034", "La transferencia no puede ser procesada, mo se pudo registar el registro"),
    MDWTRM_035(HttpStatus.BAD_REQUEST, "TRACKING_UPDATE_ERROR", "MDWTRM-035", "La transferencia no puede ser procesada, no se pudo actualizar el seguimiento"),
    MDWTRM_036(HttpStatus.BAD_REQUEST, "AUTHORIZATION_REGISTER_ERROR", "MDWTRM-036", "La transferencia no puede ser procesada, porque no se pudo procesar el registro de autorización"),
    MDWTRM_037(HttpStatus.BAD_REQUEST, "BLACKLIST_FOUND", "MDWTRM-037", "La cuenta de origen se encuentra en una lista negra interna"),
    MDWTRM_038(HttpStatus.BAD_REQUEST, "ERROR_VERIFY_POWER_TRX", "MDWTRM-038", "La transferencia no puede ser procesada, por la validación de poderes trx"),
    MDWTRM_039(HttpStatus.BAD_REQUEST, "VALIDATE_ROLE_PERSON", "MDWTRM-039", "Validación del person rol fallida"),
    MDWTRM_040(HttpStatus.BAD_REQUEST, "LIMIT_KO", "MDWTRM-040", "No se pudo realizar la transferencia, actualiza el límite diario"),
    MDWTRM_041(HttpStatus.BAD_REQUEST, "LIMIT_KO", "MDWTRM-041", "No se pudo realizar la transferencia, actualiza el límite diario"),
    MDWTRM_042(HttpStatus.BAD_REQUEST, "LIMIT_KO", "MDWTRM-042", "No se pudo validar los limites por día de la cuenta"),
    MDWTRM_043(HttpStatus.BAD_REQUEST, "ERROR_GET_AUTHORIZATION", "MDWTRM-043", "La transferencia no puede ser procesada, error al obtener la auth maestro"),
    MDWTRM_044(HttpStatus.BAD_REQUEST, "ERROR_PERSON_ID_NOT_NULL", "MDWTRM-044", "El codigo de persona no puede ser nulo"),
    MDWTRM_045(HttpStatus.BAD_REQUEST, "ERROR_COMPANY_ID_NOT_NULL", "MDWTRM-045", "El companyId no puede ser nulo"),
    MDWTRM_046(HttpStatus.BAD_REQUEST, "ERROR_COMPANY_ID", "MDWTRM-046", "SchemeName sólo puede ser companyId"),
    MDWTRM_047(HttpStatus.BAD_REQUEST, "ERROR_PERSON_ID", "MDWTRM-047", "SchemeName sólo puede ser personId"),
    MDWTRM_048(HttpStatus.BAD_REQUEST, "ERROR_GET_ACCOUNT", "MDWTRM-048", "Ocurrio un error al obtener la cuenta"),
    MDWTRM_049(HttpStatus.BAD_REQUEST, "ERROR_ACCOUNT_VALIDATE", "MDWTRM-049", "La cuenta no pertenece al usuario"),
    MDWTRM_050(HttpStatus.BAD_REQUEST, "ERROR_WORKFLOW_INVALID", "MDWTRM-050", "Ocurrio un error"),
    MDWTRM_051(HttpStatus.BAD_REQUEST, "ACCOUNT_DESTINY_INVALID", "MDWTRM-051", "La cuenta de destino es inválida"),
    MDWTRM_052(HttpStatus.BAD_REQUEST, "ACCOUNT_ORIGIN_INVALID", "MDWTRM-052", "La cuenta de origen es inválida"),
    MDWTRM_053(HttpStatus.BAD_REQUEST, "ERROR_NUMBER_TRANSACTION", "MDWTRM-053", "Error al obtener el número de transacción"),
    MDWTRM_054(HttpStatus.BAD_REQUEST, "ERROR_POWER_VALIDATION", "MDWTRM-054", "Ocurrio un error, al validar los poderes"),
    MDWTRM_055(HttpStatus.BAD_REQUEST, "POWER_VALIDATION", "MDWTRM-055", "No tiene autorización para realizar la transacción"),
    MDWTRM_056(HttpStatus.BAD_REQUEST, "TRUSTED_ACCOUNT", "MDWTRM-056", "La cuenta no está en la lista de confianza"),
    MDWTRM_400(HttpStatus.BAD_REQUEST, "INVALID_CURRENCY", "MDWPGL-400", "Problemas con el tipo de la moneda"),
    MDWTRM_PENDING(HttpStatus.OK, "TRANSFER_PENDING", "MDWPGL-PENDING", "La transferencia está pendiente de aprobación por el titular. Por favor, espera la confirmación"),

    //QR
    MDWGQM_001(HttpStatus.BAD_REQUEST, "GENERATE_QR_IMAGE", "MDWGQM-001", "Hubo un error al generar el qr, revise los datos e intente nuevamente."),
    MDWGQM_002(HttpStatus.BAD_REQUEST, "GENERATE_QR_IMAGE_BASE_64", "MDWGQM-002", "Hubo un error al generar el qr, revise los datos e intente nuevamente."),
    MDWGQM_003(HttpStatus.BAD_REQUEST, "DECODE_IMAGE_BASE_64", "MDWGQM-003", "Hubo un error al generar el qr, revise los datos e intente nuevamente."),
    MDWGQM_012(HttpStatus.BAD_REQUEST, "ERROR_ENCRYPT", "MDWGQM-012", "Hubo un error al generar el qr, revise los datos e intente nuevamente."),
    MDWGQM_015(HttpStatus.BAD_REQUEST, "ERROR_DECRYPT", "MDWGQM-015", "Hubo un error al desencriptar el qr, intente nuevamente más tarde."),
    MDWGQM_007(HttpStatus.BAD_REQUEST, "ERROR_DATA_ACCOUNT_ORIGIN", "MDWGQM-007", "Hubo un error al obtener los datos de la cuenta origen, revise los datos e intente nuevamente."),
    MDWGQM_009(HttpStatus.INTERNAL_SERVER_ERROR, "INTERNAL_SERVER_ERROR", "MDWGQM-009", "Internal server error."),
    MDWGQM_010(HttpStatus.BAD_REQUEST, "BAD_REQUEST", "MDWGQM-010", "Error en los parametros."),
    MDWGQM_013(HttpStatus.BAD_REQUEST, "ACCOUNT_CURRENCY_ORIGIN_DIFFERENT", "MDWGQM-013", "La moneda de la cuenta origen es distinta."),
    MDWGQM_014(HttpStatus.BAD_REQUEST, "FORMAT_IS_NOT_VALID_ENTITY", "MDWGQM-014", "El formato de la entidad bancaria no es válido o no existe."),
    MDWGQM_011(HttpStatus.BAD_REQUEST, "ERROR_DATE", "MDWGQM-011", "La fecha otogada no es correcta."),
    MDWGQM_004(HttpStatus.BAD_REQUEST, "CONVERTING_DATE_FORMAT", "MDWGQM-004", "El formato de la fecha no es correcta."),
    MDWGQM_008(HttpStatus.BAD_REQUEST, "SOURCE_ACCOUNT_KO", "MDWGQM-008", "La cuenta de origen no se encuentra operativa"),
    MDWGQM_017(HttpStatus.BAD_REQUEST, "BAD_REQUEST", "MDWGQM-017", "La cuenta o el código de persona no pertenece"),

    // Genericos
    BAD_REQUEST(HttpStatus.BAD_REQUEST, "BAD_REQUEST", "BAD_REQUEST", "Error en los parametros"),
    MDWACM_012(HttpStatus.BAD_REQUEST, "DATA_INVALID", "MDWACM-012", "Datos Invalidos"),
    MDWACM_027(HttpStatus.BAD_REQUEST, "DATA_INVALID", "MDWACM-027", "Error cantidad de limites maximo"),

    DEFAULT(HttpStatus.INTERNAL_SERVER_ERROR, "INTERNAL_SERVER_ERROR", "DEFAULT", "Error interno"),

    //Usuarios
    NOT_ACCEPTABLE_UPDATE_PERSONAL_INFORMATION(HttpStatus.NOT_ACCEPTABLE, "NOT_ACCEPTABLE", "NOT_ACCEPTABLE", "Los datos solo se pueden actulizar unsa sola vez por día"),
    VALIDATE_MARRIED_PERSON(HttpStatus.BAD_REQUEST, "BAD_REQUEST", "BAD_REQUEST", "EL nombre del conyuge no puede estar vacio"),
    VALIDATE_MARRIED_AND_USE_HUSBAND_LAST_NAME_PERSON(HttpStatus.BAD_REQUEST, "BAD_REQUEST", "BAD_REQUEST", "El apellido del conyuge no puede estar vacio"),
    VALIDATE_USE_HUSBAND_LAST_NAME_PERSON(HttpStatus.BAD_REQUEST, "BAD_REQUEST", "BAD_REQUEST", "El campo usa apellido del conyuge no puede estar vacio"),
    REFERENCE_INVALID(HttpStatus.BAD_REQUEST, "BAD_REQUEST","BAD_REQUEST" , "Los datos de la referencia estan incompletos"),
    INCOME_LEVEL_NOT_EXIST(HttpStatus.BAD_REQUEST, "BAD_REQUEST","BAD_REQUEST" , "El código de nivel de ingreso no existe"),
    INCOME_SOURCE_NOT_EXIST(HttpStatus.BAD_REQUEST, "BAD_REQUEST","BAD_REQUEST" , "El código de fuente de ingreso no existe"),
    POSITION_NOT_EXIST(HttpStatus.BAD_REQUEST, "BAD_REQUEST","BAD_REQUEST" , "El código de cargo no existe"),
    ECONOMIC_ACTIVITY_NOT_EXIST(HttpStatus.BAD_REQUEST, "BAD_REQUEST","BAD_REQUEST" , "El código de actividad económica no existe"),
    COMPANY_NAME_NOT_NULL(HttpStatus.BAD_REQUEST, "BAD_REQUEST","BAD_REQUEST" , "El nombre de la compañia no debe ser nulo"),
    CITY_CODE_NOT_EXIST(HttpStatus.BAD_REQUEST, "BAD_REQUEST" , "BAD_REQUEST","El código de ciudad no existe" );

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
