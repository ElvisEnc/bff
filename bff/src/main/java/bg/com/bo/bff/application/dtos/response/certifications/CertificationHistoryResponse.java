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

    @Schema(description = "Dia en que se realizo la solicitud")
    private String day;

    @Schema(description = "Mes en el que se realizo la solicitud")
    private String month;

    @Schema(description = "Año en que se realizo la soliciatud")
    private String year;

    @Schema(description = "Titulo del documento")
    private String title;

    @Schema(description = "CITE del documento")
    private String docNumber;

    @Schema(description = "Estado de la solicitud")
    private String state;

    @Schema(description = "correo electronico del solicitante")
    private String mail;

}
