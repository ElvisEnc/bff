package bg.com.bo.bff.providers.interfaces;

import bg.com.bo.bff.application.dtos.response.destination.account.AddAccountResponse;
import bg.com.bo.bff.application.dtos.response.generic.GenericResponse;
import bg.com.bo.bff.application.dtos.response.destination.account.ValidateAccountResponse;
import bg.com.bo.bff.providers.dtos.request.third.account.mw.AddThirdAccountBasicRequest;
import bg.com.bo.bff.providers.dtos.request.third.account.mw.AddWalletAccountBasicRequest;
import bg.com.bo.bff.providers.dtos.request.third.account.mw.DeleteThirdAccountMWRequest;
import bg.com.bo.bff.providers.dtos.response.third.account.mw.ThirdAccountsMWResponse;

import java.io.IOException;
import java.util.Map;

public interface IThirdAccountProvider {
    ValidateAccountResponse validateAccount(String accountNumber, String clientName, Map<String, String> parameters) throws IOException;

    AddAccountResponse addThirdAccount(AddThirdAccountBasicRequest request, Map<String, String> parameters) throws IOException;

    AddAccountResponse addWalletAccount(AddWalletAccountBasicRequest request, Map<String, String> parameters) throws IOException;

    GenericResponse deleteThirdAccount(DeleteThirdAccountMWRequest request, Map<String, String> parameters) throws IOException;

    GenericResponse deleteWalletAccount(String personId, long identifier, long accountNumber, Map<String, String> parameters) throws IOException;

    ThirdAccountsMWResponse getThirdAccounts(String personId, Map<String, String> parameters) throws IOException;

    ThirdAccountsMWResponse getWalletAccounts(String personId, Map<String, String> parameters) throws IOException;
}
