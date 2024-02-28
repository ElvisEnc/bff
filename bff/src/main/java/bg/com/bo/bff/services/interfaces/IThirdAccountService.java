package bg.com.bo.bff.services.interfaces;

import bg.com.bo.bff.models.ThirdAccountListResponse;

import java.io.IOException;

public interface IThirdAccountService {
    ThirdAccountListResponse getListThridAccounts( String personId, String compani) throws IOException;
}
