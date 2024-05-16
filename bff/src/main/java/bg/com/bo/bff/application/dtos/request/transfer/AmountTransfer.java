package bg.com.bo.bff.application.dtos.request.transfer;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonIgnoreProperties(ignoreUnknown = true)
public class AmountTransfer {
    @NotNull(message = "Invalid currency")
    @NotBlank(message = "Currency cannot be empty")
    @Pattern(regexp = "\\d+", message = "Currency must contain only numbers")
    @Schema(description = "Currency code", example = "068")
    private String currency;

    @NotNull(message = "Invalid amount")
    @DecimalMin(value = "0", inclusive = false, message = "Amount must be greater than zero")
    @Digits(integer = 13, fraction = 2, message = "Invalid amount")
    @Schema(description = "Transfer amount", example = "100.00")
    private BigDecimal amount;
}
