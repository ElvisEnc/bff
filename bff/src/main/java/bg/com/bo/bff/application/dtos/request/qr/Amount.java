package bg.com.bo.bff.application.dtos.request.qr;

import bg.com.bo.bff.commons.annotations.generics.ValidAmountQR;
import jakarta.validation.constraints.DecimalMin;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class Amount {

    private String currency;
    @ValidAmountQR
    @DecimalMin(value = "0.10", inclusive = true, message = "El monto debe ser mayor a cero")
    private String amount;
}
