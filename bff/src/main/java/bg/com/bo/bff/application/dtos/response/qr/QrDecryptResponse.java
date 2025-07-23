package bg.com.bo.bff.application.dtos.response.qr;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class QrDecryptResponse {
    @Schema(description = "Identificador único para el código QR", example = "1234567890")
    private String qrId;

    @Schema(description = "Nombre de la empresa", example = "Compañía Ejemplo")
    private String companyName;

    @Schema(description = "Número de carnet de identidad", example = "987654321")
    private String identificationNumber;

    @Schema(description = "Información EIF", example = "EIF Ejemplo")
    private String eif;

    @Schema(description = "Número de cuenta bancaria", example = "123-456-789")
    private String accountNumber;

    @Schema(description = "Tipo de moneda", example = "BOB")
    private String currency;

    @Schema(description = "Monto de la transacción", example = "150.75")
    private BigDecimal amount;

    @Schema(description = "Referencia de la transacción", example = "Ref123456")
    private String reference;

    @Schema(description = "Fecha de vencimiento del código QR", example = "2024-12-31T23:59:59Z")
    private String expirationDate;

    @Schema(description = "Indica si el código QR es de un solo uso", example = "0")
    private String singleUse;

    @Schema(description = "Código de servicio asociado con el código QR", example = "18001")
    private Integer serviceCode;

    @Schema(description = "Descripción adicional o campo libre", example = "Pago por servicios")
    private String freeField;

    @Schema(description = "Número de serie del código QR", example = "SN123456789")
    private String serialNumber;

    @Schema(description = "Nombre del banco", example = "Banco Ejemplo")
    private String bank;

    @Schema(description = "Tipo de banco", example = "Banco Ejemplo")
    private String bankType;
}
