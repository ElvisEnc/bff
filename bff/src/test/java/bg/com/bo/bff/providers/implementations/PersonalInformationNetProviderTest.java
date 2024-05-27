package bg.com.bo.bff.providers.implementations;

import bg.com.bo.bff.application.config.HttpClientConfig;
import bg.com.bo.bff.application.dtos.response.GetPersonalInformationResponseFixture;
import bg.com.bo.bff.application.dtos.response.user.PersonalResponse;
import bg.com.bo.bff.commons.enums.DeviceMW;
import bg.com.bo.bff.commons.utils.Util;
import bg.com.bo.bff.models.ClientToken;
import bg.com.bo.bff.models.interfaces.IHttpClientFactory;
import bg.com.bo.bff.providers.dtos.requests.PersonalInformationNetRequestFixture;
import bg.com.bo.bff.providers.dtos.requests.personal.information.PersonalInformationNetRequest;
import bg.com.bo.bff.providers.dtos.responses.personal.information.PersonalInformationNetResponse;
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
import java.util.UUID;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

@WireMockTest(proxyMode = true, httpPort = 8080)
@ExtendWith(WireMockExtension.class)
@ExtendWith(MockitoExtension.class)
class PersonalInformationNetProviderTest {

    @InjectMocks
    private PersonalInformationNetProvider personalInformationNetProvider;
    @Mock
    private IPersonalInformationMapper mapper;
    private Map<String, String> map;

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
        IHttpClientFactory httpClientFactoryMock = Mockito.mock(HttpClientConfig.class);
        Mockito.when(httpClientFactoryMock.create()).thenReturn(HttpClientBuilder.create().useSystemProperties().build());

        this.personalInformationNetProvider = new PersonalInformationNetProvider(httpClientFactoryMock, mapper);

        ClientToken clientTokenMock = new ClientToken();
        clientTokenMock.setAccessToken(UUID.randomUUID().toString());
        ReflectionTestUtils.setField(personalInformationNetProvider, "urlProviderPersonalInformationNet", "http://localhost:8080");
    }

    @Test
    void givenPersonIdWhenGetPersonalInformationThenSuccess() throws IOException {
        // Arrange
        String result = "{\"CodigoError\":\"COD000\",\"Datos\":{\"cur_datosClienteGanasueldo\":[{\"NUMEROPERSONAFISICA\":1487723,\"FECHAULTACTUALIZACION\":null,\"NOMBRECOMPLETO\":\"PERSONA NATURAL\",\"ESTADOCIVIL\":\"S\",\"SEXO\":\"M\",\"CALLE\":\"LAS LOMAS\",\"NUMEROPUERTA\":\"SN\",\"PISO\":0,\"CIUDAD\":\"SANTA CRUZ\",\"DEPARTAMENTO\":\"SANTA CRUZ\",\"COD_DEPARTAMENTO\":7,\"BARRIOZONA\":\"LAS LOMAS\",\"EMAIL\":\"rb@bg.com\",\"CELULAR\":\"77653520\",\"COD_BARRIO\":0,\"COD_CALLE\":0,\"COD_CIUDAD\":1,\"APELLIDOESPOSO\":\" \",\"USA_APELLIDOESPOSO\":\"N\",\"REFERENCIADOMICILIO\":\" \",\"OFICINA\":\" \",\"ZONA\":1,\"NOMBRE_CONYUGUE\":\" \",\"APARTAMENTO\":\" \",\"TELEFONOS\":\" \",\"FECHAACTUALIZACION\":\"  \",\"NIVEL_INGRESOS\":null,\"ACTIVIDAD_ECONOMICA\":93099,\"EMPLEADO_BANCO\":\"1\",\"COORDENADAS\":\" \"}],\"cur_referenciasPersonaFisica\":[{\"NOMBRE\":\"INGRID CAROLA SAAVEDRA MEDIN\",\"TELEFONOS\":\"78529352\",\"RELACION\":1,\"TIPOREFERENCIA\":\"P\",\"TIPO_PERSONA\":\"F\",\"ORDINAL\":0}],\"cur_actividadEconomica\":[{\"EMPRESA\":\" \",\"CARGO\":\" \",\"FUENTE_INGRESO\":\"P\"}]},\"Mensaje\":\"Ejecución Correcta\"}";
        PersonalInformationNetResponse expectedResponse = Util.stringToObject(result, PersonalInformationNetResponse.class);

        PersonalInformationNetRequest requestMapperMock = PersonalInformationNetRequestFixture.withDefault();
        PersonalResponse responseMock = GetPersonalInformationResponseFixture.withDefault();

        Mockito.when(mapper.mapperRequest(any())).thenReturn(requestMapperMock);
        Mockito.when(mapper.convertResponse(any())).thenReturn(responseMock);

        String jsonResponse = Util.objectToString(expectedResponse);
        stubFor(post(anyUrl())
                .willReturn(okJson(jsonResponse)));

        // Act
        PersonalResponse response = personalInformationNetProvider.getPersonalInformation("123", map);

        // Assert
        assertNotNull(response);
        assertEquals(responseMock, response);
    }
}
