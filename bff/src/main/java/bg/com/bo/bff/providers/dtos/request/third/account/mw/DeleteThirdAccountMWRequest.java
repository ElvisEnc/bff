package bg.com.bo.bff.providers.dtos.request.third.account.mw;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DeleteThirdAccountMWRequest {
    private String personId;
    private String identifier;
    private String accountNumber;
}
