package bg.com.bo.bff.application.dtos.response.nps;

import bg.com.bo.bff.providers.dtos.request.nps.mw.AnswerNpsMWRequest;
import bg.com.bo.bff.providers.dtos.response.nps.mw.NpsMW;
import bg.com.bo.bff.providers.dtos.response.nps.mw.RegisterNpsMWResponse;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static bg.com.bo.bff.providers.dtos.request.nps.mw.AnswerNpsMWRequest.ResponseMWRequest;

public class NpsResponseFixture {


    public static NpsMW npsDefaultResponse() {
        return NpsMW.builder()
                .questionIds(10).build();
    }

    public static ResponseMWRequest responseRequestDefault() {
        return ResponseMWRequest.builder().id(10).valor("Test").build();
    }

    public static RegisterNpsMWResponse defaultRegisterNpMWsResponse() {
        return RegisterNpsMWResponse.builder()
                .data(RegisterNpsMWResponse.RegisterNpsMW.builder()
                        .codeResponse("COD0000")
                        .identifierNps(4500)
                        .messageResponse("SUCCESS")
                        .cursorNpsOut(Arrays.asList(npsDefaultResponse())).build())
                .build();
    }

    public static AnswerNpsMWRequest withDefaultAnswerNpsMWRequest() {
        return AnswerNpsMWRequest.builder()
                .nroPerson(4545)
                .codNps(3636)
                .identifierDevice("device001")
                .response(
                        Collections.singletonList(responseRequestDefault())
                ).build();
    }

    public static List<NpsMW> defaultListNpsMW() {
        return List.of(
                NpsMW.builder()
                        .questionIds(10)
                        .build(),
                NpsMW.builder()
                        .questionIds(20)
                        .build());
    }

    public static RegisterNpsResponse withDefaultRegisterNpsResponse() {
        return RegisterNpsResponse.builder()
                .npsId(3636)
                .codeError("COD000")
                .message("SUCCESS")
                .questionIds(defaultListNpsMW())
                .build();
    }

    public static NpsResponse withDefaultNpsNpsResponse() {
        return NpsResponse.builder()
                .codeError("COD000")
                .message("SUCCESS").build();

    }


}