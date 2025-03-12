package bg.com.bo.bff.services.interfaces;

import bg.com.bo.bff.application.dtos.response.certifications.CertificationAccountsResponse;
import bg.com.bo.bff.application.dtos.response.certifications.CertificationHistoryResponse;
import bg.com.bo.bff.application.dtos.response.certifications.CertificationPrefExchRateResponse;
import bg.com.bo.bff.application.dtos.response.certifications.CertificationTypesResponse;

import java.io.IOException;
import java.util.List;

public interface ICertificationsService {

    List<CertificationTypesResponse> getCertificateTypes(String personId, String appCode) throws IOException;

    List<CertificationAccountsResponse> getAccounts(String personId) throws IOException;

    List<CertificationPrefExchRateResponse> getPreferredExchRate(String personId) throws IOException;

    List<CertificationHistoryResponse> getCertificationsHistory(String personId) throws IOException;

}
