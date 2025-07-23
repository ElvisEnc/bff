package bg.com.bo.bff.providers.dtos.response.ach.account.mw;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AchAccountsMWResponse {
    private List<AchAccountMW> data;
}
