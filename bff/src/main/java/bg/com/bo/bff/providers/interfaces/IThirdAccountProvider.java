package bg.com.bo.bff.providers.interfaces;

import bg.com.bo.bff.application.dtos.response.GenericResponse;
import bg.com.bo.bff.models.ClientToken;
import bg.com.bo.bff.models.ThirdAccountListResponse;
import bg.com.bo.bff.providers.dtos.requests.AddThirdAccountBasicRequest;
import bg.com.bo.bff.providers.dtos.requests.AddWalletAccountBasicRequest;

import java.io.IOException;
import java.util.Map;

public interface IThirdAccountProvider {
    ClientToken generateAccessToken() throws IOException;
    ThirdAccountListResponse getListThirdAccounts(String token, String personId, String compani) throws IOException;
    GenericResponse delete(String personId, int identifier, int accountId, String deviceId, String deviceIp) throws IOException;
    GenericResponse addThirdAccount(String token, AddThirdAccountBasicRequest request, Map<String, String> parameters) throws IOException;
    GenericResponse addWalletAccount(String accessToken, AddWalletAccountBasicRequest addWalletAccountBasicRequest, Map<String, String> parameter) throws IOException;
}
