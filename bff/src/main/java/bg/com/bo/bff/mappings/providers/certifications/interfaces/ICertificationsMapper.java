package bg.com.bo.bff.mappings.providers.certifications.interfaces;

import bg.com.bo.bff.application.dtos.response.certifications.*;
import bg.com.bo.bff.providers.dtos.response.certifications.*;

import java.util.List;

public interface ICertificationsMapper {

    List<CertificationTypesResponse> convertCertsTypesResponse(CertificatesTypeListMWResponse mdwResponse);

    List<CertificationAccountsResponse> convertCertsAccountsResponse(CertificatesAccountsListMWResponse mdwResponse);

    List<CertificationPrefExchRateResponse> convertCertsPreferredExChaRate(CertificationsPreferredExchMWResponse mdwResponse);

    List<CertificationHistoryResponse> convertCertificationHistory(CertificationsHistoryMWResponse mdwResponse);

    CertificationConfigResponse convertCertificationConfig(CertificationConfigMWResponse mdwResponse);

    CertificationPriceResponse convertCertificationPrice(CertificationPriceMWResponse mdwResponse);

    SaveCertificationResponse convertSaveCertification(CertificationSaveRequestMWResponse mdwResponse);

}
