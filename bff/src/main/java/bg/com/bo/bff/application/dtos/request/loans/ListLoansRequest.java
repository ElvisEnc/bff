package bg.com.bo.bff.application.dtos.request.loans;

import bg.com.bo.bff.application.dtos.request.destination.account.PaginationRequest;
import bg.com.bo.bff.application.dtos.request.qr.OrderRequest;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
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
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ListLoansRequest {
    @Valid
    @Schema(description = "Filtros para obtener la lista de préstamos")
    private ListLoansRequest.LoansFilter filters;

    @NotNull
    @Schema(description = "Indica si se debe refrescar la información")
    private Boolean refreshData;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class LoansFilter {

        @Valid
        private PaginationRequest pagination;

        @Valid
        private OrderRequest order;
    }
}
