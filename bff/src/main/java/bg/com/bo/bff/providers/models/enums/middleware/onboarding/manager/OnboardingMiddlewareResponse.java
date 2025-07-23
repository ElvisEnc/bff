package bg.com.bo.bff.providers.models.enums.middleware.onboarding.manager;

import bg.com.bo.bff.commons.enums.ConstantMessages;
import bg.com.bo.bff.providers.models.enums.middleware.response.IGenericControllerResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum OnboardingMiddlewareResponse implements IGenericControllerResponse {

    SUCCESS_DEACTIVATE_DEVICE(
            ConstantMessages.SUCCESS.getTitle(),
            "Dispositivo deshabilitado.",
            "Dispositivo deshabilitado exitosamente."),
    ERROR_DEACTIVATE_DEVICE(
            ConstantMessages.ERROR.getTitle(),
            ConstantMessages.GENERIC.getTitle(),
            "Ocurrió algo inesperado, no se pudo desactivar el dispositivo.");


    private final String code;
    private final String title;
    private final String message;

}
