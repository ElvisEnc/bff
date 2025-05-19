package bg.com.bo.bff.application.dtos.response.crypto.currency;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AccountEmailResponse {

    @Schema(description = "Correo electrónico asociado a la cuenta")
    private String email;

    @Schema(description = "Nombre del titular de la cuenta")
    private String name;
}
