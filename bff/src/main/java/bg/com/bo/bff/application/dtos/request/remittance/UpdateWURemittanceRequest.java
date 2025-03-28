package bg.com.bo.bff.application.dtos.request.remittance;

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
public class    UpdateWURemittanceRequest {

    @Valid
    @NotBlank(message = "El noConsult no puede ser vacío.")
    @NotNull(message = "El noConsult no puede ser valor nulo.")
    private String relation;

    @Valid
    @NotBlank(message = "El origin no puede ser vacío.")
    @NotNull(message = "El origin no puede ser valor nulo.")
    private String origin;

    @Valid
    @NotBlank(message = "transaction no puede ser vacío.")
    @NotNull(message = "transaction no puede ser valor nulo.")
    private String transaction;

    @Valid
    @NotBlank(message = "company no puede ser vacío.")
    @NotNull(message = "company no puede ser valor nulo.")
    private String company;

    @Valid
    @NotBlank(message = "companyLevel no puede ser vacío.")
    @NotNull(message = "companyLevel no puede ser valor nulo.")
    private String companyLevel;

    @Valid
    @NotBlank(message = "entryDate no puede ser vacío.")
    @NotNull(message = "entryDate no puede ser valor nulo.")
    private String entryDate;

    @Valid
    @NotBlank(message = "laborType no puede ser vacío.")
    @NotNull(message = "laborType no puede ser valor nulo.")
    private String laborType;


}
