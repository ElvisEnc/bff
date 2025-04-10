package bg.com.bo.bff.providers.dtos.response.loyalty;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoyaltyRegisterSubscriptionResponse {

    @Schema(description = "Codigo de respuesta de la suscripcion al programa VAMOS")
    @JsonProperty("codigoEstado")
    private int code;

    @Schema(description = "Mensaje de la suscripcion al programa VAMOS")
    @JsonProperty("mensaje")
    private String message;
}
