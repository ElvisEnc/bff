package bg.com.bo.bff.application.dtos.request.credit.card;

import bg.com.bo.bff.application.dtos.request.destination.account.PaginationRequest;
import bg.com.bo.bff.application.dtos.request.qr.OrderRequest;
import bg.com.bo.bff.application.dtos.request.commons.PeriodRequest;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreditCardStatementRequest {
    @NotBlank
    @Pattern(regexp = "13-\\d{2}-10-\\d{10}", message = "Formato inválido para cmsCard")
    @Schema(description = "número compuesto de la Tarjeta", example = "13-07-10-0000000005")
    private String cmsCard;

    @Valid
    private CreditCardFilter filters;

    @NotNull
    @Schema(description = "Indica si se debe refrescar la información", example = "false")
    private Boolean refreshData;

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class CreditCardFilter{
        @Valid
        private PaginationRequest pagination;

        @Valid
        @NotNull
        private PeriodRequest date;

        @Valid
        private OrderRequest order;
    }
}
