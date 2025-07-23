package bg.com.bo.bff.providers.dtos.response.ach.account.mw;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AddAccountMWResponse {
    private String identifier;
}
