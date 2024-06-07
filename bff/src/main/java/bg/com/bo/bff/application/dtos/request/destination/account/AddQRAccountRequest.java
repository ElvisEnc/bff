package bg.com.bo.bff.application.dtos.request.destination.account;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
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
public class AddQRAccountRequest {

    @JsonProperty(required = true)
    @NotBlank(message = "Cuenta de destino inválida.")
    @Schema(example = "123456", description = "Nro de cuenta destino.", requiredMode = Schema.RequiredMode.REQUIRED)
    private String accountNumber;

    @JsonProperty(required = true)
    @NotBlank(message = "Nombre de destino inválido.")
    @Schema(example = "Juan Perez", description = "Nombre de cuenta destino", requiredMode = Schema.RequiredMode.REQUIRED)
    private String holderName;

    @Schema(example = "123456", description = "Numero de identificacion", requiredMode = Schema.RequiredMode.REQUIRED)
    private String identificationNumber;

    @JsonProperty(required = true)
    @NotBlank(message = "Código de banco inválido.")
    @Schema(example = "123456", description = "Codigo de Banco Destino", requiredMode = Schema.RequiredMode.REQUIRED)
    private String bankCode;

    @Schema(example = "Referencias ", description = "Referencia del contacto", requiredMode = Schema.RequiredMode.REQUIRED)
    private String reference;

}

