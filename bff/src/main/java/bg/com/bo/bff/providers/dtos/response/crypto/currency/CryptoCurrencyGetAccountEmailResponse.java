package bg.com.bo.bff.providers.dtos.response.crypto.currency;

import io.swagger.v3.oas.annotations.media.Schema;
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
    @Schema(description = "errorCode")
    private String codeError;

    @Schema(description = "errorMessage")
    private String message;

    @Schema(description = "data")
    private GetAccountEmailResponse data;

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class GetAccountEmailResponse {

        @Schema(description = "pStrEmail")
        private String email;

        @Schema(description = "pStrCodError")
        private String codeError;

        @Schema(description = "pStrDesError")
        private String message;

    }
}
