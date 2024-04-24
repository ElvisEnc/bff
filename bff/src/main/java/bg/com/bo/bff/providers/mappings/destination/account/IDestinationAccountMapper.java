package bg.com.bo.bff.providers.mappings.destination.account;

import bg.com.bo.bff.application.dtos.response.BranchOfficeResponse;
import bg.com.bo.bff.providers.dtos.responses.BranchOfficeMWResponse;

public interface IDestinationAccountMapper {
    BranchOfficeResponse mapToBranchOfficeResponse(BranchOfficeMWResponse mwResponse);
}
