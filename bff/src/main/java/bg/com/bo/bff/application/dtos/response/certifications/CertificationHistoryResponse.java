package bg.com.bo.bff.application.dtos.response.certifications;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CertificationHistoryResponse {

    @Schema(description = "Numero de la solicitud realizada")
    private String requestNumber;

    @Schema(description = "Fecha de solicitud")
    private String date;

    @Schema(description = "Titulo del documento")
    private String title;

    @Schema(description = "CITE del documento")
    private String docNumber;

    @Schema(description = "Estado de la solicitud")
    private String state;

    @Schema(description = "correo electronico del solicitante")
    private String mail;

}
