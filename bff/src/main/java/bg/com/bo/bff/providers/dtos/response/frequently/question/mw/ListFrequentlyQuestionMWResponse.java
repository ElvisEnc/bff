package bg.com.bo.bff.providers.dtos.response.frequently.question.mw;

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
public class ListFrequentlyQuestionMWResponse {
    private List<FrequentlyQuestionMW> data;

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class FrequentlyQuestionMW {
        @JsonProperty("identifier")
        private int identifier;
        @JsonProperty("title")
        private String title;
        @JsonProperty("question")
        private String question;
        @JsonProperty("data")
        private List<FrequentlyQuestionDetailMW>  data;
    }

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class FrequentlyQuestionDetailMW {
        @JsonProperty("identifierDetails")
        private int identifierDetails;
        @JsonProperty("orden")
        private int orden;
        @JsonProperty("response")
        private String response;
        @JsonProperty("archive")
        private String archive;
    }
}