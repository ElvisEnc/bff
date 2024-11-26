package bg.com.bo.bff.application.dtos.request.destination.account;

import bg.com.bo.bff.commons.annotations.generics.ValidYoNParameter;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
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
public class AddAchAccountRequest {


    @JsonProperty(required = true)
    @NotBlank(message = "El campo es requerido")
    @ValidYoNParameter
    @Schema(example = "S", description = "Es favorito", requiredMode = Schema.RequiredMode.REQUIRED)
    private String isFavorite;

    @JsonProperty(required = true)
    @NotBlank(message = "El campo es requerido.")
    @ValidYoNParameter
    @Schema(example = "S", description = "Esta habilitado", requiredMode = Schema.RequiredMode.REQUIRED)
    private String isEnabled;


    @Schema(example = "Referencias ", description = "Referencia", requiredMode = Schema.RequiredMode.REQUIRED)
    @Size(max = 100, message = "El campo acepta valores entre 1 y 100 caracteres.")
    private String reference;


    @JsonProperty(required = true)
    @NotBlank(message = "Invalid destinationAccountNumber.")
    @Schema(example = "123456", description = "Nro de cuenta destino.", requiredMode = Schema.RequiredMode.REQUIRED)
    @Size(min = 1, max = 25, message = "El campo acepta valores entre 1 y 25 caracteres.")
    private String destinationAccountNumber;

    @JsonProperty(required = true)
    @NotBlank(message = "Invalid destinationBankCode.")
    @Schema(example = "123456", description = "Codigo de Banco Destino", requiredMode = Schema.RequiredMode.REQUIRED)
    @Size(min = 1, max = 10, message = "El campo acepta valores entre 1 y 10 caracteres.")
    private String destinationBankCode;


    @JsonProperty(required = true)
    @NotBlank(message = "Invalid destinationBranchOfficeCode.")
    @Schema(example = "123456", description = "Codigo de sucursal.", requiredMode = Schema.RequiredMode.REQUIRED)
    @Size(min = 1, max = 20, message = "El campo acepta valores entre 1 y 20 caracteres.")
    private String destinationBranchOfficeCode;

    @JsonProperty(required = true)
    @NotBlank(message = "Invalid destinationAccountTypeCode.")
    @Schema(example = "123456", description = "Tipo de cuenta destino", requiredMode = Schema.RequiredMode.REQUIRED)
    @Size(min = 1, max = 10, message = "El campo acepta valores entre 1 y 10 caracteres.")
    private String destinationAccountTypeCode;

    @JsonProperty(required = true)
    @NotBlank(message = "Invalid destinationHolderName.")
    @Schema(example = "Juan Perez", description = "Nombre de cuanta destino", requiredMode = Schema.RequiredMode.REQUIRED)
    @Size(min = 1, max = 50, message = "El campo acepta valores entre 1 y 50 caracteres.")
    private String destinationHolderName;

    @Schema(example = "123456", description = "Numero de identificacion", requiredMode = Schema.RequiredMode.REQUIRED)
    @Size(max = 10, message = "El campo acepta valores entre 1 y 10 caracteres.")
    private String destinationIDNumber;

    @Email(message = "Invalid email")
    @Size(max = 50, message = "El campo acepta valores entre 1 y 50 caracteres.")
    @Schema(example = "reynaldo@gmail.com", description = "Correo electronico", requiredMode = Schema.RequiredMode.REQUIRED)
    private String email;
}

