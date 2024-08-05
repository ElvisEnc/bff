package bg.com.bo.bff.application.dtos.request.loans;

import bg.com.bo.bff.application.dtos.request.destination.account.PaginationRequest;
import bg.com.bo.bff.application.dtos.request.qr.OrderRequest;
import bg.com.bo.bff.application.dtos.request.qr.PeriodRequest;
import bg.com.bo.bff.commons.annotations.OnlyNumber;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoanPaymentsRequest {
    @NotBlank
    @OnlyNumber
    @Schema(description = "Número del prestamo", example = "101106790")
    private String loanNumber;

    @Valid
    @NotNull
    private LoanPaymentsRequest.LoanPaymentsFilter filters;

    @NotNull
    @Schema(description = "Indica si se debe refrescar la información", example = "false")
    private Boolean refreshData;

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class LoanPaymentsFilter {
        @Valid
        private PaginationRequest pagination;

        @Valid
        private PeriodRequest date;

        @Valid
        private OrderRequest order;
    }
}
