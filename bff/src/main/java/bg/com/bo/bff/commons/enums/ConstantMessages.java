package bg.com.bo.bff.commons.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ConstantMessages {
    SUCCESS("SUCCESS", "SUCCESS"),
    ERROR("ERROR", "ERROR"),
    GENERIC("Tuvimos un problema interno. Inténtalo nuevamente.", "Ocurrió un problema"),
    TRANSFER_DUPLICATE("Tienes un pago similar realizado hace poco. ¿Quieres repetir el pago con el mismo monto?", "Pago duplicado"),
    CRYPTO_FOUND("Estimado Cliente: las plataformas transaccionales y la compraventa de Activos Virtuales no está bajo control, supervisión ni regulación de ASFI. Estas transacciones son a riesgo del cliente. Gracias.", "Transacción no permitida"),
    QUANTITY_LIMIT_KO("Límite de cantidad ha sido superado para esta transacción.", "Límite de cantidad excedido"),
    AMOUNT_LIMIT_KO("El límite de monto ha sido superado para esta transacción.", "Límite de monto excedido"),
    NO_FUNDS_AVAILABLE("Verifica el saldo disponible e intenta nuevamente.", "Saldo insuficiente"),
    ACCOUNT_REGISTERED("La cuenta ya se encuentra registrada.", "Cuenta registrada"),
    VALIDATE_COMBINATION("Se requiere la autorización de otro firmante para completar la transacción.", "Transacción pendiente"),
    ACCOUNT_BLOCKED("No es posible utilizar esta cuenta para realizar esta transacción.", "Transacción no disponible"),
    FUNDS_REQUIRED("Es necesario indicar el origen y destino de los fondos.", "Origen y destino de fondos requeridos"),
    TRANSACTION_NOT_ALLOWED("Las transacciones en moneda extranjera están temporalmente deshabilitadas.", "Transacción no disponible"),
    INVALID_LOGIN_DATA("No se pudo validar los datos, acércate a una agencia cercana.", "Datos inválidos"),
    INVALID_USER_DATA("Verifica tu código de persona y contraseña.", "Datos de usuario inválidos"),
    INVALID_ACCOUNT("La cuenta es inválida para este flujo.", "Cuenta inválida"),
    MAX_ATTEMPTS("Se ha alcanzado el número máximo de intentos permitidos. Inténtalo nuevamente más tarde.", "Intentos máximos alcanzados"),
    NO_FOUND_DATA("No se encontraron registros para la petición.", "Datos no encontrados"),
    SOFTTOKEN_DISABLED("Usuario sin ganapin habilitado, presentando algun error", "GanaPin deshabilitado"),
    MODIFY_ERROR("No se pudo realizar la modificación, intente más tarde.", "Ocurrió un problema"),
    GENERIC_INVALID_PARAMS("Verifica que los datos enviados esten correctos.", "Datos Invalidos"),
    NOT_INFORMATION_OBTAINED("No se pudo obtener información, con los datos proporcionados.", "Datos no encontrados"),
    TRANSACTION_COULD_NOT_BE_PROCESSED("La transacción no se pudo procesar.", "Transaccion no procesada.");

    private final String message;
    private final String title;
}
