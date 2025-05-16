package bg.com.bo.bff.providers.dtos.response.crypto.currency;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CryptoCurrencyPostRegisterAccountResponse {

    @JsonProperty("errorCode")
    private String codeError;

    @JsonProperty("errorMessage")
    private String message;

    @Schema(description = "data")
    private PostRegisterResponse data;

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class PostRegisterResponse {

        @Schema(description = "cuenta")
        private Integer account;

    }
}
