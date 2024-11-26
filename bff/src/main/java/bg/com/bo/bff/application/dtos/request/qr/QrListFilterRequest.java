package bg.com.bo.bff.application.dtos.request.qr;

import bg.com.bo.bff.application.dtos.request.commons.PeriodRequest;
import bg.com.bo.bff.application.dtos.request.commons.SearchCriteriaRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class QrListFilterRequest {
    @Valid
    @NotNull(message = "period es obligatorio")
    private PeriodRequest period;

    @Valid
    @NotNull(message = "searchCriteria es obligatorio")
    private SearchCriteriaRequest searchCriteria;
}
