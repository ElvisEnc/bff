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
    @NotNull(message = "Invalid amount")
    @JsonProperty(required = true)
    private AmountTransfer amount;

    @JsonProperty
    @NotNull(message = "Invalid data")
    private DataTransfer data;

    @JsonProperty(required = true)
    @Schema(description = "Format of the transfer request", allowableValues = {"JPG", "PNG"}, example = "JPG")
    private String format;
}
