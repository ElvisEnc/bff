package bg.com.bo.bff.providers.dtos.request.debit.card.mw;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DCLockStatusMWRequest {
    private String pciId;
    private String personId;
    private String lockStatus;
    private String comment;
}
