package bg.com.bo.bff.application.dtos.response.destination.account;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ThirdAccount {
    @Schema(example = "6577483", description = "Id")
    private String id;

    @Schema(example = "4355307", description = "Id de la cuenta")
    private String accountId;

    @Schema(example = "1310325715", description = "Número de la cuenta")
    private String accountNumber;

    @Schema(example = "068", description = "Código de la moneda de la cuenta")
    private String currencyCode;

    @Schema(example = "Bs", description = "Acrónimo de la moneda")
    private String currencyAcronym;

    @Schema(example = "Caja de Ahorros", description = "Tipo de cuenta")
    private String accountType;

    @Schema(example = "CA", description = "Abreviación del tipo de cuenta")
    private String accountTypeAbbreviation;

    @Schema(example = "EMPLEADO BANCO", description = "Nombre del cliente")
    private String clientName;

    @Schema(example = "mi cuenta", description = "Alias de la cuenta")
    private String accountAliases;

    @Schema(example = "S", description = "Cuenta favorita")
    private String isFavorite;
}
