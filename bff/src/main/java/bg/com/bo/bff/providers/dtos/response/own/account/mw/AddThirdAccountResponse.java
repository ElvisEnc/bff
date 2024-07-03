package bg.com.bo.bff.providers.dtos.response.own.account.mw;

import bg.com.bo.bff.commons.enums.response.IGenericControllerResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum AddThirdAccountResponse implements IGenericControllerResponse {
    SUCCESS("SUCCESS","Se agregó correctamente la cuenta.");

    private final String code;
    private final String message;
}
