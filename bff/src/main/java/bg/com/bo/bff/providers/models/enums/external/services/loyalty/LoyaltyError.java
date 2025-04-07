package bg.com.bo.bff.providers.models.enums.external.services.loyalty;

import bg.com.bo.bff.commons.enums.CategoryError;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum LoyaltyError{

    REGISTER_ERROR(HttpStatus.CONFLICT, "REGISTER_ERROR", "Ocurrio algun problema al realizar la suscripción", "Error al suscribirse", CategoryError.MW_SPECIFIC_FAIL_RESPONSE.getCategoryId()),
    ;
    private final HttpStatus httpCode;
    private final String code;
    private final String message;
    private final String title;
    private final int categoryId;
}
