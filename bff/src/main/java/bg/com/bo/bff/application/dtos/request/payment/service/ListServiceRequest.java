package bg.com.bo.bff.application.dtos.request.payment.service;

import bg.com.bo.bff.application.dtos.request.destination.account.PaginationRequest;
import bg.com.bo.bff.application.dtos.request.qr.OrderRequest;
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
public class ListServiceRequest {

    @NotNull
    @Valid
    private Filter filters;

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Filter {
        @Valid
        private PaginationRequest pagination;

        @Valid
        private OrderRequest order;

        @Schema(example = "entel", description = "Palabra para buscar el servicio")
        private String search;
    }
}
