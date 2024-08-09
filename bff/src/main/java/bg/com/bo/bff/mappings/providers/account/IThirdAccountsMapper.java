package bg.com.bo.bff.mappings.providers.account;

import bg.com.bo.bff.application.dtos.request.destination.account.AddQRAccountRequest;
import bg.com.bo.bff.application.dtos.response.destination.account.DestinationAccount;
import bg.com.bo.bff.providers.dtos.request.third.account.mw.AddThirdAccountBasicRequest;
import bg.com.bo.bff.providers.dtos.request.third.account.mw.AddWalletAccountBasicRequest;
import bg.com.bo.bff.providers.dtos.request.third.account.mw.DeleteThirdAccountMWRequest;
import bg.com.bo.bff.providers.dtos.response.third.account.mw.ThirdAccountsMWResponse;

public interface IThirdAccountsMapper {
    DeleteThirdAccountMWRequest mapperRequest(String personId, long identifier, long accountNumber);

    DestinationAccount convertThirdAccountToDestinationAccount(ThirdAccountsMWResponse.ThirdAccountMW account, Integer type, String name);

    AddThirdAccountBasicRequest mapToThirdRequest(String personId, AddQRAccountRequest request);

    AddWalletAccountBasicRequest mapToWalletRequest(String personId, AddQRAccountRequest request);

}
