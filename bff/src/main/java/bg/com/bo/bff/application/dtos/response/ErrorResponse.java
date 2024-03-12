package bg.com.bo.bff.application.dtos.response;

import lombok.AllArgsConstructor;
import lombok.ToString;

@lombok.Data
@ToString
@AllArgsConstructor
public class ErrorResponse {
    private String code;
    private String message;
}
