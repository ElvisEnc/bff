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
    RM001(HttpStatus.NOT_ACCEPTABLE, CodeError.DATA_NOT_FOUND.getCode(), "RM001", ConstantMessages.NO_FOUND_DATA.getMessage(), ConstantMessages.NO_FOUND_DATA.getTitle(), CategoryError.MW_GENERIC_INTERNAL_FAILURE.getCategoryId()),
    RM002(HttpStatus.NOT_ACCEPTABLE, CodeError.ERROR_PROCEDURE.getCode(), "RM002", ConstantMessages.GENERIC.getMessage(), ConstantMessages.GENERIC.getTitle(), CategoryError.MW_GENERIC_INTERNAL_FAILURE.getCategoryId()),
    RM004(HttpStatus.NOT_ACCEPTABLE, CodeError.REMITTANCE_COLLETED.getCode(), "RM004", "Estimado cliente, la remesa ya fue pagada previamente, por favor contacte a nuestro call center.",  CodeError.REMITTANCE_COLLETED.getDescription(), CategoryError.MW_GENERIC_INTERNAL_FAILURE.getCategoryId()),
    RM005(HttpStatus.NOT_ACCEPTABLE, CodeError.INVALID_DATA.getCode(), "RM005", "Estimado cliente, la combinación de datos ingresada no es válida, por favor verificar con el remitente y volver a intentarlo.", "Datos inválidos", CategoryError.MW_GENERIC_INTERNAL_FAILURE.getCategoryId()),
    RM006(HttpStatus.NOT_ACCEPTABLE, CodeError.ERROR_REMITTANCE.getCode(), "RM006", "Estimado cliente, el servicio no está disponible, por favor inténtelo más tarde.", ConstantMessages.GENERIC.getTitle(), CategoryError.MW_GENERIC_INTERNAL_FAILURE.getCategoryId()),
    RM007(HttpStatus.NOT_ACCEPTABLE, CodeError.INVALID_DATA.getCode(), "RM007", "No se encontraron registros con el número de consulta, verifique e intente nuevamente.", ConstantMessages.NOT_INFORMATION_OBTAINED.getTitle(), CategoryError.MW_GENERIC_FAIL_RESPONSE.getCategoryId()),
    RM008(HttpStatus.CONFLICT, CodeError.INVALID_DATA.getCode(), "RM008", "No se encontraron registros con el número de solicitud dado, verifique e intente nuevamente.", ConstantMessages.NOT_INFORMATION_OBTAINED.getTitle(), CategoryError.MW_GENERIC_FAIL_RESPONSE.getCategoryId()),
    RM009(HttpStatus.NOT_ACCEPTABLE, CodeError.DATA_NOT_FOUND.getCode(), "RM009", "No se encontraron registros de depósito, verifique e intente nuevamente.", ConstantMessages.NOT_INFORMATION_OBTAINED.getTitle(), CategoryError.MW_GENERIC_FAIL_RESPONSE.getCategoryId()),
    RM010(HttpStatus.CONFLICT, CodeError.DATA_NOT_FOUND.getCode(), "RM010", "No se encontraron registros del remitente, verifique e intente nuevamente.", ConstantMessages.NOT_INFORMATION_OBTAINED.getTitle(), CategoryError.MW_GENERIC_FAIL_RESPONSE.getCategoryId()),
    RM013(HttpStatus.NOT_ACCEPTABLE, CodeError.ERROR_PROCEDURE.getCode(), "RM013", ConstantMessages.GENERIC.getMessage(), ConstantMessages.GENERIC.getTitle(), CategoryError.MW_GENERIC_INTERNAL_FAILURE.getCategoryId()),
    RM014(HttpStatus.NOT_ACCEPTABLE, CodeError.ERROR_PROCEDURE.getCode(), "RM014", "Ocurrió un problema al obtener la información de la remesa, verifique e intente nuevamente.", ConstantMessages.GENERIC.getTitle(), CategoryError.MW_GENERIC_INTERNAL_FAILURE.getCategoryId()),
    RM015(HttpStatus.CONFLICT, "LIMITED_REACHED", "RM015", "Se alcanzó el límite de remesas por día, intente nuevamente más tarde.", "Límites superados", CategoryError.MW_GENERIC_FAIL_RESPONSE.getCategoryId()),
    RM016(HttpStatus.NOT_ACCEPTABLE, CodeError.ERROR_LOADING.getCode(), "RM016", "Ocurrió un problema, no se pudo cargar tu información necesaria.", "Carga de información", CategoryError.MW_GENERIC_INTERNAL_FAILURE.getCategoryId()),
    RM017(HttpStatus.NOT_ACCEPTABLE, CodeError.ERROR_LOADING.getCode(), "RM017", "Ocurrió un problema, actualice su información personal o sus datos de contacto.", "Información personal", CategoryError.MW_GENERIC_INTERNAL_FAILURE.getCategoryId()),
    RM018(HttpStatus.NOT_ACCEPTABLE, CodeError.ERROR_LOADING.getCode(), "RM018", ConstantMessages.GENERIC.getMessage(), ConstantMessages.GENERIC.getTitle(), CategoryError.MW_GENERIC_INTERNAL_FAILURE.getCategoryId()),
    RM019(HttpStatus.NOT_ACCEPTABLE, CodeError.ERROR_LOADING.getCode(), "RM019", "Ocurrió un problema, no se pudo cargar la orden.", "Cargar orden", CategoryError.MW_GENERIC_INTERNAL_FAILURE.getCategoryId()),
    RM020(HttpStatus.NOT_ACCEPTABLE, CodeError.ERROR_PROCEDURE.getCode(), "RM020", "Ocurrió un problema, no se pudo actualizar la información, verifique e intente nuevamente.", ConstantMessages.GENERIC.getTitle(), CategoryError.MW_GENERIC_INTERNAL_FAILURE.getCategoryId()),
    RM021(HttpStatus.CONFLICT, CodeError.ERROR_PROCEDURE.getCode(), "RM021", ConstantMessages.INVALID_ACCOUNT.getMessage(), ConstantMessages.INVALID_ACCOUNT.getTitle(), CategoryError.MW_GENERIC_FAIL_RESPONSE.getCategoryId()),
    RM022(HttpStatus.CONFLICT, CodeError.TRANSACTION_NOT_ALLOWED.getCode(), "RM022", ConstantMessages.TRANSACTION_NOT_ALLOWED.getMessage(), ConstantMessages.TRANSACTION_NOT_ALLOWED.getTitle(), CategoryError.MW_GENERIC_FAIL_RESPONSE.getCategoryId()),
    RM023(HttpStatus.CONFLICT, CodeError.ERROR_PROCEDURE.getCode(), "RM023", ConstantMessages.INVALID_ACCOUNT.getMessage(), ConstantMessages.INVALID_ACCOUNT.getTitle(), CategoryError.MW_GENERIC_FAIL_RESPONSE.getCategoryId()),
    RM024(HttpStatus.CONFLICT, CodeError.ERROR_PROCEDURE.getCode(), "RM024", ConstantMessages.INVALID_ACCOUNT.getMessage(), ConstantMessages.INVALID_ACCOUNT.getTitle(), CategoryError.MW_GENERIC_FAIL_RESPONSE.getCategoryId()),
    RM026(HttpStatus.NOT_ACCEPTABLE, CodeError.ERROR_PROCEDURE.getCode(), "RM026", ConstantMessages.GENERIC.getMessage(), ConstantMessages.GENERIC.getTitle(), CategoryError.MW_GENERIC_INTERNAL_FAILURE.getCategoryId()),
    RM029(HttpStatus.NOT_ACCEPTABLE, CodeError.DATA_NOT_FOUND.getCode(), "RM029", ConstantMessages.NO_FOUND_DATA.getMessage(), ConstantMessages.NO_FOUND_DATA.getTitle(), CategoryError.MW_GENERIC_INTERNAL_FAILURE.getCategoryId()),
    RM031(HttpStatus.CONFLICT, CodeError.ACCOUNT_BLOCKED.getCode(), "RM031", "No es posible utilizar esta cuenta para realizar esta transacción.", "Cuenta inactiva", CategoryError.MW_GENERIC_FAIL_RESPONSE.getCategoryId()),
    RM034(HttpStatus.NOT_ACCEPTABLE, CodeError.ERROR_PROCEDURE.getCode(), "RM034", "Ocurrió un problema al obtener la información del pago en la remesedora, intente más tarde.", ConstantMessages.GENERIC.getTitle(), CategoryError.MW_GENERIC_INTERNAL_FAILURE.getCategoryId()),
    RM035(HttpStatus.NOT_ACCEPTABLE, CodeError.ERROR_PROCEDURE.getCode(), "RM035", ConstantMessages.GENERIC.getMessage(), ConstantMessages.GENERIC.getTitle(), CategoryError.MW_GENERIC_INTERNAL_FAILURE.getCategoryId()),

    RM_T001(HttpStatus.NOT_ACCEPTABLE, CodeError.ERROR_PROCEDURE.getCode(), "RM-T001", ConstantMessages.GENERIC.getMessage(), ConstantMessages.GENERIC.getTitle(), CategoryError.MW_GENERIC_INTERNAL_FAILURE.getCategoryId()),
    RM_T002(HttpStatus.NOT_ACCEPTABLE, CodeError.ERROR_PROCEDURE.getCode(), "RM-T002", ConstantMessages.GENERIC.getMessage(), ConstantMessages.GENERIC.getTitle(), CategoryError.MW_GENERIC_INTERNAL_FAILURE.getCategoryId()),
    RM_T003(HttpStatus.NOT_ACCEPTABLE, CodeError.ERROR_PROCEDURE.getCode(), "RM-T003", ConstantMessages.GENERIC.getMessage(), ConstantMessages.GENERIC.getTitle(), CategoryError.MW_GENERIC_INTERNAL_FAILURE.getCategoryId()),
    RM_T004(HttpStatus.NOT_ACCEPTABLE, CodeError.ERROR_PROCEDURE.getCode(), "RM-T004", ConstantMessages.GENERIC.getMessage(), ConstantMessages.GENERIC.getTitle(), CategoryError.MW_GENERIC_INTERNAL_FAILURE.getCategoryId()),
    RM_T005(HttpStatus.NOT_ACCEPTABLE, CodeError.ERROR_PROCEDURE.getCode(), "RM-T005", "Ocurrió un problema, la remesadora está actualmente bloqueada para pagos.", ConstantMessages.GENERIC.getTitle(), CategoryError.MW_GENERIC_INTERNAL_FAILURE.getCategoryId()),
    RM_T006(HttpStatus.NOT_ACCEPTABLE, CodeError.ERROR_PROCEDURE.getCode(), "RM-T006", ConstantMessages.GENERIC.getMessage(), ConstantMessages.GENERIC.getTitle(), CategoryError.MW_GENERIC_INTERNAL_FAILURE.getCategoryId()),
    RM_T007(HttpStatus.NOT_ACCEPTABLE, CodeError.ERROR_PROCEDURE.getCode(), "RM-T007", "Ocurrió un problema, la remesadora no permite realizar pagos.", ConstantMessages.GENERIC.getTitle(), CategoryError.MW_GENERIC_INTERNAL_FAILURE.getCategoryId()),
    RM_T008(HttpStatus.NOT_ACCEPTABLE, CodeError.ERROR_PROCEDURE.getCode(), "RM-T008", "Ocurrió un problema, la remesadora está actualmente bloqueada para pagos.", ConstantMessages.GENERIC.getTitle(), CategoryError.MW_GENERIC_INTERNAL_FAILURE.getCategoryId()),
    RM_T009(HttpStatus.NOT_ACCEPTABLE, CodeError.ERROR_PROCEDURE.getCode(), "RM-T009", ConstantMessages.GENERIC.getMessage(), ConstantMessages.GENERIC.getTitle(), CategoryError.MW_GENERIC_INTERNAL_FAILURE.getCategoryId()),
    RM_T010(HttpStatus.NOT_ACCEPTABLE, CodeError.ERROR_PROCEDURE.getCode(), "RM-T010", "Ocurrió un problema, no se pueden realizar pagos en este momento.", ConstantMessages.GENERIC.getTitle(), CategoryError.MW_GENERIC_INTERNAL_FAILURE.getCategoryId()),
    RM_T011(HttpStatus.NOT_ACCEPTABLE, CodeError.REMITTANCE_COLLETED.getCode(), "RM-T011", "Estimado cliente, la remesa ya fue cobrada.",  CodeError.REMITTANCE_COLLETED.getDescription(), CategoryError.MW_GENERIC_INTERNAL_FAILURE.getCategoryId()),
    RM_T012(HttpStatus.NOT_ACCEPTABLE, CodeError.ERROR_PROCEDURE.getCode(), "RM-T012", ConstantMessages.GENERIC.getMessage(), ConstantMessages.GENERIC.getTitle(), CategoryError.MW_GENERIC_INTERNAL_FAILURE.getCategoryId()),
    RM_T013(HttpStatus.NOT_ACCEPTABLE, CodeError.ERROR_PROCEDURE.getCode(), "RM-T013", ConstantMessages.GENERIC.getMessage(), ConstantMessages.GENERIC.getTitle(), CategoryError.MW_GENERIC_INTERNAL_FAILURE.getCategoryId()),
    RM_T014(HttpStatus.NOT_ACCEPTABLE, CodeError.ERROR_PROCEDURE.getCode(), "RM-T014", ConstantMessages.GENERIC.getMessage(), ConstantMessages.GENERIC.getTitle(), CategoryError.MW_GENERIC_INTERNAL_FAILURE.getCategoryId()),
    RM_T015(HttpStatus.NOT_ACCEPTABLE, CodeError.ERROR_PROCEDURE.getCode(), "RM-T015", ConstantMessages.GENERIC.getMessage(), ConstantMessages.GENERIC.getTitle(), CategoryError.MW_GENERIC_INTERNAL_FAILURE.getCategoryId()),
    RM_T016(HttpStatus.NOT_ACCEPTABLE, CodeError.ERROR_PROCEDURE.getCode(), "RM-T016", ConstantMessages.GENERIC.getMessage(), ConstantMessages.GENERIC.getTitle(), CategoryError.MW_GENERIC_INTERNAL_FAILURE.getCategoryId()),
    RM_T017(HttpStatus.NOT_ACCEPTABLE, CodeError.ERROR_PROCEDURE.getCode(), "RM-T017", ConstantMessages.GENERIC.getMessage(), ConstantMessages.GENERIC.getTitle(), CategoryError.MW_GENERIC_INTERNAL_FAILURE.getCategoryId()),
    RM_T018(HttpStatus.NOT_ACCEPTABLE, CodeError.ERROR_PROCEDURE.getCode(), "RM-T018", ConstantMessages.GENERIC.getMessage(), ConstantMessages.GENERIC.getTitle(), CategoryError.MW_GENERIC_INTERNAL_FAILURE.getCategoryId()),
    RM_T019(HttpStatus.NOT_ACCEPTABLE, CodeError.ERROR_PROCEDURE.getCode(), "RM-T019", ConstantMessages.GENERIC.getMessage(), ConstantMessages.GENERIC.getTitle(), CategoryError.MW_GENERIC_INTERNAL_FAILURE.getCategoryId()),
    RM_T020(HttpStatus.NOT_ACCEPTABLE, CodeError.ERROR_PROCEDURE.getCode(), "RM-T020", ConstantMessages.GENERIC.getMessage(), ConstantMessages.GENERIC.getTitle(), CategoryError.MW_GENERIC_INTERNAL_FAILURE.getCategoryId()),

    RM_MG001(HttpStatus.NOT_ACCEPTABLE, CodeError.ERROR_REMITTANCE.getCode(), "RM-MG001", "Ocurrió un problema al obtener la información de la remesadora, intente más tarde.", ConstantMessages.GENERIC.getTitle(), CategoryError.MW_GENERIC_INTERNAL_FAILURE.getCategoryId()),
    RM_MG002(HttpStatus.NOT_ACCEPTABLE, CodeError.ERROR_REMITTANCE.getCode(), "RM-MG002", "Ocurrió un problema al obtener la información de la remesas de la remesadora, intente más tarde.", ConstantMessages.GENERIC.getTitle(), CategoryError.MW_GENERIC_INTERNAL_FAILURE.getCategoryId()),
    RM_MG003(HttpStatus.NOT_ACCEPTABLE, CodeError.INVALID_DATA.getCode(), "RM-MG003", "Los datos que ingresaste no son válidos. Por favor, verifica que el código sea correcto.", "Código no encontrado", CategoryError.MW_GENERIC_INTERNAL_FAILURE.getCategoryId()),
    RM_MG004(HttpStatus.NOT_ACCEPTABLE, CodeError.ERROR_REMITTANCE.getCode(), "RM-MG004", "Ocurrió un problema, no existe orden para realizar el pago en MONEY GRAM, intente más tarde.", ConstantMessages.GENERIC.getTitle(), CategoryError.MW_GENERIC_INTERNAL_FAILURE.getCategoryId()),
    RM_MG005(HttpStatus.NOT_ACCEPTABLE, CodeError.REMITTANCE_COLLETED.getCode(), "RM-MG005", "Estimado cliente, la remesa de MONEY GRAM ya fue cobrada.", CodeError.REMITTANCE_COLLETED.getDescription(), CategoryError.MW_GENERIC_INTERNAL_FAILURE.getCategoryId()),
    RM_RIA001(HttpStatus.NOT_ACCEPTABLE, CodeError.ERROR_REMITTANCE.getCode(), "RM-RIA001", "Ocurrió un problema al obtener la información de la remesedora, intente más tarde.", ConstantMessages.GENERIC.getTitle(), CategoryError.MW_GENERIC_INTERNAL_FAILURE.getCategoryId()),
    RM_RIA002(HttpStatus.NOT_ACCEPTABLE, CodeError.REMITTANCE_COLLETED.getCode(), "RM-RIA002", "Estimado cliente, la remesa de RIA ya fue cobrada.",  CodeError.REMITTANCE_COLLETED.getDescription(), CategoryError.MW_GENERIC_INTERNAL_FAILURE.getCategoryId()),
    RM_WU001(HttpStatus.NOT_ACCEPTABLE, CodeError.ERROR_PROCEDURE.getCode(), "RM-WU001", "Ocurrió un problema al obtener la información de la remesadora, intente más tarde.", ConstantMessages.GENERIC.getTitle(), CategoryError.MW_GENERIC_INTERNAL_FAILURE.getCategoryId()),
    RM_WU004(HttpStatus.NOT_ACCEPTABLE, CodeError.ERROR_PROCEDURE.getCode(), "RM-WU004", "La remesa ya ha sido consultado, por favor inténtalo de nuevo en unos minutos","Consulta ya realizada.", CategoryError.MW_GENERIC_INTERNAL_FAILURE.getCategoryId()),
    RM_WU008(HttpStatus.NOT_ACCEPTABLE, CodeError.ERROR_PROCEDURE.getCode(), "RM-WU008", "Ocurrió un problema al realizar el cobro de la remesa, intente más tarde.",ConstantMessages.GENERIC.getTitle(), CategoryError.MW_GENERIC_INTERNAL_FAILURE.getCategoryId()),
    ;
    private final HttpStatus httpCode;
    private final String code;
    private final String codeMiddleware;
    private final String message;
    private final String title;
    private final int categoryId;
}
