package bg.com.bo.bff.application.dtos.request.payment.service;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ValidateAffiliateCriteriaRequest {

    @Schema(description = "Year", example = "2023")
    @NotNull(message = "Year is required")
    private Integer year;

    @Schema(description = "Search Criteria ID", example = "28")
    @NotBlank(message = "Search criteria ID is required")
    private String criteriaId;

    @Schema(description = "Search Criteria ID Abbreviation", example = "3")
    @NotBlank(message = "Search criteria ID abbreviation is required")
    private String criteriaIdAbbreviation;

    @Schema(description = "Search fields")
    @NotNull(message = "Search fields are required")
    private List<SearchField> searchFields;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SearchField {

        @Schema(description = "Field code", example = "28")
        @NotBlank(message = "Field code is required")
        private String code;

        @Schema(description = "Field value", example = "73166120")
        @NotBlank(message = "Field value is required")
        private String value;
    }
}
