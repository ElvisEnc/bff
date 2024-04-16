package bg.com.bo.bff.providers.interfaces;

import bg.com.bo.bff.application.dtos.response.GenericResponse;
import bg.com.bo.bff.models.ClientToken;
import bg.com.bo.bff.models.ThirdAccountListResponse;

import java.io.IOException;

public interface IThirdAccountProvider {
    ClientToken generateAccessToken() throws IOException;

    ThirdAccountListResponse getListThirdAccounts(String token, String personId, String compani) throws IOException;

    GenericResponse delete(String personId, int identifier, int accountId, String deviceId, String deviceIp) throws IOException;
}
