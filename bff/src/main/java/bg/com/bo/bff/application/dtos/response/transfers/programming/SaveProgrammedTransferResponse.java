package bg.com.bo.bff.application.dtos.response.transfers.programming;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SaveProgrammedTransferResponse {

    @Schema(description = "Titulo para mensaje de respuesta")
    private String titulo;

    @Schema(description = "Mensaje de respuesta final")
    private String mensaje;

}
