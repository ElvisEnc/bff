package bg.com.bo.bff.application.dtos.request.account.statement;

import bg.com.bo.bff.application.dtos.request.destination.account.PaginationRequest;
import bg.com.bo.bff.application.dtos.request.qr.OrderRequest;
import bg.com.bo.bff.application.dtos.request.commons.PeriodRequest;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccountStatementsRequest {
    @Valid
    @NotNull
    private AccountStatementsFilter filters;

    @NotNull
    @Schema(description = "Indica si se debe refrescar la información", example = "false")
    private Boolean refreshData;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class AccountStatementsFilter {
        @Valid
        @NotNull
        private PeriodRequest date;

        @Valid
        @NotNull
        private PaginationRequest pagination;

        @Valid
        private OrderRequest order;

        @Valid
        private AmountRange amount;

        @Pattern(regexp = "[12]", message = "1 = debito o 2 = credito")
        @Schema(description = "Para filtrar por tipo de movimiento. 1 = Debito, 2 = Credito")
        private String movementType;
    }
}
