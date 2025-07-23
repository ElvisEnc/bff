package bg.com.bo.bff.mappings.providers.nps;

import bg.com.bo.bff.application.dtos.request.nps.ResponseNpsRequest;
import bg.com.bo.bff.application.dtos.response.nps.NpsResponse;
import bg.com.bo.bff.application.dtos.response.nps.RegisterNpsResponse;
import bg.com.bo.bff.providers.dtos.request.nps.mw.AnswerNpsMWRequest;
import bg.com.bo.bff.providers.dtos.response.nps.mw.AnswerNpsMWResponse;
import bg.com.bo.bff.providers.dtos.response.nps.mw.RegisterNpsMWResponse;
import org.springframework.stereotype.Component;

@Component
public class NpsMapper implements INpsMapper {

    @Override
    public RegisterNpsResponse npsRegisterResponseMWMapper(RegisterNpsMWResponse mwResponse) {
        return RegisterNpsResponse.builder()
                .npsId(mwResponse.getData().getIdentifierNps())
                .codeError(mwResponse.getData().getCodeResponse())
                .message(mwResponse.getData().getMessageResponse())
                .questionIds(mwResponse.getData().getCursorNpsOut())
                .build();
    }

    @Override
    public AnswerNpsMWRequest mapperAnswerNps(int personId, String deviceId, ResponseNpsRequest request) {
        return AnswerNpsMWRequest.builder()
                .codNps(request.getNpsId())
                .nroPerson(personId)
                .identifierDevice(deviceId)
                .response(
                        request.getAnswers().stream()
                                .map(mw -> AnswerNpsMWRequest.ResponseMWRequest.builder()
                                        .id(mw.getQuestionId())
                                        .valor(mw.getValue())
                                        .build()
                                ).toList()
                ).build();
    }

    @Override
    public NpsResponse convertResponse(AnswerNpsMWResponse mwResponse) {
        return NpsResponse.builder()
                .message(mwResponse.getData().getMessageResponse())
                .codeError(mwResponse.getData().getCodeResponse())
                .build();
    }
}
