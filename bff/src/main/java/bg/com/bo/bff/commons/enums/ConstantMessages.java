package bg.com.bo.bff.commons.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ConstantMessages {
    GENERIC("Tuvimos un problema interno. Inténtalo nuevamente.", "Ocurrió un problema"),
    TRANSFER_DUPLICATE("Tienes un pago similar realizado hace poco. ¿Quieres repetir el pago con el mismo monto?", "Pago duplicado"),
    CRYPTO_FOUND("Estimado Cliente: las plataformas transaccionales y la compraventa de Activos Virtuales no está bajo control, supervisión ni regulación de ASFI. Estas transacciones son a riesgo del cliente. Gracias.", "Transacción no permitida"),
    QUANTITY_LIMIT_KO("Límite de cantidad ha sido superado para esta transacción.", "Límite de cantidad excedido"),
    AMOUNT_LIMIT_KO("El límite de monto ha sido superado para esta transacción.", "Límite de monto excedido"),
    NO_FUNDS_AVAILABLE("Verifica el saldo disponible e intenta nuevamente.", "Saldo insuficiente"),
    VALIDATE_COMBINATION("Se requiere la autorización de otro firmante para completar la transacción.", "Transacción pendiente"),
    ACCOUNT_BLOCKED("No es posible utilizar esta cuenta para realizar esta transacción.", "Transacción no disponible"),
    FUNDS_REQUIRED("Es necesario indicar el origen y destino de los fondos.", "Origen y destino de fondos requeridos"),
    TRANSACTION_NOT_ALLOWED("Las transacciones en moneda extranjera están temporalmente deshabilitadas.", "Transacción no disponible"),
    INVALID_LOGIN_DATA("No se pudo validar los datos, acércate a una agencia cercana.", "Datos inválidos"),
    INVALID_USER_DATA("Verifica tu código de persona y contraseña.", "Datos de usuario inválidos"),
    MAX_ATTEMPTS("Se ha alcanzado el número máximo de intentos permitidos. Inténtalo nuevamente más tarde.", "Intentos máximos alcanzados"),
    NO_FOUND_DATA("No se encontraron registros.", "Datos no encontrados"),
    SOFTTOKEN_DISABLED("GanaPin deshabilitado", "Usuario sin ganapin habilitado, presentando algun error");

    private final String message;
    private final String title;
}
