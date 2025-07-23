package bg.com.bo.bff.providers.models.enums.middleware.transfer;

import bg.com.bo.bff.commons.enums.CategoryError;
import bg.com.bo.bff.commons.enums.CodeError;
import bg.com.bo.bff.commons.enums.ConstantMessages;
import bg.com.bo.bff.providers.models.interfaces.middleware.IMiddlewareError;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum TransferMiddlewareError implements IMiddlewareError {
    MDWTRM_001(HttpStatus.NOT_ACCEPTABLE, "SOURCE_ACCOUNT_KO", "MDWTRM-001", "No se pudo obtener la cuenta. Inténtalo nuevamente.", "Ocurrió un problema", CategoryError.MW_SPECIFIC_INTERNAL_FAILURE.getCategoryId()),
    MDWTRM_002(HttpStatus.CONFLICT, "ACCOUNT_BLOCKED", "MDWTRM-002", "No es posible utilizar esta cuenta para realizar esta transacción.", "Transacción no disponible", CategoryError.MW_GENERIC_FAIL_RESPONSE.getCategoryId()),
    MDWTRM_003(HttpStatus.NOT_ACCEPTABLE, "TARGET_ACCOUNT_KO", "MDWTRM-003", "Tuvimos un problema al obtener la cuenta de destino. Inténtalo nuevamente.", "Ocurrió un problema", CategoryError.MW_SPECIFIC_INTERNAL_FAILURE.getCategoryId()),
    MDWTRM_004(HttpStatus.CONFLICT, "TARGET_ACCOUNT_BLOCKED", "MDWTRM-004", "No es posible utilizar esta cuenta para realizar esta transacción.", "Cuenta de destino inactiva", CategoryError.MW_GENERIC_FAIL_RESPONSE.getCategoryId()),
    MDWTRM_005(HttpStatus.CONFLICT, "SAME_ACCOUNT", "MDWTRM-005", "La cuenta de origen y de destino no pueden ser la misma.", "Cuenta de origen y destino iguales", CategoryError.MW_GENERIC_FAIL_RESPONSE.getCategoryId()),
    MDWTRM_006(HttpStatus.NOT_ACCEPTABLE, "CONVERTING_AMOUNT", "MDWTRM-006", ConstantMessages.GENERIC.getMessage(), ConstantMessages.GENERIC.getTitle(), CategoryError.MW_GENERIC_INTERNAL_FAILURE.getCategoryId()),
    MDWTRM_007(HttpStatus.NOT_ACCEPTABLE, CodeError.ERROR_ITF.getCode(), "MDWTRM-007", ConstantMessages.GENERIC.getMessage(), ConstantMessages.GENERIC.getTitle(), CategoryError.MW_GENERIC_INTERNAL_FAILURE.getCategoryId()),
    MDWTRM_008(HttpStatus.CONFLICT, CodeError.NO_FUNDS_AVAILABLE.getCode(), "MDWTRM-008", ConstantMessages.NO_FUNDS_AVAILABLE.getMessage(), ConstantMessages.NO_FUNDS_AVAILABLE.getTitle(), CategoryError.MW_SPECIFIC_FAIL_RESPONSE.getCategoryId()),
    MDWTRM_009(HttpStatus.NOT_ACCEPTABLE, "VALIDATE_LIMITS", "MDWTRM-009", "No se pudo validar el límite de la cuenta.", "Límite de cuenta", CategoryError.MW_SPECIFIC_INTERNAL_FAILURE.getCategoryId()),
    MDWTRM_010(HttpStatus.CONFLICT, CodeError.AMOUNT_LIMIT_KO.getCode(), "MDWTRM-010", ConstantMessages.AMOUNT_LIMIT_KO.getMessage(), ConstantMessages.AMOUNT_LIMIT_KO.getTitle(), CategoryError.MW_SPECIFIC_FAIL_RESPONSE.getCategoryId()),
    MDWTRM_011(HttpStatus.NOT_ACCEPTABLE, CodeError.VALIDATE_COMBINATION.getCode(), "MDWTRM-011", ConstantMessages.VALIDATE_COMBINATION.getMessage(), ConstantMessages.VALIDATE_COMBINATION.getTitle(), CategoryError.MW_SPECIFIC_INTERNAL_FAILURE.getCategoryId()),
    MDWTRM_012(HttpStatus.NOT_ACCEPTABLE, "TRANSFER_KO", "MDWTRM-012", ConstantMessages.GENERIC.getMessage(), ConstantMessages.GENERIC.getTitle(), CategoryError.MW_GENERIC_INTERNAL_FAILURE.getCategoryId()),
    MDWTRM_013(HttpStatus.NOT_ACCEPTABLE, "NOT_FOUND_AVAILABLE", "MDWTRM-013", ConstantMessages.GENERIC.getMessage(), ConstantMessages.GENERIC.getTitle(), CategoryError.MW_GENERIC_INTERNAL_FAILURE.getCategoryId()),
    MDWTRM_014(HttpStatus.NOT_ACCEPTABLE, "PAYMENT_LOAND_ERROR", "MDWTRM-014", ConstantMessages.GENERIC.getMessage(), ConstantMessages.GENERIC.getTitle(), CategoryError.MW_GENERIC_INTERNAL_FAILURE.getCategoryId()),
    MDWTRM_015(HttpStatus.NOT_ACCEPTABLE, "NOT_FOUND_RECEIPT", "MDWTRM-015", "Tuvimos un problema interno al obtener el comprobante, revisa tu extracto.", "Ocurrió un problema", CategoryError.MW_GENERIC_INTERNAL_FAILURE.getCategoryId()),
    MDWTRM_016(HttpStatus.NOT_ACCEPTABLE, "SOURCE_ACCOUNT_KO", "MDWTRM-016", "No es posible utilizar esta cuenta para realizar esta transacción.", "Cuenta de origen inactiva", CategoryError.MW_GENERIC_INTERNAL_FAILURE.getCategoryId()),
    MDWTRM_017(HttpStatus.NOT_ACCEPTABLE, "TRANSFER_KO", "MDWTRM-017", ConstantMessages.GENERIC.getMessage(), ConstantMessages.GENERIC.getTitle(), CategoryError.MW_GENERIC_INTERNAL_FAILURE.getCategoryId()),
    MDWTRM_018(HttpStatus.CONFLICT, "INVALID_CURRENCY", "MDWTRM-018", "Problemas con el tipo de la moneda.", "Moneda inválida", CategoryError.MW_GENERIC_FAIL_RESPONSE.getCategoryId()),
    MDWTRM_019(HttpStatus.NOT_ACCEPTABLE, "ERROR_PROCEDURE", "MDWTRM-019", ConstantMessages.GENERIC.getMessage(), ConstantMessages.GENERIC.getTitle(), CategoryError.MW_GENERIC_INTERNAL_FAILURE.getCategoryId()),
    MDWTRM_020(HttpStatus.CONFLICT, CodeError.TRANSFER_DUPLICATE.getCode(), "MDWTRM-020", ConstantMessages.TRANSFER_DUPLICATE.getMessage(), ConstantMessages.TRANSFER_DUPLICATE.getTitle(), CategoryError.MW_SPECIFIC_FAIL_RESPONSE.getCategoryId()),
    MDWTRM_021(HttpStatus.NOT_ACCEPTABLE, CodeError.ERROR_TRANSFER_DUPLICATE.getCode(), "MDWTRM-021", ConstantMessages.GENERIC.getMessage(), ConstantMessages.GENERIC.getTitle(), CategoryError.MW_GENERIC_INTERNAL_FAILURE.getCategoryId()),
    MDWTRM_022(HttpStatus.NOT_ACCEPTABLE, CodeError.ERROR_TRANSFER_DUPLICATE.getCode(), "MDWTRM-022", ConstantMessages.GENERIC.getMessage(), ConstantMessages.GENERIC.getTitle(), CategoryError.MW_GENERIC_INTERNAL_FAILURE.getCategoryId()),
    MDWTRM_023(HttpStatus.CONFLICT, "TRANSACTION_NOT_ALLOWED", "MDWTRM-023", "Las transacciones en moneda extranjera están temporalmente deshabilitadas.", "Transacción no disponible", CategoryError.MW_GENERIC_FAIL_RESPONSE.getCategoryId()),
    MDWTRM_024(HttpStatus.NOT_ACCEPTABLE, "LIST_KO", "MDWTRM-024", ConstantMessages.GENERIC.getMessage(), ConstantMessages.GENERIC.getTitle(), CategoryError.MW_GENERIC_INTERNAL_FAILURE.getCategoryId()),
    MDWTRM_025(HttpStatus.CONFLICT, "TRUST_LIST", "MDWTRM-025", "La cuenta de origen no se encuentra en una lista de confianza.", "Ocurrió un problema", CategoryError.MW_GENERIC_FAIL_RESPONSE.getCategoryId()),
    MDWTRM_026(HttpStatus.CONFLICT, CodeError.FUNDS_REQUIRED.getCode(), "MDWTRM-026", ConstantMessages.FUNDS_REQUIRED.getMessage(), ConstantMessages.FUNDS_REQUIRED.getTitle(), CategoryError.MW_GENERIC_FAIL_RESPONSE.getCategoryId()),
    MDWTRM_032(HttpStatus.CONFLICT, "OWNER_ACCOUNT_KO", "MDWTRM-032", "No es posible utilizar esta cuenta para realizar esta transacción.", "Cuenta de origen inactiva", CategoryError.MW_GENERIC_FAIL_RESPONSE.getCategoryId()),
    MDWTRM_033(HttpStatus.CONFLICT, CodeError.CRYPTO_FOUND.getCode(), "MDWTRM-033", ConstantMessages.CRYPTO_FOUND.getMessage(), ConstantMessages.CRYPTO_FOUND.getTitle(), CategoryError.MW_GENERIC_FAIL_RESPONSE.getCategoryId()),
    MDWTRM_034(HttpStatus.NOT_ACCEPTABLE, "TRACKING_ERROR", "MDWTRM-034", ConstantMessages.GENERIC.getMessage(), ConstantMessages.GENERIC.getTitle(), CategoryError.MW_GENERIC_INTERNAL_FAILURE.getCategoryId()),
    MDWTRM_035(HttpStatus.NOT_ACCEPTABLE, "TRACKING_UPDATE_ERROR", "MDWTRM-035", ConstantMessages.GENERIC.getMessage(), ConstantMessages.GENERIC.getTitle(), CategoryError.MW_GENERIC_INTERNAL_FAILURE.getCategoryId()),
    MDWTRM_036(HttpStatus.NOT_ACCEPTABLE, "AUTHORIZATION_REGISTER_ERROR", "MDWTRM-036", ConstantMessages.GENERIC.getMessage(), ConstantMessages.GENERIC.getTitle(), CategoryError.MW_GENERIC_INTERNAL_FAILURE.getCategoryId()),
    MDWTRM_037(HttpStatus.CONFLICT, "BLACKLIST_FOUND", "MDWTRM-037", "La cuenta de origen se encuentra en una lista negra interna.", "Ocurrió un problema", CategoryError.MW_GENERIC_FAIL_RESPONSE.getCategoryId()),
    MDWTRM_038(HttpStatus.CONFLICT, "ERROR_VERIFY_POWER_TRX", "MDWTRM-038", "La transferencia no puede ser procesada, por la validación de poderes.", "Ocurrió un problema", CategoryError.MW_GENERIC_FAIL_RESPONSE.getCategoryId()),
    MDWTRM_039(HttpStatus.NOT_ACCEPTABLE, "VALIDATE_ROLE_PERSON", "MDWTRM-039", "Validación de persona rol fallida.", "Ocurrió un problema", CategoryError.MW_SPECIFIC_INTERNAL_FAILURE.getCategoryId()),
    MDWTRM_040(HttpStatus.CONFLICT, CodeError.QUANTITY_LIMIT_KO.getCode(), "MDWTRM-040", ConstantMessages.QUANTITY_LIMIT_KO.getMessage(), ConstantMessages.QUANTITY_LIMIT_KO.getTitle(), CategoryError.MW_SPECIFIC_FAIL_RESPONSE.getCategoryId()),
    MDWTRM_041(HttpStatus.NOT_ACCEPTABLE, "UPDATE_QUANTITY_LIMIT_KO", "MDWTRM-041", ConstantMessages.GENERIC.getMessage(), ConstantMessages.GENERIC.getTitle(), CategoryError.MW_GENERIC_INTERNAL_FAILURE.getCategoryId()),
    MDWTRM_042(HttpStatus.NOT_ACCEPTABLE, "QUANTITY_LIMIT_VALIDATION_KO", "MDWTRM-042", ConstantMessages.GENERIC.getMessage(), ConstantMessages.GENERIC.getTitle(), CategoryError.MW_GENERIC_INTERNAL_FAILURE.getCategoryId()),
    MDWTRM_043(HttpStatus.NOT_ACCEPTABLE, "ERROR_GET_AUTHORIZATION", "MDWTRM-043", ConstantMessages.GENERIC.getMessage(), ConstantMessages.GENERIC.getTitle(), CategoryError.MW_GENERIC_INTERNAL_FAILURE.getCategoryId()),
    MDWTRM_044(HttpStatus.NOT_IMPLEMENTED, "ERROR_PERSON_ID_NOT_NULL", "MDWTRM-044", "El codigo de persona no puede ser nulo", "Ocurrió un problema", CategoryError.MW_UNEXPECTED_FORMAT.getCategoryId()),
    MDWTRM_045(HttpStatus.NOT_IMPLEMENTED, "ERROR_COMPANY_ID_NOT_NULL", "MDWTRM-045", "El companyId no puede ser nulo", "Ocurrió un problema", CategoryError.MW_UNEXPECTED_FORMAT.getCategoryId()),
    MDWTRM_046(HttpStatus.NOT_IMPLEMENTED, "ERROR_COMPANY_ID", "MDWTRM-046", "SchemeName sólo puede ser companyId", "Ocurrió un problema", CategoryError.MW_UNEXPECTED_FORMAT.getCategoryId()),
    MDWTRM_047(HttpStatus.NOT_IMPLEMENTED, "ERROR_PERSON_ID", "MDWTRM-047", "SchemeName sólo puede ser personId", "Ocurrió un problema", CategoryError.MW_UNEXPECTED_FORMAT.getCategoryId()),
    MDWTRM_048(HttpStatus.NOT_ACCEPTABLE, "ERROR_GET_ACCOUNT", "MDWTRM-048", ConstantMessages.GENERIC.getMessage(), ConstantMessages.GENERIC.getTitle(), CategoryError.MW_GENERIC_INTERNAL_FAILURE.getCategoryId()),
    MDWTRM_049(HttpStatus.CONFLICT, "ERROR_ACCOUNT_VALIDATE", "MDWTRM-049", "La cuenta no pertenece al usuario", "Ocurrió un problema", CategoryError.MW_GENERIC_FAIL_RESPONSE.getCategoryId()),
    MDWTRM_050(HttpStatus.NOT_ACCEPTABLE, "ERROR_WORKFLOW_INVALID", "MDWTRM-050", ConstantMessages.GENERIC.getMessage(), ConstantMessages.GENERIC.getTitle(), CategoryError.MW_GENERIC_INTERNAL_FAILURE.getCategoryId()),
    MDWTRM_051(HttpStatus.NOT_ACCEPTABLE, "ERROR_REQUEST_CONSENT", "MDWTRM-051", ConstantMessages.GENERIC.getMessage(), ConstantMessages.GENERIC.getTitle(), CategoryError.MW_GENERIC_INTERNAL_FAILURE.getCategoryId()),
    MDWTRM_052(HttpStatus.NOT_ACCEPTABLE, "ERROR_UPDATE_STATE", "MDWTRM-052", ConstantMessages.GENERIC.getMessage(), ConstantMessages.GENERIC.getTitle(), CategoryError.MW_GENERIC_INTERNAL_FAILURE.getCategoryId()),
    MDWTRM_053(HttpStatus.NOT_ACCEPTABLE, "ERROR_REGISTER_PAYMENT", "MDWTRM-053", ConstantMessages.GENERIC.getMessage(), ConstantMessages.GENERIC.getTitle(), CategoryError.MW_GENERIC_INTERNAL_FAILURE.getCategoryId()),
    MDWTRM_054(HttpStatus.CONFLICT, CodeError.TRANSACTION_NOT_ALLOWED.getCode(), "MDWTRM-054", ConstantMessages.TRANSACTION_NOT_ALLOWED.getMessage(), ConstantMessages.TRANSACTION_NOT_ALLOWED.getTitle(), CategoryError.MW_GENERIC_FAIL_RESPONSE.getCategoryId()),
    MDWTRM_055(HttpStatus.NOT_ACCEPTABLE, "ERROR_NUMBER_TRANSACTION", "MDWTRM-055", ConstantMessages.GENERIC.getMessage(), ConstantMessages.GENERIC.getTitle(), CategoryError.MW_GENERIC_INTERNAL_FAILURE.getCategoryId()),
    MDWTRM_056(HttpStatus.NOT_ACCEPTABLE, "TRANSFER_KO", "MDWTRM-056", ConstantMessages.GENERIC.getMessage(), ConstantMessages.GENERIC.getTitle(), CategoryError.MW_GENERIC_INTERNAL_FAILURE.getCategoryId()),
    MDWTRM_057(HttpStatus.NOT_ACCEPTABLE, "POWER_VALIDATION", "MDWTRM-057", ConstantMessages.GENERIC.getMessage(), ConstantMessages.GENERIC.getTitle(), CategoryError.MW_GENERIC_INTERNAL_FAILURE.getCategoryId()),
    MDWTRM_058(HttpStatus.CONFLICT, "ACCOUNT_BLOCKED", "MDWTRM-058", "No es posible utilizar esta cuenta para realizar esta transacción.", "Cuenta de origen inactiva", CategoryError.MW_GENERIC_FAIL_RESPONSE.getCategoryId()),
    MDWTRM_059(HttpStatus.CONFLICT, "TARGET_ACCOUNT_BLOCKED", "MDWTRM-059", "No es posible utilizar esta cuenta para realizar esta transacción.", "Cuenta de destino inactiva", CategoryError.MW_GENERIC_FAIL_RESPONSE.getCategoryId()),
    MDWTRM_060(HttpStatus.NOT_ACCEPTABLE, "LIST_KO", "MDWTRM-060", ConstantMessages.GENERIC.getMessage(), ConstantMessages.GENERIC.getTitle(), CategoryError.MW_GENERIC_INTERNAL_FAILURE.getCategoryId()),
    MDWTRM_061(HttpStatus.CONFLICT, "TRUST_LIST", "MDWTRM-061", "La cuenta de destino no está en la lista de confianza.", "Ocurrió un problema", CategoryError.MW_GENERIC_FAIL_RESPONSE.getCategoryId()),
    MDWTRM_062(HttpStatus.NOT_ACCEPTABLE, "ERROR_NOTIFICATION", "MDWTRM-062", ConstantMessages.GENERIC.getMessage(), ConstantMessages.GENERIC.getTitle(), CategoryError.MW_GENERIC_INTERNAL_FAILURE.getCategoryId()),
    MDWTRM_063(HttpStatus.CONFLICT, "INVALID_TARGET_ACCOUNT", "MDWTRM-063", "La cuenta de destino es inválida.", "Ocurrió un problema", CategoryError.MW_GENERIC_FAIL_RESPONSE.getCategoryId()),
    MDWTRM_064(HttpStatus.CONFLICT, "INVALID_ORIGIN_ACCOUNT", "MDWTRM-064", "La cuenta de origen es inválida.", "Ocurrió un problema", CategoryError.MW_GENERIC_FAIL_RESPONSE.getCategoryId()),
    MDWTRM_065(HttpStatus.NOT_ACCEPTABLE, "POWER_VALIDATION", "MDWTRM-065", ConstantMessages.GENERIC.getMessage(), ConstantMessages.GENERIC.getTitle(), CategoryError.MW_GENERIC_INTERNAL_FAILURE.getCategoryId()),
    MDWTRM_066(HttpStatus.NOT_ACCEPTABLE, CodeError.VALIDATE_COMBINATION.getCode(), "MDWTRM-066", ConstantMessages.VALIDATE_COMBINATION.getMessage(), ConstantMessages.VALIDATE_COMBINATION.getTitle(), CategoryError.MW_SPECIFIC_INTERNAL_FAILURE.getCategoryId()),

    MDWTRM_PENDING(HttpStatus.OK, "TRANSFER_PENDING", "MDWPGL-PENDING", "Se requiere la autorización de otro firmante para completar la transacción.", "Transacción pendiente", CategoryError.SUCCESS.getCategoryId()),
    MDWTRM_CRYPTO(HttpStatus.CONFLICT, CodeError.CRYPTO_FOUND.getCode(), null, ConstantMessages.CRYPTO_FOUND.getMessage(), ConstantMessages.CRYPTO_FOUND.getTitle(), CategoryError.MW_GENERIC_FAIL_RESPONSE.getCategoryId());

    private final HttpStatus httpCode;
    private final String code;
    private final String codeMiddleware;
    private final String message;
    private final String title;
    private final int categoryId;
}
