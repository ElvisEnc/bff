package bg.com.bo.bff.providers.dtos.response.login;

import bg.com.bo.bff.application.dtos.response.UpdateBiometricsResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UpdateBiometricMWResponse {
    private UpdateBiometricsResponse data;
}
