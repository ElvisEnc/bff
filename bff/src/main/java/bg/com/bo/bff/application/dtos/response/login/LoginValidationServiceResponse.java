package bg.com.bo.bff.application.dtos.response.login;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginValidationServiceResponse {
    private String statusCode;
    private String personId;
    private String name;
    private String fullName;
    private String userDeviceId;
    private String rolePersonId;
    private String lastConnectionDate;
    private Boolean keyChange;
    private String keyChangeMessage;
}