package bg.com.bo.bff.providers.interfaces;

import bg.com.bo.bff.providers.dtos.request.certifications.CertificationConfigMWRequest;
import bg.com.bo.bff.providers.dtos.request.certifications.CertificationPriceMWRequest;
import bg.com.bo.bff.providers.dtos.request.certifications.SaveCertificationMWRequest;
import bg.com.bo.bff.providers.dtos.response.certifications.*;

import java.io.IOException;

public interface ICertificationsProvider {

    CertificatesTypeListMWResponse getCertificatesType(String personId, String appCode) throws IOException;

    CertificatesAccountsListMWResponse getAccountsList(String personId) throws IOException;

    CertificationsPreferredExchMWResponse getPreferredExRate(String personId) throws IOException;

    CertificationsHistoryMWResponse getCertificationsHistory(String personId) throws IOException;

    CertificationConfigMWResponse getConfig(CertificationConfigMWRequest request) throws IOException;

    CertificationPriceMWResponse getCertificationPrice(CertificationPriceMWRequest request) throws IOException;

    CertificationSaveRequestMWResponse saveCertificateRequest(SaveCertificationMWRequest request) throws IOException;

}
