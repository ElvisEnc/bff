package bg.com.bo.bff.services.implementations.v1;

import bg.com.bo.bff.application.dtos.response.dpf.DpfListResponse;
import bg.com.bo.bff.application.dtos.response.dpf.DpfResponseFixture;
import bg.com.bo.bff.providers.dtos.response.dpf.mw.DpfMWResponseFixture;
import bg.com.bo.bff.providers.dtos.response.dpf.mw.DpfMWResponse;
import bg.com.bo.bff.providers.interfaces.IDPFProvider;
import bg.com.bo.bff.mappings.providers.dpf.IDPFMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class DPFServiceTest {
    @Spy
    @InjectMocks
    private DPFService service;

    @Mock
    private IDPFProvider provider;

    @Mock
    private IDPFMapper iDPFMapper;

    @Test
    void giveValidPersonIdWhenGetListDPFsThenReturnSuccess() throws IOException {
        // Arrange
        String personId = "169494";
        DpfMWResponse expectedResponse = DpfMWResponseFixture.withDefaultDpfMWResponse();
        DpfListResponse expected = DpfResponseFixture.withDefaultDpfListResponse();
        Mockito.when(provider.getDPFsList(Mockito.any(), Mockito.any(), Mockito.any())).thenReturn(expectedResponse);
        Mockito.when(iDPFMapper.mapToDPFListResponse(expectedResponse)).thenReturn(expected);

        // Act
        DpfListResponse response = service.getDPFsList(personId, "123", new HashMap<>());

        // Assert
        Assertions.assertNotNull(response);
        assertEquals(expected, response);
        verify(provider).getDPFsList(personId, "123", new HashMap<>());
        verify(iDPFMapper).mapToDPFListResponse(expectedResponse);
    }
}