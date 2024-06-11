package bg.com.bo.bff.providers.implementations;

import bg.com.bo.bff.application.config.HttpClientConfig;
import bg.com.bo.bff.application.dtos.response.GetPersonalInformationResponseFixture;
import bg.com.bo.bff.application.dtos.response.user.*;
import bg.com.bo.bff.application.exceptions.GenericException;
import bg.com.bo.bff.commons.enums.AppError;
import bg.com.bo.bff.commons.enums.DeviceMW;
import bg.com.bo.bff.commons.utils.Util;
import bg.com.bo.bff.models.interfaces.IHttpClientFactory;
import bg.com.bo.bff.providers.dtos.request.PersonalInformationNetRequestFixture;
import bg.com.bo.bff.providers.dtos.request.personal.information.ApiPersonalInformationNetRequest;
import bg.com.bo.bff.providers.dtos.request.personal.information.DistrictsNetRequest;
import bg.com.bo.bff.providers.dtos.response.ErrorMiddlewareProvider;
import bg.com.bo.bff.providers.dtos.response.ErrorMiddlewareProviderFixture;
import bg.com.bo.bff.providers.dtos.response.ProviderNetResponse;
import bg.com.bo.bff.providers.dtos.response.apiface.DistrictsNetResponse;
import bg.com.bo.bff.providers.dtos.request.UpdatePersonalInformationNetRequestFixture;
import bg.com.bo.bff.providers.dtos.request.personal.information.UpdatePersonalInformationNetRequest;
import bg.com.bo.bff.providers.dtos.response.PersonalUpdateNetResponseFixture;
import bg.com.bo.bff.providers.dtos.response.personal.information.PersonalInformationNetResponse;
import bg.com.bo.bff.providers.dtos.response.personal.update.PersonalUpdateNetResponse;
import bg.com.bo.bff.providers.mappings.personal.information.IPersonalInformationMapper;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import java.io.InputStream;
import java.util.List;
import java.util.Map;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@WireMockTest(proxyMode = true, httpPort = 8080)
@ExtendWith(WireMockExtension.class)
@ExtendWith(MockitoExtension.class)
class PersonalInformationNetProviderTest {
    @InjectMocks
    private PersonalInformationNetProvider personalInformationNetProvider;
    @Mock
    private IPersonalInformationMapper mapper;
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
        lenient().when(httpClientFactoryMock.create()).thenReturn(HttpClientBuilder.create().useSystemProperties().build());

        this.personalInformationNetProvider = new PersonalInformationNetProvider(httpClientFactoryMock, mapper);
        ReflectionTestUtils.setField(personalInformationNetProvider, "urlProviderPersonalInformationNet", "http://localhost:8080");
    }

    @Test
    void givenPersonIdWhenGetPersonalInformationThenSuccess() throws IOException {
        // Arrange
        String result = "{\"CodigoError\":\"COD000\",\"Datos\":{\"cur_datosClienteGanasueldo\":[{\"NUMEROPERSONAFISICA\":1487723,\"FECHAULTACTUALIZACION\":null,\"NOMBRECOMPLETO\":\"PERSONA NATURAL\",\"ESTADOCIVIL\":\"S\",\"SEXO\":\"M\",\"CALLE\":\"LAS LOMAS\",\"NUMEROPUERTA\":\"SN\",\"PISO\":0,\"CIUDAD\":\"SANTA CRUZ\",\"DEPARTAMENTO\":\"SANTA CRUZ\",\"COD_DEPARTAMENTO\":7,\"BARRIOZONA\":\"LAS LOMAS\",\"EMAIL\":\"rb@bg.com\",\"CELULAR\":\"77653520\",\"COD_BARRIO\":0,\"COD_CALLE\":0,\"COD_CIUDAD\":1,\"APELLIDOESPOSO\":\" \",\"USA_APELLIDOESPOSO\":\"N\",\"REFERENCIADOMICILIO\":\" \",\"OFICINA\":\" \",\"ZONA\":1,\"NOMBRE_CONYUGUE\":\" \",\"APARTAMENTO\":\" \",\"TELEFONOS\":\" \",\"FECHAACTUALIZACION\":\"  \",\"NIVEL_INGRESOS\":null,\"ACTIVIDAD_ECONOMICA\":93099,\"EMPLEADO_BANCO\":\"1\",\"COORDENADAS\":\" \"}],\"cur_referenciasPersonaFisica\":[{\"NOMBRE\":\"INGRID CAROLA SAAVEDRA MEDIN\",\"TELEFONOS\":\"78529352\",\"RELACION\":1,\"TIPOREFERENCIA\":\"P\",\"TIPO_PERSONA\":\"F\",\"ORDINAL\":0}],\"cur_actividadEconomica\":[{\"EMPRESA\":\" \",\"CARGO\":\" \",\"FUENTE_INGRESO\":\"P\"}]},\"Mensaje\":\"Ejecución Correcta\"}";
        PersonalInformationNetResponse expectedResponse = Util.stringToObject(result, PersonalInformationNetResponse.class);

        ApiPersonalInformationNetRequest requestMapperMock = PersonalInformationNetRequestFixture.withDefault();
        PersonalResponse responseMock = GetPersonalInformationResponseFixture.withDefault();

        when(mapper.mapperRequest(any())).thenReturn(requestMapperMock);
        when(mapper.convertResponse(any())).thenReturn(responseMock);

        String jsonResponse = Util.objectToString(expectedResponse);
        stubFor(post(anyUrl())
                .willReturn(okJson(jsonResponse)));

        // Act
        PersonalResponse response = personalInformationNetProvider.getPersonalInformation("123", map);

        // Assert
        assertNotNull(response);
        assertEquals(responseMock, response);
    }

    @Test
    void givenPersonIdWhenGetEconomicalActivityThenSuccess() throws IOException {
        // Arrange
        ApiPersonalInformationNetRequest requestMapperMock = PersonalInformationNetRequestFixture.withDefault();
        EconomicActivityResponse responseMock = EconomicActivityResponseFixture.withDefault();
        when(mapper.mapperRequest(any())).thenReturn(requestMapperMock);
        when(mapper.convertEconomicActivity(any())).thenReturn(responseMock);

        String resultNet = "{\"CodigoError\":\"COD000\",\"Datos\":[{\"actividadEconomica\":[{\"id\":\"1401\",\"name\":\"Agricultura/Ganaderia\"},{\"id\":\"36990\",\"name\":\"Industriamanofacturera\"},{\"id\":\"45209\",\"name\":\"Construcción\"},{\"id\":\"52399\",\"name\":\"Ventaalpormayorymenor\"},{\"id\":\"70201\",\"name\":\"Serviciosinmobiliarios\"},{\"id\":\"99003\",\"name\":\"Salud,hoteleríayentretenimiento\"},{\"id\":\"60211\",\"name\":\"Transporteycomunicación\"},{\"id\":\"99002\",\"name\":\"Estudiantes\"},{\"id\":\"65191\",\"name\":\"Serviciosdelasentidadesfinancieras\"},{\"id\":\"99003\",\"name\":\"Amasdecasa\"},{\"id\":\"93099\",\"name\":\"Otros\"}]},{\"nivelIngreso\":[{\"OPCIONINTERNA\":\"1\",\"DESCRIPCION\":\"MENOSDE$600\"},{\"OPCIONINTERNA\":\"2\",\"DESCRIPCION\":\"$600-$1200\"},{\"OPCIONINTERNA\":\"3\",\"DESCRIPCION\":\"$1201-$2000\"},{\"OPCIONINTERNA\":\"4\",\"DESCRIPCION\":\"$2001-$5000\"},{\"OPCIONINTERNA\":\"5\",\"DESCRIPCION\":\"MASDE$5000\"}]},{\"fuenteIngreso\":[{\"OPCIONINTERNA\":\"R\",\"DESCRIPCION\":\"Rentista/jubilado\"},{\"OPCIONINTERNA\":\"U\",\"DESCRIPCION\":\"Unipersonal\"},{\"OPCIONINTERNA\":\"S\",\"DESCRIPCION\":\"Sinactividad\"},{\"OPCIONINTERNA\":\"P\",\"DESCRIPCION\":\"Amadecasa/estudiante\"},{\"OPCIONINTERNA\":\"D\",\"DESCRIPCION\":\"Asalariado\"},{\"OPCIONINTERNA\":\"I\",\"DESCRIPCION\":\"Independiente\"},{\"OPCIONINTERNA\":\"V\",\"DESCRIPCION\":\"Inversionista\"}]},{\"cargo\":[{\"CODIGO\":\"AR1\",\"CARGO\":\"GERENTE\"},{\"CODIGO\":\"AR2\",\"CARGO\":\"JEFE\"},{\"CODIGO\":\"AR3\",\"CARGO\":\"ADMINISTRATIVO\"},{\"CODIGO\":\"AR4\",\"CARGO\":\"OPERATIVO\"},{\"CODIGO\":\"AR5\",\"CARGO\":\"FUNCIONARIOPÚBLICO\"}]}],\"Mensaje\":\"ejecutado\"}";
        ProviderNetResponse expectedResponse = Util.stringToObject(resultNet, ProviderNetResponse.class);
        String jsonResponse = Util.objectToString(expectedResponse);
        stubFor(post(anyUrl())
                .willReturn(okJson(jsonResponse)));

        // Act
        EconomicActivityResponse response = personalInformationNetProvider.getEconomicalActivity(123);

        // Assert
        assertNotNull(response);
        assertEquals(responseMock, response);
        verify(httpClientFactoryMock).create();
        verify(mapper).mapperRequest("123");
        verify(mapper).convertEconomicActivity(expectedResponse);
    }

    @Test
    void givenErrorWhenGetEconomicalActivityThenError() throws IOException {
        // Arrange
        ApiPersonalInformationNetRequest requestMapperMock = PersonalInformationNetRequestFixture.withDefault();
        when(mapper.mapperRequest(any())).thenReturn(requestMapperMock);

        String resultErrorNet = "{\"CodigoError\":\"ERROR\",\"Datos\":[],\"Mensaje\":\"ERROR\"}";
        ProviderNetResponse expectedResponse = Util.stringToObject(resultErrorNet, ProviderNetResponse.class);
        String jsonResponse = Util.objectToString(expectedResponse);
        stubFor(post(anyUrl())
                .willReturn(okJson(jsonResponse)));

        // Act
        GenericException exception = assertThrows(GenericException.class, () -> {
            personalInformationNetProvider.getEconomicalActivity(123);
        });

        // Assert
        assertTrue(exception.getCode().contains("ERROR"));
        verify(mapper).mapperRequest("123");
        verify(httpClientFactoryMock).create();
    }


    @Test
    void givenError400WhenGetEconomicalActivityThenError400() throws IOException {
        // Arrange
        ApiPersonalInformationNetRequest requestMapperMock = PersonalInformationNetRequestFixture.withDefault();
        when(mapper.mapperRequest(any())).thenReturn(requestMapperMock);

        ErrorMiddlewareProvider error = ErrorMiddlewareProviderFixture.errorBadRequest();
        stubFor(post(anyUrl())
                .willReturn(aResponse()
                        .withStatus(400)
                        .withBody(Util.objectToString(error))
                )
        );

        // Act
        GenericException exception = assertThrows(GenericException.class, () -> {
            personalInformationNetProvider.getEconomicalActivity(123);
        });

        // Assert
        assertTrue(exception.getCode().contains("BAD_REQUEST"));
        verify(mapper).mapperRequest("123");
        verify(httpClientFactoryMock).create();
    }

    @Test
    void givenError500WhenGetEconomicalActivityThenError500() throws IOException {
        // Arrange
        Mockito.when(httpClientFactoryMock.create()).thenThrow(new RuntimeException("Error al crear cliente HTTP"));

        // Act
        Exception exception = assertThrows(RuntimeException.class, () -> {
            personalInformationNetProvider.getEconomicalActivity(123);
        });

        // Assert
        assertEquals(AppError.DEFAULT.getMessage(), exception.getMessage());
        verify(mapper).mapperRequest("123");
    }

    @Test
    void givenDepartmentIdWhenGetDistrictsThenSuccess() throws IOException {
        // Arrange
        DistrictsNetRequest requestMapperMock = PersonalInformationNetRequestFixture.withDefaultDistricts();

        String resultNet = "{\"CodigoError\":\"COD000\",\"Datos\":{\"cur_localidades\":[{\"DESC_CIUDAD\":\"SUCRE\",\"COD_LOCALIDAD\":1},{\"DESC_CIUDAD\":\"INCAHUASI\",\"COD_LOCALIDAD\":19},{\"DESC_CIUDAD\":\"VILLA MACHARETI\",\"COD_LOCALIDAD\":20},{\"DESC_CIUDAD\":\"MACHARETI\",\"COD_LOCALIDAD\":21},{\"DESC_CIUDAD\":\"EL VILLAR\",\"COD_LOCALIDAD\":22},{\"DESC_CIUDAD\":\"ICLA\",\"COD_LOCALIDAD\":23},{\"DESC_CIUDAD\":\"MOJOCOYA\",\"COD_LOCALIDAD\":24},{\"DESC_CIUDAD\":\"PRESTO\",\"COD_LOCALIDAD\":25},{\"DESC_CIUDAD\":\"TARVITA\",\"COD_LOCALIDAD\":26},{\"DESC_CIUDAD\":\"ALCALA\",\"COD_LOCALIDAD\":27},{\"DESC_CIUDAD\":\"YOTALA\",\"COD_LOCALIDAD\":28}]},\"Mensaje\":\"Ejecución Correcta\"}";
        DistrictsNetResponse expectedResponse = Util.stringToObject(resultNet, DistrictsNetResponse.class);
        String jsonResponse = Util.objectToString(expectedResponse);
        stubFor(post(anyUrl())
                .willReturn(okJson(jsonResponse)));

        // Act
        DistrictsNetResponse response = personalInformationNetProvider.getDistricts(requestMapperMock,map);

        // Assert
        assertNotNull(response);
        assertEquals(expectedResponse, response);
    }

    @Test
    void getMaritalStatusesSuccess() throws IOException {
        // Arrange
        ObjectMapper objectMapper = new ObjectMapper();
        TypeReference<List<MaritalStatus>> typeReference = new TypeReference<>() {};
        InputStream inputStream = TypeReference.class.getResourceAsStream("/files/MaritalStatusResponse.json");
        List<MaritalStatus> expectedMaritalStatusList = objectMapper.readValue(inputStream, typeReference);

        // Act
        MaritalStatusResponse response = personalInformationNetProvider.getMaritalStatuses();

        // Assert
        assertNotNull(response);
        assertEquals(expectedMaritalStatusList, response.getData());
    }

    @Test
    void givenUpdateDataUserRequestWhenUpdateDataUserThenUpdateDataUserResponse() throws IOException {
        //Arrange
        UpdatePersonalInformationNetRequest request = UpdatePersonalInformationNetRequestFixture.withDefault();
        PersonalUpdateNetResponse expectedResponse = PersonalUpdateNetResponseFixture.withDefault();
        String jsonResponse = Util.objectToString(expectedResponse);

        stubFor(post(anyUrl())
                .willReturn(okJson(jsonResponse)));
        //Act
        PersonalUpdateNetResponse response = personalInformationNetProvider.updatePersonalInformation(request, map);

        assertNotNull(response);
        assertEquals(expectedResponse.getMessage(), response.getMessage());
        assertEquals(expectedResponse.getErrorCode(), response.getErrorCode());
    }
}
