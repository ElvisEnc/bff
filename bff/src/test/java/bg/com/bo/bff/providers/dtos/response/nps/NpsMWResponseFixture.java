package bg.com.bo.bff.providers.dtos.response.nps;

import bg.com.bo.bff.application.dtos.request.nps.AnswerRequest;
import bg.com.bo.bff.application.dtos.request.nps.ResponseNpsRequest;
import bg.com.bo.bff.application.dtos.response.nps.NpsResponse;
import bg.com.bo.bff.application.dtos.response.nps.RegisterNpsResponse;
import bg.com.bo.bff.providers.dtos.response.nps.mw.AnswerNpsMWResponse;
import bg.com.bo.bff.providers.dtos.response.nps.mw.NpsMW;
import bg.com.bo.bff.providers.dtos.response.nps.mw.RegisterNpsMWResponse;

import java.util.Collections;
import java.util.List;

public class NpsMWResponseFixture {

    public static List<NpsMW> npsMWDefault() {
        return List.of(NpsMW.builder().questionIds(5).build());
    }

    public static RegisterNpsMWResponse withDefaultRegisterNps() {
        return RegisterNpsMWResponse.builder()
                .data(RegisterNpsMWResponse.RegisterNpsMW.builder()
                        .identifierNps(5000)
                        .codeResponse("5000")
                        .messageResponse("SUCCESS")
                        .cursorNpsOut(npsMWDefault()).build()).build();
    }

    public static RegisterNpsResponse withDefaultRegisterNpsResponse() {
        return RegisterNpsResponse.builder()
                .npsId(5000)
                .codeError("5000")
                .message("SUCCESS")
                .questionIds(npsMWDefault())
                .build();
    }

    public static ResponseNpsRequest withDefaultSendAnswerRequest() {
        return ResponseNpsRequest.
                builder()
                .npsId(40)
                .answers(
                        Collections.singletonList(AnswerRequest
                                .builder()
                                .value("Ok")
                                .questionId(1)
                                .build())
                ).build();
    }

    public static AnswerNpsMWResponse answerNpsMWResponseWithDefault() {
        return AnswerNpsMWResponse.builder()
                .data(AnswerNpsMWResponse.AnswerNpsMW.builder()
                        .codeResponse("COD000")
                        .messageResponse("SUCCESS").build()).build();
    }

    public static NpsResponse withDefaultNpsResponse() {
        return NpsResponse.builder()
                .codeError("COD000")
                .message("SUCCESS")
                .build();
    }


}
