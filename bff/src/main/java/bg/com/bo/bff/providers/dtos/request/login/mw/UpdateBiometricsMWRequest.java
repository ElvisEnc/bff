package bg.com.bo.bff.providers.dtos.request.login.mw;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UpdateBiometricsMWRequest {
    private String statusBiometric;
    private String tokenBiometric;
    private String typeAuthentication;
}