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
public class DeleteTransferResponse {

    @Schema(description = "Titulo de respuesta")
    private String titulo;

    @Schema(description = "Mensaje de respuesta")
    private String mensaje;

}
