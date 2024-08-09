package bg.com.bo.bff.mappings.providers.account;

import bg.com.bo.bff.application.dtos.request.destination.account.AddAchAccountRequest;
import bg.com.bo.bff.application.dtos.request.destination.account.AddQRAccountRequest;
import bg.com.bo.bff.application.dtos.response.destination.account.BranchOfficeResponse;
import bg.com.bo.bff.application.dtos.response.destination.account.DestinationAccount;
import bg.com.bo.bff.providers.dtos.request.ach.account.mw.AddAchAccountBasicRequest;
import bg.com.bo.bff.providers.dtos.request.ach.account.mw.DeleteAchAccountMWRequest;
import bg.com.bo.bff.providers.dtos.response.ach.account.mw.BranchOfficeMWResponse;
import bg.com.bo.bff.providers.dtos.response.ach.account.mw.AchAccountMW;

public interface IAchAccountsMapper {
    AddAchAccountBasicRequest mapperRequest(String personId, AddAchAccountRequest addAchAccountRequest);

    DeleteAchAccountMWRequest mapperRequest(String personId, long identifier);

    BranchOfficeResponse mapToBranchOfficeResponse(BranchOfficeMWResponse mwResponse);

    DestinationAccount convertAchAccountToDestinationAccount(AchAccountMW achAccount);

    AddAchAccountBasicRequest mapToAchRequest(String personId, AddQRAccountRequest request);
}
