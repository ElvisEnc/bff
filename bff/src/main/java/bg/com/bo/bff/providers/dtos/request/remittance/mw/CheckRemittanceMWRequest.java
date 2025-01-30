package bg.com.bo.bff.providers.dtos.request.remittance.mw;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CheckRemittanceMWRequest {

    private String codPerson;
    private String codApplication;
    private String withGanaMobile;
    private String noRemittance;
}
