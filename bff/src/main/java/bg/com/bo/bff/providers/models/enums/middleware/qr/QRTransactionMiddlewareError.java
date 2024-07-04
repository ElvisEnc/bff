package bg.com.bo.bff.providers.models.enums.middleware.qr;

import bg.com.bo.bff.providers.models.interfaces.middleware.IMiddlewareError;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum QRTransactionMiddlewareError implements IMiddlewareError {

    //QR-TRANSACTION MANAGER
    MDWQTM_001(HttpStatus.BAD_REQUEST, "TRANSFER_USD_NOT_ALLOWED","MDWQTM-001","Esta transacción se encuentra deshabilitada temporalmente"),
    MDWQTM_002(HttpStatus.BAD_REQUEST, "TRANSFER_DUPLICATE","MDWQTM-002","Transacción duplicada"),
    MDWQTM_003(HttpStatus.BAD_REQUEST, "ERROR_REDIS_TRANSFER_DUPLICATE","MDWQTM-003","Error al Verificar Transacción Duplicada"),
    MDWQTM_004(HttpStatus.BAD_REQUEST, "ERROR_REDIS_REGISTER_TRANSFER_DUPLICATE","MDWQTM-004","Error al Registrar Transacciones Duplicadas"),
    MDWQTM_005(HttpStatus.BAD_REQUEST, "VALIDATE_COMBINATION","MDWQTM-005","No hay una combinación legal de poderes para el monto de la transacción"),
    MDWQTM_006(HttpStatus.BAD_REQUEST, "ERROR_DATA_ACCOUNT_ORIGIN","MDWQTM-006","Error al Obtener Datos de la Cuenta de Origen"),
    MDWQTM_007(HttpStatus.BAD_REQUEST, "INVALID_CURRENCY","MDWQTM-007","Moneda Incorrecta"),
    MDWQTM_008(HttpStatus.BAD_REQUEST, "ERROR_CONVERTION_AMOUNT","MDWQTM-008","Error al Convertir el Monto"),
    MDWQTM_009(HttpStatus.BAD_REQUEST, "ERROR_ITF","MDWQTM-009","Error al Obtener el ITF"),
    MDWQTM_010(HttpStatus.BAD_REQUEST, "VALIDATE_LIMITS","MDWQTM-010","Límite de Monto Excedido"),
    MDWQTM_011(HttpStatus.BAD_REQUEST, "NO_FOUNDS_AVAILABLE","MDWQTM-011","Fondos No Disponibles en la Cuenta"),
    MDWQTM_012(HttpStatus.BAD_REQUEST, "ERROR_NUMBER_PLEDGE","MDWQTM-012","Error al Obtener el Número de Reserva"),
    MDWQTM_013(HttpStatus.BAD_REQUEST, "ERROR_REGISTER_PLEDGE","MDWQTM-013","Error en el Compromiso y Registro de ACH"),
    MDWQTM_014(HttpStatus.BAD_REQUEST, "ERROR_NUMBER_ACH","MDWQTM-014","Error al Obtener el Número de Orden ACH"),
    MDWQTM_015(HttpStatus.BAD_REQUEST, "TRANSFER_ERROR","MDWQTM-015","Error en el Procesamiento de Transferencia"),
    MDWQTM_016(HttpStatus.BAD_REQUEST, "ACCOUNT_ORIGIN_BLOCKED_FOR_DEBIT","MDWQTM-016","La cuenta de origen no está autorizada para débitos"),
    MDWQTM_017(HttpStatus.BAD_REQUEST, "CRYPTO_ACTIVE","MDWQTM-017","La transacción no puede ser procesada, para validación de criptomonedas"),
    MDWQTM_018(HttpStatus.BAD_REQUEST, "ERROR_ROL_USER_EMPRESA","MDWQTM-018","La transacción no puede ser procesada, para el rol del usuario"),
    MDWQTM_019(HttpStatus.BAD_REQUEST, "ERROR_INSERT_MAE","MDWQTM-019","La transacción no puede ser procesada por inserción en mee"),
    MDWQTM_020(HttpStatus.BAD_REQUEST, "ERROR_INSERT_AUTHORIZATION","MDWQTM-020","La transacción no puede ser procesada para inserción en autorización"),
    MDWQTM_021(HttpStatus.BAD_REQUEST, "ERROR_VERIFY_POWER_TRX","MDWQTM-021","La transacción no puede ser procesada para verificar en la transacción de poder"),
    MDWQTM_022(HttpStatus.BAD_REQUEST, "ERROR_UPDATE_MAE","MDWQTM-022","La transacción no puede ser procesada por actulización de mee"),
    MDWQTM_023(HttpStatus.BAD_REQUEST, "QUANTITY_LIMIT_FOR_DAY","MDWQTM-023","Cantidad de transacciones excedida"),
    MDWQTM_024(HttpStatus.BAD_REQUEST, "UPDATE_QUANTITY_LIMIT_FOR_DAY","MDWQTM-024","Actualizar la cantidad límite"),
    MDWQTM_025(HttpStatus.BAD_REQUEST, "ERROR_NUMBER_TRANSACTION","MDWQTM-025","Error al Obtener el Número de Transacción"),
    MDWQTM_026(HttpStatus.BAD_REQUEST, "ERROR_OBTAIN_MAE","MDWQTM-026","La transacción no puede ser procesada por la obtencion de la mae"),
    MDWQTM_027(HttpStatus.BAD_REQUEST, "ERROR_ACH_DATA","MDWQTM-027","Error al obtener el código de la cámara de la transacción"),
    MDWQTM_028(HttpStatus.BAD_REQUEST, "ERROR_RELEASE_PLEDGE","MDWQTM-028","Error al liberar la reserva"),
    MDWQTM_029(HttpStatus.BAD_REQUEST, "ERROR_REGISTER_MASTER_QR","MDWQTM-029","Error al registrar QR Master"),
    MDWQTM_030(HttpStatus.BAD_REQUEST, "ERROR_ACH_EXPRESS","MDWQTM-030","Error al enviar la orden express"),
    MDWQTM_031(HttpStatus.BAD_REQUEST, "ACCOUNT_DESTINY_BLOCKED_FOR_CREDIT","MDWQTM-031","La cuenta de destino no está autorizada para recibir créditos"),
    MDWQTM_032(HttpStatus.BAD_REQUEST, "ERROR_DATA_ACCOUNT_DESTINY","MDWQTM-032","Error al Obtener los Datos de la Cuenta de Destino"),
    MDWQTM_033(HttpStatus.BAD_REQUEST, "ERROR_ACCOUNTS_EQUALS","MDWQTM-033","No se puede procesar la transacción para la misma cuenta de origen y destino"),
    MDWQTM_034(HttpStatus.BAD_REQUEST, "ERROR_ACCOUNTS_PERSON","MDWQTM-034","No se puede procesar la transacción para la cuenta de origen"),
    MDWQTM_035(HttpStatus.BAD_REQUEST, "ERROR_POWER_OLD","MDWQTM-035","La transacción no puede ser procesada, para verificar en poderes"),
    MDWQTM_036(HttpStatus.BAD_REQUEST, "NOT_URL_INVALID","MDWQTM-036","URL inválida"),
    MDWQTM_037(HttpStatus.BAD_REQUEST, "ERROR_DUE_DATE","MDWQTM-037","No se puede procesar la transacción para la fecha de vencimiento"),
    MDWQTM_038(HttpStatus.BAD_REQUEST, "INVALID_CHANNEL","MDWQTM-038","Canal inválido para transacción a cuenta billetera"),
    MDWQTM_039(HttpStatus.BAD_REQUEST, "FUNDS_REQUIRED","MDWQTM-039","El origen y destino de fondos son requeridos"),
    MDWQTM_040(HttpStatus.BAD_REQUEST, "QR_HAS_BEEN_USED","MDWQTM-040","El QR leído ya fue utilizado"),
    MDWGQM_PENDING(HttpStatus.OK, "TRANSFER_PENDING", "MDWPGL-PENDING", "La transferencia está pendiente de aprobación por el titular. Por favor, espera la confirmación");

    private final HttpStatus httpCode;
    private final String code;
    private final String codeMiddleware;
    private final String message;
}
