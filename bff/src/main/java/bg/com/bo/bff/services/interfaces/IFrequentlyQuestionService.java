package bg.com.bo.bff.services.interfaces;

import bg.com.bo.bff.application.dtos.response.frequently.questions.ListFrequentlyQuestionResponse;

import java.io.IOException;

public interface IFrequentlyQuestionService {

    ListFrequentlyQuestionResponse getFrequentlyQuestions() throws IOException;
}
