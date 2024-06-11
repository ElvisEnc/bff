package bg.com.bo.bff.application.dtos.response.debit.card;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AccountTD {
    @Schema(description = "id de la cuenta")
    private String id;

    @Schema(description = "número de la cuenta")
    private String accountNumber;

    @Schema(description = "descripción de la cuenta")
    private String description;
}
