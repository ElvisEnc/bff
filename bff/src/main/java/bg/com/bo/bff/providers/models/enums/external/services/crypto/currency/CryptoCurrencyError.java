package bg.com.bo.bff.providers.models.enums.external.services.crypto.currency;

import bg.com.bo.bff.commons.enums.CategoryError;
import bg.com.bo.bff.commons.enums.ConstantMessages;
import bg.com.bo.bff.providers.models.external.services.interfaces.IExternalError;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum CryptoCurrencyError implements IExternalError {
    ACCESS_DENIED(HttpStatus.UNAUTHORIZED, "ACCESS_DENIED", "Unauthorized", "Error token expirado", "Access token expired", CategoryError.AUTH.getCategoryId()),
    USER_REGISTERED(HttpStatus.NOT_ACCEPTABLE, "USER_REGISTERED", "Usuario tiene una cuenta cripto", "Usuario ya tiene una cuenta en GanaCripto", "Usuario ya registrado", CategoryError.MW_SPECIFIC_FAIL_RESPONSE.getCategoryId()),
    USER_NOT_EXIST(HttpStatus.NOT_ACCEPTABLE, "USER_NOT_EXIST", "No existe el código de persona", "El código de persona no existe", "Usuario no existente", CategoryError.MW_SPECIFIC_FAIL_RESPONSE.getCategoryId()),
    ACCOUNT_NOT_FOUND(HttpStatus.NOT_ACCEPTABLE, "ACCOUNT_NOT_FOUND", null, "No se pudo obtener la cuenta", "No se obtuvo la cuenta", CategoryError.MW_SPECIFIC_FAIL_RESPONSE.getCategoryId()),
    EMAIL_NOT_FOUND(HttpStatus.NOT_ACCEPTABLE, "EMAIL_NOT_FOUND", null, "No se pudo obtener el correo", "No se obtuvo correo", CategoryError.MW_SPECIFIC_FAIL_RESPONSE.getCategoryId()),
    EXTRACT_NOT_FOUND(HttpStatus.NOT_ACCEPTABLE, "EXTRACT_NOT_FOUND", null, "No se pudo obtener el extrato", "No se obtuvo el extracto", CategoryError.MW_SPECIFIC_FAIL_RESPONSE.getCategoryId()),
    ERROR_EXCHANGE(HttpStatus.NOT_ACCEPTABLE, "ERROR_EXCHANGE", null, ConstantMessages.GENERIC.getMessage(), ConstantMessages.GENERIC.getTitle(), CategoryError.MW_GENERIC_INTERNAL_FAILURE.getCategoryId()),
    ERROR_EXCHANGE_OPERATION(HttpStatus.NOT_ACCEPTABLE, "ERROR_EXCHANGE_OPERATION", null, ConstantMessages.GENERIC.getMessage(), ConstantMessages.GENERIC.getTitle(), CategoryError.MW_GENERIC_INTERNAL_FAILURE.getCategoryId()),
    ERROR_VOUCHER(HttpStatus.NOT_ACCEPTABLE, "ERROR_VOUCHER", null, ConstantMessages.GENERIC.getMessage(), ConstantMessages.GENERIC.getTitle(), CategoryError.MW_GENERIC_INTERNAL_FAILURE.getCategoryId()),
    ;

    private final HttpStatus httpCode;
    private final String code;
    private final String msgError;
    private final String message;
    private final String title;
    private final int categoryId;
}
