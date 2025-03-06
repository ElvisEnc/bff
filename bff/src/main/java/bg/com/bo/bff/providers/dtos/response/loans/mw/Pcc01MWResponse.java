package bg.com.bo.bff.providers.dtos.response.loans.mw;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Pcc01MWResponse {

    private String requireUif;
}
