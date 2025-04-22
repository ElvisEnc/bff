package bg.com.bo.bff.providers.implementations;


import bg.com.bo.bff.application.config.MiddlewareConfig;
import bg.com.bo.bff.commons.enums.config.provider.ProjectNameMW;
import bg.com.bo.bff.commons.interfaces.IHttpClientFactory;
import bg.com.bo.bff.providers.dtos.request.nps.mw.AnswerNpsMWRequest;
import bg.com.bo.bff.providers.dtos.response.nps.mw.AnswerNpsMWResponse;
import bg.com.bo.bff.providers.dtos.response.nps.mw.RegisterNpsMWResponse;
import bg.com.bo.bff.providers.interfaces.INpsProvider;
import bg.com.bo.bff.providers.interfaces.ITokenMiddlewareProvider;
import bg.com.bo.bff.providers.models.enums.middleware.nps.NpsMiddlewareError;
import bg.com.bo.bff.providers.models.enums.middleware.nps.NpsMiddlewareService;
import bg.com.bo.bff.providers.models.middleware.HeadersMW;
import bg.com.bo.bff.providers.models.middleware.MiddlewareProvider;
import bg.com.bo.bff.providers.models.middleware.response.handler.ByMwErrorResponseHandler;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class NpsProvider extends MiddlewareProvider<NpsMiddlewareError> implements INpsProvider {
    private final String baseUrl;
    private final HttpServletRequest httpServletRequest;

    public NpsProvider(
            ITokenMiddlewareProvider tokenMiddlewareProvider,
            MiddlewareConfig middlewareConfig,
            IHttpClientFactory httpClientFactory,
            HttpServletRequest httpServletRequest
    ) {
        super(
                ProjectNameMW.USERS_QUESTIONS_MANAGER,
                NpsMiddlewareError.class,
                tokenMiddlewareProvider,
                middlewareConfig,
                httpClientFactory,
                middlewareConfig.getClientUsersQuestionsManager()
        );
        this.baseUrl = middlewareConfig.getUrlBase() + ProjectNameMW.USERS_QUESTIONS_MANAGER.getName();
        this.httpServletRequest = httpServletRequest;
    }


    @Override
    public RegisterNpsMWResponse registerDevice(String personId, String deviceId) throws IOException {
        String url = baseUrl + String.format(NpsMiddlewareService.REGISTER_NPS.getServiceURL(), personId, deviceId);
        ByMwErrorResponseHandler<RegisterNpsMWResponse> responseHandler = ByMwErrorResponseHandler
                .instance(NpsMiddlewareError.RM001);
        return post(
                url,
                HeadersMW.getDefaultHeaders(httpServletRequest),
                null,
                RegisterNpsMWResponse.class, responseHandler
        );
    }

    @Override
    public AnswerNpsMWResponse answerNps(int personId, String deviceId, AnswerNpsMWRequest request) throws IOException {
        String url = baseUrl + String.format(NpsMiddlewareService.RESPONSE_NPS.getServiceURL(), personId, deviceId);
        ByMwErrorResponseHandler<AnswerNpsMWResponse> responseHandler = ByMwErrorResponseHandler
                .instance(NpsMiddlewareError.RM001);
        return post(
                url,
                HeadersMW.getDefaultHeaders(httpServletRequest),
                request,
                AnswerNpsMWResponse.class, responseHandler);
    }

}
