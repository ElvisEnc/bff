package bg.com.bo.bff.application.dtos.response.payment.service;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PaymentTypeResponse {

    @Schema(description = "Codigo del concepto")
    private String concept;

    @Schema(description = "Descripcion")
    private String description;

    @Schema(description = "Abreviacion de la descripcion")
    private String abbreviation;

}
