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
public class GPJobLevelsMW {

        @JsonProperty("jobLevelId")
        private int jobLevelId;
        @JsonProperty("jobLevelDescription")
        private String jobLevelDescription;
}
