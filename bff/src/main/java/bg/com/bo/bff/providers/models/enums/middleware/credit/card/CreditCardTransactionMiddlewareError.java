package bg.com.bo.bff.providers.models.enums.middleware.credit.card;

import bg.com.bo.bff.commons.enums.CategoryError;
import bg.com.bo.bff.commons.enums.CodeError;
import bg.com.bo.bff.commons.enums.ConstantMessages;
import bg.com.bo.bff.providers.models.interfaces.middleware.IMiddlewareError;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum CreditCardTransactionMiddlewareError implements IMiddlewareError {
    MDWCCTM_001(
            HttpStatus.INTERNAL_SERVER_ERROR, "ERROR_PROCEDURE", "MDWCCTM-001",
            ConstantMessages.GENERIC.getTitle(),
            "Ocurrio un error en el proceso",
            CategoryError.MW_GENERIC_INTERNAL_FAILURE.getCategoryId()),
    MDWCCTM_002(
            HttpStatus.INTERNAL_SERVER_ERROR, "TRANSFER_ERROR", "MDWCCTM-002",
            ConstantMessages.GENERIC.getTitle(),
            "Ocurrió un error en el proceso de pago",
            CategoryError.MW_GENERIC_INTERNAL_FAILURE.getCategoryId()),
    MDWCCTM_003(
            HttpStatus.BAD_REQUEST, "ERROR_DATA_ACCOUNT_ORIGIN", "MDWCCTM-003",
            ConstantMessages.GENERIC.getTitle(),
            "Error al obtener los datos de la cuenta",
            CategoryError.MW_GENERIC_INTERNAL_FAILURE.getCategoryId()),
    MDWCCTM_004(
            HttpStatus.NOT_ACCEPTABLE, "ORIGIN_ACCOUNT_UNAVAILABLE_FOR_DEBIT", "MDWCCTM-004",
            ConstantMessages.GENERIC.getTitle(),
            "No se puede debitar desde la cuenta de origen, ya que fue configurada para no realizar debito.",
            CategoryError.MW_SPECIFIC_INTERNAL_FAILURE.getCategoryId()),
    MDWCCTM_005(
            HttpStatus.BAD_REQUEST, "ERROR_CONVERTION_AMOUNT", "MDWCCTM-005",
            ConstantMessages.GENERIC.getTitle(),
            "Error al convertir el monto",
            CategoryError.MW_SPECIFIC_INTERNAL_FAILURE.getCategoryId()),
    MDWCCTM_006(
            HttpStatus.NOT_ACCEPTABLE, "ERROR_ITF", "MDWCCTM-006",
            ConstantMessages.GENERIC.getTitle(),
            "Error al obtener el ITF",
            CategoryError.MW_SPECIFIC_INTERNAL_FAILURE.getCategoryId()),
    MDWCCTM_007(
            HttpStatus.BAD_REQUEST, "NO_FUNDS_AVAILABLE", "MDWCCTM-007",
            ConstantMessages.GENERIC.getTitle(),
            "Error, no tiene fondos suficientes",
            CategoryError.MW_SPECIFIC_INTERNAL_FAILURE.getCategoryId()),
    MDWCCTM_008(
            HttpStatus.BAD_REQUEST, "AMOUNT_LIMIT_KO", "MDWCCTM-008",
            ConstantMessages.GENERIC.getTitle(),
            "No se pudo realizar la transferencia, límites de monto superados.",
            CategoryError.MW_SPECIFIC_INTERNAL_FAILURE.getCategoryId()),
    MDWCCTM_009(
            HttpStatus.NOT_ACCEPTABLE, "VALIDATE_COMBINATION", "MDWCCTM-009",
            ConstantMessages.GENERIC.getTitle(),
            "No hay combinaciones de poderes para el pago.",
            CategoryError.MW_SPECIFIC_INTERNAL_FAILURE.getCategoryId()),
    MDWCCTM_010(
            HttpStatus.BAD_REQUEST, "NOT_FOUND_DATA", "MDWCCTM-010",
            ConstantMessages.GENERIC.getTitle(),
            "Datos no encontrados",
            CategoryError.MW_SPECIFIC_INTERNAL_FAILURE.getCategoryId()),
    MDWCCTM_011(
            HttpStatus.BAD_REQUEST, "ACCOUNT_NOT_PERMIT", "MDWCCTM-011",
            ConstantMessages.GENERIC.getTitle(),
            "El estado de la cuenta no permite el pago por este canal.",
            CategoryError.MW_SPECIFIC_INTERNAL_FAILURE.getCategoryId()),
    MDWCCTM_012(
            HttpStatus.NOT_ACCEPTABLE, "TRANSFER_DUPLICATE", "MDWCCTM-012",
            ConstantMessages.GENERIC.getTitle(),
            "No se pudo verificar la transacción duplicada",
            CategoryError.MW_SPECIFIC_INTERNAL_FAILURE.getCategoryId()),
    MDWCCTM_013(
            HttpStatus.NOT_ACCEPTABLE, "TRANSFER_DUPLICATE", "MDWCCTM-013",
            ConstantMessages.GENERIC.getTitle(),
            "El pago ya ha sido realizado anteriormente",
            CategoryError.MW_SPECIFIC_INTERNAL_FAILURE.getCategoryId()),
    MDWCCTM_014(
            HttpStatus.NOT_ACCEPTABLE, "ERROR_TRANSFER_DUPLICATE", "MDWCCTM-014",
            ConstantMessages.GENERIC.getTitle(),
            "Error al registrar una transacción duplicada",
            CategoryError.MW_SPECIFIC_INTERNAL_FAILURE.getCategoryId()),
    MDWCCTM_015(
            HttpStatus.INTERNAL_SERVER_ERROR, "ERROR_TICKET", "MDWCCTM-015",
            ConstantMessages.GENERIC.getTitle(),
            "Ocurrio un error con la conexión con Linkser",
            CategoryError.MW_SPECIFIC_INTERNAL_FAILURE.getCategoryId()),
    MDWCCTM_016(
            HttpStatus.INTERNAL_SERVER_ERROR, "TICKET_EQUALS", "MDWCCTM-016",
            ConstantMessages.GENERIC.getTitle(),
            "Ocurrio un error al obtener en el proceso",
            CategoryError.MW_SPECIFIC_INTERNAL_FAILURE.getCategoryId()),
    MDWCCTM_017(
            HttpStatus.BAD_REQUEST, CodeError.BAD_REQUEST.getCode(), "MDWCCTM-017",
            ConstantMessages.GENERIC.getTitle(),
            "El nombre del esquema debe ser CompanyId para los canales 1 - 6",
            CategoryError.MW_SPECIFIC_INTERNAL_FAILURE.getCategoryId()),
    MDWCCTM_018(
            HttpStatus.BAD_REQUEST, CodeError.BAD_REQUEST.getCode(), "MDWCCTM-018",
            ConstantMessages.GENERIC.getTitle(),
            "Datos invalidos para companyId",
            CategoryError.MW_SPECIFIC_INTERNAL_FAILURE.getCategoryId()),
    MDWCCTM_019(
            HttpStatus.BAD_REQUEST, CodeError.BAD_REQUEST.getCode(), "MDWCCTM-019",
            ConstantMessages.GENERIC.getTitle(),
            "El nombre del esquema debe ser PersonId para el canal 2",
            CategoryError.MW_SPECIFIC_INTERNAL_FAILURE.getCategoryId()),
    MDWCCTM_020(
            HttpStatus.BAD_REQUEST, "GET_CURRENCY_BY_TRANSACTION_TYPE_INVALID", "MDWCCTM-020",
            ConstantMessages.GENERIC.getTitle(),
            "Error al obtener la divisa por tipo de transacción",
            CategoryError.MW_SPECIFIC_INTERNAL_FAILURE.getCategoryId()),
    MDWCCTM_021(
            HttpStatus.BAD_REQUEST, "CONVERTING_DATE_FORMAT", "MDWCCTM-021",
            ConstantMessages.GENERIC.getTitle(),
            "Error al convertir el formato de fecha",
            CategoryError.MW_SPECIFIC_INTERNAL_FAILURE.getCategoryId()),
    MDWCCTM_022(
            HttpStatus.BAD_REQUEST, "NO_FUNDS_AVAILABLE", "MDWCCTM-022",
            ConstantMessages.GENERIC.getTitle(),
            "No hay fondos disponibles en la cuenta",
            CategoryError.MW_SPECIFIC_INTERNAL_FAILURE.getCategoryId()),
    MDWCCTM_023(
            HttpStatus.INTERNAL_SERVER_ERROR, "ERROR_INSERT_MAE", "MDWCCTM-023",
            ConstantMessages.GENERIC.getTitle(),
            ConstantMessages.TRANSACTION_COULD_NOT_BE_PROCESSED.getMessage(),
            CategoryError.MW_SPECIFIC_INTERNAL_FAILURE.getCategoryId()),
    MDWCCTM_024(
            HttpStatus.INTERNAL_SERVER_ERROR, "ERROR_INSERT_AUTHORIZATION", "MDWCCTM-024",
            ConstantMessages.GENERIC.getTitle(),
            "Transacción no puede ser procesada, para insertar en autorización",
            CategoryError.MW_SPECIFIC_INTERNAL_FAILURE.getCategoryId()),
    MDWCCTM_025(
            HttpStatus.INTERNAL_SERVER_ERROR, "ERROR_UPDATE_MAE", "MDWCCTM-025",
            ConstantMessages.GENERIC.getTitle(),
            ConstantMessages.TRANSACTION_COULD_NOT_BE_PROCESSED.getMessage(),
            CategoryError.MW_SPECIFIC_INTERNAL_FAILURE.getCategoryId()),
    MDWCCTM_026(
            HttpStatus.BAD_REQUEST, "QUANTITY_LIMIT_KO", "MDWCCTM-026",
            ConstantMessages.GENERIC.getTitle(),
            "No se pudo realizar la transferencia, límites de cantidad superados",
            CategoryError.MW_SPECIFIC_INTERNAL_FAILURE.getCategoryId()),
    MDWCCTM_027(
            HttpStatus.INTERNAL_SERVER_ERROR, "ERROR_NUMBER_TRANSACTION", "MDWCCTM-027",
            ConstantMessages.GENERIC.getTitle(),
            "Ocurrio un error al obtener el número de transacción",
            CategoryError.MW_SPECIFIC_INTERNAL_FAILURE.getCategoryId()),
    MDWCCTM_028(
            HttpStatus.INTERNAL_SERVER_ERROR, "ERROR_OBTAIN_MAE", "MDWCCTM-028",
            ConstantMessages.GENERIC.getTitle(),
            ConstantMessages.TRANSACTION_COULD_NOT_BE_PROCESSED.getMessage(),
            CategoryError.MW_SPECIFIC_INTERNAL_FAILURE.getCategoryId()),
    MDWCCTM_029(
            HttpStatus.NOT_ACCEPTABLE, "ERROR_ACCOUNTS_PERSON", "MDWCCTM-029",
            ConstantMessages.GENERIC.getTitle(),
            "No se puede procesar la transacción para la cuenta de origen",
            CategoryError.MW_SPECIFIC_INTERNAL_FAILURE.getCategoryId()),
    MDWCCTM_030(
            HttpStatus.NOT_ACCEPTABLE, "ERROR_POWER_VALIDATION", "MDWCCTM-030",
            ConstantMessages.GENERIC.getTitle(),
            "La transferencia no puede ser procesada, por la validación de poderes",
            CategoryError.MW_SPECIFIC_INTERNAL_FAILURE.getCategoryId()),
    MDWCCTM_031(
            HttpStatus.INTERNAL_SERVER_ERROR, "ERROR_WSTCPP", "MDWCCTM-031",
            ConstantMessages.GENERIC.getTitle(),
            "Error obtener respuesta o conexión servicio WSTCPP",
            CategoryError.MW_SPECIFIC_INTERNAL_FAILURE.getCategoryId()),
    MDWCCTM_032(
            HttpStatus.INTERNAL_SERVER_ERROR, "ERROR_UPDATE_REQUEST_PAYMENT_SP", "MDWCCTM-032",
            ConstantMessages.GENERIC.getTitle(),
            "Error al ejecutar la solicitud de actualización de pago",
            CategoryError.MW_SPECIFIC_INTERNAL_FAILURE.getCategoryId()),
    MDWCCTM_033(
            HttpStatus.BAD_REQUEST, CodeError.BAD_REQUEST.getCode(), "MDWCCTM-033",
            ConstantMessages.GENERIC.getTitle(),
            "No se puede procesar el pago, verifique las divisas",
            CategoryError.MW_SPECIFIC_INTERNAL_FAILURE.getCategoryId()),
    MDWCCTM_034(
            HttpStatus.BAD_REQUEST, CodeError.BAD_REQUEST.getCode(), "MDWCCTM-034",
            ConstantMessages.GENERIC.getTitle(),
            "Codigo de monedas es incorrecto",
            CategoryError.MW_SPECIFIC_INTERNAL_FAILURE.getCategoryId()),
    MDWCCTM_035(
            HttpStatus.INTERNAL_SERVER_ERROR, "ERROR_VERIFY_TICKET", "MDWCCTM-035",
            ConstantMessages.GENERIC.getTitle(),
            "Error al intentar verificar el ticket",
            CategoryError.MW_SPECIFIC_INTERNAL_FAILURE.getCategoryId()),
    MDWCCTM_038(
            HttpStatus.BAD_REQUEST, CodeError.BAD_REQUEST.getCode(), "MDWCCTM-038",
            ConstantMessages.GENERIC.getTitle(),
            "No se puede procesar la transacción, para el usuario rol",
            CategoryError.MW_SPECIFIC_INTERNAL_FAILURE.getCategoryId()),
    MDWCCTM_039(
            HttpStatus.NOT_ACCEPTABLE, "ERROR_VERIFY_POWER_TRX", "MDWCCTM-039",
            ConstantMessages.GENERIC.getTitle(),
            "No se han podido verificar los poderes en TRX, no se puede procesar la transacción",
            CategoryError.MW_SPECIFIC_INTERNAL_FAILURE.getCategoryId()),

    PENDING(
            HttpStatus.OK, "TRANSFER_PENDING", "MDWPGL-PENDING",
            ConstantMessages.GENERIC.getTitle(),
            "El pago de la tarjeta de crédito está pendiente de aprobación por el titular. Por favor, " +
                    "espera la confirmación",
            CategoryError.MW_SPECIFIC_INTERNAL_FAILURE.getCategoryId());

    private final HttpStatus httpCode;
    private final String code;
    private final String codeMiddleware;
    private final String title;
    private final String message;
    private final int categoryId;
}
