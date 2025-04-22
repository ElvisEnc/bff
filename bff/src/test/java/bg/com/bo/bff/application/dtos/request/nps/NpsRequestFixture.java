package bg.com.bo.bff.application.dtos.request.nps;

import java.util.List;

public class NpsRequestFixture {

    public static ResponseNpsRequest withDefaultResponseNpsRequest() {
        return ResponseNpsRequest.builder()
                .npsId(363636)
                .answers(
                        List.of(AnswerRequest.builder()
                                .value("Ok")
                                .questionId(1)
                                .build())
                ).build();
    }
}
