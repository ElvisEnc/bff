package bg.com.bo.bff.services.interfaces;

import bg.com.bo.bff.application.dtos.request.certifications.CertificationConfigRequest;
import bg.com.bo.bff.application.dtos.request.certifications.CertificationPriceRequest;
import bg.com.bo.bff.application.dtos.request.certifications.SaveCertificationRequest;
import bg.com.bo.bff.application.dtos.response.certifications.*;
import bg.com.bo.bff.providers.dtos.request.certifications.SaveCertificationMWRequest;

import java.io.IOException;
import java.util.List;

public interface ICertificationsService {

    List<CertificationTypesResponse> getCertificateTypes(String personId, String appCode) throws IOException;

    List<CertificationAccountsResponse> getAccounts(String personId) throws IOException;

    List<CertificationPrefExchRateResponse> getPreferredExchRate(String personId) throws IOException;

    List<CertificationHistoryResponse> getCertificationsHistory(String personId) throws IOException;

    CertificationConfigResponse getConfig(CertificationConfigRequest request) throws IOException;

    CertificationPriceResponse getCertificationPrice(CertificationPriceRequest request) throws IOException;

    SaveCertificationResponse saveCertRequest(SaveCertificationRequest request) throws IOException;
}
