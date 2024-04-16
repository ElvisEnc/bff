package bg.com.bo.bff.application.dtos.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

@lombok.Data
public class DeleteThirdAccountRequest {
    @NotNull(message = "Invalid accountId")
    @Min(value = 1, message = "Invalid accountId")
    @Schema(example = "1234", description = "Account ID de la cuenta.", requiredMode = Schema.RequiredMode.REQUIRED)
    private int accountId;
}
