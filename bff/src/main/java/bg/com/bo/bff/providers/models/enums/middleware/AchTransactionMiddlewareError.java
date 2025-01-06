package bg.com.bo.bff.providers.models.enums.middleware;

import bg.com.bo.bff.commons.enums.CategoryError;
import bg.com.bo.bff.commons.enums.CodeError;
import bg.com.bo.bff.commons.enums.ConstantMessages;
import bg.com.bo.bff.providers.models.interfaces.middleware.IMiddlewareError;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum AchTransactionMiddlewareError implements IMiddlewareError {
    MDWACH_001(HttpStatus.CONFLICT, CodeError.TRANSACTION_NOT_ALLOWED.getCode(), "MDWACH-001", "Las transacciones en moneda extranjera están temporalmente deshabilitadas.", "Transacción no disponible", CategoryError.MW_GENERIC_FAIL_RESPONSE.getCategoryId()),
    MDWACH_002(HttpStatus.NOT_ACCEPTABLE, CodeError.TRANSFER_DUPLICATE.getCode(), "MDWACH-002", ConstantMessages.ACH_DUPLICATE.getMessage(), ConstantMessages.ACH_DUPLICATE.getTitle(), CategoryError.MW_SPECIFIC_INTERNAL_FAILURE.getCategoryId()),
    MDWACH_003(HttpStatus.NOT_ACCEPTABLE, CodeError.TRANSFER_DUPLICATE.getCode(), "MDWACH-003", "Ocurrio un problema interno al validar la transacción duplicada", "Ocurrió un problema", CategoryError.MW_SPECIFIC_INTERNAL_FAILURE.getCategoryId()),
    MDWACH_004(HttpStatus.NOT_ACCEPTABLE, CodeError.TRANSFER_DUPLICATE.getCode(), "MDWACH-004", "Ocurrio un problema interno al registrar la transacción duplicada", "Ocurrió un problema", CategoryError.MW_SPECIFIC_INTERNAL_FAILURE.getCategoryId()),
    MDWACH_005(HttpStatus.NOT_ACCEPTABLE, "VALIDATE_COMBINATION", "MDWACH-005", "Se requiere la autorización de otro firmante para completar la transacción.", "Transacción pendiente", CategoryError.MW_SPECIFIC_INTERNAL_FAILURE.getCategoryId()),
    MDWACH_006(HttpStatus.NOT_IMPLEMENTED, CodeError.INVALID_DATA.getCode(), "MDWACH-006", "Error al obtener la cuenta origen BD", "Error cuenta", CategoryError.MW_UNEXPECTED_FORMAT.getCategoryId()), // error al obtener la cuenta origen BD
    MDWACH_007(HttpStatus.NOT_IMPLEMENTED, CodeError.INVALID_DATA.getCode(), "MDWACH-007", "La moneda es invalida", "Error moneda", CategoryError.MW_UNEXPECTED_FORMAT.getCategoryId()), // moneda invalida
    MDWACH_008(HttpStatus.NOT_IMPLEMENTED, CodeError.ERROR_PROCEDURE.getCode(), "MDWACH-008", "Error al convertir la moneda, pcc01", "Error moneda", CategoryError.MW_UNEXPECTED_FORMAT.getCategoryId()), // error al convertir la moneda pcc01
    MDWACH_009(HttpStatus.NOT_ACCEPTABLE, CodeError.ERROR_PROCEDURE.getCode(), "MDWACH-009", ConstantMessages.GENERIC.getMessage(), ConstantMessages.GENERIC.getTitle(), CategoryError.MW_SPECIFIC_INTERNAL_FAILURE.getCategoryId()), // error get monto ITF
    MDWACH_010(HttpStatus.CONFLICT, "AMOUNT_LIMIT_KO", "MDWACH-010", "El límite de monto ha sido superado para esta transacción.", "Límite de monto excedido", CategoryError.MW_GENERIC_FAIL_RESPONSE.getCategoryId()),

    MDWACH_011(HttpStatus.CONFLICT, "NOT_FOUND_AVAILABLE", "MDWACH-011", "Verifica el saldo disponible e intenta nuevamente.", "Saldo insuficiente", CategoryError.MW_GENERIC_FAIL_RESPONSE.getCategoryId()),
    MDWACH_012(HttpStatus.NOT_ACCEPTABLE, CodeError.ERROR_PROCEDURE.getCode(), "MDWACH-012", ConstantMessages.GENERIC.getMessage(), ConstantMessages.GENERIC.getTitle(), CategoryError.MW_GENERIC_INTERNAL_FAILURE.getCategoryId()), // Error Getting Reserve Number
    MDWACH_013(HttpStatus.NOT_ACCEPTABLE, CodeError.ERROR_PROCEDURE.getCode(), "MDWACH-013", ConstantMessages.GENERIC.getMessage(), ConstantMessages.GENERIC.getTitle(), CategoryError.MW_GENERIC_INTERNAL_FAILURE.getCategoryId()), // ACH Pledge and Registration Error
    MDWACH_014(HttpStatus.NOT_ACCEPTABLE, CodeError.ERROR_PROCEDURE.getCode(), "MDWACH-014", ConstantMessages.GENERIC.getMessage(), ConstantMessages.GENERIC.getTitle(), CategoryError.MW_GENERIC_INTERNAL_FAILURE.getCategoryId()), // Error Getting ACH Order Number
    MDWACH_015(HttpStatus.NOT_ACCEPTABLE, "ERROR_TRANSFER", "MDWACH-015", ConstantMessages.GENERIC.getMessage(), ConstantMessages.GENERIC.getTitle(), CategoryError.MW_GENERIC_INTERNAL_FAILURE.getCategoryId()), // Error at Transfer Processing
    MDWACH_016(HttpStatus.CONFLICT, "SOURCE_ACCOUNT_KO_002", "MDWACH-016", "No es posible utilizar esta cuenta para realizar transacciones.", "Cuenta de origen inactiva", CategoryError.MW_GENERIC_FAIL_RESPONSE.getCategoryId()),
    MDWACH_017(HttpStatus.NOT_ACCEPTABLE, CodeError.ERROR_PROCEDURE.getCode(), "MDWACH-017", ConstantMessages.GENERIC.getMessage(), ConstantMessages.GENERIC.getTitle(), CategoryError.MW_GENERIC_INTERNAL_FAILURE.getCategoryId()), // Error Verifying Duplicated ACH Order
    MDWACH_018(HttpStatus.NOT_ACCEPTABLE, CodeError.ERROR_PROCEDURE.getCode(), "MDWACH-018", ConstantMessages.ACH_DUPLICATE.getMessage(), ConstantMessages.ACH_DUPLICATE.getTitle(), CategoryError.MW_GENERIC_INTERNAL_FAILURE.getCategoryId()), // Order ACH Duplicated
    MDWACH_019(HttpStatus.CONFLICT, "FUNDS_REQUIRED", "MDWACH-019", "Es necesario indicar el origen y destino de los fondos.", "Origen y destino de fondos requeridos", CategoryError.MW_GENERIC_FAIL_RESPONSE.getCategoryId()),
    MDWACH_020(HttpStatus.NOT_ACCEPTABLE, CodeError.ERROR_PROCEDURE.getCode(), "MDWACH-020", ConstantMessages.GENERIC.getMessage(), ConstantMessages.GENERIC.getTitle(), CategoryError.MW_GENERIC_INTERNAL_FAILURE.getCategoryId()), // Error getting Trusted List

    MDWACH_021(HttpStatus.NOT_IMPLEMENTED, CodeError.INVALID_DATA.getCode(), "MDWACH-021", "Tipo de cuenta inválida.", "Cuenta inválida", CategoryError.MW_UNEXPECTED_FORMAT.getCategoryId()),
    MDWACH_022(HttpStatus.NOT_IMPLEMENTED, CodeError.INVALID_DATA.getCode(), "MDWACH-022", "Parámetros de tamaño incorrectos", "Parámetros inválidos", CategoryError.MW_UNEXPECTED_FORMAT.getCategoryId()),
    MDWACH_023(HttpStatus.NOT_IMPLEMENTED, CodeError.INVALID_DATA.getCode(), "MDWACH-023", "Error en el nombre del esquema de la cuenta de crédito", "Ocurrió un problema", CategoryError.MW_UNEXPECTED_FORMAT.getCategoryId()),
    MDWACH_024(HttpStatus.NOT_ACCEPTABLE, CodeError.INVALID_DATA.getCode(), "MDWACH-024", "Error al obtener los datos de la cuenta de destino.", "Ocurrió un problema", CategoryError.MW_SPECIFIC_INTERNAL_FAILURE.getCategoryId()),
    MDWACH_025(HttpStatus.NOT_IMPLEMENTED, CodeError.INVALID_DATA.getCode(), "MDWACH-025", "Identificación secundaria inválida", "Identificación inválida", CategoryError.MW_UNEXPECTED_FORMAT.getCategoryId()),
    MDWACH_026(HttpStatus.NOT_IMPLEMENTED, CodeError.INVALID_DATA.getCode(), "MDWACH-026", "Identificación del documento inválida", "Identificación inválida", CategoryError.MW_UNEXPECTED_FORMAT.getCategoryId()),
    MDWACH_027(HttpStatus.NOT_IMPLEMENTED, CodeError.INVALID_DATA.getCode(), "MDWACH-027", "Nombre inválido", "Identificación inválida", CategoryError.MW_UNEXPECTED_FORMAT.getCategoryId()),
    MDWACH_028(HttpStatus.NOT_IMPLEMENTED, CodeError.INVALID_DATA.getCode(), "MDWACH-028", "Tamaño de los parámetros invalidos", "Parámetros inválidos", CategoryError.MW_UNEXPECTED_FORMAT.getCategoryId()),
    MDWACH_029(HttpStatus.CONFLICT, "CRYPTO_FOUND", "MDWACH-029", "Esta operación presenta relación con la comercialización de criptomonedas y no puede realizarse.", "Operación no permitida", CategoryError.MW_GENERIC_FAIL_RESPONSE.getCategoryId()),
    MDWACH_030(HttpStatus.NOT_IMPLEMENTED, CodeError.INVALID_USER_DATA.getCode(), "MDWACH-030", "Transacción no procesada, rol de usuario no válido", "Transacción no procesada", CategoryError.NO_HEADER_PARAMS_MW_ERROR.getCategoryId()),

    MDWACH_031(HttpStatus.NOT_ACCEPTABLE, "ERROR_MAE", "MDWACH-031", ConstantMessages.GENERIC.getMessage(), ConstantMessages.GENERIC.getTitle(), CategoryError.MW_GENERIC_INTERNAL_FAILURE.getCategoryId()), // error, la trasaccion no puedo ser procesada, for insert mae
    MDWACH_032(HttpStatus.NOT_ACCEPTABLE, CodeError.ERROR_PROCEDURE.getCode(), "MDWACH-032", ConstantMessages.GENERIC.getMessage(), ConstantMessages.GENERIC.getTitle(), CategoryError.MW_GENERIC_INTERNAL_FAILURE.getCategoryId()), // error, la trasaccion no puedo ser procesada, for insert in authorization
    MDWACH_033(HttpStatus.NOT_ACCEPTABLE, "ERROR_VERIFY_POWER_TRX", "MDWACH-033", ConstantMessages.GENERIC.getMessage(), ConstantMessages.GENERIC.getTitle(), CategoryError.MW_GENERIC_INTERNAL_FAILURE.getCategoryId()), // error, la trasaccion no puedo ser procesada, for verify in power trx
    MDWACH_034(HttpStatus.NOT_ACCEPTABLE, "ERROR_UPDATE_MAE", "MDWACH-034", ConstantMessages.GENERIC.getMessage(), ConstantMessages.GENERIC.getTitle(), CategoryError.MW_GENERIC_INTERNAL_FAILURE.getCategoryId()), // error, la trasaccion no puedo ser procesada, for update mae
    MDWACH_035(HttpStatus.NOT_ACCEPTABLE, "QUANTITY_LIMIT_KO", "MDWACH-035", "Límite de cantidad ha sido superado para esta transacción.", "Límite de cantidad excedido", CategoryError.MW_SPECIFIC_INTERNAL_FAILURE.getCategoryId()),
    MDWACH_036(HttpStatus.NOT_ACCEPTABLE, "UPDATE_QUANTITY_LIMIT_KO", "MDWACH-036", ConstantMessages.GENERIC.getMessage(), ConstantMessages.GENERIC.getTitle(), CategoryError.MW_GENERIC_INTERNAL_FAILURE.getCategoryId()), // Error internp al actualizar los limites
    MDWACH_037(HttpStatus.NOT_ACCEPTABLE, CodeError.ERROR_PROCEDURE.getCode(), "MDWACH-037", ConstantMessages.GENERIC.getMessage(), ConstantMessages.GENERIC.getTitle(), CategoryError.MW_GENERIC_INTERNAL_FAILURE.getCategoryId()), // Error interno al obtener el numero de transaccion
    MDWACH_038(HttpStatus.NOT_ACCEPTABLE, CodeError.ERROR_PROCEDURE.getCode(), "MDWACH-038", ConstantMessages.GENERIC.getMessage(), ConstantMessages.GENERIC.getTitle(), CategoryError.MW_GENERIC_INTERNAL_FAILURE.getCategoryId()), // Error interno al obtener el mae
    MDWACH_039(HttpStatus.INTERNAL_SERVER_ERROR, CodeError.ERROR_PROCEDURE.getCode(), "MDWACH-039", ConstantMessages.GENERIC.getMessage(), ConstantMessages.GENERIC.getTitle(), CategoryError.MW_GENERIC_INTERNAL_FAILURE.getCategoryId()), // Error interno en el procedure
    MDWACH_040(HttpStatus.NOT_IMPLEMENTED, CodeError.INVALID_PARAMS.getCode(), "MDWACH-040", "Error en la validación de parámetros.", "Validación de parámetros", CategoryError.UNKNOWN_MW_ERROR.getCategoryId()),

    MDWACH_041(HttpStatus.NOT_IMPLEMENTED, CodeError.INVALID_PARAMS.getCode(), "MDWACH-041", "Canal no implementado.", "Parámetros inválidos", CategoryError.NO_HEADER_PARAMS_MW_ERROR.getCategoryId()),
    MDWACH_042(HttpStatus.NOT_ACCEPTABLE, CodeError.INVALID_DATA.getCode(), "MDWACH-043", ConstantMessages.GENERIC.getMessage(), ConstantMessages.GENERIC.getTitle(), CategoryError.MW_GENERIC_INTERNAL_FAILURE.getCategoryId()), // Error al obtener la cuenta de origen
    MDWACH_043(HttpStatus.NOT_ACCEPTABLE, CodeError.INVALID_DATA.getCode(), "MDWACH-043", ConstantMessages.GENERIC.getMessage(), ConstantMessages.GENERIC.getTitle(), CategoryError.MW_GENERIC_INTERNAL_FAILURE.getCategoryId()), // Error al obtener la cuenta, la cuenta no pertenece al cliente
    MDWACH_044(HttpStatus.NOT_ACCEPTABLE, "AUTHORIZATION_INVALID", "MDWACH-044", "No tienes la autorización para realizar la transacción.", "Transacción no procesada", CategoryError.MW_SPECIFIC_INTERNAL_FAILURE.getCategoryId()),
    MDWACH_045(HttpStatus.NOT_ACCEPTABLE, "ERROR_VERIFY_POWER_TRX", "MDWACH-045", ConstantMessages.GENERIC.getMessage(), ConstantMessages.GENERIC.getTitle(), CategoryError.MW_GENERIC_INTERNAL_FAILURE.getCategoryId()), // Error en la validacion de poderes
    MDWACH_046(HttpStatus.NOT_ACCEPTABLE, CodeError.ERROR_PROCEDURE.getCode(), "MDWACH-046", ConstantMessages.GENERIC.getMessage(), ConstantMessages.GENERIC.getTitle(), CategoryError.MW_GENERIC_INTERNAL_FAILURE.getCategoryId()), // Error al registrar la authorizacion.
    MDWACH_047(HttpStatus.NOT_IMPLEMENTED, CodeError.INVALID_DATA.getCode(), "MDWACH-047", "Cuenta billetera inválida, verifica e intenta nuevamente.", "Cuenta inválida", CategoryError.MW_UNEXPECTED_FORMAT.getCategoryId()),

    MDWACH_048(HttpStatus.NOT_ACCEPTABLE, CodeError.ERROR_PROCEDURE.getCode(), "MDWACH-048", ConstantMessages.GENERIC.getMessage(), ConstantMessages.GENERIC.getTitle(), CategoryError.MW_GENERIC_INTERNAL_FAILURE.getCategoryId()), // Error al validar los limites
    MDWACH_049(HttpStatus.NOT_ACCEPTABLE, "CONTINGENCY_DESTINATION_BANK", "MDWACH-049", "La entidad financiera de destino esta fuera de servicio para realizar esta transacción. Intentálo más tarde.", "Servicio no disponible", CategoryError.MW_SPECIFIC_INTERNAL_FAILURE.getCategoryId()),
    MDWACH_050(HttpStatus.NOT_ACCEPTABLE, "CONTINGENCY_ORIGIN_BANK", "MDWACH-050", "Nuestra entidad esta fuera de servicio para realizar esta transacción. Intentálo más tarde.", "Servicio no disponible", CategoryError.MW_SPECIFIC_INTERNAL_FAILURE.getCategoryId()),

    MDWACH_051(HttpStatus.NOT_ACCEPTABLE, CodeError.ERROR_PROCEDURE.getCode(), "MDWACH-051", ConstantMessages.GENERIC.getMessage(), ConstantMessages.GENERIC.getTitle(), CategoryError.MW_GENERIC_INTERNAL_FAILURE.getCategoryId()), // Error register pcc01
    MDWACH_052(HttpStatus.NOT_ACCEPTABLE, CodeError.ERROR_PROCEDURE.getCode(), "MDWACH-052", ConstantMessages.GENERIC.getMessage(), ConstantMessages.GENERIC.getTitle(), CategoryError.MW_GENERIC_INTERNAL_FAILURE.getCategoryId()); // Error validate  money laundering

    private final HttpStatus httpCode;
    private final String code;
    private final String codeMiddleware;
    private final String message;
    private final String title;
    private final int categoryId;
}
