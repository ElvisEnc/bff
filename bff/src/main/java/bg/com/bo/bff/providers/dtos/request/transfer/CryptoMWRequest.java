package bg.com.bo.bff.providers.dtos.request.transfer;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CryptoMWRequest {

    private String description;
}
