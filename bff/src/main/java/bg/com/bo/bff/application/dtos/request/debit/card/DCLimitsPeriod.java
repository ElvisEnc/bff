package bg.com.bo.bff.application.dtos.request.debit.card;

import bg.com.bo.bff.commons.annotations.DatePattern;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
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
    @Schema(description = "Periodo de inicio del límite", example = "2024-08-04")
    @DatePattern
    private String start;

    @Schema(description = "Periodo de finalización del límite", example = "2024-08-04")
    @DatePattern
    private String end;
}
