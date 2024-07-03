package bg.com.bo.bff.providers.interfaces;

import bg.com.bo.bff.application.dtos.request.qr.QrListRequest;
import bg.com.bo.bff.application.dtos.response.generic.GenericResponse;
import bg.com.bo.bff.models.ClientToken;
import bg.com.bo.bff.providers.dtos.response.ach.account.mw.BanksMWResponse;
import bg.com.bo.bff.providers.dtos.request.ach.account.mw.AddAchAccountBasicRequest;
import bg.com.bo.bff.providers.dtos.response.ach.account.mw.BranchOfficeMWResponse;
import bg.com.bo.bff.providers.dtos.response.ach.account.mw.AchAccountMWResponse;
import bg.com.bo.bff.providers.dtos.response.ach.account.mw.QrListMWResponse;

import java.io.IOException;
import java.util.Map;

public interface IAchAccountProvider {
    ClientToken generateAccessToken() throws IOException;
    GenericResponse addAchAccount(String accessToken, AddAchAccountBasicRequest addAchAccountBasicRequest, Map<String, String> parameters) throws IOException;
    GenericResponse deleteAchAccount(String personId, long identifier, String deviceId, String deviceIp) throws IOException;
    BanksMWResponse getBanks() throws IOException;
    BranchOfficeMWResponse getAllBranchOfficeBank(Integer code) throws IOException;
    AchAccountMWResponse getAchAccounts(Integer personId, Map<String, String> parameters) throws IOException;
    QrListMWResponse getListQrGeneratePaidMW(QrListRequest request, Integer personId, Map<String, String> parameters) throws IOException;
}
