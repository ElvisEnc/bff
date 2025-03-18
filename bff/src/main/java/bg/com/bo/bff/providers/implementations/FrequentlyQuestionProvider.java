package bg.com.bo.bff.providers.implementations;

import bg.com.bo.bff.application.config.MiddlewareConfig;
import bg.com.bo.bff.commons.enums.config.provider.ProjectNameMW;
import bg.com.bo.bff.commons.interfaces.IHttpClientFactory;
import bg.com.bo.bff.commons.utils.Util;
import bg.com.bo.bff.providers.dtos.response.frequently.question.mw.ListFrequentlyQuestionMWResponse;
import bg.com.bo.bff.providers.interfaces.IFrequentlyQuestionProvider;
import bg.com.bo.bff.providers.interfaces.ITokenMiddlewareProvider;
import bg.com.bo.bff.providers.models.enums.middleware.frequently.question.FrequentlyQuestionError;
import bg.com.bo.bff.providers.models.enums.middleware.frequently.question.FrequentlyQuestionServices;
import bg.com.bo.bff.providers.models.middleware.HeadersMW;
import bg.com.bo.bff.providers.models.middleware.MiddlewareProvider;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class FrequentlyQuestionProvider extends MiddlewareProvider<FrequentlyQuestionError> implements IFrequentlyQuestionProvider {

    private final String baseUrl;

    public FrequentlyQuestionProvider(ITokenMiddlewareProvider tokenMiddlewareProvider, MiddlewareConfig middlewareConfig, IHttpClientFactory httpClientFactory) {
        super(ProjectNameMW.FREQUENTLY_QUESTION_MANAGER, FrequentlyQuestionError.class, tokenMiddlewareProvider, middlewareConfig, httpClientFactory, middlewareConfig.getClientFrequentlyQuestionManager());
        this.baseUrl = middlewareConfig.getUrlBase() + ProjectNameMW.FREQUENTLY_QUESTION_MANAGER.getName();
    }

    @Override
    public ListFrequentlyQuestionMWResponse getFrequentlyQuestions() throws IOException {
        String url = baseUrl + FrequentlyQuestionServices.GET_FREQUENTLY_QUESTION.getServiceURL();
        ListFrequentlyQuestionMWResponse mwResponse = get(url, HeadersMW.getMWIdentificationChannelHeaders(), ListFrequentlyQuestionMWResponse.class);
        return Util.stringToObject(Util.objectToString(mwResponse), ListFrequentlyQuestionMWResponse.class);
    }
}