package bg.com.bo.bff.application.dtos.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonIgnoreProperties(ignoreUnknown = true)
public class AddWalletAccountRequest {

    @JsonProperty(required = true)
    @NotBlank(message = "Invalid toAccountNumber.")
    @Schema(example = "123456", description = "Nro de cuenta destino.", requiredMode = Schema.RequiredMode.REQUIRED)
    private String toAccountNumber;

    @JsonProperty(required = true)
    @NotBlank(message = "Invalid reference.")
    @Schema(example = "Referencias ", description = "Referencia", requiredMode = Schema.RequiredMode.REQUIRED)
    private String reference;

    @JsonProperty(required = true)
    @NotBlank(message = "Invalid isFavorite.")
    @Schema(example = "S", description = "Es favorito", requiredMode = Schema.RequiredMode.REQUIRED)
    private String isFavorite;
}

