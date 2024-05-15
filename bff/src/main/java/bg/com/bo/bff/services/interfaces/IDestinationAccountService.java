package bg.com.bo.bff.services.interfaces;

import bg.com.bo.bff.application.dtos.request.AddAchAccountRequest;
import bg.com.bo.bff.application.dtos.request.AddThirdAccountRequest;
import bg.com.bo.bff.application.dtos.request.AddWalletAccountRequest;
import bg.com.bo.bff.application.dtos.request.destination.account.DestinationAccountRequest;
import bg.com.bo.bff.application.dtos.response.BanksResponse;
import bg.com.bo.bff.application.dtos.response.AccountTypeListResponse;
import bg.com.bo.bff.application.dtos.response.GenericResponse;
import bg.com.bo.bff.application.dtos.response.BranchOfficeResponse;
import bg.com.bo.bff.application.dtos.response.ValidateAccountResponse;
import bg.com.bo.bff.application.dtos.response.destination.account.DestinationAccountResponse;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Map;

@Service
public interface IDestinationAccountService {
    GenericResponse addThirdAccount(String personId, AddThirdAccountRequest addThirdAccountRequest, Map<String, String> parameters) throws IOException;
    GenericResponse addWalletAccount(String personId, AddWalletAccountRequest addWalletAccountRequest, Map<String, String> parameter) throws IOException ;
    GenericResponse addAchAccount(String personId, AddAchAccountRequest addAchAccountRequest, Map<String, String> parameter) throws IOException;
    BanksResponse getBanks() throws IOException;
    BranchOfficeResponse getBranchOffice(Integer bankCode) throws IOException;
    AccountTypeListResponse accountTypes();
    GenericResponse deleteThirdAccount(String personId, long identifier, long accountNumber, String deviceId, String deviceIp) throws IOException;
    GenericResponse deleteWalletAccount(String personId, long identifier, long accountNumber,String deviceId, String deviceIp) throws IOException;
    GenericResponse deleteAchAccount(String personId, long identifier, String deviceId, String deviceIp) throws IOException;
    DestinationAccountResponse getDestinationAccounts(Integer personId, DestinationAccountRequest request, Map<String, String> parameter) throws IOException;
    ValidateAccountResponse getValidateDestinationAccounts(String accountNumber, String clientName, Map<String, String> parameter) throws IOException;
}
