package bg.com.bo.bff.mappings.providers.account;

import bg.com.bo.bff.application.dtos.request.destination.account.AddQRAccountRequest;
import bg.com.bo.bff.application.dtos.response.destination.account.BranchOfficeResponse;
import bg.com.bo.bff.application.dtos.response.destination.account.DestinationAccount;
import bg.com.bo.bff.application.dtos.response.destination.account.ThirdAccount;
import bg.com.bo.bff.providers.dtos.request.ach.account.mw.AddAchAccountBasicRequest;
import bg.com.bo.bff.providers.dtos.request.third.account.mw.AddThirdAccountBasicRequest;
import bg.com.bo.bff.providers.dtos.request.third.account.mw.AddWalletAccountBasicRequest;
import bg.com.bo.bff.providers.dtos.response.ach.account.mw.BranchOfficeMWResponse;
import bg.com.bo.bff.providers.dtos.response.ach.account.mw.AchAccountMW;

public interface IDestinationAccountMapper {
    BranchOfficeResponse mapToBranchOfficeResponse(BranchOfficeMWResponse mwResponse);
    DestinationAccount convertThirdAccountToDestinationAccount(ThirdAccount account, Integer type, String name);
    DestinationAccount convertAchAccountToDestinationAccount(AchAccountMW achAccount);
    AddThirdAccountBasicRequest mapToThirdRequest (String personId, AddQRAccountRequest request);
    AddWalletAccountBasicRequest mapToWalletRequest (String personId, AddQRAccountRequest request);
    AddAchAccountBasicRequest mapToAchRequest (String personId, AddQRAccountRequest request);
}
