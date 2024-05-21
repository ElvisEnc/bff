package bg.com.bo.bff.providers.dtos.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class QRCodeRegenerateMWRequest {
    private String fields;
}
