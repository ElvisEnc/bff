package bg.com.bo.bff.application.dtos.request.transfer;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonRootName(value = "OwnAccountTransferRequest")
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonIgnoreProperties(ignoreUnknown = true)
public class TransferRequest {
    @Valid
    @NotNull(message = "Cuenta de destino no válida")
    @JsonProperty(required = true)
    private TargetAccount targetAccount;

    @Valid
    @NotNull(message = "Monto no válido")
    @JsonProperty(required = true)
    private AmountTransfer amount;

    @Valid
    @JsonProperty
    @NotNull(message = "Datos no válidos")
    private DataTransfer data;

    @Valid
    @NotNull(message = "Formato no válido")
    @JsonProperty(required = true)
    @Schema(description = "Formato del comprobante", allowableValues = {"JPG", "PNG"}, example = "PNG")
    private String format;
}
