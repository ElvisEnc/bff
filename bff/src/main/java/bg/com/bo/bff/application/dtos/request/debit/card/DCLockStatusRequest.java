package bg.com.bo.bff.application.dtos.request.debit.card;

import bg.com.bo.bff.commons.annotations.debit.card.ValidType;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonIgnoreProperties(ignoreUnknown = true)
public class DCLockStatusRequest {

    @NotNull(message = "El estado de la tarjeta es obligatorio")
    @Schema(description = "Tipo de estado de la tarjeta", example = "0")
    @ValidType
    private Integer type;
}
