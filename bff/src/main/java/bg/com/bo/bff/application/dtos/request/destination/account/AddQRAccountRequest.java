package bg.com.bo.bff.application.dtos.request.destination.account;

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
public class AddQRAccountRequest {

    @JsonProperty(required = true)
    @NotBlank(message = "Cuenta de destino inválida.")
    @Size(min = 1, max = 25, message = "El campo acepta valores entre 1 y 25 caracteres.")
    @Schema(example = "123456", description = "Nro de cuenta destino.", requiredMode = Schema.RequiredMode.REQUIRED)
    private String accountNumber;

    @JsonProperty(required = true)
    @NotBlank(message = "Nombre de destino inválido.")
    @Size(min = 1, max = 50, message = "El campo acepta valores entre 1 y 50 caracteres.")
    @Schema(example = "Juan Perez", description = "Nombre de cuenta destino", requiredMode = Schema.RequiredMode.REQUIRED)
    private String holderName;

    @Schema(example = "123456", description = "Numero de identificacion", requiredMode = Schema.RequiredMode.REQUIRED)
    @Size(max = 10, message = "El campo acepta valores entre 1 y 10 caracteres.")
    private String identificationNumber;

    @JsonProperty(required = true)
    @Schema(example = "123456", description = "Codigo de Banco Destino", requiredMode = Schema.RequiredMode.REQUIRED)
    @Size(max = 10, message = "El campo acepta valores entre 1 y 10 caracteres.")
    private String bankCode;

    @Schema(example = "Referencias ", description = "Referencia del contacto", requiredMode = Schema.RequiredMode.REQUIRED)
    @Size(max = 40, message = "El campo acepta valores entre 1 y 40 caracteres.")
    private String reference;

}

