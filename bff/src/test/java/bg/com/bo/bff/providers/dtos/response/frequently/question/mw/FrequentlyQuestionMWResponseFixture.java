package bg.com.bo.bff.providers.dtos.response.frequently.question.mw;

import java.util.ArrayList;
import java.util.List;

public class FrequentlyQuestionMWResponseFixture {
    public static ListFrequentlyQuestionMWResponse withDefaultFrequentlyQuestionMWResponseEmpty() {
        return ListFrequentlyQuestionMWResponse.builder()
                .data(null)
                .build();

    }
    public static ListFrequentlyQuestionMWResponse withDefaultFrequentlyQuestionMWResponse() {
        List<ListFrequentlyQuestionMWResponse.FrequentlyQuestionMW> response = new ArrayList<>();
        response.add(withDefaultFrequentlyQuestionMWData());
        return ListFrequentlyQuestionMWResponse.builder()
                .data(response)
                .build();

    }
    public static ListFrequentlyQuestionMWResponse.FrequentlyQuestionMW withDefaultFrequentlyQuestionMWData() {
        List<ListFrequentlyQuestionMWResponse.FrequentlyQuestionDetailMW> response = new ArrayList<>();
        response.add(withDefaultFrequentlyQuestionMWDetail());
        return ListFrequentlyQuestionMWResponse.FrequentlyQuestionMW.builder()
                .identifier(1)
                .title("test")
                .question("test")
                .data(response)
                .build();

    }
    public static ListFrequentlyQuestionMWResponse.FrequentlyQuestionDetailMW withDefaultFrequentlyQuestionMWDetail() {
        return ListFrequentlyQuestionMWResponse.FrequentlyQuestionDetailMW.builder()
                .identifierDetails(1)
                .orden(1)
                .response("test")
                .archive("test")
                .build();

    }
}