package bg.com.bo.bff.mappings.providers.certifications.implementation;

import bg.com.bo.bff.application.dtos.response.certifications.CertificationTypesResponse;
import bg.com.bo.bff.mappings.providers.certifications.interfaces.ICertificationsMapper;
import bg.com.bo.bff.providers.dtos.response.certifications.CertificatesTypeListMWResponse;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Component
public class CertificationsMapper implements ICertificationsMapper {

    @Override
    public List<CertificationTypesResponse> convertResponse(CertificatesTypeListMWResponse mdwResponse) {
        if (mdwResponse == null || mdwResponse.getData() == null)
            return Collections.emptyList();

        return mdwResponse.getData().stream()
                .map(
                        mdw -> CertificationTypesResponse.builder()
                                .requestCode(mdw.getRequestCode())
                                .typeCode(mdw.getTypeCode())
                                .description(mdw.getDescription())
                                .build()
                ).toList();
    }

}
