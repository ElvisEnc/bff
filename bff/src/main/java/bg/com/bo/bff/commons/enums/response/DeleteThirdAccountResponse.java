package bg.com.bo.bff.commons.enums.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum DeleteThirdAccountResponse implements IGenericControllerResponse {
    SUCCESS("SUCCESS", "Se borró la cuenta exitosamente.");

    private final String code;
    private final String message;
}
