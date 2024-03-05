package bg.com.bo.bff.services.implementations.v1;

import bg.com.bo.bff.models.ClientToken;
import bg.com.bo.bff.models.ThirdAccountListResponse;
import bg.com.bo.bff.providers.interfaces.IThirdAccountProvider;
import bg.com.bo.bff.services.interfaces.IThirdAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class ThirdAccountService implements IThirdAccountService {
    @Autowired
    private IThirdAccountProvider iThirdAccountMiddlewareService;

    public ThirdAccountListResponse getListThirdAccounts(String personId, String company) throws IOException {
        ClientToken clientToken = iThirdAccountMiddlewareService.generateAccessToken();
        String token = clientToken.getAccessToken();
        return iThirdAccountMiddlewareService.getListThirdAccounts(token, personId, company);
    }
}
