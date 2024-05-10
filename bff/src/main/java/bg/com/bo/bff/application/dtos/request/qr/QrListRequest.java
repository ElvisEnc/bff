package bg.com.bo.bff.application.dtos.request.qr;

import bg.com.bo.bff.application.dtos.request.destination.account.PaginationRequest;
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
    @Valid
    @NotNull(message = "filters es obligatorio")
    private QrListFilterRequest filters;

    @Valid
    private PaginationRequest pagination;

    @Valid
    private QrListOrderRequest order;
}
