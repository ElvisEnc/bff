package bg.com.bo.bff.providers.interfaces;

import bg.com.bo.bff.providers.dtos.response.frequently.question.mw.ListFrequentlyQuestionMWResponse;

import java.io.IOException;

public interface IFrequentlyQuestionProvider {

    ListFrequentlyQuestionMWResponse getFrequentlyQuestions() throws IOException;
}

