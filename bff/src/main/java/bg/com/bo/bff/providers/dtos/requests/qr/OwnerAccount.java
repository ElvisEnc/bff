package bg.com.bo.bff.providers.dtos.requests.qr;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class OwnerAccount {
    private String schemeName;
    private String personId;
}
