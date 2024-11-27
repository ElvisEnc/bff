package bg.com.bo.bff.commons.converters;

import org.springframework.http.HttpStatus;

public class ChangePasswordErrorResponseConverter extends ErrorResponseConverter {
    public static ErrorResponseConverter instance = new ChangePasswordErrorResponseConverter();

    private ChangePasswordErrorResponseConverter() {
        super();
    }

    @lombok.Getter
    @lombok.AllArgsConstructor
    public enum ChangePasswordErrorResponse implements IErrorResponse {
        MDWPWD7777(HttpStatus.INTERNAL_SERVER_ERROR, "INTERNAL_SERVER_ERROR", "MDWPGL-500", "Ha ocurrido un error interno en el servidor. Inténtalo de nuevo más tarde.", "Error interno"),
        MDWPGL_500(HttpStatus.INTERNAL_SERVER_ERROR, "INTERNAL_SERVER_ERROR", "MDWPGL-500", "Ha ocurrido un error interno en el servidor. Inténtalo de nuevo más tarde.", "Error interno"),
        MDWRLIB_0001(HttpStatus.INTERNAL_SERVER_ERROR, "INTERNAL_SERVER_ERROR", "MDWRLIB-0001", "Ha ocurrido un error interno en el servidor. Inténtalo de nuevo más tarde.", "Error interno"),
        MDWRLIB_0011(HttpStatus.INTERNAL_SERVER_ERROR, "INTERNAL_SERVER_ERROR", "MDWRLIB-0011", "Ha ocurrido un error interno en el servidor. Inténtalo de nuevo más tarde.", "Error interno"),
        MDWPGL_400(HttpStatus.BAD_REQUEST, "INVALID_DATA", "MDWPGL-400", "Los datos proporcionados no son válidos. Verifica e intenta nuevamente.", "Datos inválidos"),
        MDWPGL_404(HttpStatus.INTERNAL_SERVER_ERROR, "INTERNAL_SERVER_ERROR", "MDWPGL-404", "Ha ocurrido un error interno en el servidor. Inténtalo de nuevo más tarde.", "Error interno"),
        MDWRLIB_0009(HttpStatus.INTERNAL_SERVER_ERROR, "INTERNAL_SERVER_ERROR", "MDWRLIB-0009", "Ha ocurrido un error interno en el servidor. Inténtalo de nuevo más tarde.", "Error interno"),
        MDWPGL_405(HttpStatus.INTERNAL_SERVER_ERROR, "INTERNAL_SERVER_ERROR", "MDWPGL-405", "Ha ocurrido un error interno en el servidor. Inténtalo de nuevo más tarde.", "Error interno"),
        MDWRLIB_0012(HttpStatus.INTERNAL_SERVER_ERROR, "INTERNAL_SERVER_ERROR", "MDWRLIB-0012", "Ha ocurrido un error interno en el servidor. Inténtalo de nuevo más tarde.", "Error interno"),
        WDPWD_0002(HttpStatus.BAD_REQUEST, "INVALID_DATA", "MDWPWD-0002", "Los datos proporcionados no son válidos. Verifica e intenta nuevamente.", "Datos inválidos"),
        MDWPWD_0005(HttpStatus.INTERNAL_SERVER_ERROR, "INTERNAL_SERVER_ERROR", "MDWPWD-0005", "Ha ocurrido un error interno en el servidor. Inténtalo de nuevo más tarde.", "Error interno"),
        MDWPWD_0006(HttpStatus.INTERNAL_SERVER_ERROR, "INTERNAL_SERVER_ERROR", "MDWPWD-0006", "Ha ocurrido un error interno en el servidor. Inténtalo de nuevo más tarde.", "Error interno"),
        SAME_PASSWORD(HttpStatus.BAD_REQUEST, "SAME_PASSWORD", "MDWPWD-0007", "La nueva contraseña no puede ser igual a la anterior. Elige una diferente.", "Contraseña duplicada"),
        MDWPWD_0099(HttpStatus.BAD_REQUEST, "REPEATED_PASSWORD", "MDWPWD-0099", "La contraseña ingresada ya ha sido utilizada. Elige una nueva contraseña.", "Contraseña repetida"),
        MDWPWD_0008(HttpStatus.INTERNAL_SERVER_ERROR, "INTERNAL_SERVER_ERROR", "MDWPWD-0008", "Ha ocurrido un error interno en el servidor. Inténtalo de nuevo más tarde.", "Error interno"),
        MDWPWD_0009(HttpStatus.INTERNAL_SERVER_ERROR, "INTERNAL_SERVER_ERROR", "MDWPWD-0009", "Ha ocurrido un error interno en el servidor. Inténtalo de nuevo más tarde.", "Error interno"),
        MDWPWD_0010(HttpStatus.INTERNAL_SERVER_ERROR, "INTERNAL_SERVER_ERROR", "MDWPWD-0010", "Ha ocurrido un error interno en el servidor. Inténtalo de nuevo más tarde.", "Error interno"),
        MDWPWDSEG_004(HttpStatus.UNAUTHORIZED, "INVALID_PASSWORD", "MDWPWD-SEG004", "La contraseña ingresada es incorrecta. Verifique e intente nuevamente.", "contraseña incorrecta"),
        MDWPWD_2001(HttpStatus.BAD_REQUEST, "INVALID_USER", "MDWPWD-2001", "El usuario proporcionado no es válido. Verifica los datos e inténtalo nuevamente.", "Usuario inválido"),
        MDWPWD_9001(HttpStatus.BAD_REQUEST, "INVALID_USER", "MDWPWD-9001", "El usuario proporcionado no es válido. Verifica los datos e inténtalo nuevamente.", "Usuario inválido"),
        MDWPWD_0011(HttpStatus.BAD_REQUEST, "INVALID_USER", "MDWPWD-0011", "El usuario proporcionado no es válido. Verifica los datos e inténtalo nuevamente.", "Usuario inválido"),
        MDWPWDSEG_014(HttpStatus.UNAUTHORIZED, "MAX_ATTEMPS", "MDWPWD-SEG014", "Se ha alcanzado el número máximo de intentos permitidos. Por favor, inténtalo nuevamente más tarde.", "Intentos máximos alcanzados"),
        NOT_VALID_PASSWORD(HttpStatus.BAD_REQUEST, "NOT_VALID_PASSWORD", null, "La nueva contraseña no cumple con los requisitos. Verifica e intenta nuevamente.", "Contraseña inválida");

        private final HttpStatus httpCode;
        private final String code;
        private final String mwCode;
        private final String message;
        private final String title;
    }

    @Override
    protected IErrorResponse[] getValues() {
        return ChangePasswordErrorResponse.values();
    }
}
