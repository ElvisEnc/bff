package bg.com.bo.bff.application.dtos.request.qr;


import bg.com.bo.bff.commons.annotations.generics.DescriptionChars;
import bg.com.bo.bff.commons.annotations.generics.ValidAmountQR;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class QRCodeGenerateRequest {

    @NotBlank(message = "Razón Social no puede estar vacio")
    @Schema(example = "Banco Ganadero", description = "Razón social")
    private String companyName;

    @ValidAmountQR
    @DecimalMin(value = "0", inclusive = true, message = "El monto debe ser mayor o igual a cero")
    private String amount;

    @NotBlank(message = "El código de moneda no puede estar vacío")
    @Schema(example = "068", description = "Este es el código de la moneda")
    private String currency;


    @Schema(example = "Gastos Generales", description = "Esta es la referencia")
    @DescriptionChars
    private String reference;

    @NotBlank(message = "El periodo de finalizacion no puede estar vacío")
    @Schema(example = "1", description = "Periodo de duración QR 1=dia, 2=mes, 3=año ")
    private String typeDueDate;

    @NotBlank(message = "El codigo de servicio no puede estar vacío")
    @Schema(example = "1", description = "Codigo de servicio 1 a 5")
    private String codService;

    @NotBlank(message = "")
    @Schema(example = "1", description = "Este es campo indica si es de uso unico o multiple 0 ó 1")
    private String singleUse;

    @NotBlank(message = "Número de cuennta")
    @Schema(example = "1234223232", description = "Este es el Número de cuenta")
    private String accountNumber;

    @NotBlank(message = "El campo field no puede estar vacío")
    @Schema(example = "texto", description = "Campos")
    private String field;

    @NotBlank(message = "El número de serie no puede estar vacío")
    @Schema(example = "12121ddss", description = "Este es el código de la moneda")
    private String serialNumber;

    @NotBlank(message = "El tipo de operación no debe estara vacio")
    @Schema(example = "1", description = "Este es el código de la moneda")
    private String operationType;

    @NotBlank(message = "El código de Persona no puede estar vacío")
    @Schema(example = "841210", description = "Codigo de persona")
    private String personId;

    @NotBlank(message = "El usuario de regisrno puede estar vacío")
    @Schema(example = "1", description = "Este es el código de la moneda")
    private String userRegister;

    @NotBlank(message = "El usuario de regisrno puede estar vacío")
    @Schema(example = "840", description = "Este es el código de la moneda")
    private String typeReturn;

    @NotBlank(message = "El Formato de imagen no puede estar vacío")
    @Schema(example = "3", description = "1 a 6")
    private String formatImage;

}



