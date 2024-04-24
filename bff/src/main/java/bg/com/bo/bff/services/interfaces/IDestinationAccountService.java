package bg.com.bo.bff.services.interfaces;


import bg.com.bo.bff.application.dtos.request.AddAchAccountRequest;
import bg.com.bo.bff.application.dtos.request.AddThirdAccountRequest;
import bg.com.bo.bff.application.dtos.request.AddWalletAccountRequest;
import bg.com.bo.bff.application.dtos.request.DeleteThirdAccountRequest;
import bg.com.bo.bff.application.dtos.response.AccountTypeListResponse;
import bg.com.bo.bff.application.dtos.response.GenericResponse;
import bg.com.bo.bff.application.dtos.response.BranchOfficeResponse;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Map;

@Service
public interface IDestinationAccountService {
    GenericResponse addThirdAccount(String personId, AddThirdAccountRequest addThirdAccountRequest, Map<String, String> parameters) throws IOException;
    GenericResponse addAchAccount(String personId, AddAchAccountRequest addAchAccountRequest, Map<String, String> parameter) throws IOException;
    GenericResponse delete(String personId, int identifier, String deviceId, String deviceIp, DeleteThirdAccountRequest request) throws IOException;
    GenericResponse addWalletAccount(String personId, AddWalletAccountRequest addWalletAccountRequest, Map<String, String> parameter) throws IOException ;
    GenericResponse deleteAchAccount(String personId, int identifier, String deviceId, String deviceIp) throws IOException;
    AccountTypeListResponse accountTypes();
    BranchOfficeResponse getBranchOffice(Integer bankCode) throws IOException;
}
