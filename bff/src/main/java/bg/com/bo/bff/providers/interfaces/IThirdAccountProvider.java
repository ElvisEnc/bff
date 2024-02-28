package bg.com.bo.bff.providers.interfaces;

import bg.com.bo.bff.models.ClientToken;
import bg.com.bo.bff.models.ThirdAccountListResponse;

import java.io.IOException;

public interface IThirdAccountProvider {
    ClientToken generateAccessToken() throws IOException;
    ThirdAccountListResponse getListThridAccounts(String token, String personId, String compani) throws IOException;
}
