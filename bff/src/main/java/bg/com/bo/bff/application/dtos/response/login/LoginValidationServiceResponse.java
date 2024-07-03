package bg.com.bo.bff.application.dtos.response.login;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginValidationServiceResponse {
    private String statusCode;
    private String personId;
    private String userDeviceId;
    private String rolePersonId;
    private String lastConnectionDate;
    private Boolean keyChange;
    private String keyChangeMessage;
}