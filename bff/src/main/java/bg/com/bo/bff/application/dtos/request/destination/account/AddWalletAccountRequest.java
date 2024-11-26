package bg.com.bo.bff.application.dtos.request.destination.account;

import bg.com.bo.bff.commons.annotations.generics.ValidYoNParameter;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
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
    @Size(min = 1, max = 13, message = "El campo acepta valores entre 1 y 13 caracteres.")
    @Schema(example = "123456", description = "Nro de cuenta destino.", requiredMode = Schema.RequiredMode.REQUIRED)
    private String toAccountNumber;

    @Schema(example = "Referencias ", description = "Referencia", requiredMode = Schema.RequiredMode.REQUIRED)
    @Size(max = 40, message = "El campo acepta valores entre 1 y 40 caracteres.")
    private String reference;

    @JsonProperty(required = true)
    @NotBlank(message = "Invalid isFavorite.")
    @Size(min = 1, max = 40, message = "El campo acepta valores entre 1 y 40 caracteres.")
    @Schema(example = "S", description = "Es favorito", requiredMode = Schema.RequiredMode.REQUIRED)
    @ValidYoNParameter
    private String isFavorite;
}

