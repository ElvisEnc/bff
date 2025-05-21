package bg.com.bo.bff.mappings.providers.nps;

import bg.com.bo.bff.application.dtos.request.nps.ResponseNpsRequest;
import bg.com.bo.bff.application.dtos.response.nps.NpsResponse;
import bg.com.bo.bff.application.dtos.response.nps.RegisterNpsResponse;
import bg.com.bo.bff.providers.dtos.request.nps.mw.AnswerNpsMWRequest;
import bg.com.bo.bff.providers.dtos.response.nps.mw.AnswerNpsMWResponse;
import bg.com.bo.bff.providers.dtos.response.nps.mw.RegisterNpsMWResponse;

public interface INpsMapper {

    RegisterNpsResponse npsRegisterResponseMWMapper(RegisterNpsMWResponse mwResponse);

    AnswerNpsMWRequest mapperAnswerNps(int personId, String deviceId, ResponseNpsRequest request);

    NpsResponse convertResponse(AnswerNpsMWResponse mwResponse);

}
