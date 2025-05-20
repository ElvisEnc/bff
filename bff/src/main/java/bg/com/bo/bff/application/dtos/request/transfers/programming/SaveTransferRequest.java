package bg.com.bo.bff.application.dtos.request.transfers.programming;

import bg.com.bo.bff.commons.annotations.OnlyNumber;
import bg.com.bo.bff.commons.annotations.generics.ValidAmount;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SaveTransferRequest {

    @Schema(description = "Identificador de la cuenta de origen")
    @OnlyNumber
    private String originJtsOid;

    @Schema(description = "Nro de cuenta destino")
    @OnlyNumber
    private String destinationAccountNumber;

    @Schema(description = "Monto que sera transferido")
    @ValidAmount
    private String amount;

    @Schema(description = "Codigo de moneda")
    @OnlyNumber
    private String currency;

    @Schema(description = "Tipo de transferencia")
    private String transferType;

    @Schema(description = "Glosa para la transferencia programada")
    private String description;

    @Schema(description = "Codigo de frecuencia para la transferencia")
    private String frequencyCode;

    @Schema(description = "Fecha de inicio para la programación")
    private String initDate;

    @Schema(description = "Fecha de final para la programación")
    private String endDate;

    @Schema(description = "Usuario que registra")
    private String userRegistry;

    @Schema(description = "Identificador de la cuenta destino")
    private String destinationJtsOid;

    @Schema(description = "Codigo de lista")
    private String listCode;

    @Schema(description = "Codigo EIF")
    private String codEif;

    @Schema(description = "Destino de los fondos")
    private String destinationFounds;

    @Schema(description = "Origen de los fondos")
    private String originFounds;

    @Schema(description = "Razon por la cual se hace la transferencia")
    private String reason;

    @Schema(description = "Nombre del titular de la cuenta destino")
    private String destinationName;

    @Schema(description = "Nombre del banco")
    private String bankName;

    @Schema(description = "Codigo de control UIF")
    private String uifControl;


}
