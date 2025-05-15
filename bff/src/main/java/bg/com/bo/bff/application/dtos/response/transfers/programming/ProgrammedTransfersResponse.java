package bg.com.bo.bff.application.dtos.response.transfers.programming;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProgrammedTransfersResponse {

    @Schema(description = "Identificador de la transferencia")
    private int transferId;

    @Schema(description = "NUmero de persona")
    private int personId;

    @Schema(description = "Id de la cuenta de origen")
    private int originJtsOid;

    @Schema(description = "Numero de cuenta destino")
    private String destinationAccount;

    @Schema(description = "Monto de transferencia")
    private BigDecimal amount;

    @Schema(description = "Codigo de moneda")
    private int currency;

    @Schema(description = "Tipo de transferencia")
    private String transferType;

    @Schema(description = "Descripcion")
    private String description;

    @Schema(description = "Codigo de frecuencia")
    private String frequencyCode;

    @Schema(description = "Fecha de inicio para la programacion de transferencia")
    private String initDate;

    @Schema(description = "Fecha de end para la programacion de transferencia")
    private String endDate;

    @Schema(description = "Usuario que hace el registro")
    private String userRegistry;

    @Schema(description = "Fecha de registro")
    private String registryDate;

    @Schema(description = "Usuario que modifica el registro")
    private String userModification;

    @Schema(description = "Fecha de modificacion")
    private String modificationDate;

    @Schema(description = "Codigo TZ_LOCK")
    private int tzLock;

    @Schema(description = "Id de la cuenta destino")
    private int destinationJtsOid;

    @Schema(description = "Codigo de lista")
    private int listCode;

    @Schema(description = "Codigo EIF")
    private String codEif;

    @Schema(description = "Destino de fondos")
    private String destinationFounds;

    @Schema(description = "Origen de fondos")
    private String originFounds;

    @Schema(description = "Motivo de la programación de la transferencia")
    private String reason;

    @Schema(description = "Estado")
    private String status;

    @Schema(description = "Nombre del destinatrario")
    private String destinationName;

    @Schema(description = "Nombre del banco")
    private String bankName;

    @Schema(description = "Coigo de control UIF")
    private String uifControl;

    @Schema(description = "Numero de cuotas")
    private int feeNumber;

}
