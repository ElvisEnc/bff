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
public class DeleteAccountRequest {

    @JsonProperty(required = true)
    @NotBlank(message = "Invalid destinationAccountNumber.")
    @Schema(example = "123456", description = "Nro de cuenta.", requiredMode = Schema.RequiredMode.REQUIRED)
    @Size(min = 1, max = 25, message = "El campo acepta valores entre 1 y 25 caracteres.")
    private long accountNumber;
}

