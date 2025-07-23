package bg.com.bo.bff.application.dtos.request.qr;

import bg.com.bo.bff.application.dtos.request.destination.account.PaginationRequest;
import bg.com.bo.bff.commons.annotations.qr.ValidOperationType;
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
public class QrListRequest {
    @NotNull
    @ValidOperationType
    @Schema(description = "Tipo de operación", example = "1")
    private String operationType;

    @Valid
    @NotNull(message = "filters es obligatorio")
    private QrListFilterRequest filters;

    @Valid
    private PaginationRequest pagination;

    @Valid
    private OrderRequest order;
}
