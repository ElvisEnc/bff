package bg.com.bo.bff.providers.dtos.request.qr.mw;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class QRCodeRegenerateMWRequest {
    private String fields;
}
