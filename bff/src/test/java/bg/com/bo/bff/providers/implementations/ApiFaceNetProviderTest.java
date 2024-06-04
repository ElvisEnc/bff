package bg.com.bo.bff.providers.implementations;

import bg.com.bo.bff.application.config.HttpClientConfig;
import bg.com.bo.bff.application.dtos.response.GetPersonalInformationResponseFixture;
import bg.com.bo.bff.application.dtos.response.user.EconomicActivityResponse;
import bg.com.bo.bff.application.dtos.response.user.EconomicActivityResponseFixture;
import bg.com.bo.bff.application.dtos.response.user.PersonalResponse;
import bg.com.bo.bff.application.exceptions.GenericException;
import bg.com.bo.bff.commons.enums.AppError;
import bg.com.bo.bff.commons.enums.DeviceMW;
import bg.com.bo.bff.commons.utils.Util;
import bg.com.bo.bff.models.interfaces.IHttpClientFactory;
import bg.com.bo.bff.providers.dtos.request.PersonalInformationNetRequestFixture;
import bg.com.bo.bff.providers.dtos.request.personal.information.ApiPersonalInformationNetRequest;
import bg.com.bo.bff.providers.dtos.response.ErrorMiddlewareProvider;
import bg.com.bo.bff.providers.dtos.response.ErrorMiddlewareProviderFixture;
import bg.com.bo.bff.providers.dtos.response.ProviderNetResponse;
import bg.com.bo.bff.providers.dtos.response.apiface.DepartmentsNetResponse;
import bg.com.bo.bff.providers.dtos.response.personal.information.PersonalInformationNetResponse;
import bg.com.bo.bff.providers.interfaces.IApiFaceNetProvider;
import bg.com.bo.bff.providers.mappings.apiface.IApiFaceMapper;
import bg.com.bo.bff.providers.mappings.personal.information.IPersonalInformationMapper;
import com.github.tomakehurst.wiremock.junit5.WireMockExtension;
import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import org.apache.http.impl.client.HttpClientBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.io.IOException;
import java.util.Map;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@WireMockTest(proxyMode = true, httpPort = 8080)
@ExtendWith(WireMockExtension.class)
@ExtendWith(MockitoExtension.class)
class ApiFaceNetProviderTest {
    @InjectMocks
    private ApiFaceNetProvider apiFaceNetProvider;
    private Map<String, String> map;
    private IHttpClientFactory httpClientFactoryMock;

    @BeforeEach
    void setUp() {
        this.map = Map.of(
                DeviceMW.DEVICE_ID.getCode(), "1234",
                DeviceMW.DEVICE_IP.getCode(), "12344",
                DeviceMW.DEVICE_NAME.getCode(), "OS",
                DeviceMW.GEO_POSITION_X.getCode(), "121.11",
                DeviceMW.GEO_POSITION_Y.getCode(), "121.11",
                DeviceMW.APP_VERSION.getCode(), "1.0.0"
        );
        httpClientFactoryMock = Mockito.mock(HttpClientConfig.class);
        when(httpClientFactoryMock.create()).thenReturn(HttpClientBuilder.create().useSystemProperties().build());

        this.apiFaceNetProvider = new ApiFaceNetProvider(httpClientFactoryMock);
        ReflectionTestUtils.setField(apiFaceNetProvider, "urlApiFaceNet", "http://localhost:8080");
    }

    @Test
    void givenValidDataWhenGetDepartmentsThenSuccess() throws IOException {
        // Arrange
        String result = "{\"endPointACListadoPlazaResult\":[{\"pCodError\":\"\",\"pDesError\":\"\",\"pIntCodRespuesta\":0,\"pStrDescripcion\":\"CHUQUISACA\",\"pStrMensajeApp\":null,\"pStrMensajeUsuario\":null,\"pStrNroSesion\":null,\"pintCodPlaza\":1}]}";
        DepartmentsNetResponse expectedResponse = Util.stringToObject(result, DepartmentsNetResponse.class);

        String jsonResponse = Util.objectToString(expectedResponse);
        stubFor(post(anyUrl())
                .willReturn(okJson(jsonResponse)));

        // Act
        DepartmentsNetResponse response = apiFaceNetProvider.getDepartments(map);

        // Assert
        assertNotNull(response);
        assertEquals(expectedResponse, response);
    }

    @Test
    void givenError400WhenGetDepartmentsThenError400() throws IOException {
        // Arrange
        ErrorMiddlewareProvider error = ErrorMiddlewareProviderFixture.errorBadRequest();
        stubFor(post(anyUrl())
                .willReturn(aResponse()
                        .withStatus(400)
                        .withBody(Util.objectToString(error))
                )
        );

        // Act
        GenericException exception = assertThrows(GenericException.class, () -> {
            apiFaceNetProvider.getDepartments(map);
        });

        // Assert
        assertTrue(exception.getCode().contains("BAD_REQUEST"));
        verify(httpClientFactoryMock).create();
    }

    @Test
    void givenError500WhenGetDepartmentsThenError500() throws IOException {
        // Arrange
        Mockito.when(httpClientFactoryMock.create()).thenThrow(new RuntimeException("Error al crear cliente HTTP"));

        // Act
        Exception exception = assertThrows(RuntimeException.class, () -> {
            apiFaceNetProvider.getDepartments(map);
        });

        // Assert
        assertEquals(AppError.DEFAULT.getMessage(), exception.getMessage());
    }
}
