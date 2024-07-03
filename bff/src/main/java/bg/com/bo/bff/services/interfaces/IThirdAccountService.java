package bg.com.bo.bff.services.interfaces;

import bg.com.bo.bff.application.dtos.response.destination.account.ThirdAccountListResponse;

import java.io.IOException;

public interface IThirdAccountService {
    ThirdAccountListResponse getListThirdAccounts( String personId, String compani) throws IOException;
}
