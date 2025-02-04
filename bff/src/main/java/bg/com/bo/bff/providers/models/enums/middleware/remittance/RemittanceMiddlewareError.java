package bg.com.bo.bff.providers.models.enums.middleware.remittance;

import bg.com.bo.bff.commons.enums.CategoryError;
import bg.com.bo.bff.commons.enums.CodeError;
import bg.com.bo.bff.commons.enums.ConstantMessages;
import bg.com.bo.bff.providers.models.interfaces.middleware.IMiddlewareError;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum RemittanceMiddlewareError implements IMiddlewareError {
    RM_RIA_002(HttpStatus.NOT_ACCEPTABLE, CodeError.ERROR_PROCEDURE.getCode(), "RM-RIA-002", "Ocurrió un problema al obtener la información de la remesedora, intente más tarde.", ConstantMessages.GENERIC.getTitle(), CategoryError.MW_GENERIC_INTERNAL_FAILURE.getCategoryId()),
    RM001(HttpStatus.NOT_ACCEPTABLE, CodeError.DATA_NOT_FOUND.getCode(), "RM001", ConstantMessages.NO_FOUND_DATA.getMessage(), ConstantMessages.NO_FOUND_DATA.getTitle(), CategoryError.MW_GENERIC_INTERNAL_FAILURE.getCategoryId()),
    RM_MG_001(HttpStatus.NOT_ACCEPTABLE, CodeError.ERROR_PROCEDURE.getCode(), "RM-MG-001", "Ocurrió un problema al obtener la información de la remesedora, intente más tarde.", "Ocurrió un problema", CategoryError.MW_GENERIC_INTERNAL_FAILURE.getCategoryId()),
    RM_008(HttpStatus.CONFLICT, CodeError.DATA_NOT_FOUND.getCode(), "RM-008", "No se encontraron registros con el número de solicitud dado, verifique e intente nuevamente.", "Datos no encontrados", CategoryError.MW_GENERIC_FAIL_RESPONSE.getCategoryId()),
    RM_012(HttpStatus.CONFLICT, CodeError.INVALID_DATA.getCode(), "RM-012", "No se encontraron registros del remitente, verifique e intente nuevamente.", "Remitente inválido", CategoryError.MW_GENERIC_FAIL_RESPONSE.getCategoryId()),
    RM_013(HttpStatus.NOT_ACCEPTABLE, CodeError.ERROR_PROCEDURE.getCode(), "RM-013", "Ocurrió un problema al obtener la información de la remesa, verifique e intente nuevamente.", ConstantMessages.GENERIC.getTitle(), CategoryError.MW_GENERIC_INTERNAL_FAILURE.getCategoryId()),
    RM_014(HttpStatus.NOT_ACCEPTABLE, CodeError.ERROR_PROCEDURE.getCode(), "RM-014", "Ocurrió un problema al obtener la información de la remesa, verifique e intente nuevamente.", ConstantMessages.GENERIC.getTitle(), CategoryError.MW_GENERIC_INTERNAL_FAILURE.getCategoryId()),
    RM_015(HttpStatus.CONFLICT, "LIMITED_REACHED", "RM-015", "Se alcanzó el límite de remesas por día, intente nuevamente más tarde.", "Límites superados", CategoryError.MW_GENERIC_FAIL_RESPONSE.getCategoryId()),
    RM_016(HttpStatus.NOT_ACCEPTABLE, CodeError.ERROR_LOADING.getCode(), "RM-016", "Ocurrió un problema, no se pudo cargar tu información necesaria.", "Carga de información", CategoryError.MW_GENERIC_INTERNAL_FAILURE.getCategoryId()),
    RM_017(HttpStatus.NOT_ACCEPTABLE, CodeError.ERROR_LOADING.getCode(), "RM-017", "Ocurrió un problema, actualice su información personal o sus datos de contacto.", "Información personal", CategoryError.MW_GENERIC_INTERNAL_FAILURE.getCategoryId()),
    RM_019(HttpStatus.NOT_ACCEPTABLE, CodeError.ERROR_LOADING.getCode(), "RM-019", "Ocurrió un problema, no se pudo cargar la orden.", "Cargar orden", CategoryError.MW_GENERIC_INTERNAL_FAILURE.getCategoryId()),
    RM_020(HttpStatus.NOT_ACCEPTABLE, CodeError.ERROR_PROCEDURE.getCode(), "RM-020", "Ocurrió un problema, no se pudo actualizar la información, verifique e intente nuevamente.", ConstantMessages.GENERIC.getTitle(), CategoryError.MW_GENERIC_INTERNAL_FAILURE.getCategoryId()),
    RM_022(HttpStatus.CONFLICT, CodeError.TRANSACTION_NOT_ALLOWED.getCode(), "RM-022", ConstantMessages.TRANSACTION_NOT_ALLOWED.getMessage(), ConstantMessages.TRANSACTION_NOT_ALLOWED.getTitle(), CategoryError.MW_GENERIC_FAIL_RESPONSE.getCategoryId()),
    RM_029(HttpStatus.NOT_ACCEPTABLE, CodeError.DATA_NOT_FOUND.getCode(), "RM-029", ConstantMessages.NO_FOUND_DATA.getMessage(), ConstantMessages.NO_FOUND_DATA.getTitle(), CategoryError.MW_GENERIC_INTERNAL_FAILURE.getCategoryId()),
    RM_030(HttpStatus.CONFLICT, CodeError.DATA_NOT_FOUND.getCode(), "RM-030", ConstantMessages.NO_FOUND_DATA.getMessage(), ConstantMessages.NO_FOUND_DATA.getTitle(), CategoryError.MW_GENERIC_FAIL_RESPONSE.getCategoryId()),
    RM_031(HttpStatus.CONFLICT, CodeError.ACCOUNT_BLOCKED.getCode(), "RM-031", "No es posible utilizar esta cuenta para realizar esta transacción.", "Cuenta inactiva", CategoryError.MW_GENERIC_FAIL_RESPONSE.getCategoryId()),
    RM_RM_034(HttpStatus.NOT_ACCEPTABLE, CodeError.ERROR_PROCEDURE.getCode(), "RM-RM-034", ConstantMessages.GENERIC.getMessage(), ConstantMessages.GENERIC.getTitle(), CategoryError.MW_GENERIC_INTERNAL_FAILURE.getCategoryId()),
    RM002(HttpStatus.CONFLICT, CodeError.DATA_NOT_FOUND.getCode(), "RM002", ConstantMessages.NO_FOUND_DATA.getMessage(), ConstantMessages.NO_FOUND_DATA.getTitle(), CategoryError.MW_GENERIC_FAIL_RESPONSE.getCategoryId());

    private final HttpStatus httpCode;
    private final String code;
    private final String codeMiddleware;
    private final String message;
    private final String title;
    private final int categoryId;
}
