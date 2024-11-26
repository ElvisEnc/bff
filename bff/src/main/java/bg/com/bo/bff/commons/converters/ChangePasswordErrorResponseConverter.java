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
        MDWPWD7777(HttpStatus.INTERNAL_SERVER_ERROR, "INTERNAL_SERVER_ERROR", "MDWPGL-500", "Internal server error."),
        MDWPGL_500(HttpStatus.INTERNAL_SERVER_ERROR, "INTERNAL_SERVER_ERROR", "MDWPGL-500", "Internal server error."),
        MDWRLIB_0001(HttpStatus.INTERNAL_SERVER_ERROR, "INTERNAL_SERVER_ERROR", "MDWRLIB-0001", "Internal server error."),
        MDWRLIB_0011(HttpStatus.INTERNAL_SERVER_ERROR, "INTERNAL_SERVER_ERROR", "MDWRLIB-0011", "Internal server error."),
        MDWPGL_400(HttpStatus.INTERNAL_SERVER_ERROR, "INVALID_DATA", "MDWPGL-400", "Datos inválidos."),
        MDWPGL_404(HttpStatus.INTERNAL_SERVER_ERROR, "INTERNAL_SERVER_ERROR", "MDWPGL-404", "Internal server error."),
        MDWRLIB_0009(HttpStatus.INTERNAL_SERVER_ERROR, "INTERNAL_SERVER_ERROR", "MDWRLIB-0009", "Internal server error."),
        MDWPGL_405(HttpStatus.INTERNAL_SERVER_ERROR, "INTERNAL_SERVER_ERROR", "MDWPGL-405", "Internal server error."),
        MDWRLIB_0012(HttpStatus.INTERNAL_SERVER_ERROR, "INTERNAL_SERVER_ERROR", "MDWRLIB-0012", "Internal server error."),
        WDPWD_0002(HttpStatus.BAD_REQUEST, "INVALID_DATA", "MDWPWD-0002", "Datos inválidos."),
        MDWPWD_0005(HttpStatus.INTERNAL_SERVER_ERROR, "INTERNAL_SERVER_ERROR", "MDWPWD-0005", "Internal server error."),
        MDWPWD_0006(HttpStatus.INTERNAL_SERVER_ERROR, "INTERNAL_SERVER_ERROR", "MDWPWD-0006", "Internal server error."),
        SAME_PASSWORD(HttpStatus.BAD_REQUEST, "SAME_PASSWORD", "MDWPWD-0007", "La contraseña no puede ser la misma."),
        MDWPWD_0099(HttpStatus.BAD_REQUEST, "REPEATED_PASSWORD", "MDWPWD-0099", "La contraseña es repetida."),
        MDWPWD_0008(HttpStatus.INTERNAL_SERVER_ERROR, "INTERNAL_SERVER_ERROR", "MDWPWD-0008", "Internal server error."),
        MDWPWD_0009(HttpStatus.INTERNAL_SERVER_ERROR, "INTERNAL_SERVER_ERROR", "MDWPWD-0009", "Internal server error."),
        MDWPWD_0010(HttpStatus.INTERNAL_SERVER_ERROR, "INTERNAL_SERVER_ERROR", "MDWPWD-0010", "Internal server error."),
        MDWPWDSEG_004(HttpStatus.UNAUTHORIZED, "INVALID_PASSWORD", "MDWPWD-SEG004", "Contraseña incorrecta."),
        MDWPWD_2001(HttpStatus.BAD_REQUEST, "INVALID_USER", "MDWPWD-2001", "Usuario inválido."),
        MDWPWD_9001(HttpStatus.BAD_REQUEST, "INVALID_USER", "MDWPWD-9001", "Usuario inválido."),
        MDWPWD_0011(HttpStatus.BAD_REQUEST, "INVALID_USER", "MDWPWD-0011", "Usuario inválido."),
        MDWPWDSEG_014(HttpStatus.UNAUTHORIZED, "MAX_ATTEMPS", "MDWPWD-SEG014", "Número máximo de intento alcanzados"),
        NOT_VALID_PASSWORD(HttpStatus.BAD_REQUEST, "NOT_VALID_PASSWORD", null, "La nueva contraseña no es válida.");

        private final HttpStatus httpCode;
        private final String code;
        private final String mwCode;
        private final String message;
    }

    @Override
    protected IErrorResponse[] getValues() {
        return ChangePasswordErrorResponse.values();
    }
}
