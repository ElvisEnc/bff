package bg.com.bo.bff.controllers.request;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import java.math.BigDecimal;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
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
@Schema(name = "Transfer Own Account", description = "Transfer Own Account Request")
public class TransferRequest {
    @Valid
    @NotNull(message = "Invalid source account")
    @Pattern(regexp = "[0-9]+", message = "Invalid source account")
    @JsonProperty(required = true)
    @Schema(description = "From", required = true)
    private String fromAccountId;

    @Valid
    @NotNull(message = "Invalid destination account")
    @Pattern(regexp = "[0-9]+", message = "Invalid destination account")
    @JsonProperty(required = true)
    @Schema(description = "To", required = true)
    private String toAccountId;

    @NotNull(message = "Invalid currency account")
    @NotBlank(message = "Currency cannot be empty")
    @JsonProperty(required = true)
    @Schema(description = "Currency", required = true)
    private String currency;

    @DecimalMin(value = "0", inclusive = false, message = "Amount must be greater than zero")
    @NotNull(message = "Invalid amount")
    @JsonProperty(required = true)
    @Digits(integer = 13, fraction = 2, message = "Invalid amount")
    @Schema(description = "Amount")
    private String amount;

    @JsonProperty
    @NotNull(message = "Invalid gloss")
    @Size(min = 3, max = 120, message="Invalid gloss")
    @Schema(description = "Description")
    private String description;

    @Schema(description = "sourceOfFunds")
    private String sourceOfFunds;

    @Schema(description = "destinationOfFunds")
    private String destinationOfFunds;

}
