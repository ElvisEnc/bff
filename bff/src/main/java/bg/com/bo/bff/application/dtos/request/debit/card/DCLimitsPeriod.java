package bg.com.bo.bff.application.dtos.request.debit.card;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonIgnoreProperties(ignoreUnknown = true)
@Setter
public class DCLimitsPeriod {
    @NotNull(message = "La fecha de inicio no puede estar vacía")
    @Schema(description = "Periodo de inicio del límite", example = "2024-08-04")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private String start;

    @NotNull(message = "La fecha final no puede estar vacía")
    @Schema(description = "Periodo de finalización del límite", example = "2024-08-04")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private String end;
}
