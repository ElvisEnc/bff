package bg.com.bo.bff.application.dtos.request.payment.service;

import bg.com.bo.bff.commons.annotations.OnlyNumber;
import bg.com.bo.bff.commons.annotations.ValidText;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;

public record PaymentDebtsRequest(
        @NotBlank
        @Size(min = 1, max = 10)
        @Pattern(regexp = "\\d+", message = "La moneda debe contener solo números")
        @Schema(description = "Código de la moneda", example = "068")
        String currencyCode,

        @NotNull
        @DecimalMin(value = "0", inclusive = false, message = "El monto debe ser mayor que cero")
        @Digits(integer = 12, fraction = 2, message = "El monto debe tener hasta 12 dígitos enteros y 2 decimales")
        @Schema(description = "Monto", example = "100.00")
        BigDecimal amount,

        @NotNull
        @Min(value = 1)
        @Schema(description = "Cuenda de origen", example = "12546878")
        Long fromAccountId,

        @NotBlank
        @Size(min = 1, max = 100)
        @Schema(description = "uuid", example = "324a029a-553f-4acb-abf4-4dcb25574463")
        String idGenerated,

        @OnlyNumber
        @Schema(description = "Nit de la factura", example = "12546878")
        String invoiceNit,

        @NotBlank
        @Size(max = 150)
        @Schema(description = "Nombre de la factura", example = "Juan Perez")
        String invoiceName,

        @Schema(description = "Tipo de facturacion")
        String invoiceType,

        @Schema(description = "Complemento del carnet de identidad", example = "1L")
        @Size(max = 2)
        String invoiceComplementId,

        @Schema(description = "Correo electronico al que se envia la factura.", example = "test@hotmail.com")
        @Size(max = 50)
        String invoiceEmail,

        @NotBlank
        @Size(max = 150)
        @Schema(description = "Compañia", example = "company")
        String company,

        @OnlyNumber
        @Schema(description = "Código de servicio", example = "77")
        String serviceCode,

        @NotBlank
        @ValidText
        @Size(max = 150)
        @Schema(description = "Descripción", example = "Pagos")
        String description
) {
}
