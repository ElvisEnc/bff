package bg.com.bo.bff.services.implementations.v1;

import bg.com.bo.bff.application.dtos.response.certifications.CertificationTypesResponse;
import bg.com.bo.bff.mappings.providers.certifications.interfaces.ICertificationsMapper;
import bg.com.bo.bff.providers.dtos.response.certifications.CertificatesTypeListMWResponse;
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
    public List<CertificationTypesResponse> getCertificateTypes(String personId, String appCode) throws IOException {
        CertificatesTypeListMWResponse response = certificationsProvider.getCertificatesType(personId, appCode);
        return certsMapper.convertResponse(response);
    }
}
