package bg.com.bo.bff.providers.dtos.response.crypto.currency;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CryptoCurrencyGetAccountEmailResponse {
    @JsonProperty("errorCode")
    private String codeError;

    @JsonProperty("errorMessage")
    private String message;

    @JsonProperty("data")
    private GetAccountEmailResponse data;

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class GetAccountEmailResponse {

        @JsonProperty("pStrEmail")
        private String email;

        @JsonProperty("pStrNombreCompleto")
        private String name;

        @JsonProperty("pStrCodError")
        private String codeError;

        @JsonProperty("pStrDesError")
        private String message;

    }
}
