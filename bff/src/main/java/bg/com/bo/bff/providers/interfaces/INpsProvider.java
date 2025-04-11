package bg.com.bo.bff.providers.interfaces;

import bg.com.bo.bff.providers.dtos.request.nps.mw.AnswerNpsMWRequest;
import bg.com.bo.bff.providers.dtos.response.nps.mw.AnswerNpsMWResponse;
import bg.com.bo.bff.providers.dtos.response.nps.mw.RegisterNpsMWResponse;

import java.io.IOException;

public interface INpsProvider {
    RegisterNpsMWResponse registerDevice(String personId, String deviceId) throws IOException;

    AnswerNpsMWResponse answerNps(int personId, String deviceId, AnswerNpsMWRequest request) throws IOException;
}
