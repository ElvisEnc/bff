package bg.com.bo.bff.application.dtos.response.remittance;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GPRelationships {

        private int relationshipId;
        private String description;
}
