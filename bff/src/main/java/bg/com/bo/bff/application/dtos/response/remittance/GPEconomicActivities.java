package bg.com.bo.bff.application.dtos.response.remittance;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GPEconomicActivities {

        private String name;
        private char incomeSourceCode;
        private String startDate;
}
