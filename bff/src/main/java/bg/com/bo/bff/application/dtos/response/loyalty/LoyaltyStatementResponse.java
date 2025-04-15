package bg.com.bo.bff.application.dtos.response.loyalty;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoyaltyStatementResponse {

    @Schema(description = "Es la lista de los movimientos")
    @JsonProperty("movements")
    private List<LoyaltyDetailStatementPoints> movements;

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class LoyaltyDetailStatementPoints {
        @Schema(description = "Es la accion que realiza")
        @JsonProperty("action")
        private String action;

        @Schema(description = "Es el comentario")
        @JsonProperty("comment")
        private String comment;

        @Schema(description = "Es la fecha de creacion")
        @JsonProperty("dateCreation")
        private String dateCreation;

        @Schema(description = "Es el origen de donde optiene los puntos")
        @JsonProperty("origin")
        private String origin;

        @Schema(description = "Es la puntuacion de la campaña")
        @JsonProperty("campaignScore")
        private double campaignScore;
    }
}
