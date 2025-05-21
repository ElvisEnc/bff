package bg.com.bo.bff.application.dtos.response.onboarding.manager;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DisableDeviceResponse {
    private String codeError;
    private String message;
}
