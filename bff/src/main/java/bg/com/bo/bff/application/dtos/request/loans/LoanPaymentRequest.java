package bg.com.bo.bff.application.dtos.request.loans;

import bg.com.bo.bff.commons.annotations.OnlyNumber;
import bg.com.bo.bff.commons.annotations.generics.DescriptionChars;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonIgnoreProperties(ignoreUnknown = true)
public class LoanPaymentRequest {

    @OnlyNumber
    @Parameter(description = "Este es el correlativeId del préstamo", example = "12345")
    private String correlativeId;

    private SupplementaryData supplementaryData;

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class SupplementaryData {

        @DescriptionChars
        @Size(min = 25, max = 100, message = "La fuente de los fondos debe tener entre 25 y 100 caracteres.")
        @Schema(description = "Fuente de fondos para la transferencia", example = "Fuente de fondos para la transferencia")
        private String sourceOfFunds;

        @DescriptionChars
        @Size(min = 25, max = 100, message = "El destino de los fondos debe tener entre 25 y 100 caracteres.")
        @Schema(description = "Destino de los fondos para la transferencia", example = "Destino de los fondos para la transferencia")
        private String destinationOfFunds;
    }
}
