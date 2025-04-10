package bg.com.bo.bff.services.implementations.v1;

import bg.com.bo.bff.application.dtos.request.nps.ResponseNpsRequest;
import bg.com.bo.bff.application.dtos.response.nps.NpsResponse;
import bg.com.bo.bff.application.dtos.response.nps.RegisterNpsResponse;
import bg.com.bo.bff.mappings.providers.nps.INpsMapper;
import bg.com.bo.bff.providers.dtos.request.nps.mw.AnswerNpsMWRequest;
import bg.com.bo.bff.providers.dtos.response.nps.mw.AnswerNpsMWResponse;
import bg.com.bo.bff.providers.dtos.response.nps.mw.RegisterNpsMWResponse;
import bg.com.bo.bff.providers.interfaces.INpsProvider;
import bg.com.bo.bff.services.interfaces.INpsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class NpsService implements INpsService {
    private final INpsProvider provider;
    private final INpsMapper mapper;

    @Autowired
    public NpsService(INpsProvider provider, INpsMapper mapper) {
        this.provider = provider;
        this.mapper = mapper;
    }


    @Override
    public RegisterNpsResponse registerDevice(String personId, String deviceId) throws IOException {
        RegisterNpsMWResponse mwResponse = provider.registerDevice(personId, deviceId);
        return mapper.npsRegisterResponseMWMapper(mwResponse);
    }

    @Override
    public NpsResponse sendAnswerNps(int personId, String deviceId, ResponseNpsRequest request) throws IOException {
        AnswerNpsMWRequest mwRequest = mapper.mapperAnswerNps(personId, deviceId, request);
        AnswerNpsMWResponse mwResponse = provider.answerNps(personId, deviceId, mwRequest);
        return mapper.convertResponse(mwResponse);
    }


}
