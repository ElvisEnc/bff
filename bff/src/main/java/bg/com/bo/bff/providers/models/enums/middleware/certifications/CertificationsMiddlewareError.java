package bg.com.bo.bff.providers.models.enums.middleware.certifications;

import bg.com.bo.bff.commons.enums.CategoryError;
import bg.com.bo.bff.commons.enums.CodeError;
import bg.com.bo.bff.commons.enums.ConstantMessages;
import bg.com.bo.bff.providers.models.interfaces.middleware.IMiddlewareError;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum CertificationsMiddlewareError implements IMiddlewareError {

    MCDCERTMGR_0001(
            HttpStatus.CONFLICT,
            CodeError.DATA_NOT_FOUND.getCode(),
            "MCDCERTMGR-0001",
            ConstantMessages.NO_FOUND_DATA.getTitle(),
            ConstantMessages.NO_FOUND_DATA.getMessage(),
            CategoryError.MW_NO_DATA_FAIL_RESPONSE.getCategoryId()
    ),
    MCDCERTMGR_0002(
            HttpStatus.INTERNAL_SERVER_ERROR,
            CodeError.ERROR_DB_PROCEDURE.getCode(),
            "MCDCERTMGR-0002",
            ConstantMessages.GENERIC.getTitle(),
            ConstantMessages.GENERIC.getMessage(),
            CategoryError.MW_NO_DATA_FAIL_RESPONSE.getCategoryId()
    ),
    MCDCERTMGR_0003(
            HttpStatus.CONFLICT,
            CodeError.ERROR_DB_PROCEDURE.getCode(),
            "MCDCERTMGR-0003",
            "Error al obtener el histórico.",
            "Tuvimos un problema al obtener el histórico de certificados, por favor intente más tarde.",
            CategoryError.MW_SPECIFIC_FAIL_RESPONSE.getCategoryId()
    ),
    MCDCERTMGR_0004(
            HttpStatus.CONFLICT,
            CodeError.ERROR_DB_PROCEDURE.getCode(),
            "MCDCERTMGR-0004",
            "Error al calcular el costo del certificado.",
            "Tuvimos un problema al realizar el cálculo del costo del certificado seleccionado, por favor intente más tarde ",
            CategoryError.MW_SPECIFIC_FAIL_RESPONSE.getCategoryId()
    ),
    MCDCERTMGR_0005(
            HttpStatus.CONFLICT,
            CodeError.ERROR_DB_PROCEDURE.getCode(),
            "MCDCERTMGR-0005",
            "Error al traer cuentas",
            "Tuvimos un problema al realizar el cálculo del costo del certificado seleccionado.",
            CategoryError.MW_SPECIFIC_FAIL_RESPONSE.getCategoryId()
    ),
    MCDCERTMGR_0006(
            HttpStatus.CONFLICT,
            CodeError.ERROR_DB_PROCEDURE.getCode(),
            "MCDCERTMGR-0006",
            "Error al obtener tipo de cambio.",
            "Tuvimos un problema al tratar de obtener el tipo de cambio, por favor intente más tearde.",
            CategoryError.MW_SPECIFIC_FAIL_RESPONSE.getCategoryId()
    ),
    MCDCERTMGR_0007(
            HttpStatus.BAD_REQUEST,
            CodeError.BAD_REQUEST.getCode(),
            "MCDCERTMGR-0007",
            "Parametros invalidos.",
            "El código de certificado enviado en la solicitud es incorrecto.",
            CategoryError.MW_SPECIFIC_FAIL_RESPONSE.getCategoryId()
    ),
    MCDCERTMGR_0008(
            HttpStatus.INTERNAL_SERVER_ERROR,
            CodeError.ERROR_TOPAZ_PROCEDURE.getCode(),
            "MCDCERTMGR-0008",
            CodeError.ERROR_TOPAZ_PROCEDURE.getDescription(),
            "Ocurrió algun error al ejecutar un servicio interno.",
            CategoryError.MW_SPECIFIC_FAIL_RESPONSE.getCategoryId()
    ),
    MCDCERTMGR_0009(
            HttpStatus.CONFLICT,
            CodeError.ERROR_DB_PROCEDURE.getCode(),
            "MCDCERTMGR-0009",
            "Error al registrar",
            "Algo salió mal al registrar la solicitud del certificado.",
            CategoryError.MW_GENERIC_INTERNAL_FAILURE.getCategoryId()
    ),
    MCDCERTMGR_0011(
            HttpStatus.INTERNAL_SERVER_ERROR,
            CodeError.ERROR_DB_PROCEDURE.getCode(),
            "MCDCERTMGR-0011",
            ConstantMessages.GENERIC.getTitle(),
            ConstantMessages.GENERIC.getMessage(),
            CategoryError.MW_SPECIFIC_FAIL_RESPONSE.getCategoryId()
    ),


    ;

    private final HttpStatus httpCode;
    private final String code;
    private final String codeMiddleware;
    private final String title;
    private final String message;
    private final int categoryId;
}
