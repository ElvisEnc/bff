package bg.com.bo.bff.providers.mappings.destination.account;

import bg.com.bo.bff.application.dtos.response.BranchOfficeResponse;
import bg.com.bo.bff.application.dtos.response.destination.account.DestinationAccount;
import bg.com.bo.bff.models.ThirdAccount;
import bg.com.bo.bff.providers.dtos.response.BranchOfficeMWResponse;
import bg.com.bo.bff.providers.dtos.response.account.ach.AchAccountMW;

public interface IDestinationAccountMapper {
    BranchOfficeResponse mapToBranchOfficeResponse(BranchOfficeMWResponse mwResponse);
    DestinationAccount convertThirdAccountToDestinationAccount(ThirdAccount account, Integer type, String name);
    DestinationAccount convertAchAccountToDestinationAccount(AchAccountMW achAccount);
}
