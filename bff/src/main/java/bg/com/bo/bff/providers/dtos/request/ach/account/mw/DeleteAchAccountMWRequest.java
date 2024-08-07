package bg.com.bo.bff.providers.dtos.request.ach.account.mw;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DeleteAchAccountMWRequest {
    private String personId;
    private String identifier;
}
