package bg.com.bo.bff.providers.models.enums.middleware.softtoken;

import bg.com.bo.bff.providers.models.enums.middleware.response.IGenericControllerResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
@Getter
@AllArgsConstructor
public enum SoftTokenMiddlewareResponse implements IGenericControllerResponse {
    ENROLLMENT("ENROLLMENT", "El dispositivo ha sido enrolado exitosamente.", "Enrolado"),
    NOT_ENROLLED("NOT_ENROLLED", "El dispositivo no se encuentra enrolado.", "No enrolado"),
    CODE_SENT("SUCCESS_SENT", "El código ha sido enviado correctamente.", "Envío exitoso"),
    VALIDATED("VALIDATED", "El código ingresado es válido.", "Código validado"),
    QUESTION_VALIDATED("VALIDATED", "La pregunta ingresada es válida.", "Pregunta validada"),
    VALIDATED_TOKEN("SUCCESS", "El token ingresado es válido.", "Token validado"),
    REGISTERED_TOKEN("SUCCESS", "El token ha sido registrado correctamente.", "Token registrado") ;

    private final String code;
    private final String message;
    private final String tittle;
}