package bg.com.bo.bff.application.dtos.request.qr;

import bg.com.bo.bff.commons.annotations.generics.DescriptionChars;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SupplementaryData {

    private String idQr;
    private String nroDni;
    @DescriptionChars
    private String description;
    private String dueDate;
    private String typeUse;
    private String serviceCode;
    private String fields;
    private String serialNumber;
    private String allowsDuplicate;

    @DescriptionChars
    @Size(min = 25, max = 100, message = "La fuente de los fondos debe tener entre 25 y 100 caracteres.")
    @Schema(description = "Fuente de fondos para la transferencia", example = "Fuente de fondos para la transferencia")
    private String sourceOfFunds;

    @DescriptionChars
    @Size(min = 25, max = 100, message = "El destino de los fondos debe tener entre 25 y 100 caracteres.")
    @Schema(description = "Destino de los fondos para la transferencia", example = "Destino de los fondos para la transferencia")
    private String destinationOfFunds;

}
