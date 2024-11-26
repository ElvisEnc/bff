package bg.com.bo.bff.providers.dtos.response.point.attention.mw;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ListPointsAttentionMWResponse {
    private List<PointAttentionMW> data;

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class PointAttentionMW {
        @JsonProperty("IdPoint")
        private int idPoint;
        @JsonProperty("IdReference")
        private String idReference;
        @JsonProperty("TypePoint")
        private char typePoint;
        @JsonProperty("Name")
        private String name;
        @JsonProperty("Description")
        private String description;
        @JsonProperty("Plaza")
        private int plaza;
        @JsonProperty("Direction")
        private String direction;
        @JsonProperty("Telephone")
        private String telephone;
        @JsonProperty("Latitude")
        private String latitude;
        @JsonProperty("Longitude")
        private String longitude;
        @JsonProperty("UserRegistration")
        private String userRegistration;
        @JsonProperty("RegistrationDate")
        private String registrationDate;
        @JsonProperty("TzLock")
        private int tzLock;
        @JsonProperty("PointSubType")
        private char pointSubType;
        @JsonProperty("Central")
        private char central;
        @JsonProperty("Depositary")
        private char depositary;
        @JsonProperty("Handicapped")
        private char handicapped;
        @JsonProperty("HourHand")
        private char hourHand;
        @JsonProperty("Currency")
        private char currency;
    }
}
