package bg.com.bo.bff.providers.dtos.response.loyalty;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoyaltyStatementPointsResponse {

    @JsonProperty("accion")
    private String action;

    @JsonProperty("glosa")
    private String comment;

    @JsonProperty("fechaCreacion")
    private String dateCreation;

    @JsonProperty("origen")
    private String origin;

    @JsonProperty("puntuacionCampana")
    private double campaignScore;

}
