package bg.com.bo.bff.application.dtos.request.debit.card;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonRootName;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonRootName(value = "ModifyAccountsOrderRequest")
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonIgnoreProperties(ignoreUnknown = true)
public class DCAccountsOrderRequest {
    @Schema(description = "Lista de cuentas a ordenar")
    @NotNull(message = "La lista de cuentas es obligatoria")
    @Size(min =  1, max = 4, message = "La lista debe tener como mínimo una cuenta y máximo cuatro cuentas")
    private List<Account> data;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonRootName(value = "ModifyAccountsOrderItemRequest")
    public static class Account {

        @Schema(example = "1234567890", description = "Numeró de la cuenta asociada")
        @NotNull(message = "El número de la cuenta es obligatorio")
        private String accountNumber;
    }
}
