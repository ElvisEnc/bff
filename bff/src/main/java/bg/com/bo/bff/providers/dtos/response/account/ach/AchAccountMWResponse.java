package bg.com.bo.bff.providers.dtos.response.account.ach;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AchAccountMWResponse {
    private List<AchAccountMW> data;
}
