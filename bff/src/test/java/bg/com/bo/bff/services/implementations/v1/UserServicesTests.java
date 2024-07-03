package bg.com.bo.bff.services.implementations.v1;

import bg.com.bo.bff.application.dtos.request.user.ChangePasswordRequest;
import bg.com.bo.bff.application.dtos.request.user.UpdateBiometricsRequest;
import bg.com.bo.bff.application.dtos.request.user.UserRequestFixture;
import bg.com.bo.bff.application.dtos.request.user.UpdateDataUserRequest;
import bg.com.bo.bff.application.dtos.response.generic.GenericResponse;
import bg.com.bo.bff.application.dtos.response.user.apiface.DepartmentsResponse;
import bg.com.bo.bff.application.dtos.response.user.apiface.DistrictsResponse;
import bg.com.bo.bff.application.dtos.response.user.*;
import bg.com.bo.bff.application.exceptions.GenericException;
import bg.com.bo.bff.application.exceptions.HandledException;
import bg.com.bo.bff.commons.converters.ChangePasswordErrorResponseConverter;
import bg.com.bo.bff.commons.enums.AppError;
import bg.com.bo.bff.commons.enums.DeviceMW;
import bg.com.bo.bff.commons.utils.Util;
import bg.com.bo.bff.providers.dtos.request.personal.information.ApiPersonalInformationNetRequest;
import bg.com.bo.bff.providers.dtos.request.personal.information.PersonalInformationNetRequestFixture;
import bg.com.bo.bff.providers.dtos.response.apiface.DepartmentsNetResponse;
import bg.com.bo.bff.providers.dtos.response.apiface.DistrictsNetResponse;
import bg.com.bo.bff.providers.dtos.response.login.mw.LoginMWResponseFixture;
import bg.com.bo.bff.providers.dtos.response.personal.information.PersonalInformationNetResponseFixture;
import bg.com.bo.bff.providers.dtos.response.login.mw.BiometricStatusMWResponse;
import bg.com.bo.bff.providers.dtos.response.personal.information.PersonalInformationNetResponse;
import bg.com.bo.bff.providers.interfaces.IApiFaceNetProvider;
import bg.com.bo.bff.providers.interfaces.ILoginMiddlewareProvider;
import bg.com.bo.bff.providers.interfaces.IPersonalInformationNetProvider;
import bg.com.bo.bff.mappings.providers.apiface.IApiFaceMapper;
import bg.com.bo.bff.mappings.providers.information.IPersonalInformationMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServicesTests {
    @Mock
    private ILoginMiddlewareProvider provider;
    @Mock
    private IPersonalInformationNetProvider personalInformationNetProvider;
    @Mock
    private IPersonalInformationMapper iPersonalInformationMapper;
    @Mock
    private IApiFaceNetProvider apiFaceNetProvider;
    @Mock
    private IApiFaceMapper iApiFaceMapper;
    @InjectMocks
    private UserService service;
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
        this.service = new UserService(provider, personalInformationNetProvider, iPersonalInformationMapper, apiFaceNetProvider, iApiFaceMapper);
    }

    @Test
    void givenValidDataWhenChangePasswordThenReturnOk() throws IOException {
        // Arrange
        ChangePasswordRequest changePasswordRequest = new ChangePasswordRequest();
        changePasswordRequest.setOldPassword("123456");
        changePasswordRequest.setNewPassword("ab1234");
        String rolePersonId = "1";
        String personId = "999";

        GenericResponse expectedResponse = new GenericResponse();
        expectedResponse.setCode("SUCCESS");
        expectedResponse.setMessage("Satisfactorio");

        when(provider.changePassword(any(), any(), any(), any())).thenReturn(expectedResponse);

        // Act
        GenericResponse response = service.changePassword(personId, rolePersonId, changePasswordRequest, new HashMap<>());

        // Assert
        verify(provider).changePassword(any(), any(), any(), any());
        assertEquals(expectedResponse, response);
    }

    @Test
    void givenShortPasswordWhenChangePasswordThenReturnBadRequestException() throws IOException {
        // Arrange
        ChangePasswordRequest changePasswordRequest = new ChangePasswordRequest();
        changePasswordRequest.setOldPassword("123456");
        changePasswordRequest.setNewPassword("ab12");

        String rolePersonId = "1";
        String personId = "999";

        HandledException expectedResponse = new HandledException(ChangePasswordErrorResponseConverter.ChangePasswordErrorResponse.NOT_VALID_PASSWORD);

        // Act
        Exception exception = assertThrows(Exception.class, () -> service.changePassword(personId, rolePersonId, changePasswordRequest, new HashMap<>()));

        // Assert
        assertEquals(expectedResponse.getCode(), ((HandledException) exception).getCode());
        assertEquals(expectedResponse.getMessage(), exception.getMessage());
    }

    @Test
    void givenLongPasswordWhenChangePasswordThenReturnBadRequestException() {
        // Arrange
        ChangePasswordRequest changePasswordRequest = new ChangePasswordRequest();
        changePasswordRequest.setOldPassword("123456");
        changePasswordRequest.setNewPassword("123456789012345a");

        String rolePersonId = "1";
        String personId = "999";

        HandledException expectedResponse = new HandledException(ChangePasswordErrorResponseConverter.ChangePasswordErrorResponse.NOT_VALID_PASSWORD);

        // Act
        Exception exception = assertThrows(Exception.class, () -> service.changePassword(personId, rolePersonId, changePasswordRequest, new HashMap<>()));

        // Assert
        assertEquals(expectedResponse.getCode(), ((HandledException) exception).getCode());
        assertEquals(expectedResponse.getMessage(), exception.getMessage());
    }

    @Test
    void givenSamePasswordWhenChangePasswordThenReturnBadRequestException() {
        // Arrange
        ChangePasswordRequest changePasswordRequest = new ChangePasswordRequest();
        changePasswordRequest.setOldPassword("ab1234");
        changePasswordRequest.setNewPassword("ab1234");

        String rolePersonId = "1";
        String personId = "999";

        HandledException expectedResponse = new HandledException(ChangePasswordErrorResponseConverter.ChangePasswordErrorResponse.SAME_PASSWORD);

        // Act
        Exception exception = assertThrows(Exception.class, () -> service.changePassword(personId, rolePersonId, changePasswordRequest, new HashMap<>()));

        // Assert
        assertEquals(expectedResponse.getCode(), ((HandledException) exception).getCode());
        assertEquals(expectedResponse.getMessage(), exception.getMessage());
    }

    @Test
    void givenPasswordNotContainsLetterWhenChangePasswordThenReturnBadRequestException() {
        // Arrange
        ChangePasswordRequest changePasswordRequest = new ChangePasswordRequest();
        changePasswordRequest.setOldPassword("ab1234");
        changePasswordRequest.setNewPassword("123456");

        String rolePersonId = "1";
        String personId = "999";

        HandledException expectedResponse = new HandledException(ChangePasswordErrorResponseConverter.ChangePasswordErrorResponse.NOT_VALID_PASSWORD);

        // Act
        Exception exception = assertThrows(Exception.class, () -> service.changePassword(personId, rolePersonId, changePasswordRequest, new HashMap<>()));

        // Assert
        assertEquals(expectedResponse.getCode(), ((HandledException) exception).getCode());
        assertEquals(expectedResponse.getMessage(), exception.getMessage());
    }

    @Test
    void givenPasswordNotContainsNumberWhenChangePasswordThenReturnBadRequestException() {
        // Arrange
        ChangePasswordRequest changePasswordRequest = new ChangePasswordRequest();
        changePasswordRequest.setOldPassword("ab1234");
        changePasswordRequest.setNewPassword("abcdef");

        String rolePersonId = "1";
        String personId = "999";

        HandledException expectedResponse = new HandledException(ChangePasswordErrorResponseConverter.ChangePasswordErrorResponse.NOT_VALID_PASSWORD);

        // Act
        Exception exception = assertThrows(Exception.class, () -> service.changePassword(personId, rolePersonId, changePasswordRequest, new HashMap<>()));

        // Assert
        assertEquals(expectedResponse.getCode(), ((HandledException) exception).getCode());
        assertEquals(expectedResponse.getMessage(), exception.getMessage());
    }

    @Test
    void givenValidDataWhenGetContactInformation() throws IOException {
        // Arrange
        ContactResponse expected = UserResponseFixture.withDefaultContactResponse();
        when(provider.getContactInfo()).thenReturn(expected);

        // Act
        ContactResponse response = service.getContactInfo();

        // Assert
        verify(provider).getContactInfo();
        assertEquals(expected, response);
    }

    @Test
    void givenValidDataWhenGetPersonalInformation() throws IOException {
        // Arrange
        String result = "{\"CodigoError\":\"COD000\",\"Datos\":{\"cur_datosClienteGanasueldo\":[{\"NUMEROPERSONAFISICA\":1487723,\"FECHAULTACTUALIZACION\":null,\"NOMBRECOMPLETO\":\"PERSONA NATURAL\",\"ESTADOCIVIL\":\"S\",\"SEXO\":\"M\",\"CALLE\":\"LAS LOMAS\",\"NUMEROPUERTA\":\"SN\",\"PISO\":0,\"CIUDAD\":\"SANTA CRUZ\",\"DEPARTAMENTO\":\"SANTA CRUZ\",\"COD_DEPARTAMENTO\":7,\"BARRIOZONA\":\"LAS LOMAS\",\"EMAIL\":\"rb@bg.com\",\"CELULAR\":\"77653520\",\"COD_BARRIO\":0,\"COD_CALLE\":0,\"COD_CIUDAD\":1,\"APELLIDOESPOSO\":\" \",\"USA_APELLIDOESPOSO\":\"N\",\"REFERENCIADOMICILIO\":\" \",\"OFICINA\":\" \",\"ZONA\":1,\"NOMBRE_CONYUGUE\":\" \",\"APARTAMENTO\":\" \",\"TELEFONOS\":\" \",\"FECHAACTUALIZACION\":\"  \",\"NIVEL_INGRESOS\":null,\"ACTIVIDAD_ECONOMICA\":93099,\"EMPLEADO_BANCO\":\"1\",\"COORDENADAS\":\" \"}],\"cur_referenciasPersonaFisica\":[{\"NOMBRE\":\"INGRID CAROLA SAAVEDRA MEDIN\",\"TELEFONOS\":\"78529352\",\"RELACION\":1,\"TIPOREFERENCIA\":\"P\",\"TIPO_PERSONA\":\"F\",\"ORDINAL\":0}],\"cur_actividadEconomica\":[{\"EMPRESA\":\" \",\"CARGO\":\" \",\"FUENTE_INGRESO\":\"P\"}]},\"Mensaje\":\"Ejecución Correcta\"}";
        PersonalInformationNetResponse expectedResponse = Util.stringToObject(result, PersonalInformationNetResponse.class);
        PersonalResponse expected = UserResponseFixture.withDefaultPersonalResponse();
        ApiPersonalInformationNetRequest requestMapperMock = PersonalInformationNetRequestFixture.withDefaultApiPersonalInformationNetRequest();

        when(iPersonalInformationMapper.mapperRequest(any())).thenReturn(requestMapperMock);
        when(personalInformationNetProvider.getPersonalInformation(requestMapperMock, map)).thenReturn(expectedResponse);
        when(iPersonalInformationMapper.convertRequest(expectedResponse)).thenReturn(expected);

        // Act
        PersonalResponse response = service.getPersonalInformation("123", map);

        // Assert
        verify(personalInformationNetProvider).getPersonalInformation(requestMapperMock, map);
        assertNotNull(response);
        assertEquals(expected, response);
    }

    @Test
    void givenValidPersonIdWhenGetBiometricStatus() throws IOException {
        // Arrange
        BiometricStatusMWResponse responseExpected = LoginMWResponseFixture.withDefaultBiometricStatusMWResponse();
        when(provider.getBiometricsMW(any(), any())).thenReturn(responseExpected);

        // Act
        BiometricsResponse response = service.getBiometrics(123, new HashMap<>());

        // Assert
        verify(provider).getBiometricsMW(any(), any());
        assertEquals(UserResponseFixture.withDefaultBiometricsResponse(), response);
    }

    @Test
    void givenPersonIdWhenUpdateBiometricThenResponseExpected() throws IOException {
        // Arrange
        UpdateBiometricsResponse responseExpected = UserResponseFixture.withDefaultUpdateBiometricsResponse();
        UpdateBiometricsRequest request = UserRequestFixture.withDefaultUpdateBiometricsRequest();
        when(provider.updateBiometricsMW(any(), any(), any())).thenReturn(responseExpected);

        // Act
        UpdateBiometricsResponse response = service.updateBiometrics(123, request, new HashMap<>());

        // Assert
        verify(provider).updateBiometricsMW(any(), any(), any());
        assertEquals(UserResponseFixture.withDefaultUpdateBiometricsResponse(), response);
    }

    @Test
    void givenStatusNullWhenUpdateBiometricThenResponseBadRequest() throws IOException {
        // Arrange
        UpdateBiometricsRequest request = UpdateBiometricsRequest.builder().build();
        Map<String, String> map = new HashMap<>();

        // Act
        GenericException exception = assertThrows(GenericException.class, () -> {
            service.updateBiometrics(123, request, map);
        });

        // Assert
        assertTrue(exception.getCode().contains("BAD_REQUEST"));
    }

    @Test
    void givenPersonIdWhenGetEconomicActivityThenResponseExpected() throws IOException {
        // Arrange
        EconomicActivityResponse responseExpected = UserResponseFixture.withDefaultEconomicActivityResponse();
        when(personalInformationNetProvider.getEconomicalActivity(any())).thenReturn(responseExpected);

        // Act
        EconomicActivityResponse response = service.getEconomicActivity(123, new HashMap<>());

        // Assert
        verify(personalInformationNetProvider).getEconomicalActivity(any());
        assertEquals(UserResponseFixture.withDefaultEconomicActivityResponse(), response);
    }

    @Test
    void givenValidDataWhenGetDepartments() throws IOException {
        // Arrange
        DepartmentsNetResponse expectedNet = PersonalInformationNetResponseFixture.withDefaultDepartmentsNetResponse();
        DepartmentsResponse expected = UserResponseFixture.withDefaultDepartmentsResponse();

        Mockito.when(apiFaceNetProvider.getDepartments(Mockito.any())).thenReturn(expectedNet);
        Mockito.when(iApiFaceMapper.mapToDepartmentsResponse(expectedNet)).thenReturn(expected);

        // Act
        DepartmentsResponse response = service.getDepartments(map);

        // Assert
        verify(apiFaceNetProvider).getDepartments(map);
        assertEquals(expected, response);
    }

    @Test
    void givenDepartmentIdWhenGetDistrictsThenReturnSuccess() throws IOException {
        // Arrange
        String departmentId = "5";
        DistrictsNetResponse expectedNetResponse = PersonalInformationNetResponseFixture.withDefaultDistrictsNetResponse();
        DistrictsResponse expectedResponse = UserResponseFixture.withDefaultDistrictsResponse();

        Mockito.when(personalInformationNetProvider.getDistricts(Mockito.any(), Mockito.any())).thenReturn(expectedNetResponse);
        Mockito.when(iPersonalInformationMapper.mapToDistrictsResponse(expectedNetResponse)).thenReturn(expectedResponse);

        // Act
        DistrictsResponse response = service.getDistricts(departmentId, map);

        // Assert
        Assertions.assertNotNull(response);
        assertEquals(expectedResponse, response);
    }

    @Test
    void whenGetMaritalStatusThenResponseExpected() {
        // Arrange
        MaritalStatusResponse responseExpected = UserResponseFixture.withDefaultMaritalStatusResponse();
        when(personalInformationNetProvider.getMaritalStatuses()).thenReturn(responseExpected);

        // Act
        MaritalStatusResponse response = service.getMaritalStatus(new HashMap<>());

        // Assert
        verify(personalInformationNetProvider).getMaritalStatuses();
        assertEquals(UserResponseFixture.withDefaultMaritalStatusResponse(), response);
    }

    @Test
    void givenUpdateDataUserRequestWhenUpdateDataUserThenUpdateDataUserResponse() throws IOException {
        // Assert
        GenericResponse expected = GenericResponse.instance(UpdateDataUserResponse.SUCCESS);
        UpdateDataUserRequest request = UserRequestFixture.withDefaultUpdateDataUserRequest();
        String jsonPersonalInformation = "{\"CodigoError\":\"COD000\",\"Datos\":{\"cur_datosClienteGanasueldo\":[{\"NUMEROPERSONAFISICA\":1487723,\"FECHAULTACTUALIZACION\":null,\"NOMBRECOMPLETO\":\"PERSONA NATURAL\",\"ESTADOCIVIL\":\"S\",\"SEXO\":\"M\",\"CALLE\":\"LAS LOMAS\",\"NUMEROPUERTA\":\"SN\",\"PISO\":0,\"CIUDAD\":\"SANTA CRUZ\",\"DEPARTAMENTO\":\"SANTA CRUZ\",\"COD_DEPARTAMENTO\":7,\"BARRIOZONA\":\"LAS LOMAS\",\"EMAIL\":\"rb@bg.com\",\"CELULAR\":\"77653520\",\"COD_BARRIO\":0,\"COD_CALLE\":0,\"COD_CIUDAD\":1,\"APELLIDOESPOSO\":\" \",\"USA_APELLIDOESPOSO\":\"N\",\"REFERENCIADOMICILIO\":\" \",\"OFICINA\":\" \",\"ZONA\":1,\"NOMBRE_CONYUGUE\":\" \",\"APARTAMENTO\":\" \",\"TELEFONOS\":\" \",\"FECHAACTUALIZACION\":\"  \",\"NIVEL_INGRESOS\":null,\"ACTIVIDAD_ECONOMICA\":93099,\"EMPLEADO_BANCO\":\"1\",\"COORDENADAS\":\" \"}],\"cur_referenciasPersonaFisica\":[{\"NOMBRE\":\"INGRID CAROLA SAAVEDRA MEDIN\",\"TELEFONOS\":\"78529352\",\"RELACION\":1,\"TIPOREFERENCIA\":\"P\",\"TIPO_PERSONA\":\"F\",\"ORDINAL\":0}],\"cur_actividadEconomica\":[{\"EMPRESA\":\" \",\"CARGO\":\" \",\"FUENTE_INGRESO\":\"P\"}]},\"Mensaje\":\"Ejecución Correcta\"}";
        DistrictsNetResponse expectedNetResponse = PersonalInformationNetResponseFixture.withDefaultDistrictsNetResponse();
        String personId = "1234";
        when(personalInformationNetProvider.getEconomicalActivity(any())).thenReturn(UserResponseFixture.withDefaultEconomicActivity());
        when(personalInformationNetProvider.getPersonalInformation(any(), any())).thenReturn(Util.stringToObject(jsonPersonalInformation, PersonalInformationNetResponse.class));
        when(personalInformationNetProvider.updatePersonalInformation(any(), any())).thenReturn(PersonalInformationNetResponseFixture.withDefaultPersonalUpdateNetResponse());
        when(personalInformationNetProvider.getDistricts(Mockito.any(), Mockito.any())).thenReturn(expectedNetResponse);
        // Act
        GenericResponse actual = service.updateDataUser(personId, request, map);

        //Assert
        assertEquals(expected, actual);
        verify(personalInformationNetProvider).getPersonalInformation(any(), any());
        verify(personalInformationNetProvider).updatePersonalInformation(any(), any());
    }

    @Test
    void givenUpdateDataUserRequestWhenUpdateDataUserThenNotAcceptable() throws IOException {
        // Assert
        UpdateDataUserRequest request = UserRequestFixture.withDefaultUpdateDataUserRequest();
        String jsonPersonalInformation = "{\"CodigoError\":\"COD000\",\"Datos\":{\"cur_datosClienteGanasueldo\":[],\"cur_referenciasPersonaFisica\":[{\"NOMBRE\":\"INGRID CAROLA SAAVEDRA MEDIN\",\"TELEFONOS\":\"78529352\",\"RELACION\":1,\"TIPOREFERENCIA\":\"P\",\"TIPO_PERSONA\":\"F\",\"ORDINAL\":0}],\"cur_actividadEconomica\":[{\"EMPRESA\":\" \",\"CARGO\":\" \",\"FUENTE_INGRESO\":\"P\"}]},\"Mensaje\":\"Ejecución Correcta\"}";
        String personId = "1234";
        when(personalInformationNetProvider.getPersonalInformation(any(), any())).thenReturn(Util.stringToObject(jsonPersonalInformation, PersonalInformationNetResponse.class));

        // Act
        try {
            service.updateDataUser(personId, request, map);
        } catch (GenericException e) {

            //Assert
            assertEquals(e.getMessage(), AppError.NOT_ACCEPTABLE_UPDATE_PERSONAL_INFORMATION.getMessage());
            assertEquals(e.getCode(), AppError.NOT_ACCEPTABLE_UPDATE_PERSONAL_INFORMATION.getCode());
            assertEquals(e.getStatus(), AppError.NOT_ACCEPTABLE_UPDATE_PERSONAL_INFORMATION.getHttpCode());
            verify(personalInformationNetProvider).getPersonalInformation(any(), any());
        }

    }

    @Test
    void givenUpdateDataUserRequestAndSpouseNameIsNullWhenUpdateDataUserThenValidateMarriedPerson() throws IOException {
        // Assert
        GenericResponse expected = GenericResponse.instance(UpdateDataUserResponse.SUCCESS);
        UpdateDataUserRequest request = UserRequestFixture.validateMarriedPersonWhenSpouseNameIsNull();
        String jsonPersonalInformation = "{\"CodigoError\":\"COD000\",\"Datos\":{\"cur_datosClienteGanasueldo\":[{\"NUMEROPERSONAFISICA\":1487723,\"FECHAULTACTUALIZACION\":null,\"NOMBRECOMPLETO\":\"PERSONA NATURAL\",\"ESTADOCIVIL\":\"S\",\"SEXO\":\"M\",\"CALLE\":\"LAS LOMAS\",\"NUMEROPUERTA\":\"SN\",\"PISO\":0,\"CIUDAD\":\"SANTA CRUZ\",\"DEPARTAMENTO\":\"SANTA CRUZ\",\"COD_DEPARTAMENTO\":7,\"BARRIOZONA\":\"LAS LOMAS\",\"EMAIL\":\"rb@bg.com\",\"CELULAR\":\"77653520\",\"COD_BARRIO\":0,\"COD_CALLE\":0,\"COD_CIUDAD\":1,\"APELLIDOESPOSO\":\" \",\"USA_APELLIDOESPOSO\":\"N\",\"REFERENCIADOMICILIO\":\" \",\"OFICINA\":\" \",\"ZONA\":1,\"NOMBRE_CONYUGUE\":\" \",\"APARTAMENTO\":\" \",\"TELEFONOS\":\" \",\"FECHAACTUALIZACION\":\"  \",\"NIVEL_INGRESOS\":null,\"ACTIVIDAD_ECONOMICA\":93099,\"EMPLEADO_BANCO\":\"1\",\"COORDENADAS\":\" \"}],\"cur_referenciasPersonaFisica\":[{\"NOMBRE\":\"INGRID CAROLA SAAVEDRA MEDIN\",\"TELEFONOS\":\"78529352\",\"RELACION\":1,\"TIPOREFERENCIA\":\"P\",\"TIPO_PERSONA\":\"F\",\"ORDINAL\":0}],\"cur_actividadEconomica\":[{\"EMPRESA\":\" \",\"CARGO\":\" \",\"FUENTE_INGRESO\":\"P\"}]},\"Mensaje\":\"Ejecución Correcta\"}";

        String personId = "1234";
        when(personalInformationNetProvider.getPersonalInformation(any(), any())).thenReturn(Util.stringToObject(jsonPersonalInformation, PersonalInformationNetResponse.class));


        // Act
        try {
            service.updateDataUser(personId, request, map);
        } catch (GenericException e) {

            //Assert
            assertEquals(e.getMessage(), AppError.VALIDATE_MARRIED_PERSON.getMessage());
            assertEquals(e.getCode(), AppError.VALIDATE_MARRIED_PERSON.getCode());
            assertEquals(e.getStatus(), AppError.VALIDATE_MARRIED_PERSON.getHttpCode());
            verify(personalInformationNetProvider).getPersonalInformation(any(), any());
        }

    }

    @Test
    void givenUpdateDataUserRequestAndSpouseNameIsBlankWhenUpdateDataUserThenValidateMarriedPerson() throws IOException {
        // Assert
        UpdateDataUserRequest request = UserRequestFixture.validateMarriedPersonWhenSpouseNameIsBlank();
        String jsonPersonalInformation = "{\"CodigoError\":\"COD000\",\"Datos\":{\"cur_datosClienteGanasueldo\":[{\"NUMEROPERSONAFISICA\":1487723,\"FECHAULTACTUALIZACION\":null,\"NOMBRECOMPLETO\":\"PERSONA NATURAL\",\"ESTADOCIVIL\":\"S\",\"SEXO\":\"F\",\"CALLE\":\"LAS LOMAS\",\"NUMEROPUERTA\":\"SN\",\"PISO\":0,\"CIUDAD\":\"SANTA CRUZ\",\"DEPARTAMENTO\":\"SANTA CRUZ\",\"COD_DEPARTAMENTO\":7,\"BARRIOZONA\":\"LAS LOMAS\",\"EMAIL\":\"rb@bg.com\",\"CELULAR\":\"77653520\",\"COD_BARRIO\":0,\"COD_CALLE\":0,\"COD_CIUDAD\":1,\"APELLIDOESPOSO\":\" \",\"USA_APELLIDOESPOSO\":\"N\",\"REFERENCIADOMICILIO\":\" \",\"OFICINA\":\" \",\"ZONA\":1,\"NOMBRE_CONYUGUE\":\" \",\"APARTAMENTO\":\" \",\"TELEFONOS\":\" \",\"FECHAACTUALIZACION\":\"  \",\"NIVEL_INGRESOS\":null,\"ACTIVIDAD_ECONOMICA\":93099,\"EMPLEADO_BANCO\":\"1\",\"COORDENADAS\":\" \"}],\"cur_referenciasPersonaFisica\":[{\"NOMBRE\":\"INGRID CAROLA SAAVEDRA MEDIN\",\"TELEFONOS\":\"78529352\",\"RELACION\":1,\"TIPOREFERENCIA\":\"P\",\"TIPO_PERSONA\":\"F\",\"ORDINAL\":0}],\"cur_actividadEconomica\":[{\"EMPRESA\":\" \",\"CARGO\":\" \",\"FUENTE_INGRESO\":\"P\"}]},\"Mensaje\":\"Ejecución Correcta\"}";

        String personId = "1234";
        when(personalInformationNetProvider.getPersonalInformation(any(), any())).thenReturn(Util.stringToObject(jsonPersonalInformation, PersonalInformationNetResponse.class));

        // Act
        try {
            service.updateDataUser(personId, request, map);
        } catch (GenericException e) {

            //Assert
            assertEquals(e.getMessage(), AppError.VALIDATE_MARRIED_PERSON.getMessage());
            assertEquals(e.getCode(), AppError.VALIDATE_MARRIED_PERSON.getCode());
            assertEquals(e.getStatus(), AppError.VALIDATE_MARRIED_PERSON.getHttpCode());
            verify(personalInformationNetProvider).getPersonalInformation(any(), any());
        }
    }

    @Test
    void givenUpdateDataUserRequestAndHasHusbandLastNameIsSWhenUpdateDataUserThenValidateMarriedPerson() throws IOException {
        // Assert
        UpdateDataUserRequest request = UserRequestFixture.validateMarriedPersonWhenAsHusbandLastNameIsS();
        String jsonPersonalInformation = "{\"CodigoError\":\"COD000\",\"Datos\":{\"cur_datosClienteGanasueldo\":[{\"NUMEROPERSONAFISICA\":1487723,\"FECHAULTACTUALIZACION\":null,\"NOMBRECOMPLETO\":\"PERSONA NATURAL\",\"ESTADOCIVIL\":\"S\",\"SEXO\":\"F\",\"CALLE\":\"LAS LOMAS\",\"NUMEROPUERTA\":\"SN\",\"PISO\":0,\"CIUDAD\":\"SANTA CRUZ\",\"DEPARTAMENTO\":\"SANTA CRUZ\",\"COD_DEPARTAMENTO\":7,\"BARRIOZONA\":\"LAS LOMAS\",\"EMAIL\":\"rb@bg.com\",\"CELULAR\":\"77653520\",\"COD_BARRIO\":0,\"COD_CALLE\":0,\"COD_CIUDAD\":1,\"APELLIDOESPOSO\":\" \",\"USA_APELLIDOESPOSO\":\"N\",\"REFERENCIADOMICILIO\":\" \",\"OFICINA\":\" \",\"ZONA\":1,\"NOMBRE_CONYUGUE\":\" \",\"APARTAMENTO\":\" \",\"TELEFONOS\":\" \",\"FECHAACTUALIZACION\":\"  \",\"NIVEL_INGRESOS\":null,\"ACTIVIDAD_ECONOMICA\":93099,\"EMPLEADO_BANCO\":\"1\",\"COORDENADAS\":\" \"}],\"cur_referenciasPersonaFisica\":[{\"NOMBRE\":\"INGRID CAROLA SAAVEDRA MEDIN\",\"TELEFONOS\":\"78529352\",\"RELACION\":1,\"TIPOREFERENCIA\":\"P\",\"TIPO_PERSONA\":\"F\",\"ORDINAL\":0}],\"cur_actividadEconomica\":[{\"EMPRESA\":\" \",\"CARGO\":\" \",\"FUENTE_INGRESO\":\"P\"}]},\"Mensaje\":\"Ejecución Correcta\"}";

        String personId = "1234";
        when(personalInformationNetProvider.getPersonalInformation(any(), any())).thenReturn(Util.stringToObject(jsonPersonalInformation, PersonalInformationNetResponse.class));

        // Act
        try {
            service.updateDataUser(personId, request, map);
        } catch (GenericException e) {

            //Assert
            assertEquals(e.getMessage(), AppError.VALIDATE_MARRIED_AND_USE_HUSBAND_LAST_NAME_PERSON.getMessage());
            assertEquals(e.getCode(), AppError.VALIDATE_MARRIED_AND_USE_HUSBAND_LAST_NAME_PERSON.getCode());
            assertEquals(e.getStatus(), AppError.VALIDATE_MARRIED_AND_USE_HUSBAND_LAST_NAME_PERSON.getHttpCode());
            verify(personalInformationNetProvider).getPersonalInformation(any(), any());
        }
    }
}
