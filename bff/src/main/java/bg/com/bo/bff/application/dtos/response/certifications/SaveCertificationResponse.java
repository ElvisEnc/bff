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
@Schema(description = "Respuesta del servicio de registro de solicitud de Certificados")
public class SaveCertificationResponse {

    @Schema(description = "Costo del certificado")
    private String certPrice;

    @Schema(description = "Fecha en que se realizo la solicitud")
    private String requestDate;

    @Schema(description = "Hora en que se realizo la solicitud")
    private String requestTime;

    @Schema(description = "Moneda de la cuenta con la que se pago")
    private String fromCurrency;

    @Schema(description = "Nro de cuenta con la que se pago")
    private String originAccount;

    @Schema(description = "Nombre del cliente")
    private String clientAccountName;

    @Schema(description = "Correo electronico del cliente")
    private String email;

    @Schema(description = "Descripcion del Certificado")
    private String certDescription;

    @Schema(description = "Rango de tiempo para el certificado solicitado")
    private String dateRange;

    @Schema(description = "Nro de solicitud")
    private String requestNumber;

}
