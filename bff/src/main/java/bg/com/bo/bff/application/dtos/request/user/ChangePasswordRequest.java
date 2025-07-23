package bg.com.bo.bff.application.dtos.request.user;

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
public class ChangePasswordRequest {
    @JsonProperty(required = true)
    @NotBlank(message = "Invalid old password.")
    @Schema(example = "1234", description = "Contraseña anterior.", requiredMode = Schema.RequiredMode.REQUIRED)
    private String oldPassword;

    @JsonProperty(required = true)
    @NotBlank(message = "Invalid new password.")
    @Schema(example = "1234", description = "Nueva contraseña.", requiredMode = Schema.RequiredMode.REQUIRED)
    private String newPassword;
}
