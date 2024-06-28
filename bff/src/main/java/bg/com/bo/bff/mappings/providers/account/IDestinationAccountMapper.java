package bg.com.bo.bff.mappings.providers.account;

import bg.com.bo.bff.application.dtos.request.destination.account.AddQRAccountRequest;
import bg.com.bo.bff.application.dtos.response.BranchOfficeResponse;
import bg.com.bo.bff.application.dtos.response.destination.account.DestinationAccount;
import bg.com.bo.bff.models.ThirdAccount;
import bg.com.bo.bff.providers.dtos.request.AddAchAccountBasicRequest;
import bg.com.bo.bff.providers.dtos.request.AddThirdAccountBasicRequest;
import bg.com.bo.bff.providers.dtos.request.AddWalletAccountBasicRequest;
import bg.com.bo.bff.providers.dtos.response.BranchOfficeMWResponse;
import bg.com.bo.bff.providers.dtos.response.account.ach.AchAccountMW;

public interface IDestinationAccountMapper {
    BranchOfficeResponse mapToBranchOfficeResponse(BranchOfficeMWResponse mwResponse);
    DestinationAccount convertThirdAccountToDestinationAccount(ThirdAccount account, Integer type, String name);
    DestinationAccount convertAchAccountToDestinationAccount(AchAccountMW achAccount);
    AddThirdAccountBasicRequest mapToThirdRequest (String personId, AddQRAccountRequest request);
    AddWalletAccountBasicRequest mapToWalletRequest (String personId, AddQRAccountRequest request);
    AddAchAccountBasicRequest mapToAchRequest (String personId, AddQRAccountRequest request);
}
