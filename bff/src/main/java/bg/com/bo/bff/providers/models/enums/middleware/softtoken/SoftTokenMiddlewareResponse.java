package bg.com.bo.bff.providers.models.enums.middleware.softtoken;

import bg.com.bo.bff.providers.models.enums.middleware.response.IGenericControllerResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
@Getter
@AllArgsConstructor
public enum SoftTokenMiddlewareResponse implements IGenericControllerResponse {
    ENROLLMENT("PINDIG002", "Dispositivo enrolado", ""),
    NOT_ENROLLMENT("PINDIG003", "Dispositivo no enrolado", ""),
    CODE_SENT("COD000", "Código enviado", "");

    private final String code;
    private final String message;
    private final String tittle;
}