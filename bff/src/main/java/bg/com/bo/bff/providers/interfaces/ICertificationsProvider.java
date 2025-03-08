package bg.com.bo.bff.providers.interfaces;

import bg.com.bo.bff.providers.dtos.response.certifications.CertificatesTypeListMWResponse;

import java.io.IOException;
import java.util.Map;

public interface ICertificationsProvider {

    CertificatesTypeListMWResponse getCertificatesType(String personId, String appCode) throws IOException;

}
