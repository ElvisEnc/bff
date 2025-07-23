package bg.com.bo.bff.providers.dtos.request.login.agm;

import com.fasterxml.jackson.annotation.JsonProperty;

@lombok.Data
@lombok.NoArgsConstructor
@lombok.AllArgsConstructor
@lombok.Builder
public class DeviceDataRequest {
    @JsonProperty("KonyDeviceIdentificator")
    private KonyDeviceIdentificatorRequest konyDeviceIdentificator;
    @JsonProperty("ReactDeviceIdentificator")
    private ReactDeviceIdentificatorRequest reactDeviceIdentificator;
    @JsonProperty("Credentials")
    private CredentialsRequest credentials;
    @JsonProperty("UserKeys")
    private UserKeysRequest userKeys;
}
