package bg.com.bo.bff.providers.dtos.response.qr.mw;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class QRCodeGenerateResponse {
    private QRCDataResponse data;
}

