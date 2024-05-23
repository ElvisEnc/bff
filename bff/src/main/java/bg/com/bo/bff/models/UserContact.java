package bg.com.bo.bff.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserContact {
    @JsonProperty("companyContact")
    private CompanyContactDetail companyContactDetail;

    @Data
    @NoArgsConstructor
    public static class CompanyContactDetail {
        @JsonProperty("socialNetworks")
        private SocialNetworks socialNetworks;
        @JsonProperty("attentionLines")
        private AttentionLines attentionLines;
        @JsonProperty("contact")
        private Contact contact;
    }

    @Data
    @NoArgsConstructor
    public static class SocialNetworks {
        @JsonProperty("facebook")
        private String facebook;
        @JsonProperty("likedin")
        private String likedin;
        @JsonProperty("youtube")
        private String youtube;
    }

    @Data
    @NoArgsConstructor
    public static class AttentionLines {
        @JsonProperty("helpNumber")
        private String helpNumber;
        @JsonProperty("creditNumber")
        private String creditNumber;
    }

    @Data
    @NoArgsConstructor
    public static class Contact {
        @JsonProperty("whatsapp")
        private String whatsapp;
        @JsonProperty("countryNumberCode")
        private String countryNumberCode;
    }
}
