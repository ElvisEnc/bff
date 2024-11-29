package bg.com.bo.bff.application.dtos.request.qr;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
@JsonRootName(value = "QRTransferRequest")
public class QRPaymentRequest {

    private TargetAccount targetAccount;

    @Valid
    @NotNull(message = "Monto no válido")
    @JsonProperty(required = true)
    private Amount amount;

    private SupplementaryData supplementaryData;
    private Risk risk;
}
