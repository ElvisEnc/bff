package bg.com.bo.bff.application.dtos.request.destination.account;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
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
    @Pattern(regexp = "^$|.{3,}", message = "El nombre puede ser vacío o tiene que tener arriba de 3 caracteres")
    private String name;

    @Valid
    private PaginationRequest pagination;
}
