package bg.com.bo.bff.providers.dtos.request.loginagn;

import com.fasterxml.jackson.annotation.JsonProperty;

@lombok.Data
@lombok.NoArgsConstructor
@lombok.AllArgsConstructor
@lombok.Builder
public class RegistryDataRequest {
    @JsonProperty("objDatosDispositivo")
    private DeviceDataRequest deviceDataRequest;
}
