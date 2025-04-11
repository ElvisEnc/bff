package bg.com.bo.bff.services.interfaces;

import bg.com.bo.bff.application.dtos.request.nps.ResponseNpsRequest;
import bg.com.bo.bff.application.dtos.response.nps.NpsResponse;
import bg.com.bo.bff.application.dtos.response.nps.RegisterNpsResponse;

import java.io.IOException;


public interface INpsService {

    RegisterNpsResponse registerDevice(String personId, String deviceId) throws IOException;

    NpsResponse sendAnswerNps(int personId, String deviceId, ResponseNpsRequest request) throws IOException;

}






