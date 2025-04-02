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
public class DepositRemittanceWURequest {

    @Valid
    @NotBlank(message = "El JTS_OID de la persona no puede ser vacío.")
    @NotNull(message = "El JTS_OID no puede ser valor nulo.")
    @OnlyNumber(message = "El JTS_OID de la persona solo puede contener números.")
    private String accountId;

    @Valid
    private String consultationId;

    @Valid
    @NotBlank(message = "El PCC01 de la remesa no puede ser vacío.")
    @NotNull(message = "El PCC01 de la remesa no puede ser valor nulo.")
    private String pccType;

    @Valid
    private String originFunds;


    @Valid
    private String originDestiny;
}