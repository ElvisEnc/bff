package bg.com.bo.bff.mappings.providers.frequentlyquestion;

import bg.com.bo.bff.application.dtos.response.frequently.questions.ListFrequentlyQuestionResponse;
import bg.com.bo.bff.providers.dtos.response.frequently.question.mw.ListFrequentlyQuestionMWResponse;

public interface IFrequentlyQuestionMapper {
    ListFrequentlyQuestionResponse convertResponse(ListFrequentlyQuestionMWResponse mwResponse);
}
