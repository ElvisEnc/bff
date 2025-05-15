package bg.com.bo.bff.providers.models.enums.middleware.transfers.programming;

import bg.com.bo.bff.commons.enums.CategoryError;
import bg.com.bo.bff.commons.enums.CodeError;
import bg.com.bo.bff.commons.enums.ConstantMessages;
import bg.com.bo.bff.providers.models.interfaces.middleware.IMiddlewareError;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum TransferProgrammingMDWError implements IMiddlewareError {

    TPM_0001(
            HttpStatus.INTERNAL_SERVER_ERROR,
            CodeError.ERROR_DB_PROCEDURE.getCode(),
            "TPM-0001",
            ConstantMessages.GENERIC.getTitle(),
            ConstantMessages.GENERIC.getMessage(),
            CategoryError.MW_GENERIC_INTERNAL_FAILURE.getCategoryId()
    ),
    TPM_0002(
            HttpStatus.CONFLICT,
            CodeError.DATA_NOT_FOUND.getCode(),
            "TPM-0002",
            ConstantMessages.NO_FOUND_DATA.getTitle(),
            ConstantMessages.NO_FOUND_DATA.getMessage(),
            CategoryError.MW_NO_DATA_FAIL_RESPONSE.getCategoryId()
    ),
    TPM_0003(
            HttpStatus.CONFLICT,
            CodeError.ERROR_DB_PROCEDURE.getCode(),
            "TPM-0003",
            ConstantMessages.GENERIC.getTitle(),
            "No pudimos obtener la lista de transferencias, por favor intenta más tarde.",
            CategoryError.MW_NO_DATA_FAIL_RESPONSE.getCategoryId()
    ),
    TPM_0004(
            HttpStatus.CONFLICT,
            CodeError.DATA_NOT_FOUND.getCode(),
            "TPM-0004",
            ConstantMessages.GENERIC.getTitle(),
            "No pudimos obtener el plan de pagos para la transferencia seleccionada, por favor intenta más tarde.",
            CategoryError.MW_NO_DATA_FAIL_RESPONSE.getCategoryId()
    ),
    TPM_0005(
            HttpStatus.CONFLICT,
            CodeError.DATA_NOT_FOUND.getCode(),
            "TPM-0005",
            ConstantMessages.GENERIC.getTitle(),
            "No se pudo obtener respuesta al consultar la habilitación del modulo",
            CategoryError.MW_NO_DATA_FAIL_RESPONSE.getCategoryId()
    ),
    TPM_0006(
            HttpStatus.CONFLICT,
            CodeError.ERROR_PROCEDURE.getCode(),
            "TPM-0006",
            ConstantMessages.GENERIC.getTitle(),
            "Tuvimos problemas para eliminar la transferencia programada, por favor intenta mas tarde.",
            CategoryError.MW_GENERIC_FAIL_RESPONSE.getCategoryId()
    ),
    TPM_0007(
            HttpStatus.INTERNAL_SERVER_ERROR,
            CodeError.ERROR_PROCEDURE.getCode(),
            "TPM-0007",
            ConstantMessages.GENERIC.getTitle(),
            "No fue posible validar si la transferencia a programar esta duplicada.",
            CategoryError.MW_GENERIC_FAIL_RESPONSE.getCategoryId()
    ),
    TPM_0008(
            HttpStatus.CONFLICT,
            CodeError.ERROR_PROCEDURE.getCode(),
            "TPM-0008",
            ConstantMessages.GENERIC.getTitle(),
            "Algo salió mal al intentar registrar la programación de transferencia, por favor intente más tarde.",
            CategoryError.MW_NO_DATA_FAIL_RESPONSE.getCategoryId()
    ),
    TPM_0009(
            HttpStatus.BAD_REQUEST,
            CodeError.INVALID_PARAMS.getCode(),
            "TPM-0009",
            "Datos Incorrectos",
            "Las fechas seleccionadas no son validas para el rango no son validas, por favor verifique los datos.",
            CategoryError.MW_NO_DATA_FAIL_RESPONSE.getCategoryId()
    ),
    TPM_0010(
            HttpStatus.CONFLICT,
            CodeError.TRANSFER_DUPLICATE.getCode(),
            "TPM-0010",
            "Transferencia Suplicada",
            "Actualmente ya cuenta con una transferencia programada con estas caracteristicas.",
            CategoryError.MW_NO_DATA_FAIL_RESPONSE.getCategoryId()
    ),
    TPM_0011(
            HttpStatus.BAD_REQUEST,
            CodeError.INVALID_PARAMS.getCode(),
            "TPM-0011",
            "Datos Incorrectos",
            "Existe algun problema con la frecuencia seleccionada, por favor verifique los datos.",
            CategoryError.MW_NO_DATA_FAIL_RESPONSE.getCategoryId()
    ),
    ;

    private final HttpStatus httpCode;
    private final String code;
    private final String codeMiddleware;
    private final String title;
    private final String message;
    private final int categoryId;

}
