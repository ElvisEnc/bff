package bg.com.bo.bff.services.implementations.v1;

import bg.com.bo.bff.application.dtos.response.certifications.*;
import bg.com.bo.bff.mappings.providers.certifications.implementation.CertificationsMapper;
import bg.com.bo.bff.providers.dtos.response.certificates.CertificatesAccountsListMWResponseFixture;
import bg.com.bo.bff.providers.dtos.response.certificates.CertificatesTypeListMWResponseFixture;
import bg.com.bo.bff.providers.dtos.response.certificates.CertificationsHistoryMWResponseFixture;
import bg.com.bo.bff.providers.dtos.response.certificates.CertificationsPreferredExchMWResponseFixture;
import bg.com.bo.bff.providers.dtos.response.certifications.CertificatesAccountsListMWResponse;
import bg.com.bo.bff.providers.dtos.response.certifications.CertificatesTypeListMWResponse;
import bg.com.bo.bff.providers.dtos.response.certifications.CertificationsHistoryMWResponse;
import bg.com.bo.bff.providers.dtos.response.certifications.CertificationsPreferredExchMWResponse;
import bg.com.bo.bff.providers.interfaces.ICertificationsProvider;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.util.ArrayList;
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

    @Spy
    private CertificationsMapper certsMapper = new CertificationsMapper();

    @Test
    void getCertificationsTypeOK() throws IOException {
        List<CertificationTypesResponse> expected = CertificationResponseFixture.withDefaults();
        CertificatesTypeListMWResponse mdwExpected = CertificatesTypeListMWResponseFixture.withDefaults();
        when(provider.getCertificatesType(any(), any())).thenReturn(mdwExpected);
        when(certsMapper.convertCertsTypesResponse(mdwExpected)).thenReturn(expected);

        List<CertificationTypesResponse> response = service.getCertificateTypes("123", "32121");

        assertEquals(expected.size(), response.size());
    }

    @Test
    void getCertificationsTypeOKWithNull() throws IOException {
        List<CertificationTypesResponse> expected = new ArrayList<>();
        when(provider.getCertificatesType(any(), any())).thenReturn(null);
        when(certsMapper.convertCertsTypesResponse(any())).thenReturn(expected);

        List<CertificationTypesResponse> response = service.getCertificateTypes("123", "32121");

        assertEquals(expected.size(), response.size());
    }

    @Test
    void getCertificationsTypeOKWithEmptyList() throws IOException {
        List<CertificationTypesResponse> expected = new ArrayList<>();
        CertificatesTypeListMWResponse mdwExpected = CertificatesTypeListMWResponseFixture.withEmptyData();
        when(provider.getCertificatesType(any(), any())).thenReturn(mdwExpected);
        when(certsMapper.convertCertsTypesResponse(mdwExpected)).thenReturn(expected);

        List<CertificationTypesResponse> response = service.getCertificateTypes("123", "32121");

        assertEquals(expected.size(), response.size());
    }

    @Test
    void getAccountsOK() throws IOException {
        List<CertificationAccountsResponse> expected = CertificationResponseFixture.withDefaultsAccounts();
        CertificatesAccountsListMWResponse mdwExpected = CertificatesAccountsListMWResponseFixture.withDefaults();
        when(provider.getAccountsList(any())).thenReturn(mdwExpected);
        when(certsMapper.convertCertsAccountsResponse(mdwExpected)).thenReturn(expected);

        List<CertificationAccountsResponse> response = service.getAccounts("123");

        assertEquals(expected.size(), response.size());
    }

    @Test
    void getAccountsEmptyData() throws IOException {
        List<CertificationAccountsResponse> expected = CertificationResponseFixture.withDefaultsAccounts();
        CertificatesAccountsListMWResponse mdwExpected = CertificatesAccountsListMWResponseFixture.withEmptyData();
        when(provider.getAccountsList(any())).thenReturn(mdwExpected);
        when(certsMapper.convertCertsAccountsResponse(mdwExpected)).thenReturn(expected);

        List<CertificationAccountsResponse> response = service.getAccounts("123");

        assertEquals(expected.size(), response.size());
    }

    @Test
    void getAccountsNull() throws IOException {
        List<CertificationAccountsResponse> expected = new ArrayList<>();
        when(provider.getAccountsList(any())).thenReturn(null);
        when(certsMapper.convertCertsAccountsResponse(any())).thenReturn(expected);

        List<CertificationAccountsResponse> response = service.getAccounts("123");

        assertEquals(expected.size(), response.size());
    }

    @Test
    void getPrefExchRateOK() throws IOException {
        List<CertificationPrefExchRateResponse> expected = CertificationResponseFixture.withDefaultsPrefExchRate();
        CertificationsPreferredExchMWResponse mdwExpected = CertificationsPreferredExchMWResponseFixture.withDefaults();
        when(provider.getPreferredExRate(any())).thenReturn(mdwExpected);
        when(certsMapper.convertCertsPreferredExChaRate(mdwExpected)).thenReturn(expected);

        List<CertificationPrefExchRateResponse> response = service.getPreferredExchRate("1234");

        assertEquals(expected.size(), response.size());
    }

    @Test
    void getPrefExchRateEmptyData() throws IOException {
        List<CertificationPrefExchRateResponse> expected = CertificationResponseFixture.withDefaultsPrefExchRate();
        CertificationsPreferredExchMWResponse mdwExpected = CertificationsPreferredExchMWResponseFixture.withEmptyData();
        when(provider.getPreferredExRate(any())).thenReturn(mdwExpected);
        when(certsMapper.convertCertsPreferredExChaRate(mdwExpected)).thenReturn(expected);

        List<CertificationPrefExchRateResponse> response = service.getPreferredExchRate("1234");

        assertEquals(expected.size(), response.size());
    }

    @Test
    void getPrefExchRateNull() throws IOException {
        List<CertificationPrefExchRateResponse> expected = CertificationResponseFixture.withDefaultsPrefExchRate();
        when(provider.getPreferredExRate(any())).thenReturn(null);
        when(certsMapper.convertCertsPreferredExChaRate(any())).thenReturn(expected);

        List<CertificationPrefExchRateResponse> response = service.getPreferredExchRate("1234");

        assertEquals(expected.size(), response.size());
    }

    @Test
    void getCertsHistoryOK() throws IOException {
        List<CertificationHistoryResponse> expected = CertificationResponseFixture.withDefaultsHistory();
        CertificationsHistoryMWResponse mdwExpected = CertificationsHistoryMWResponseFixture.withDefaults();
        when(provider.getCertificationsHistory(any())).thenReturn(mdwExpected);
        when(certsMapper.convertCertificationHistory(mdwExpected)).thenReturn(expected);

        List<CertificationHistoryResponse> response = service.getCertificationsHistory("1234");

        assertEquals(expected.size(), response.size());
    }

    @Test
    void getCertsHistoryEmptyData() throws IOException {
        List<CertificationHistoryResponse> expected = CertificationResponseFixture.withDefaultsHistory();
        CertificationsHistoryMWResponse mdwExpected = CertificationsHistoryMWResponseFixture.withEmptyData();
        when(provider.getCertificationsHistory(any())).thenReturn(mdwExpected);
        when(certsMapper.convertCertificationHistory(mdwExpected)).thenReturn(expected);

        List<CertificationHistoryResponse> response = service.getCertificationsHistory("1234");

        assertEquals(expected.size(), response.size());
    }

    @Test
    void getCertsHistoryNull() throws IOException {
        List<CertificationHistoryResponse> expected = CertificationResponseFixture.withDefaultsHistory();
        when(provider.getCertificationsHistory(any())).thenReturn(null);
        when(certsMapper.convertCertificationHistory(any())).thenReturn(expected);

        List<CertificationHistoryResponse> response = service.getCertificationsHistory("1234");

        assertEquals(expected.size(), response.size());
    }

}