package bg.com.bo.bff.application.dtos.request.account.statement;

import bg.com.bo.bff.application.dtos.request.commons.PeriodRequest;
import bg.com.bo.bff.application.dtos.request.commons.SearchCriteriaRequest;
import bg.com.bo.bff.application.dtos.request.destination.account.PaginationRequest;
import bg.com.bo.bff.application.dtos.request.qr.OrderRequest;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TransferMovementsRequest {

    @Valid
    @NotNull
    private TransferMovementsRequest.MovementsFilter filters;

    @NotNull
    @Schema(description = "Indica si se debe refrescar la información", example = "false")
    private Boolean refreshData;

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class MovementsFilter {
        @Valid
        private SearchCriteriaRequest searchCriteria;

        @Valid
        private PaginationRequest pagination;

        @Valid
        private PeriodRequest period;

        @Valid
        private OrderRequest order;
    }
}
