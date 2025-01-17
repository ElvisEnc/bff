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
public class GPRelationshipsMW {

        @JsonProperty("relationshipId")
        private int relationshipId;
        @JsonProperty("relationshipDescription")
        private String relationshipDescription;
}
