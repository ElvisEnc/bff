package bg.com.bo.bff.providers.dtos.response.login.mw;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DeviceEnrollmentMWResponse {
        private String personId;
        private String statusCode;
}
