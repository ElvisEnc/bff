package bg.com.bo.bff.application.dtos.request.remittance;

import bg.com.bo.bff.commons.annotations.OnlyNumber;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@lombok.Data
@lombok.Builder
@lombok.NoArgsConstructor
@lombok.AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonIgnoreProperties(ignoreUnknown = true)
public class DepositRemittanceRequest {

    @Valid
    @NotBlank(message = "El identificador de la persona no puede ser vacío.")
    @NotNull(message = "La cuenta no puede ser valor nulo.")
    @OnlyNumber(message = "El número de cuenta de la persona solo puede contener números.")
    private String accountId;

    @Valid
    @NotBlank(message = "El identificador de la consulta no puede ser vacío.")
    @NotNull(message = "La consulta no puede ser valor nulo.")
    @OnlyNumber(message = "El identificador de la persona solo puede contener números.")
    private String consultationId;
}
