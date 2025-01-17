package bg.com.bo.bff.providers.dtos.response.remittance.mw;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GPEconomicActivitiesMW {

        @JsonProperty("companyName")
        private String companyName;
        @JsonProperty("incomeSourceCode")
        private char incomeSourceCode;
        @JsonProperty("startDate")
        private String startDate;
}
