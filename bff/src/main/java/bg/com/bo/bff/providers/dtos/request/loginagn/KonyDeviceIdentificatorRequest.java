package bg.com.bo.bff.providers.dtos.request.loginagn;

import com.fasterxml.jackson.annotation.JsonProperty;

@lombok.Data
@lombok.NoArgsConstructor
@lombok.AllArgsConstructor
@lombok.Builder
public class KonyDeviceIdentificatorRequest {
    @JsonProperty("idDispositivo")
    private String deviceId;
    @JsonProperty("codImei")
    private String imei;
    @JsonProperty("modelo")
    private String model;
    @JsonProperty("osName")
    private String os;
}
