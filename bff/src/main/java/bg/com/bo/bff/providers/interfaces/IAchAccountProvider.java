package bg.com.bo.bff.providers.interfaces;

import bg.com.bo.bff.application.dtos.response.GenericResponse;
import bg.com.bo.bff.models.ClientToken;
import bg.com.bo.bff.providers.dtos.requests.AddAchAccountBasicRequest;

import java.io.IOException;
import java.util.Map;

public interface IAchAccountProvider {
    ClientToken generateAccessToken() throws IOException;
    GenericResponse addAchAccount(String accessToken, AddAchAccountBasicRequest addAchAccountBasicRequest, Map<String, String> parameters) throws IOException;
}
