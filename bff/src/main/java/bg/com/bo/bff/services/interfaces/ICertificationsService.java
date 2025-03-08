package bg.com.bo.bff.services.interfaces;

import bg.com.bo.bff.application.dtos.response.certifications.CertificationTypesResponse;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface ICertificationsService {

    List<CertificationTypesResponse> getCertificateTypes(String personId, String appCode) throws IOException;

}
