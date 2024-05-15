package bg.com.bo.bff.application.dtos.request;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import java.math.BigDecimal;

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
    private Amount amount;

    @JsonProperty
    @NotNull(message = "Invalid data")
    private Data data;

    @JsonProperty(required = true)
    @Schema(description = "Format of the transfer request", allowableValues = {"JPG", "PNG"}, example = "JPG")
    private String format;

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class TargetAccount {
        @NotNull(message = "Invalid id")
        @NotBlank(message = "Id cannot be empty")
        @Schema(description = "Identifier of the target account", example = "123456789")
        private String id;
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Amount {
        @NotNull(message = "Invalid currency")
        @NotBlank(message = "Currency cannot be empty")
        @Size(min = 3, message = "Description must be greater than 3 digits")
        @Pattern(regexp = "\\d+", message = "Currency must contain only numbers")
        @Schema(description = "Currency code", example = "068")
        private String currency;

        @NotNull(message = "Invalid amount")
        @DecimalMin(value = "0", inclusive = false, message = "Amount must be greater than zero")
        @Digits(integer = 13, fraction = 2, message = "Invalid amount")
        @Schema(description = "Transfer amount", example = "100.00")
        private BigDecimal amount;
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonRootName(value = "DataTransferRequest")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Data {
        @NotBlank(message = "Description cannot be empty")
        @NotNull(message = "Invalid description")
        @Size(min = 3, message = "Description must be greater than 3 character")
        @Schema(description = "Description of the transfer", example = "Payment of services")
        private String description;

        @Schema(description = "Source of funds for the transfer", example = "Salary")
        private String sourceOfFounds;

        @Schema(description = "Destination of funds for the transfer", example = "Services")
        private String destinationOfFounds;
    }
}
