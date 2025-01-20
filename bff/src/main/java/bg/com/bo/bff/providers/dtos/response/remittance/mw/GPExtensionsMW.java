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
public class GPExtensionsMW {

        @JsonProperty("extensionId")
        private int extensionId;
        @JsonProperty("extensionName")
        private String extensionName;
        @JsonProperty("extensionAbbr")
        private String extensionAbbr;
}
