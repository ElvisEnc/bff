package bg.com.bo.bff.providers.dtos.response.nps.mw;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NpsMW {

    @JsonProperty("id")
    private int questionIds;

}
