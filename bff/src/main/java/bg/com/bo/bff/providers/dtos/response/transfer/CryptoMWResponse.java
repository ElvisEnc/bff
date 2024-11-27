package bg.com.bo.bff.providers.dtos.response.transfer;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CryptoMWResponse {

    @JsonProperty("code")
    private String code;
    @JsonProperty("cryptoName")
    private List<String> cryptoName;
    @JsonProperty("message")
    private String message;
}
