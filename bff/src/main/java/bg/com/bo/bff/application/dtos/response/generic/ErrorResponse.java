package bg.com.bo.bff.application.dtos.response.generic;

@lombok.Data
@lombok.ToString
@lombok.AllArgsConstructor
@lombok.Builder
public class ErrorResponse {
    private String code;
    private String message;
}
