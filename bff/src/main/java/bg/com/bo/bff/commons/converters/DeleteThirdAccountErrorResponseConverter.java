package bg.com.bo.bff.commons.converters;

import org.springframework.http.HttpStatus;

public class DeleteThirdAccountErrorResponseConverter extends ErrorResponseConverter {
    public static ErrorResponseConverter INSTANCE = new DeleteThirdAccountErrorResponseConverter();

    private DeleteThirdAccountErrorResponseConverter() {
        super();
    }

    @lombok.Getter
    @lombok.AllArgsConstructor
    public enum DeleteThirdAccountErrorResponse implements IErrorResponse {
        MDWPGL_500(HttpStatus.INTERNAL_SERVER_ERROR, "INTERNAL_SERVER_ERROR", "MDWPGL-500", "Internal server error."),
        MDWRLIB_0001(HttpStatus.INTERNAL_SERVER_ERROR, "INTERNAL_SERVER_ERROR", "MDWRLIB-0001", "Internal server error."),
        MDWRLIB_0011(HttpStatus.INTERNAL_SERVER_ERROR, "INTERNAL_SERVER_ERROR", "MDWRLIB-0011", "Internal server error."),
        MDWPGL_400(HttpStatus.BAD_REQUEST, "INVALID_DATA", "MDWPGL-400", "Datos no válidos."),
        MDWPGL_404(HttpStatus.INTERNAL_SERVER_ERROR, "INTERNAL_SERVER_ERROR", "MDWPGL-404", "Internal server error."),
        MDWRLIB_0012(HttpStatus.INTERNAL_SERVER_ERROR, "INTERNAL_SERVER_ERROR", "MDWRLIB-0012", "Internal server error."),
        MDWRLIB_0009(HttpStatus.INTERNAL_SERVER_ERROR, "INTERNAL_SERVER_ERROR", "MDWRLIB-0009", "Internal server error."),
        MDWPGL_405(HttpStatus.INTERNAL_SERVER_ERROR, "INTERNAL_SERVER_ERROR", "MDWPGL-405", "Internal server error."),
        MDWRACTM_004(HttpStatus.BAD_REQUEST, "INVALID_DATA", "MDWRACTM-004", "Datos no válidos."),
        MDWRACTM_028(HttpStatus.BAD_REQUEST, "INVALID_DATA", "MDWRACTM-028", "No se encontraron registros para eliminar.");


        private final HttpStatus httpCode;
        private final String code;
        private final String mwCode;
        private final String message;
    }

    @Override
    protected IErrorResponse[] getValues() {
        return DeleteThirdAccountErrorResponse.values();
    }
}
