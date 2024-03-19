package bg.com.bo.bff.models.dtos.login;

@lombok.Data
@lombok.NoArgsConstructor
@lombok.AllArgsConstructor
public class LoginValidationServiceResponse {
    private String statusCode;
    private String personId;
    private String userDeviceId;
    private String rolePersonId;
}