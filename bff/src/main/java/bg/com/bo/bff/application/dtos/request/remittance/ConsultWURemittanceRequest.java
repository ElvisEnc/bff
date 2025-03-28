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
public class ConsultWURemittanceRequest {

    @Valid
    @NotBlank(message = "El  valor applicationId no puede ser vacío.")
    @NotNull(message = "El valor applicationId no puede ser valor nulo.")
    @OnlyNumber(message = "El valor applicationId solo puede contener números.")
    private String applicationId;

    @Valid
    @NotBlank(message = "La cuenta no puede ser vacío.")
    @NotNull(message = "La cuenta no puede ser valor nulo.")
    @OnlyNumber(message = "El identificador de la cuenta solo puede contener números.")
    private String jtsOidAccount;


}
