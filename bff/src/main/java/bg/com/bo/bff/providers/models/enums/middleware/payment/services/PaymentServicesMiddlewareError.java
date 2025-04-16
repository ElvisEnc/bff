package bg.com.bo.bff.providers.models.enums.middleware.payment.services;


import bg.com.bo.bff.commons.enums.CategoryError;
import bg.com.bo.bff.commons.enums.CodeError;
import bg.com.bo.bff.commons.enums.ConstantMessages;
import bg.com.bo.bff.providers.models.interfaces.middleware.IMiddlewareError;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum PaymentServicesMiddlewareError implements IMiddlewareError {
    MDWPSM_001(
            HttpStatus.CONFLICT,
            CodeError.DATA_NOT_FOUND.getCode(),
            "MDWPSM-001",
            "Hubo un problema al consultar los servicios.",
            ConstantMessages.NO_FOUND_DATA.getTitle(),
            CategoryError.MW_GENERIC_FAIL_RESPONSE.getCategoryId()
    ),
    MDWPSM_002(
            HttpStatus.CONFLICT,
            CodeError.DATA_NOT_FOUND.getCode(),
            "MDWPSM-002",
            ConstantMessages.NO_FOUND_DATA.getMessage(),
            ConstantMessages.NO_FOUND_DATA.getTitle(),
            CategoryError.MW_GENERIC_FAIL_RESPONSE.getCategoryId()
    ),
    MDWPSM_003(
            HttpStatus.CONFLICT,
            "NOT_FOUND_SUBCATEGORIES",
            "MDWPSM-003",
            "No se encontraron registros de subcategorías.",
            ConstantMessages.NO_FOUND_DATA.getTitle(),
            CategoryError.MW_GENERIC_FAIL_RESPONSE.getCategoryId()
    ),
    MDWPSM_004(
            HttpStatus.CONFLICT,
            "NOT_FOUND_CITIES",
            "MDWPSM-004",
            ConstantMessages.NO_FOUND_DATA.getMessage(),
            ConstantMessages.NO_FOUND_DATA.getTitle(),
            CategoryError.MW_GENERIC_FAIL_RESPONSE.getCategoryId()
    ),
    MDWPSM_005(
            HttpStatus.CONFLICT,
            "NO_AFFILIATED_SERVICES",
            "MDWPSM-005",
            "No tiene servicios afiliados.",
            ConstantMessages.NO_FOUND_DATA.getTitle(),
            CategoryError.MW_GENERIC_FAIL_RESPONSE.getCategoryId()
    ),
    MDWPSM_006(
            HttpStatus.CONFLICT,
            CodeError.INVALID_USER_DATA.getCode(),
            "MDWPSM-006",
            "Parámetros inválidos.",
            ConstantMessages.GENERIC_INVALID_PARAMS.getTitle(),
            CategoryError.MW_GENERIC_FAIL_RESPONSE.getCategoryId()
    ),
    MDWPSM_007(
            HttpStatus.CONFLICT,
            "SERVICES_NOT_FOUND",
            "MDWPSM-007",
            "No tiene servicios.",
            ConstantMessages.NO_FOUND_DATA.getTitle(),
            CategoryError.MW_GENERIC_FAIL_RESPONSE.getCategoryId()
    ),
    MDWPSM_009(
            HttpStatus.NOT_ACCEPTABLE,
            CodeError.ERROR_PROCEDURE.getCode(),
            "MDWPSM-009",
            CodeError.ERROR_PROCEDURE.getDescription(),
            ConstantMessages.GENERIC.getTitle(),
            CategoryError.MW_GENERIC_INTERNAL_FAILURE.getCategoryId()
    ),
    MDWPSM_010(
            HttpStatus.CONFLICT,
            "NOT_FOUND_SEARCH_CRITERIA",
            "MDWPSM-010",
            ConstantMessages.NO_FOUND_DATA.getMessage(),
            ConstantMessages.NO_FOUND_DATA.getTitle(),
            CategoryError.MW_GENERIC_FAIL_RESPONSE.getCategoryId()
    ),
    MDWPSM_011(
            HttpStatus.CONFLICT,
            "ERROR_GETTING_SERVICES",
            "MDWPSM-011",
            "Ocurrio un error al obtener los servicios afiliados",
            ConstantMessages.NO_FOUND_DATA.getTitle(),
            CategoryError.MW_GENERIC_FAIL_RESPONSE.getCategoryId()
    ),
    MDWPSM_013(
            HttpStatus.CONFLICT,
            CodeError.ERROR_PROCEDURE.getCode(),
            "MDWPSM-013",
            "Ocurrio un error en el proceso de afiliación",
            ConstantMessages.GENERIC.getTitle(),
            CategoryError.MW_GENERIC_FAIL_RESPONSE.getCategoryId()
    ),
    MDWPSM_014(
            HttpStatus.CONFLICT,
            "NO_DEBTS_FOUND",
            "MDWPSM-014",
            "No tiene deudas en el servicio afiliado",
            ConstantMessages.NO_FOUND_DATA.getTitle(),
            CategoryError.MW_NO_DATA_FAIL_RESPONSE.getCategoryId()
    ),
    MDWPSM_015(
            HttpStatus.NOT_ACCEPTABLE,
            CodeError.ERROR_PROCEDURE.getCode(),
            "MDWPSM-015",
            "Ocurrio un error al obtener los datos.",
            ConstantMessages.GENERIC.getTitle(),
            CategoryError.MW_GENERIC_INTERNAL_FAILURE.getCategoryId()
    ),
    MDWPSM_016(
            HttpStatus.NOT_ACCEPTABLE,
            "ERROR_GETTING_MORA",
            "MDWPSM-016",
            "Ocurrio un error al obtener la mora",
            ConstantMessages.GENERIC.getTitle(),
            CategoryError.MW_GENERIC_INTERNAL_FAILURE.getCategoryId()
    ),
    MDWPSM_017(
            HttpStatus.CONFLICT,
            "NO_DEBTS_FOUND",
            "MDWPSM-017",
            "No tiene deudas en el servicio afiliado.",
            ConstantMessages.NO_FOUND_DATA.getTitle(),
            CategoryError.MW_GENERIC_INTERNAL_FAILURE.getCategoryId()
    ),
    MDWPSM_018(
            HttpStatus.NOT_ACCEPTABLE,
            "PAYMENT_COLLEGE_UNPROCESSED",
            "MDWPSM-018",
            "Ocurrio un error al obtener los datos.",
            ConstantMessages.GENERIC.getTitle(),
            CategoryError.MW_GENERIC_INTERNAL_FAILURE.getCategoryId()
    ),
    MDWPSM_019(
            HttpStatus.CONFLICT,
            "ERROR_DELETING_AFFILIATE",
            "MDWPSM-019",
            "Error al borrar el servicio afiliado",
            ConstantMessages.GENERIC.getTitle(),
            CategoryError.MW_GENERIC_FAIL_RESPONSE.getCategoryId()
    ),
    MDWPSM_020(
            HttpStatus.CONFLICT,
            "NOT_FOUND_DATA_IN_REDIS",
            "MDWPSM-020",
            ConstantMessages.NO_FOUND_DATA.getMessage(),
            ConstantMessages.NO_FOUND_DATA.getTitle(),
            CategoryError.MW_GENERIC_FAIL_RESPONSE.getCategoryId()
    ),
    MDWPSM_021(
            HttpStatus.NOT_ACCEPTABLE,
            "ERROR_VERIFY_POWER_TRX",
            "MDWPSM-021",
            ConstantMessages.VALIDATE_COMBINATION.getMessage(),
            ConstantMessages.VALIDATE_COMBINATION.getTitle(),
            CategoryError.MW_GENERIC_INTERNAL_FAILURE.getCategoryId()
    ),
    MDWPSM_022(
            HttpStatus.CONFLICT,
            "AMOUNT_LIMIT_KO",
            "MDWPSM-022",
            ConstantMessages.AMOUNT_LIMIT_KO.getMessage(),
            ConstantMessages.AMOUNT_LIMIT_KO.getTitle(),
            CategoryError.MW_GENERIC_FAIL_RESPONSE.getCategoryId()
    ),
    MDWPSM_023(
            HttpStatus.NOT_ACCEPTABLE,
            "NOT_FOUND_AVAILABLE",
            "MDWPSM-023",
            "Ocurrió un error al realizar la conversion del monto..",
            ConstantMessages.GENERIC.getTitle(),
            CategoryError.MW_GENERIC_INTERNAL_FAILURE.getCategoryId()
    ),
    MDWPSM_025(
            HttpStatus.BAD_REQUEST,
            "INVALID_CURRENCY",
            "MDWPSM-025",
            "Código de moneda incorrecto.",
            ConstantMessages.GENERIC.getTitle(),
            CategoryError.MW_GENERIC_INTERNAL_FAILURE.getCategoryId()
    ),
    MDWPSM_026(
            HttpStatus.BAD_REQUEST,
            CodeError.SOURCE_ACCOUNT_KO.getCode(),
            "MDWPSM-026",
            "No existe la cuenta de origen.",
            "No existe la cuenta de origen.",
            CategoryError.MW_GENERIC_INTERNAL_FAILURE.getCategoryId()
    ),
    MDWPSM_029(
            HttpStatus.NOT_ACCEPTABLE,
            CodeError.SOURCE_ACCOUNT_KO.getCode(),
            "MDWPSM-029",
            ConstantMessages.NO_FUNDS_AVAILABLE.getMessage(),
            ConstantMessages.NO_FUNDS_AVAILABLE.getTitle(),
            CategoryError.MW_GENERIC_INTERNAL_FAILURE.getCategoryId()
    ),
    MDWPSM_030(
            HttpStatus.BAD_REQUEST,
            "SELECT_FIRST_DEBT",
            "MDWPSM-030",
            "Debe seleccionar la primera cuota.",
            "Error",
            CategoryError.MW_GENERIC_INTERNAL_FAILURE.getCategoryId()
    ),
    MDWPSM_033(
            HttpStatus.NOT_ACCEPTABLE,
            "ERROR_OBTAINING_ITF",
            "MDWPSM-033",
            ConstantMessages.GENERIC.getMessage(),
            ConstantMessages.GENERIC.getTitle(),
            CategoryError.MW_GENERIC_INTERNAL_FAILURE.getCategoryId()
    ),
    MDWPSM_034(
            HttpStatus.INTERNAL_SERVER_ERROR,
            "NO_COMBINATIONS",
            "MDWPSM-034",
            ConstantMessages.GENERIC.getMessage(),
            ConstantMessages.GENERIC.getTitle(),
            CategoryError.MW_GENERIC_INTERNAL_FAILURE.getCategoryId()
    ),
    MDWPSM_035(
            HttpStatus.BAD_REQUEST,
            CodeError.SOURCE_ACCOUNT_KO.getCode(),
            "MDWPSM-035",
            "No se puede procesar la transacción para la cuenta de origen.",
            ConstantMessages.GENERIC.getTitle(),
            CategoryError.MW_GENERIC_INTERNAL_FAILURE.getCategoryId()
    ),
    MDWPSM_036(
            HttpStatus.NOT_ACCEPTABLE,
            "QUANTITY_LIMIT_KO",
            "MDWPSM-036",
            "No se pudo realizar el pago de servicio, límites de cantidad han sido superados",
            ConstantMessages.QUANTITY_LIMIT_KO.getTitle(),
            CategoryError.MW_GENERIC_INTERNAL_FAILURE.getCategoryId()
    ),
    MDWPSM_037(
            HttpStatus.NOT_ACCEPTABLE,
            "UPDATE_QUANTITY_LIMIT_KO",
            "MDWPSM-037",
            "No se pudo realizar el pago de servicio, error al actualizar el límite diario",
            "Limite Excedido",
            CategoryError.MW_GENERIC_INTERNAL_FAILURE.getCategoryId()
    ),
    MDWPSM_039(
            HttpStatus.BAD_REQUEST,
            CodeError.SOURCE_ACCOUNT_KO.getCode(),
            "MDWPSM-039",
            "La cuenta no tiene fondos",
            ConstantMessages.NO_FUNDS_AVAILABLE.getTitle(),
            CategoryError.MW_GENERIC_INTERNAL_FAILURE.getCategoryId()
    ),
    MDWPSM_043(
            HttpStatus.CONFLICT,
            "CANNOT_PROCESSED_AUTHORIZATION",
            "MDWPSM-043",
            ConstantMessages.GENERIC.getMessage(),
            ConstantMessages.GENERIC.getTitle(),
            CategoryError.MW_GENERIC_FAIL_RESPONSE.getCategoryId()
    ),
    MDWPSM_046(
            HttpStatus.NOT_ACCEPTABLE,
            "ERROR_PAYMENT_DEBT",
            "MDWPSM-046",
            ConstantMessages.GENERIC.getMessage(),
            ConstantMessages.GENERIC.getTitle(),
            CategoryError.MW_GENERIC_INTERNAL_FAILURE.getCategoryId()
    ),
    MDWPSM_047(
            HttpStatus.NOT_ACCEPTABLE,
            "NOT_ACCEPTABLE",
            "MDWPSM-047",
            "Afiliación duplicada. El sericio ya se encuentrtra afiliado",
            "Registro Extente",
            CategoryError.MW_GENERIC_INTERNAL_FAILURE.getCategoryId()
            ),
    MDWPSM_049(
            HttpStatus.BAD_REQUEST,
            "ERROR_GETTING_TRASACCION_NUMBER",
            "MDWPSM-049",
            ConstantMessages.GENERIC.getMessage(),
            ConstantMessages.GENERIC.getTitle(),
            CategoryError.MW_GENERIC_INTERNAL_FAILURE.getCategoryId()
    ),
    MDWPSM_050(
            HttpStatus.INTERNAL_SERVER_ERROR,
            "ERROR_REGISTER_REQUEST_PDS",
            "MDWPSM-050",
            ConstantMessages.GENERIC.getMessage(),
            ConstantMessages.GENERIC.getTitle(),
            CategoryError.MW_GENERIC_INTERNAL_FAILURE.getCategoryId()
            ),
    MDWPSM_051(
            HttpStatus.INTERNAL_SERVER_ERROR,
            "ERROR_POWER_VALIDATION",
            "MDWPSM-051",
            ConstantMessages.GENERIC.getMessage(),
            ConstantMessages.GENERIC.getTitle(),
            CategoryError.MW_GENERIC_INTERNAL_FAILURE.getCategoryId()
            );

    private final HttpStatus httpCode;
    private final String code;
    private final String codeMiddleware;
    private final String message;
    private final String title;
    private final int categoryId;
}
