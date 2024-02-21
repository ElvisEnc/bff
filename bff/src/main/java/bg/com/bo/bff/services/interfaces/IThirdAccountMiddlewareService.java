package bg.com.bo.bff.services.interfaces;

import bg.com.bo.bff.model.ClientToken;
import bg.com.bo.bff.model.ThirdAccountListResponse;

import java.io.IOException;

public interface IThirdAccountMiddlewareService {
    ClientToken generateAccessToken() throws IOException;
    ThirdAccountListResponse getListThridAccounts(String token, String personId, String compani) throws IOException;
}
