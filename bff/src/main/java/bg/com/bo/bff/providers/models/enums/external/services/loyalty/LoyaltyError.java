package bg.com.bo.bff.providers.models.enums.external.services.loyalty;

import bg.com.bo.bff.commons.enums.CategoryError;
import bg.com.bo.bff.commons.enums.ConstantMessages;
import bg.com.bo.bff.providers.models.external.services.interfaces.IExternalError;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum LoyaltyError implements IExternalError {

    GENERIC_ERROR(HttpStatus.NOT_ACCEPTABLE, "GENERIC_ERROR", ConstantMessages.GENERIC.getMessage(), ConstantMessages.GENERIC.getMessage(), ConstantMessages.GENERIC.getTitle(), CategoryError.MW_GENERIC_INTERNAL_FAILURE.getCategoryId()),
    LOYALTY_ERROR(HttpStatus.NOT_ACCEPTABLE, "GENERIC_ERROR", "Ocurrio un error al verificar Vamos", ConstantMessages.GENERIC.getMessage(), ConstantMessages.GENERIC.getTitle(), CategoryError.MW_GENERIC_INTERNAL_FAILURE.getCategoryId()),
    REGISTER_ERROR(HttpStatus.NOT_ACCEPTABLE, "REGISTER_ERROR", "Ocurrio algun problema al realizar la suscripción", "Ocurrio algun problema al realizar la suscripción", "Error al suscribirse", CategoryError.MW_SPECIFIC_FAIL_RESPONSE.getCategoryId()),
    EMAIL_REGISTERED(HttpStatus.CONFLICT, "EMAIL_REGISTERED",  "el email ya se encuentra registrado.", "Ocurrio un error, el email ya se encuentra registrado.", "Email ya registrado", CategoryError.MW_SPECIFIC_FAIL_RESPONSE.getCategoryId()),
    PERSON_REGISTERED(HttpStatus.CONFLICT, "PERSON_REGISTERED", "La persona ya se encuentra inscrita a esta campaña", "La persona ya se encuentra subscripta en el programa.", "Persona ya registrada", CategoryError.MW_SPECIFIC_FAIL_RESPONSE.getCategoryId()),
    INCOMPLETE_POINTS(HttpStatus.NOT_ACCEPTABLE, "INCOMPLETE_POINTS", "Los puntos actuales son inferiores a los del vale.", "Los puntos actuales son inferiores a los del vale.", "Ocurrió un error", CategoryError.MW_SPECIFIC_FAIL_RESPONSE.getCategoryId()),
    INACTIVE_USER(HttpStatus.NOT_ACCEPTABLE, "INACTIVE_USER", "El vale no puede ser canjeado porque el usuario no se encuentra activo.", "El vale no puede ser canjeado porque el usuario no se encuentra activo.", "Ocurrió un error", CategoryError.MW_SPECIFIC_FAIL_RESPONSE.getCategoryId()),
    VOUCHER_NOT_AVAILABLE(HttpStatus.NOT_ACCEPTABLE, "INACTIVE_USER","El vale ya no esta disponible para realizar canjes", "El vale ya no esta disponible para realizar canjes", "Vale no disponible", CategoryError.MW_SPECIFIC_FAIL_RESPONSE.getCategoryId()),
    NOT_SUBSCRIPTION(HttpStatus.NOT_ACCEPTABLE, "NOT_SUBSCRIPTION",null, "La persona no se encuentra subscripta al programa.", "Persona no registrada", CategoryError.MW_SPECIFIC_FAIL_RESPONSE.getCategoryId()),
    NOT_VALIDATE_PROGRAM(HttpStatus.NOT_ACCEPTABLE, "NOT_VALIDATE_PROGRAM",null, "La persona no pertenece al programa antiguo VAMOS.", "No cumple con las condiciones del flujo", CategoryError.MW_SPECIFIC_FAIL_RESPONSE.getCategoryId())
     ;
    private final HttpStatus httpCode;
    private final String code;
    private final String msgError;
    private final String message;
    private final String title;
    private final int categoryId;
}
