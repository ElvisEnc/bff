package bg.com.bo.bff.mappings.providers.frequentlyquestion;

import bg.com.bo.bff.application.dtos.response.frequently.questions.ListFrequentlyQuestionResponse;
import bg.com.bo.bff.providers.dtos.response.frequently.question.mw.ListFrequentlyQuestionMWResponse;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class FrequentlyQuestionMapper implements IFrequentlyQuestionMapper{

    @Override
    public ListFrequentlyQuestionResponse convertResponse(ListFrequentlyQuestionMWResponse mwResponse) {
        if (mwResponse == null || mwResponse.getData() == null) {
            return ListFrequentlyQuestionResponse.builder()
                    .data(new ArrayList<>())
                    .build();
        }
        List<ListFrequentlyQuestionResponse.FrequentlyQuestion> frequentlyQuestions = mwResponse.getData()
                .stream()
                .map(mw -> ListFrequentlyQuestionResponse.FrequentlyQuestion.builder()
                        .questionId(mw.getIdentifier())
                        .title(mw.getTitle())
                        .question(mw.getQuestion())
                        .answer(convertAnswers(mw.getData()))
                        .build()
                ).collect(Collectors.toList());
        return ListFrequentlyQuestionResponse.builder()
                .data(frequentlyQuestions)
                .build();
    }

    private List<ListFrequentlyQuestionResponse.FrequentlyQuestionResponse> convertAnswers(List<ListFrequentlyQuestionMWResponse.FrequentlyQuestionDetailMW> details) {
        if (details == null) {
            return new ArrayList<>();
        }
        return details.stream()
                .map(detail -> ListFrequentlyQuestionResponse.FrequentlyQuestionResponse.builder()
                        .response(detail.getResponse())
                        .build())
                .collect(Collectors.toList());
    }
}
