package bg.com.bo.bff.providers.dtos.response;

import com.fasterxml.jackson.annotation.JsonProperty;

@lombok.Data
@lombok.AllArgsConstructor
@lombok.NoArgsConstructor
public class RegistryDataResponse {
    @JsonProperty("Mensaje")
    private String message;
    @JsonProperty("Referencia")
    private String reference;
    @JsonProperty("StatusCode")
    private String statusCode;
}
