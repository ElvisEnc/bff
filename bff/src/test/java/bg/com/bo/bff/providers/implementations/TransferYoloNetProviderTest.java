package bg.com.bo.bff.providers.implementations;

import bg.com.bo.bff.application.config.HttpClientConfig;
import bg.com.bo.bff.application.dtos.request.TransferRequestFixture;
import bg.com.bo.bff.application.exceptions.GenericException;
import bg.com.bo.bff.commons.enums.AppError;
import bg.com.bo.bff.commons.utils.Util;
import bg.com.bo.bff.models.ClientToken;
import bg.com.bo.bff.models.interfaces.IHttpClientFactory;
import bg.com.bo.bff.providers.dtos.request.TransferYoloNetRequest;
import bg.com.bo.bff.providers.dtos.request.TransferYoloNetRequestFixture;
import bg.com.bo.bff.providers.dtos.response.*;
import bg.com.bo.bff.mappings.providers.transfer.IYoloMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.junit5.WireMockExtension;
import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import org.apache.http.impl.client.HttpClientBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.util.Collections;
import java.util.UUID;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.client.WireMock.okJson;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.util.ReflectionTestUtils.setField;

@WireMockTest(proxyMode = true, httpPort = 8080)
@ExtendWith(WireMockExtension.class)
@ExtendWith(MockitoExtension.class)
class TransferYoloNetProviderTest {
    private IHttpClientFactory httpClientFactoryMock;
    private TransferYoloNetProvider transferYoloNetProvider;
    private ClientToken clientTokenMock;
    private ErrorMiddlewareProvider errorMiddlewareProvider;
    private IYoloMapper mapper;

    @BeforeEach
    void setUp() {
        httpClientFactoryMock = Mockito.mock(HttpClientConfig.class);
        mapper = Mockito.mock(IYoloMapper.class);
        Mockito.when(httpClientFactoryMock.create()).thenReturn(HttpClientBuilder.create().useSystemProperties().build());
        transferYoloNetProvider = new TransferYoloNetProvider(httpClientFactoryMock, mapper);

        clientTokenMock = new ClientToken();
        clientTokenMock.setAccessToken(UUID.randomUUID().toString());
        setField(transferYoloNetProvider, "urlProviderYoloNet", "http://localhost:8080/");
    }

    @Test
    void givenRequestTransferWhenTransferToYoloThenSuccess() throws IOException {
        // Arrange
        String result = "{\"CodigoError\":\"COD000\",\"Datos\":[{\"NroTransaccion\":30561315,\"Mensaje\":\"COD000\",\"TipoSolicitud\":44},[{\"NRO_TRANSFERENCIA\":900000051,\"FECHATRANSACCION\":\"13/05/2024\",\"HORATRANSACCION\":\"10:08:59\",\"TITULO\":\"TRANSFERENCIA A CUENTAS DE BILLETERA\",\"NOMBRE_CLIENTE_ORIGEN\":\"EMPLEADO BANCO\",\"NOMBRE_CLIENTE_DESTINO\":\"EMPLEADO BANCO\",\"DOCUMENTO_IDENTIDAD\":\"6303180\",\"IMPORTE_ORIGEN\":\"           1,478.00\",\"EQUIVALENTE_MONTO_ORIGEN\":\"           1,478.00\",\"IMPORTE_DESTINO\":\"           1,478.00\",\"EQUIVALENTE_MONTO_DESTINO\":\"           1,478.00\",\"CUENTA_ORIGEN\":\"CA 1310104470\",\"TIPO_CAMBIO\":\"               1.00\",\"TIPO_CAMBIO_ORIGEN\":\"               1.00\",\"MONEDA_ORIGEN\":\"Bs\",\"DESCRIPCION_ORIGEN\":\"Transferencia billetera\",\"MONEDA_CUENTA\":\"Bs\",\"CUENTA_DESTINO\":\"70292838\",\"DESCRIPCION_DESTINO\":\"Transferencia billetera\"}]],\"Mensaje\":\"\"}";
        ProviderNetResponse expectedResponse = Util.stringToObject(result, ProviderNetResponse.class);
        TransferYoloNetRequest requestMapperMock=TransferYoloNetRequestFixture.withDefault();
        TransferResponseMD responseMock = TransferMWResponseFixture.withDefault();
        Mockito.when(mapper.mapperRequest(any(), any(),any(), any())).thenReturn(requestMapperMock);
        Mockito.when(mapper.convertResponse(any())).thenReturn(responseMock);

        String jsonResponse = Util.objectToString(expectedResponse);
        stubFor(post(anyUrl())
                .willReturn(okJson(jsonResponse)));

        // Act
        TransferResponseMD response = transferYoloNetProvider.transferToYolo(123, 123, 123, TransferRequestFixture.withDefault());

        // Assert
        assertNotNull(response);
    }

    @Test
    void giveRequestTransferWhenTransferToYoloThenError400() throws IOException {
        //Arrange
        errorMiddlewareProvider = ErrorMiddlewareProvider.builder()
                .errorDetailResponse(Collections.singletonList(ErrorMiddlewareProvider.ErrorDetailProvider.builder()
                        .code("BAD_REQUEST")
                        .description("BAD_REQUEST")
                        .build()))
                .build();
        stubFor(post(anyUrl()).willReturn(aResponse().withStatus(500).withBody(new ObjectMapper().writeValueAsString(errorMiddlewareProvider))));

        //Act
        GenericException exception = assertThrows(GenericException.class, () -> {
            transferYoloNetProvider.transferToYolo(123, 123, 123, TransferRequestFixture.withDefault());
        });

        //Assert
        assertTrue(exception.getCode().contains("BAD_REQUEST"));
    }

    @Test
    void giveUnexpectedErrorOccursWhenTransferToYoloThenRuntimeException() throws IOException {
        // Arrange
        Mockito.when(httpClientFactoryMock.create()).thenThrow(new RuntimeException("Error al crear cliente HTTP"));

        // Act
        Exception exception = assertThrows(RuntimeException.class, () -> {
            transferYoloNetProvider.transferToYolo(123, 123, 123, TransferRequestFixture.withDefault());
        });

        // Assert
        assertEquals(AppError.DEFAULT.getMessage(), exception.getMessage());
    }
}