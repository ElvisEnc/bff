package bg.com.bo.bff.application.dtos.request.transfer;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonIgnoreProperties(ignoreUnknown = true)
public class DataTransfer {
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
