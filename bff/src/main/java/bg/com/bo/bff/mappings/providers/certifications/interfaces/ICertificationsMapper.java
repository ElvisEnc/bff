package bg.com.bo.bff.mappings.providers.certifications.interfaces;

import bg.com.bo.bff.application.dtos.response.certifications.CertificationTypesResponse;
import bg.com.bo.bff.providers.dtos.response.certifications.CertificatesTypeListMWResponse;

import java.util.List;

public interface ICertificationsMapper {

    List<CertificationTypesResponse> convertResponse(CertificatesTypeListMWResponse mdwResponse);

}
