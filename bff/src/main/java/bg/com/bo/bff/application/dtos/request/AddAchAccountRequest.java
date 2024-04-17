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
public class AddAchAccountRequest {


    @JsonProperty(required = true)
    @NotBlank(message = "Invalid isFavorite")
    @Schema(example = "S", description = "Es favorito", requiredMode = Schema.RequiredMode.REQUIRED)
    private String isFavorite;

    @JsonProperty(required = true)
    @NotBlank(message = "Invalid isEnabled.")
    @Schema(example = "S", description = "Esta habilitado", requiredMode = Schema.RequiredMode.REQUIRED)
    private String isEnabled;

    @JsonProperty(required = true)
    @NotBlank(message = "Invalid reference.")
    @Schema(example = "Referencias ", description = "Referencia", requiredMode = Schema.RequiredMode.REQUIRED)
    private String reference;


    @JsonProperty(required = true)
    @NotBlank(message = "Invalid destinationAccountNumber.")
    @Schema(example = "123456", description = "Nro de cuenta destino.", requiredMode = Schema.RequiredMode.REQUIRED)
    private String destinationAccountNumber;

    @JsonProperty(required = true)
    @NotBlank(message = "Invalid destinationBankCode.")
    @Schema(example = "123456", description = "Codigo de Banco Destino", requiredMode = Schema.RequiredMode.REQUIRED)
    private String destinationBankCode;


    @JsonProperty(required = true)
    @NotBlank(message = "Invalid old password.")
    @Schema(example = "123456", description = "Codigo de sucursal.", requiredMode = Schema.RequiredMode.REQUIRED)
    private String destinationBranchOfficeCode;

    @JsonProperty(required = true)
    @NotBlank(message = "Invalid destinationAccountTypeCode.")
    @Schema(example = "123456", description = "Tipo de cuenta destino", requiredMode = Schema.RequiredMode.REQUIRED)
    private String destinationAccountTypeCode;

    @JsonProperty(required = true)
    @NotBlank(message = "Invalid old password.")
    @Schema(example = "Juan Perez", description = "Nombre de cuanta destino", requiredMode = Schema.RequiredMode.REQUIRED)
    private String destinationHolderName;

    @Schema(example = "123456", description = "Numero de identificacion", requiredMode = Schema.RequiredMode.REQUIRED)
    private String destinationIDNumber;

    @Email(message = "Invalid email")
    @Schema(example = "reynaldo@gmail.com", description = "Correo Electronico", requiredMode = Schema.RequiredMode.REQUIRED)
    private String email;
}

