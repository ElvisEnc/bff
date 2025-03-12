package bg.com.bo.bff.mappings.providers.certifications.interfaces;

import bg.com.bo.bff.application.dtos.response.certifications.CertificationAccountsResponse;
import bg.com.bo.bff.application.dtos.response.certifications.CertificationHistoryResponse;
import bg.com.bo.bff.application.dtos.response.certifications.CertificationPrefExchRateResponse;
import bg.com.bo.bff.application.dtos.response.certifications.CertificationTypesResponse;
import bg.com.bo.bff.providers.dtos.response.certifications.CertificatesAccountsListMWResponse;
import bg.com.bo.bff.providers.dtos.response.certifications.CertificatesTypeListMWResponse;
import bg.com.bo.bff.providers.dtos.response.certifications.CertificationsHistoryMWResponse;
import bg.com.bo.bff.providers.dtos.response.certifications.CertificationsPreferredExchMWResponse;

import java.util.List;

public interface ICertificationsMapper {

    List<CertificationTypesResponse> convertCertsTypesResponse(CertificatesTypeListMWResponse mdwResponse);

    List<CertificationAccountsResponse> convertCertsAccountsResponse(CertificatesAccountsListMWResponse mdwResponse);

    List<CertificationPrefExchRateResponse> convertCertsPreferredExChaRate(CertificationsPreferredExchMWResponse mdwResponse);

    List<CertificationHistoryResponse> convertCertificationHistory(CertificationsHistoryMWResponse mdwResponse);

}
