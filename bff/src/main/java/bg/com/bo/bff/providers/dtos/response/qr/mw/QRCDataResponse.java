package bg.com.bo.bff.providers.dtos.response.qr.mw;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QRCDataResponse {
    @Schema(description = "Generic response")
    private String response;
}
