package bg.com.bo.bff.services.implementations.v1;

import bg.com.bo.bff.application.dtos.response.frequently.questions.ListFrequentlyQuestionResponse;
import bg.com.bo.bff.mappings.providers.frequentlyquestion.IFrequentlyQuestionMapper;
import bg.com.bo.bff.providers.interfaces.IFrequentlyQuestionProvider;
import bg.com.bo.bff.services.interfaces.IFrequentlyQuestionService;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class FrequentlyQuestionService implements IFrequentlyQuestionService {
    private final IFrequentlyQuestionProvider provider;
    private final IFrequentlyQuestionMapper mapper;

    public FrequentlyQuestionService(IFrequentlyQuestionProvider provider, IFrequentlyQuestionMapper mapper) {
        this.provider = provider;
        this.mapper = mapper;
    }

    @Override
    public ListFrequentlyQuestionResponse getFrequentlyQuestions() throws IOException {
        return mapper.convertResponse(provider.getFrequentlyQuestions());
    }
}
