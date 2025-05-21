package bg.com.bo.bff.services.implementations.v1;

import bg.com.bo.bff.application.dtos.request.certifications.CertificationConfigRequest;
import bg.com.bo.bff.application.dtos.request.certifications.CertificationPriceRequest;
import bg.com.bo.bff.application.dtos.request.certifications.SaveCertificationRequest;
import bg.com.bo.bff.application.dtos.response.certifications.CertificationAccountsResponse;
import bg.com.bo.bff.application.dtos.response.certifications.CertificationConfigResponse;
import bg.com.bo.bff.application.dtos.response.certifications.CertificationHistoryResponse;
import bg.com.bo.bff.application.dtos.response.certifications.CertificationPrefExchRateResponse;
import bg.com.bo.bff.application.dtos.response.certifications.CertificationPriceResponse;
import bg.com.bo.bff.application.dtos.response.certifications.CertificationTypesResponse;
import bg.com.bo.bff.application.dtos.response.certifications.SaveCertificationResponse;
import bg.com.bo.bff.commons.enums.config.provider.CanalMW;
import bg.com.bo.bff.commons.utils.UtilDate;
import bg.com.bo.bff.mappings.providers.certifications.interfaces.ICertificationsMapper;
import bg.com.bo.bff.providers.dtos.request.certifications.CertificationConfigMWRequest;
import bg.com.bo.bff.providers.dtos.request.certifications.CertificationPriceMWRequest;
import bg.com.bo.bff.providers.dtos.request.certifications.SaveCertificationMWRequest;
import bg.com.bo.bff.providers.dtos.response.certifications.CertificatesAccountsListMWResponse;
import bg.com.bo.bff.providers.dtos.response.certifications.CertificatesTypeListMWResponse;
import bg.com.bo.bff.providers.dtos.response.certifications.CertificationConfigMWResponse;
import bg.com.bo.bff.providers.dtos.response.certifications.CertificationPriceMWResponse;
import bg.com.bo.bff.providers.dtos.response.certifications.CertificationSaveRequestMWResponse;
import bg.com.bo.bff.providers.dtos.response.certifications.CertificationsHistoryMWResponse;
import bg.com.bo.bff.providers.dtos.response.certifications.CertificationsPreferredExchMWResponse;
import bg.com.bo.bff.providers.interfaces.ICertificationsProvider;
import bg.com.bo.bff.services.interfaces.ICertificationsService;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class CertificationsService implements ICertificationsService {

    private final ICertificationsProvider certificationsProvider;
    private final ICertificationsMapper certsMapper;

    public CertificationsService(ICertificationsProvider certificationsProvider, ICertificationsMapper certsMapper) {
        this.certificationsProvider = certificationsProvider;
        this.certsMapper = certsMapper;
    }

    @Override
    public List<CertificationTypesResponse> getCertificateTypes(String personId) throws IOException {
        CertificatesTypeListMWResponse response = certificationsProvider.getCertificatesType(personId);
        return certsMapper.convertCertsTypesResponse(response);
    }

    @Override
    public List<CertificationAccountsResponse> getAccounts(String personId) throws IOException {
        CertificatesAccountsListMWResponse response = certificationsProvider.getAccountsList(personId);
        return certsMapper.convertCertsAccountsResponse(response);
    }

    @Override
    public List<CertificationPrefExchRateResponse> getPreferredExchRate(String personId) throws IOException {
        CertificationsPreferredExchMWResponse response = certificationsProvider.getPreferredExRate(personId);
        return certsMapper.convertCertsPreferredExChaRate(response);
    }

    @Override
    public List<CertificationHistoryResponse> getCertificationsHistory(String personId) throws IOException {
        CertificationsHistoryMWResponse response = certificationsProvider.getCertificationsHistory(personId);
        return certsMapper.convertCertificationHistory(response);
    }

    @Override
    public CertificationConfigResponse getConfig(CertificationConfigRequest request) throws IOException {
        CertificationConfigMWRequest mdwRequest = CertificationConfigMWRequest.builder()
                .personId(request.getPersonId())
                .languageCode("1")
                .requestCode(request.getRequestCode())
                .requestTypeCode(request.getRequestTypeCode())
                .build();
        CertificationConfigMWResponse response = certificationsProvider.getConfig(mdwRequest);
        return certsMapper.convertCertificationConfig(response);
    }

    @Override
    public CertificationPriceResponse getCertificationPrice(CertificationPriceRequest request) throws IOException {
        String initDate = UtilDate.adaptDateToMWFormat(request.getInitDate());
        String endDate = UtilDate.adaptDateToMWFormat(request.getEndDate());

        CertificationPriceMWRequest mdwRequest = CertificationPriceMWRequest.builder()
                .personId(request.getPersonId())
                .appCode("2")
                .session("")
                .initDate(initDate)
                .endDate(endDate)
                .certCode(request.getCertCode())
                .certTypeCode( request.getCertTypeCode())
                .build();
        CertificationPriceMWResponse mdwResponse = certificationsProvider.getCertificationPrice(mdwRequest);
        return certsMapper.convertCertificationPrice(mdwResponse);
    }

    @Override
    public SaveCertificationResponse saveCertRequest(SaveCertificationRequest request) throws IOException {
        String initDate = UtilDate.adaptDateToMWFormat(request.getInitDate());
        String endDate = UtilDate.adaptDateToMWFormat(request.getEndDate());
        SaveCertificationMWRequest mdwRequest = SaveCertificationMWRequest.builder()
                .personId(request.getPersonId())
                .appCode(CanalMW.GANAMOVIL.getCanal())
                .accountId(request.getAccountId())
                .chargeFeeId(request.getChargeFeeId())
                .typeCode(request.getTypeCode())
                .requestCode(request.getRequestCode())
                .nit(request.getNit())
                .clientName(request.getClientName())
                .initDate(initDate)
                .endDate(endDate)
                .email(request.getEmail())
                .build();
        CertificationSaveRequestMWResponse mdwResponse = certificationsProvider.saveCertificateRequest(mdwRequest);
        return certsMapper.convertSaveCertification(mdwResponse);
    }
}
