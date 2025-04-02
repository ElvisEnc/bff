package bg.com.bo.bff.commons.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CodeError {
    TRANSACTION_NOT_ALLOWED("TRANSACTION_NOT_ALLOWED", "No se encuentra habilitado las transacciones en moneda extranjera."),
    TRANSFER_DUPLICATE("TRANSFER_DUPLICATE", "Transferencia duplicada"),
    CRYPTO_FOUND("CRYPTO_FOUND", "Transferencia relacionado a criptomonedas"),
    QUANTITY_LIMIT_KO("QUANTITY_LIMIT_KO", "Límite de cantidad ha sido superado"),
    AMOUNT_LIMIT_KO("AMOUNT_LIMIT_KO", "El límite de monto ha sido superado"),
    ERROR_ITF("ERROR_ITF", "Error al obtener monto ITF"),
    VALIDATE_COMBINATION("VALIDATE_COMBINATION", "Se requiere la autorización de otro firmante"),
    FUNDS_REQUIRED("FUNDS_REQUIRED", "Es necesario indicar el origen y destino de los fondos"),
    ACCOUNT_REGISTERED("ACCOUNT_REGISTERED", "La cuenta ya esta registrada"),
    ACCOUNT_BLOCKED("ACCOUNT_BLOCKED", "No es posible utilizar esta cuenta"),
    NO_FUNDS_AVAILABLE("NO_FUNDS_AVAILABLE", "Verifica el saldo disponible"),
    ERROR_LOADING("ERROR_LOADING", "No se pudo cargar la información necesaria"),
    ERROR_TRANSFER_DUPLICATE("ERROR_TRANSFER_DUPLICATE", "Transferencia duplicada"),
    INVALID_PARAMS("INVALID_PARAMS", "Error en los parámetros, pueden ser los del header o path"),
    INVALID_DATA("INVALID_DATA", "Datos inválidos, ya sea desde el front o postman"),
    INVALID_VERSION("INVALID_VERSION", "Versión inválida, no se puede ejecutar la petición"),
    INVALID_ACTION("INVALID_ACTION", "Acción inválida de la petición"),
    INVALID_USER_DATA("INVALID_USER_DATA", "Datos inválidos del usuario, ya sea desde el front o postman"),
    ERROR_PROCEDURE("ERROR_PROCEDURE", "Error ocurrido en el proceso"),
    ERROR_MAE("ERROR_MAE", "Error ocurrido en el proceso MAE"),
    MAX_ATTEMPTS("MAX_ATTEMPTS", "Limite de intentos máximos alcanzados"),
    ACCESS_DENIED("ACCESS_DENIED", "Acceso denegado"),
    BLOCKED_USER("BLOCKED_USER", "Usuario Bloqueado"),
    ERROR_DB_PROCEDURE("ERROR_DB_PROCEDURE", "Error ocurrido en la BD durante el proceso"),
    ERROR_TOPAZ_PROCEDURE("ERROR_TOPAZ_PROCEDURE", "Error al ejecutar los servicios de topaz"),
    DATA_NOT_FOUND("DATA_NOT_FOUND", "Datos no encontrados"),
    ERROR_LOG("ERROR_LOG", "Error al registrar logs"),
    SOFTTOKEN_INCORRECT_DATA("INCORRECT_DATA", "Datos incorrectos"),
    ERROR_REMITTANCE("REMITTANCE_ERROR", "Datos incorrectos"),
    REMITTANCE_COLLETED("REMITTANCE_COLLETED", "Remesa pagada"),
    BAD_REQUEST("BAD_REQUEST", "Datos invalidos.");


    private final String code;
    private final String description;
}
