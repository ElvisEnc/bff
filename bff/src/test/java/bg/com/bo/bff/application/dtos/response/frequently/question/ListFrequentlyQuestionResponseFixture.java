package bg.com.bo.bff.application.dtos.response.frequently.question;

import bg.com.bo.bff.application.dtos.response.frequently.questions.ListFrequentlyQuestionResponse;

import java.util.ArrayList;
import java.util.List;

public class ListFrequentlyQuestionResponseFixture {

    public static ListFrequentlyQuestionResponse withDefaultListFrequentlyQuestionResponseEmpty() {
        return ListFrequentlyQuestionResponse.builder()
                .data(new ArrayList<>())
                .build();
    }
    public static ListFrequentlyQuestionResponse withDefaultListFrequentlyQuestionRespEmpty() {
        return ListFrequentlyQuestionResponse.builder()
                .data(null)
                .build();
    }

    public static ListFrequentlyQuestionResponse withDefaultListFrequentlyQuestionResponse() {
        List<ListFrequentlyQuestionResponse.FrequentlyQuestion> response = new ArrayList<>();
        response.add(withDefaultListFrequentlyQuesResponse());
        return ListFrequentlyQuestionResponse.builder()
                .data(response)
                .build();
    }

    public static ListFrequentlyQuestionResponse.FrequentlyQuestion withDefaultListFrequentlyQuesResponse() {
        List<ListFrequentlyQuestionResponse.FrequentlyQuestionResponse> answer = new ArrayList<>();
        answer.add(withDefaultListFrequentlyResponse());
        return ListFrequentlyQuestionResponse.FrequentlyQuestion.builder()
                .questionId(1)
                .question("test")
                .answer(answer)
                .build();
    }
    public static ListFrequentlyQuestionResponse.FrequentlyQuestionResponse withDefaultListFrequentlyResponse() {
        return ListFrequentlyQuestionResponse.FrequentlyQuestionResponse.builder()
                .response("test")
                .build();
    }
}
