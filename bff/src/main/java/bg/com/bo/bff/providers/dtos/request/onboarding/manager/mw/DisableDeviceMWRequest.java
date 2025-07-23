package bg.com.bo.bff.providers.dtos.request.onboarding.manager.mw;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DisableDeviceMWRequest {
    private Integer personNumber;
    private String deviceID;
}
