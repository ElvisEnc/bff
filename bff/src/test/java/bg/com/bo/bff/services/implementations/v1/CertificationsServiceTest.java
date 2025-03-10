package bg.com.bo.bff.services.implementations.v1;

import bg.com.bo.bff.application.dtos.response.certifications.CertificationTypesResponse;
import bg.com.bo.bff.application.dtos.response.certifications.CertificationTypesResponseFixture;
import bg.com.bo.bff.mappings.providers.certifications.interfaces.ICertificationsMapper;
import bg.com.bo.bff.providers.dtos.response.certificates.CertificatesTypeListMWResponseFixture;
import bg.com.bo.bff.providers.dtos.response.certifications.CertificatesTypeListMWResponse;
import bg.com.bo.bff.providers.interfaces.ICertificationsProvider;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CertificationsServiceTest {

    @InjectMocks
    private CertificationsService service;

    @Mock
    private ICertificationsProvider provider;

    @Mock
    private ICertificationsMapper certsMapper;

    @Test
    void getCertificationsTypeOK() throws IOException {
        List<CertificationTypesResponse> expected = CertificationTypesResponseFixture.withDefaults();
        CertificatesTypeListMWResponse mdwExpected = CertificatesTypeListMWResponseFixture.withDefaults();
        when(provider.getCertificatesType(any(), any())).thenReturn(mdwExpected);
        when(certsMapper.convertResponse(any())).thenReturn(expected);

        List<CertificationTypesResponse> response = service.getCertificateTypes("123", "32121");

        assertEquals(expected.size(), response.size());
    }

}