package bg.com.bo.bff.providers.dtos.response.debit.card.mw;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DCDetailMWResponse {
    @JsonProperty("data")
    private CardData data;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CardData {
        @JsonProperty("cardNro")
        private String cardNumber;

        @JsonProperty("typeCard")
        private String typeCard;

        @JsonProperty("branch")
        private String branch;

        @JsonProperty("nroClient")
        private String clientNumber;

        @JsonProperty("nroPerson")
        private String personNumber;

        @JsonProperty("nameCard")
        private String cardName;

        @JsonProperty("deliveryDate")
        private String deliveryDate;

        @JsonProperty("expirationDate")
        private String expirationDate;

        @JsonProperty("status")
        private String status;

        @JsonProperty("statusDescription")
        private String statusDescription;

        @JsonProperty("protectionInsurance")
        private String protectionInsurance;

        @JsonProperty("limitExpirationDate")
        private String limitExpirationDate;

        @JsonProperty("limitAmountME")
        private String limitAmountME;

        @JsonProperty("limitExtractions")
        private String limitExtractions;
    }
}
