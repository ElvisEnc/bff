package bg.com.bo.bff.application.dtos.request.nps;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResponseNpsRequest {

    @NotNull(message = "npsId es necesario.")
    @Schema(description = "número de la tarjeta de débito", example = "4200560090901100")
    private int npsId;

    @NotNull
    @Schema(description = "Listado de respuestas.")
    private List<AnswerRequest> answers;

}
