package bg.com.bo.bff.commons.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCodeMW {
    BAD_REQUEST(400, "Technical", "PRE-002"),
    UNAUTHORIZED(401, "", ""),
    NOT_FOUND(404, "Technical", "MDWPGL-404"),
    NOT_ACCEPTABLE(406, "Technical", "2"),
    INTERNAL_SERVER_ERROR(500, "Technical", "MDWPGL-500");

    private final int code;
    private final String errorType;
    private final String errorCode;
}
