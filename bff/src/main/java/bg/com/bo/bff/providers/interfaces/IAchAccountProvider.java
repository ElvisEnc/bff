package bg.com.bo.bff.providers.interfaces;

import bg.com.bo.bff.application.dtos.response.destination.account.AddAccountResponse;
import bg.com.bo.bff.application.dtos.response.generic.GenericResponse;
import bg.com.bo.bff.providers.dtos.request.ach.account.mw.DeleteAchAccountMWRequest;
import bg.com.bo.bff.providers.dtos.request.qr.mw.QrListMWRequest;
import bg.com.bo.bff.providers.dtos.response.ach.account.mw.BanksMWResponse;
import bg.com.bo.bff.providers.dtos.request.ach.account.mw.AddAchAccountBasicRequest;
import bg.com.bo.bff.providers.dtos.response.ach.account.mw.BranchOfficeMWResponse;
import bg.com.bo.bff.providers.dtos.response.ach.account.mw.AchAccountsMWResponse;
import bg.com.bo.bff.providers.dtos.response.ach.account.mw.QrListMWResponse;

import java.io.IOException;
import java.util.Map;

public interface IAchAccountProvider {
    AddAccountResponse addAchAccount(AddAchAccountBasicRequest request, Map<String, String> parameters) throws IOException;

    GenericResponse deleteAchAccount(DeleteAchAccountMWRequest request, Map<String, String> parameters) throws IOException;

    BanksMWResponse getBanks(Map<String, String> parameters) throws IOException;

    BranchOfficeMWResponse getAllBranchOfficeBank(String code, Map<String, String> parameters) throws IOException;

    AchAccountsMWResponse getAchAccounts(String personId, Map<String, String> parameters) throws IOException;

    QrListMWResponse getListQrGeneratePaidMW(QrListMWRequest request, String personId, Map<String, String> parameters) throws IOException;
}
