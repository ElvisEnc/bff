package bg.com.bo.bff.services.v1;

import bg.com.bo.bff.model.ClientToken;
import bg.com.bo.bff.model.ThirdAccountListResponse;
import bg.com.bo.bff.services.interfaces.IThirdAccountMiddlewareService;
import bg.com.bo.bff.services.interfaces.IThirdAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class ThirdAccountService implements IThirdAccountService {
    @Autowired
    private IThirdAccountMiddlewareService iThirdAccountMiddlewareService;

    public ThirdAccountListResponse getListThridAccounts(String personId, String company) throws IOException {
        ClientToken clientToken = iThirdAccountMiddlewareService.generateAccessToken();
        String token = clientToken.getAccessToken();
        return iThirdAccountMiddlewareService.getListThridAccounts(token, personId, company);
    }
}
