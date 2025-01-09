package bg.com.bo.bff.providers.models.enums.middleware.qr;

import bg.com.bo.bff.commons.enums.CategoryError;
import bg.com.bo.bff.commons.enums.CodeError;
import bg.com.bo.bff.commons.enums.ConstantMessages;
import bg.com.bo.bff.providers.models.interfaces.middleware.IMiddlewareError;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum QRTransactionMiddlewareError implements IMiddlewareError {

    //QR-TRANSACTION MANAGER
    MDWQTM_001(HttpStatus.CONFLICT, CodeError.TRANSACTION_NOT_ALLOWED.getCode(), "MDWQTM-001", ConstantMessages.TRANSACTION_NOT_ALLOWED.getMessage(), ConstantMessages.TRANSACTION_NOT_ALLOWED.getTitle(), CategoryError.MW_GENERIC_FAIL_RESPONSE.getCategoryId()),
    MDWQTM_002(HttpStatus.CONFLICT, CodeError.TRANSFER_DUPLICATE.getCode(), "MDWQTM-002", ConstantMessages.TRANSFER_DUPLICATE.getMessage(), ConstantMessages.TRANSFER_DUPLICATE.getTitle(), CategoryError.MW_SPECIFIC_FAIL_RESPONSE.getCategoryId()),
    MDWQTM_003(HttpStatus.NOT_ACCEPTABLE, CodeError.ERROR_TRANSFER_DUPLICATE.getCode(), "MDWQTM-003", ConstantMessages.GENERIC.getMessage(), ConstantMessages.GENERIC.getTitle(), CategoryError.MW_GENERIC_INTERNAL_FAILURE.getCategoryId()),
    MDWQTM_004(HttpStatus.NOT_ACCEPTABLE, CodeError.ERROR_TRANSFER_DUPLICATE.getCode(), "MDWQTM-004", ConstantMessages.GENERIC.getMessage(), ConstantMessages.GENERIC.getTitle(), CategoryError.MW_GENERIC_INTERNAL_FAILURE.getCategoryId()),
    MDWQTM_005(HttpStatus.NOT_ACCEPTABLE, CodeError.VALIDATE_COMBINATION.getCode(), "MDWQTM-005", ConstantMessages.VALIDATE_COMBINATION.getMessage(), ConstantMessages.VALIDATE_COMBINATION.getTitle(), CategoryError.MW_SPECIFIC_INTERNAL_FAILURE.getCategoryId()),
    MDWQTM_006(HttpStatus.NOT_ACCEPTABLE, "SOURCE_ACCOUNT_KO", "MDWQTM-006", "No se pudo obtener la cuenta. Inténtalo nuevamente.", "Ocurrió un problema", CategoryError.MW_SPECIFIC_INTERNAL_FAILURE.getCategoryId()),
    MDWQTM_007(HttpStatus.CONFLICT, "INVALID_CURRENCY", "MDWQTM-007", "Problemas con el tipo de la moneda.", "Moneda inválida", CategoryError.MW_GENERIC_FAIL_RESPONSE.getCategoryId()),
    MDWQTM_008(HttpStatus.NOT_ACCEPTABLE, "CONVERTING_AMOUNT", "MDWQTM-008", ConstantMessages.GENERIC.getMessage(), ConstantMessages.GENERIC.getTitle(), CategoryError.MW_GENERIC_INTERNAL_FAILURE.getCategoryId()),
    MDWQTM_009(HttpStatus.NOT_ACCEPTABLE, CodeError.ERROR_ITF.getCode(), "MDWQTM-009", ConstantMessages.GENERIC.getMessage(), ConstantMessages.GENERIC.getTitle(), CategoryError.MW_GENERIC_INTERNAL_FAILURE.getCategoryId()),
    MDWQTM_010(HttpStatus.CONFLICT, CodeError.AMOUNT_LIMIT_KO.getCode(), "MDWQTM-010", ConstantMessages.AMOUNT_LIMIT_KO.getMessage(), ConstantMessages.AMOUNT_LIMIT_KO.getTitle(), CategoryError.MW_SPECIFIC_FAIL_RESPONSE.getCategoryId()),
    MDWQTM_011(HttpStatus.CONFLICT, CodeError.NO_FUNDS_AVAILABLE.getCode(), "MDWQTM-011", ConstantMessages.NO_FUNDS_AVAILABLE.getMessage(), ConstantMessages.NO_FUNDS_AVAILABLE.getTitle(), CategoryError.MW_SPECIFIC_FAIL_RESPONSE.getCategoryId()),
    MDWQTM_012(HttpStatus.NOT_ACCEPTABLE, "ERROR_NUMBER_PLEDGE", "MDWQTM-012", ConstantMessages.GENERIC.getMessage(), ConstantMessages.GENERIC.getTitle(), CategoryError.MW_GENERIC_INTERNAL_FAILURE.getCategoryId()),
    MDWQTM_013(HttpStatus.NOT_ACCEPTABLE, "ERROR_REGISTER_PLEDGE", "MDWQTM-013", ConstantMessages.GENERIC.getMessage(), ConstantMessages.GENERIC.getTitle(), CategoryError.MW_GENERIC_INTERNAL_FAILURE.getCategoryId()),
    MDWQTM_014(HttpStatus.NOT_ACCEPTABLE, "ERROR_NUMBER_ACH", "MDWQTM-014", ConstantMessages.GENERIC.getMessage(), ConstantMessages.GENERIC.getTitle(), CategoryError.MW_GENERIC_INTERNAL_FAILURE.getCategoryId()),
    MDWQTM_015(HttpStatus.NOT_ACCEPTABLE, "TRANSFER_KO", "MDWQTM-015", ConstantMessages.GENERIC.getMessage(), ConstantMessages.GENERIC.getTitle(), CategoryError.MW_GENERIC_INTERNAL_FAILURE.getCategoryId()),
    MDWQTM_016(HttpStatus.CONFLICT, CodeError.ACCOUNT_BLOCKED.getCode(), "MDWQTM-016", ConstantMessages.ACCOUNT_BLOCKED.getMessage(), ConstantMessages.ACCOUNT_BLOCKED.getTitle(), CategoryError.MW_GENERIC_FAIL_RESPONSE.getCategoryId()),
    MDWQTM_017(HttpStatus.CONFLICT, CodeError.CRYPTO_FOUND.getCode(), "MDWQTM-017", ConstantMessages.CRYPTO_FOUND.getMessage(), ConstantMessages.CRYPTO_FOUND.getTitle(), CategoryError.MW_GENERIC_FAIL_RESPONSE.getCategoryId()),
    MDWQTM_018(HttpStatus.CONFLICT, "ERROR_ROL_USER_EMPRESA", "MDWQTM-018", "La transacción no puede ser procesada para el rol del usuario.", "Rol de usuario no autorizado", CategoryError.MW_GENERIC_FAIL_RESPONSE.getCategoryId()),
    MDWQTM_019(HttpStatus.NOT_ACCEPTABLE, "ERROR_INSERT_MAE", "MDWQTM-019", ConstantMessages.GENERIC.getMessage(), ConstantMessages.GENERIC.getTitle(), CategoryError.MW_GENERIC_INTERNAL_FAILURE.getCategoryId()),
    MDWQTM_020(HttpStatus.NOT_ACCEPTABLE, "ERROR_INSERT_AUTHORIZATION", "MDWQTM-020", ConstantMessages.GENERIC.getMessage(), ConstantMessages.GENERIC.getTitle(), CategoryError.MW_GENERIC_INTERNAL_FAILURE.getCategoryId()),
    MDWQTM_021(HttpStatus.NOT_ACCEPTABLE, "ERROR_VERIFY_POWER_TRX", "MDWQTM-021", ConstantMessages.GENERIC.getMessage(), ConstantMessages.GENERIC.getTitle(), CategoryError.MW_GENERIC_INTERNAL_FAILURE.getCategoryId()),
    MDWQTM_022(HttpStatus.NOT_ACCEPTABLE, "ERROR_UPDATE_MAE", "MDWQTM-022", ConstantMessages.GENERIC.getMessage(), ConstantMessages.GENERIC.getTitle(), CategoryError.MW_GENERIC_INTERNAL_FAILURE.getCategoryId()),
    MDWQTM_023(HttpStatus.CONFLICT, CodeError.QUANTITY_LIMIT_KO.getCode(), "MDWQTM-023", ConstantMessages.QUANTITY_LIMIT_KO.getMessage(), ConstantMessages.QUANTITY_LIMIT_KO.getTitle(), CategoryError.MW_SPECIFIC_FAIL_RESPONSE.getCategoryId()),
    MDWQTM_024(HttpStatus.NOT_ACCEPTABLE, "UPDATE_QUANTITY_LIMIT_KO", "MDWQTM-024", ConstantMessages.GENERIC.getMessage(), ConstantMessages.GENERIC.getTitle(), CategoryError.MW_GENERIC_INTERNAL_FAILURE.getCategoryId()),
    MDWQTM_025(HttpStatus.NOT_ACCEPTABLE, "ERROR_NUMBER_TRANSACTION", "MDWQTM-025", ConstantMessages.GENERIC.getMessage(), ConstantMessages.GENERIC.getTitle(), CategoryError.MW_GENERIC_INTERNAL_FAILURE.getCategoryId()),
    MDWQTM_026(HttpStatus.NOT_ACCEPTABLE, "ERROR_OBTAIN_MAE", "MDWQTM-026", ConstantMessages.GENERIC.getMessage(), ConstantMessages.GENERIC.getTitle(), CategoryError.MW_GENERIC_INTERNAL_FAILURE.getCategoryId()),
    MDWQTM_027(HttpStatus.NOT_ACCEPTABLE, "ERROR_ACH_DATA", "MDWQTM-027", ConstantMessages.GENERIC.getMessage(), ConstantMessages.GENERIC.getTitle(), CategoryError.MW_GENERIC_INTERNAL_FAILURE.getCategoryId()),
    MDWQTM_028(HttpStatus.NOT_ACCEPTABLE, "ERROR_RELEASE_PLEDGE", "MDWQTM-028", ConstantMessages.GENERIC.getMessage(), ConstantMessages.GENERIC.getTitle(), CategoryError.MW_GENERIC_INTERNAL_FAILURE.getCategoryId()),
    MDWQTM_029(HttpStatus.NOT_ACCEPTABLE, "ERROR_REGISTER_MASTER_QR", "MDWQTM-029", "No se pudo realizar la transacción, error al registrar Maestro QR.", "Ocurrió un problema", CategoryError.MW_SPECIFIC_INTERNAL_FAILURE.getCategoryId()),
    MDWQTM_030(HttpStatus.NOT_ACCEPTABLE, "ERROR_ACH_EXPRESS", "MDWQTM-030", ConstantMessages.GENERIC.getMessage(), ConstantMessages.GENERIC.getTitle(), CategoryError.MW_GENERIC_INTERNAL_FAILURE.getCategoryId()),
    MDWQTM_031(HttpStatus.CONFLICT, "TARGET_ACCOUNT_BLOCKED", "MDWQTM-031", "No es posible utilizar esta cuenta para realizar esta transacción.", "Cuenta de destino inactiva", CategoryError.MW_GENERIC_FAIL_RESPONSE.getCategoryId()),
    MDWQTM_032(HttpStatus.NOT_ACCEPTABLE, "TARGET_ACCOUNT_KO", "MDWQTM-032", "Tuvimos un problema al obtener la cuenta de destino. Inténtalo nuevamente.", "Ocurrió un problema", CategoryError.MW_SPECIFIC_INTERNAL_FAILURE.getCategoryId()),
    MDWQTM_033(HttpStatus.CONFLICT, "SAME_ACCOUNT", "MDWQTM-033", "La cuenta de origen y de destino no pueden ser la misma.", "Cuenta de origen y destino iguales", CategoryError.MW_GENERIC_FAIL_RESPONSE.getCategoryId()),
    MDWQTM_034(HttpStatus.NOT_ACCEPTABLE, "SOURCE_ACCOUNT_KO", "MDWQTM-034", "No es posible utilizar esta cuenta para realizar esta transacción.", "Cuenta de origen inactiva", CategoryError.MW_GENERIC_INTERNAL_FAILURE.getCategoryId()),
    MDWQTM_035(HttpStatus.CONFLICT, "ERROR_VERIFY_POWER_TRX", "MDWQTM-035", "La transferencia no puede ser procesada, por la validación de poderes.", "Ocurrió un problema", CategoryError.MW_GENERIC_FAIL_RESPONSE.getCategoryId()),
    MDWQTM_036(HttpStatus.NOT_IMPLEMENTED, "NOT_URL_INVALID", "MDWQTM-036", "La URL proporcionada es inválida.", "URL inválida", CategoryError.INVALID_REQUEST_MW_ERROR.getCategoryId()),
    MDWQTM_037(HttpStatus.CONFLICT, "ERROR_DUE_DATE", "MDWQTM-037", "La fecha de vencimiento es incorrecta.", "Error en fecha de vencimiento", CategoryError.MW_GENERIC_FAIL_RESPONSE.getCategoryId()),
    MDWQTM_038(HttpStatus.NOT_IMPLEMENTED, "INVALID_CHANNEL", "MDWQTM-038", "Canal inválido para esta transacción.", "Canal inválido", CategoryError.NO_HEADER_PARAMS_MW_ERROR.getCategoryId()),
    MDWQTM_039(HttpStatus.CONFLICT, CodeError.FUNDS_REQUIRED.getCode(), "MDWQTM-039", ConstantMessages.FUNDS_REQUIRED.getMessage(), ConstantMessages.FUNDS_REQUIRED.getTitle(), CategoryError.MW_GENERIC_FAIL_RESPONSE.getCategoryId()),
    MDWQTM_040(HttpStatus.CONFLICT, "QR_HAS_BEEN_USED", "MDWQTM-040", "El código QR ya ha sido utilizado.", "Código QR utilizado", CategoryError.MW_GENERIC_FAIL_RESPONSE.getCategoryId()),
    MDWQTM_041(HttpStatus.CONFLICT, "INVALID_TARGET_ACCOUNT", "MDWQTM-041", "La cuenta de destino es inválida.", "Ocurrió un problema", CategoryError.MW_GENERIC_FAIL_RESPONSE.getCategoryId()),
    MDWQTM_042(HttpStatus.CONFLICT, "DIFFERENT_AMOUNT", "MDWQTM-042", "El monto de la transacción es diferente al monto requerido.", "Monto de transacción diferente", CategoryError.MW_GENERIC_FAIL_RESPONSE.getCategoryId()),
    MDWQTM_043(HttpStatus.NOT_ACCEPTABLE, "CHECK_TRANSFER", "MDWQTM-043", "Verifica si la transacción fue satisfactoria, revisa tu extracto.", "Tiempo excedido", CategoryError.MW_SPECIFIC_INTERNAL_FAILURE.getCategoryId()),
    MDWQTM_044(HttpStatus.NOT_ACCEPTABLE, "CONTINGENCY_DESTINATION_BANK", "MDWQTM-044", "La entidad financiera de destino esta fuera de servicio para realizar esta transacción. Intentálo más tarde.", "Servicio no disponible", CategoryError.MW_SPECIFIC_INTERNAL_FAILURE.getCategoryId()),
    MDWQTM_045(HttpStatus.NOT_ACCEPTABLE, "CONTINGENCY_ORIGIN_BANK", "MDWQTM-045", "Nuestra entidad esta fuera de servicio para realizar esta transacción. Intentálo más tarde.", "Servicio no disponible", CategoryError.MW_SPECIFIC_INTERNAL_FAILURE.getCategoryId()),
    MDWGQM_PENDING(HttpStatus.OK, "TRANSFER_PENDING", "MDWPGL-PENDING", "Se requiere la autorización de otro firmante para completar la transacción.", "Transacción pendiente", CategoryError.SUCCESS.getCategoryId());

    private final HttpStatus httpCode;
    private final String code;
    private final String codeMiddleware;
    private final String message;
    private final String title;
    private final int categoryId;
}
