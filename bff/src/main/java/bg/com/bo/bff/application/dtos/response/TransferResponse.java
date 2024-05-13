package bg.com.bo.bff.application.dtos.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonRootName(value = "OwnAccountTransferRequest")
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonIgnoreProperties(ignoreUnknown = true)
@Schema(name = "Transfer Own Account", description = "Transfer Own Account Response")
public class TransferResponse {
    @JsonProperty
    @Schema(description = "Image base64")
    private String data;

    @JsonProperty
    @Schema(description = "Format Image")
    private String format;
}
