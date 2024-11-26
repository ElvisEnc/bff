package bg.com.bo.bff.services.interfaces;

import bg.com.bo.bff.application.dtos.request.destination.account.AddAchAccountRequest;
import bg.com.bo.bff.application.dtos.request.destination.account.AddThirdAccountRequest;
import bg.com.bo.bff.application.dtos.request.destination.account.AddWalletAccountRequest;
import bg.com.bo.bff.application.dtos.request.destination.account.AddQRAccountRequest;
import bg.com.bo.bff.application.dtos.request.destination.account.DestinationAccountRequest;
import bg.com.bo.bff.application.dtos.response.destination.account.*;
import bg.com.bo.bff.application.dtos.response.generic.GenericResponse;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Map;

@Service
public interface IDestinationAccountService {
    AddAccountResponse addThirdAccount(String personId, AddThirdAccountRequest addThirdAccountRequest, Map<String, String> parameters) throws IOException;

    AddAccountResponse addWalletAccount(String personId, AddWalletAccountRequest addWalletAccountRequest, Map<String, String> parameter) throws IOException;

    AddAccountResponse addAchAccount(String personId, AddAchAccountRequest addAchAccountRequest, Map<String, String> parameter) throws IOException;

    GenericResponse addQRAccount(String personId, String bankType, AddQRAccountRequest addQRAccountRequest, Map<String, String> parameter) throws IOException;

    BanksResponse getBanks(Map<String, String> parameter) throws IOException;

    BranchOfficeResponse getBranchOffice(String bankCode, Map<String, String> parameter) throws IOException;

    AccountTypeListResponse accountTypes();

    GenericResponse deleteThirdAccount(String personId, long identifier, long accountNumber, Map<String, String> parameters) throws IOException;

    GenericResponse deleteWalletAccount(String personId, long identifier, long accountNumber, Map<String, String> parameters) throws IOException;

    GenericResponse deleteAchAccount(String personId, long identifier, Map<String, String> parameter) throws IOException;

    DestinationAccountResponse getDestinationAccounts(String personId, DestinationAccountRequest request, Map<String, String> parameter) throws IOException;

    ValidateAccountResponse getValidateDestinationAccounts(String accountNumber, String clientName, Map<String, String> parameter) throws IOException;

    DestinationAccount getAccount(String personId, String accountType, String accountId, Map<String, String> parameter) throws IOException;
}
