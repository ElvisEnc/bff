package bg.com.bo.bff.application.dtos.request.destination.account;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DestinationAccountRequest {
    @Schema(example = "Kar", description = "Nombre a buscar entre las cuentas a transferir")
    private String name;

    @Valid
    private PaginationRequest pagination;
}
