package bg.com.bo.bff.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum HttpError {
    Error400(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.name(), HttpStatus.BAD_REQUEST.getReasonPhrase()),
    Error401(HttpStatus.UNAUTHORIZED.value(), HttpStatus.UNAUTHORIZED.name(), HttpStatus.UNAUTHORIZED.getReasonPhrase()),
    Error404(HttpStatus.NOT_FOUND.value(), HttpStatus.NOT_FOUND.name(), HttpStatus.NOT_FOUND.getReasonPhrase()),
    Error406(HttpStatus.NOT_ACCEPTABLE.value(), HttpStatus.NOT_ACCEPTABLE.name(), HttpStatus.NOT_ACCEPTABLE.getReasonPhrase()),
    Error422(HttpStatus.UNPROCESSABLE_ENTITY.value(), HttpStatus.UNPROCESSABLE_ENTITY.name(), HttpStatus.UNAUTHORIZED.getReasonPhrase()),
    Error500(HttpStatus.INTERNAL_SERVER_ERROR.value(), HttpStatus.INTERNAL_SERVER_ERROR.name(), HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());

    private final int code;
    private final String name;
    private final String description;
}
