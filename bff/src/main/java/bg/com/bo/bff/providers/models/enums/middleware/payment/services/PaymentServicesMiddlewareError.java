package bg.com.bo.bff.providers.models.enums.middleware.payment.services;


import bg.com.bo.bff.providers.models.interfaces.middleware.IMiddlewareError;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum PaymentServicesMiddlewareError implements IMiddlewareError {
    MDWPSM_001(HttpStatus.INTERNAL_SERVER_ERROR, "INTERNAL_SERVER_ERROR", "MDWPSM-001", "Hubo un problema al consultar los servicios."),
    MDWPSM_002(HttpStatus.NOT_FOUND, "NOT_FOUND_CATEGORIES", "MDWPSM-002", "No se encontraron registros."),
    MDWPSM_003(HttpStatus.NOT_FOUND, "NOT_FOUND_SUBCATEGORIES", "MDWPSM-003", "No se encontraron registros."),
    MDWPSM_004(HttpStatus.NOT_FOUND, "NOT_FOUND_CITIES", "MDWPSM-004", "No se encontraron registros."),
    MDWPSM_005(HttpStatus.NOT_ACCEPTABLE, "NOT_ACCEPTABLE", "MDWPSM-005", "No tiene servicios afiliados"),
    MDWPSM_006(HttpStatus.BAD_REQUEST, "BAD_REQUEST", "MDWPSM-006", "Parámetros inválidos."),
    MDWPSM_007(HttpStatus.NOT_ACCEPTABLE, "NOT_ACCEPTABLE", "MDWPSM-007", "No tiene servicios."),
    MDWPSM_009(HttpStatus.NOT_ACCEPTABLE, "ERROR_REGISTER_INTEGRATION", "MDWPSM-009", "Ocurrio un error al registar el pago."),
    MDWPSM_010(HttpStatus.BAD_REQUEST, "NOT_FOUND_SEARCH_CRITERIA", "MDWPSM-010", "No se encontraron registros."),
    MDWPSM_011(HttpStatus.NOT_ACCEPTABLE, "ERROR_GETTING_SERVICES", "MDWPSM-011", "Ocurrio un error al obtener los servicios afiliados"),
    MDWPSM_013(HttpStatus.BAD_REQUEST, "ERROR_REGISTER_AFFILIATION", "MDWPSM-013", "Ocurrio un error en el proceso de afiliación"),
    MDWPSM_014(HttpStatus.NOT_ACCEPTABLE, "ERROR_GETTING_DEBTS", "MDWPSM-014", "No tiene deudas el servicio afiliado"),
    MDWPSM_015(HttpStatus.INTERNAL_SERVER_ERROR, "NOT_FOUND", "MDWPSM-015", "Ocurrio un error al obtener los datos."),
    MDWPSM_016(HttpStatus.NOT_ACCEPTABLE, "ERROR_GETTING_MORA", "MDWPSM-016", "Ocurrio un error al obtener la mora"),
    MDWPSM_017(HttpStatus.NOT_ACCEPTABLE, "NO_DEBTS_FOUND", "MDWPSM-017", "No tiene deudas el servicio afiliado"),
    MDWPSM_018(HttpStatus.NOT_ACCEPTABLE, "PAYMENT_COLLEGE_UNPROCESSED", "MDWPSM-018", "Ocurrio un error al obtener los datos."),
    MDWPSM_019(HttpStatus.NOT_ACCEPTABLE, "ERROR_DELETING_AFFILIATE", "MDWPSM-019", "Error al borrar el servicio afiliado"),
    MDWPSM_020(HttpStatus.NOT_FOUND, "NOT_FOUND_DATA_IN_REDIS", "MDWPSM-020", "No se encontraron registros."),
    MDWPSM_021(HttpStatus.BAD_REQUEST, "ERROR_VERIFY_POWER_TRX", "MDWPSM-021", "Pendiente para aprovación de poderes."),
    MDWPSM_022(HttpStatus.BAD_REQUEST, "AMOUNT_LIMIT_KO", "MDWPSM-022", "No se pudo realizar el pago de servicio, límites de monto han sido superados"),
    MDWPSM_023(HttpStatus.BAD_REQUEST, "NOT_FOUND_AVAILABLE", "MDWPSM-023", "Error en la conversión del monto."),
    MDWPSM_025(HttpStatus.BAD_REQUEST, "INVALID_CURRENCY", "MDWPSM-025", "Código de moneda incorrecto."),
    MDWPSM_026(HttpStatus.BAD_REQUEST, "SOURCE_ACCOUNT_KO_026", "MDWPSM-026", "No existe la cuenta de origen."),
    MDWPSM_029(HttpStatus.BAD_REQUEST, "SOURCE_ACCOUNT_KO_029", "MDWPSM-029", "La cuenta de origen no tiene permitido los débitos."),
    MDWPSM_030(HttpStatus.BAD_REQUEST, "SELECT_FIRST_DEBT", "MDWPSM-030", "Debe seleccionar la primera cuota."),
    MDWPSM_033(HttpStatus.INTERNAL_SERVER_ERROR, "ERROR_OBTAINING_ITF", "MDWPSM-033", "Ocurrio un error al obtener los datos."),
    MDWPSM_034(HttpStatus.INTERNAL_SERVER_ERROR, "NO_COMBINATIONS", "MDWPSM-034", "Ocurrio un error al obtener los datos."),
    MDWPSM_035(HttpStatus.BAD_REQUEST, "SOURCE_ACCOUNT_KO_035", "MDWPSM-035", "No se puede procesar la transacción para la cuenta de origen."),
    MDWPSM_036(HttpStatus.BAD_REQUEST, "QUANTITY_LIMIT_KO", "MDWPSM-036", "No se pudo realizar el pago de servicio, límites de cantidad han sido superados"),
    MDWPSM_037(HttpStatus.INTERNAL_SERVER_ERROR, "UPDATE_QUANTITY_LIMIT_KO", "MDWPSM-037", "No se pudo realizar el pago de servicio, error al actualizar el límite diario"),
    MDWPSM_038(HttpStatus.INTERNAL_SERVER_ERROR, "INVALID_CHANNEL", "MDWPSM-038", "Canal no válido para la billetera."),
    MDWPSM_039(HttpStatus.BAD_REQUEST, "SOURCE_ACCOUNT_KO_039", "MDWPSM-039", "La cuenta no tiene fondos"),
    MDWPSM_043(HttpStatus.BAD_REQUEST, "CANNOT_PROCESSED_AUTHORIZATION", "MDWPSM-043", "La trasacción no pudo ser procesada."),
    MDWPSM_046(HttpStatus.NOT_ACCEPTABLE, "ERROR_PAYMENT_DEBT", "MDWPSM-046", "Ocurrio un error al procesar el pago"),
    MDWPSM_047(HttpStatus.BAD_REQUEST, "NOT_ACCEPTABLE", "MDWPSM-047", "Afiliación duplicada. Ya existe la afiliación"),
    MDWPSM_049(HttpStatus.BAD_REQUEST, "ERROR_GETTING_TRASACCION_NUMBER", "MDWPSM-049", "Ocurrio un error al obtener los datos."),
    MDWPSM_050(HttpStatus.INTERNAL_SERVER_ERROR, "ERROR_REGISTER_REQUEST_PDS", "MDWPSM-050", "Ocurrio un error al obtener los datos."),
    MDWPSM_051(HttpStatus.INTERNAL_SERVER_ERROR, "ERROR_POWER_VALIDATION", "MDWPSM-051", "La trasacción no pudo ser procesada.");

    private final HttpStatus httpCode;
    private final String code;
    private final String codeMiddleware;
    private final String message;
}
