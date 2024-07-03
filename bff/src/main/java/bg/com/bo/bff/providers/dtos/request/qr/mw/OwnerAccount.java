package bg.com.bo.bff.providers.dtos.request.qr.mw;

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
