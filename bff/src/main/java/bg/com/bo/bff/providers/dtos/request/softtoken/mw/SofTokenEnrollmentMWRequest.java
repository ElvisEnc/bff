package bg.com.bo.bff.providers.dtos.request.softtoken.mw;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SofTokenEnrollmentMWRequest {
    private String personId;
    private String deviceId;
    private String deviceModel;
    private String telephone;
}
