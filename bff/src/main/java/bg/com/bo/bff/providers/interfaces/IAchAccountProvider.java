package bg.com.bo.bff.providers.interfaces;

import bg.com.bo.bff.application.dtos.response.GenericResponse;
import bg.com.bo.bff.models.ClientToken;
import bg.com.bo.bff.models.dtos.BanksMWResponse;
import bg.com.bo.bff.providers.dtos.requests.AddAchAccountBasicRequest;
import bg.com.bo.bff.providers.dtos.responses.BranchOfficeMWResponse;

import java.io.IOException;
import java.util.Map;

public interface IAchAccountProvider {
    ClientToken generateAccessToken() throws IOException;
    GenericResponse addAchAccount(String accessToken, AddAchAccountBasicRequest addAchAccountBasicRequest, Map<String, String> parameters) throws IOException;
    GenericResponse deleteAchAccount(String personId, int identifier, String deviceId, String deviceIp) throws IOException;
    BranchOfficeMWResponse getAllBranchOfficeBank(Integer code) throws IOException;
    BanksMWResponse getBanks() throws IOException;
}
