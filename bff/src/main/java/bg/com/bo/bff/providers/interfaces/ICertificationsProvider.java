package bg.com.bo.bff.providers.interfaces;

import bg.com.bo.bff.providers.dtos.response.certifications.CertificatesAccountsListMWResponse;
import bg.com.bo.bff.providers.dtos.response.certifications.CertificatesTypeListMWResponse;
import bg.com.bo.bff.providers.dtos.response.certifications.CertificationsHistoryMWResponse;
import bg.com.bo.bff.providers.dtos.response.certifications.CertificationsPreferredExchMWResponse;

import java.io.IOException;
import java.util.Map;

public interface ICertificationsProvider {

    CertificatesTypeListMWResponse getCertificatesType(String personId, String appCode) throws IOException;

    CertificatesAccountsListMWResponse getAccountsList(String personId) throws IOException;

    CertificationsPreferredExchMWResponse getPreferredExRate(String personId) throws IOException;

    CertificationsHistoryMWResponse getCertificationsHistory(String personId)  throws IOException;

}
