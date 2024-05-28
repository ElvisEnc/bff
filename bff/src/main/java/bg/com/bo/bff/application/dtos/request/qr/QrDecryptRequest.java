package bg.com.bo.bff.application.dtos.request.qr;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class QrDecryptRequest {
    @Valid
    @NotBlank(message = "El campo data es obligatorio")
    @NotNull
    private String data;
}
