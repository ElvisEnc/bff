package bg.com.bo.bff.providers.dtos.responses.accounts;

import bg.com.bo.bff.commons.enums.response.IGenericControllerResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum AddAccountResponse implements IGenericControllerResponse {
    SUCCESS("SUCCESS","Se agregó correctamente la cuenta.");

    private final String code;
    private final String message;
}
