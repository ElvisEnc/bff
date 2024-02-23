package bg.com.bo.bff.model.dtos.login;

@lombok.Data
@lombok.NoArgsConstructor
@lombok.AllArgsConstructor
public class LoginValidationServiceResponse {
    private String statusCode;
    private String personId;
}