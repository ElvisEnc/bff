package bg.com.bo.bff.application.dtos.request;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class QRCodeRegenerateRequest {

    @NotBlank(message = "Identificador QR no puede estar vacio")
    @Schema(example = "1232321123", description = "Identificador QR")
    private String idQr;

    @NotBlank(message = "Razón Social no puede estar vacio")
    @Schema(example = "Banco Ganadero", description = "Razón social")
    private String companyName;

    @NotBlank(message = "Número de identificador no puede estar vacio")
    @Schema(example = "342324455", description = "Número de identificador persona")
    private String identificationNumber;

    @NotBlank(message = "eif no puede estar vacio")
    @Schema(example = "12121", description = "eif")
    private String eif;

    @NotBlank(message = "Número de cuennta")
    @Schema(example = "1234223232", description = "Este es el Número de cuenta")
    private String accountNumber;

    @NotBlank(message = "El código de moneda no puede estar vacío")
    @Schema(example = "BOB", description = "Este es el código de la moneda")
    private String currency;

    @NotBlank(message = "El monto no puede estar vacío")
    @Schema(example = "100.50", description = "Este es el monto de pago")
    private String amount;

    @NotBlank(message = "La referencia no puede estar vacío")
    @Schema(example = "Gastos Generales", description = "Esta es la referencia")
    private String reference;

    @NotBlank(message = "El periodo de finalizacion no puede estar vacío")
    @Schema(example = "2024-05-10", description = "Fecha de expiracióñ ")
    private String expirationDate;

    @NotBlank(message = "")
    @Schema(example = "1", description = "Este es campo indica si es de uso unico o multiple 0 ó 1")
    private String singleUse;

    @NotBlank(message = "El codigo de servicio no puede estar vacío")
    @Schema(example = "1", description = "Codigo de servicio 1 a 5")
    private String codService;

    @NotBlank(message = "El campo field no puede estar vacío")
    @Schema(example = "texto", description = "Campos")
    private String field;

}


