package bg.com.bo.bff.application.dtos.request.certifications;

import bg.com.bo.bff.commons.annotations.DatePattern;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class SaveCertificationRequest {

    @Schema(description = "Numero de Persona")
    private String personId;

    @Schema(description = "Identificador de la cuenta")
    private String accountId;

    @Schema(description = "Codigo de Cargo de Tarifa")
    private String chargeFeeId;

    @Schema(description = "Codigo de tipo de solicitud")
    private String typeCode;

    @Schema(description = "Codigo de la solicitud")
    private String requestCode;

    @Schema(description = "Numero de NIT del cliente")
    private String nit;

    @Schema(description = "Nombre del cliente")
    private String clientName;

    @Schema(description = "Fecha de inicio")
    @DatePattern(message = "Formato de fecha invalido")
    private String initDate;

    @Schema(description = "Fecha de fin")
    @DatePattern(message = "Formato de fecha invalido")
    private String endDate;

    @Schema(description = "correo electronico del cliente")
    @Email
    private String email;

}
