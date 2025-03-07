package bg.com.bo.bff.services.implementations.v1;

import bg.com.bo.bff.application.dtos.request.softtoken.*;
import bg.com.bo.bff.application.dtos.response.generic.GenericResponse;
import bg.com.bo.bff.application.dtos.response.softtoken.*;
import bg.com.bo.bff.application.exceptions.GenericException;
import bg.com.bo.bff.mappings.providers.softtoken.SoftTokenMapper;
import bg.com.bo.bff.providers.dtos.response.softtoken.SoftTokenMWResponseFixture;
import bg.com.bo.bff.providers.dtos.response.softtoken.mw.*;
import bg.com.bo.bff.providers.interfaces.ISoftTokenProvider;
import bg.com.bo.bff.providers.models.enums.middleware.softtoken.SoftTokenMiddlewareResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SoftTokenServiceTest {
    @InjectMocks
    private SoftTokenService service;
    @Mock
    private ISoftTokenProvider provider;
    @Spy
    private SoftTokenMapper mapper = new SoftTokenMapper();

    @Test
    void givenValidDataWhenGetWelcome() throws IOException {
        //Arrange
        SoftTokenWelcomeMWResponse expectedResponse = SoftTokenMWResponseFixture.withDefaultWelcome();
        when(provider.getWelcomeMessage(any())).thenReturn(expectedResponse);

        //Act
        SoftTokenWelcomeResponse response = service.getWelcomeMessage("1234");

        assertNotNull(response);
        assertThat(response).usingRecursiveComparison().isEqualTo(expectedResponse);
        verify(provider).getWelcomeMessage(any());
        verify(mapper).convertResponse(any(SoftTokenWelcomeMWResponse.class));
    }

    @Test
    void givenValidDataWhenGetDataEnrollment() throws IOException {
        //Arrange
        SoftTokenDataEnrollmentMWResponse expectedResponse = SoftTokenMWResponseFixture.withDefaultDataEnrollment();
        when(provider.getDataEnrollment(any())).thenReturn(expectedResponse);

        //Act
        SoftTokenDataEnrollmentResponse response = service.getDataEnrollment("1234");

        assertNotNull(response);
        verify(provider).getDataEnrollment(any());
        verify(mapper).convertResponse(any(SoftTokenDataEnrollmentMWResponse.class));
    }

    @Test
    void givenValidDataWhenGetDataEnrollmentNull() throws IOException {
        //Arrange
        SoftTokenDataEnrollmentMWResponse expectedResponse = SoftTokenMWResponseFixture.withDefaultDataEnrollmentNull();
        when(provider.getDataEnrollment(any())).thenReturn(expectedResponse);

        //Act
        SoftTokenDataEnrollmentResponse response = service.getDataEnrollment("1234");

        assertNotNull(response);
        verify(provider).getDataEnrollment(any());
        verify(mapper).convertResponse(any(SoftTokenDataEnrollmentMWResponse.class));
    }

    @Test
    void givenValidDataWhenGetQuestionEnrollment() throws IOException {
        //Arrange
        SoftTokenQuestionEnrollmentMWResponse expectedResponse = SoftTokenMWResponseFixture.withDefaultQuestion();
        when(provider.getQuestionEnrollment(any())).thenReturn(expectedResponse);

        //Act
        List<SoftTokenQuestionEnrollmentResponse> response = service.getQuestionEnrollment("1234");

        assertNotNull(response);
        verify(provider).getQuestionEnrollment(any());
        verify(mapper).convertResponse(any(SoftTokenQuestionEnrollmentMWResponse.class));
    }

    @Test
    void givenValidDataWhenGetQuestionEnrollmentResponseNull() throws IOException {
        //Arrange
        SoftTokenQuestionEnrollmentMWResponse expectedResponse = SoftTokenMWResponseFixture.withDefaultQuestionNull();
        when(provider.getQuestionEnrollment(any())).thenReturn(expectedResponse);

        //Act
        List<SoftTokenQuestionEnrollmentResponse> response = service.getQuestionEnrollment("123");

        //Assert
        assertNotNull(response);
        verify(provider).getQuestionEnrollment(any());
        verify(mapper).convertResponse(expectedResponse);
    }

    @Test
    void givenValidDataWhenGetValidationEnrollment() throws IOException {
        //Arrange
        SoftTokenEnrollmentMWResponse expectedResponse = SoftTokenMWResponseFixture.withDefaultValidate();
        when(provider.getValidationEnrollment(any())).thenReturn(expectedResponse);

        //Act
        GenericResponse response = service.getValidationEnrollment("1234");

        assertNotNull(response);
        assertEquals(SoftTokenMiddlewareResponse.ENROLLMENT.getMessage(), response.getMessage());
        verify(provider).getValidationEnrollment(any());
    }

    @Test
    void givenValidDataWhenGetValidationNotEnrollment() throws IOException {
        //Arrange
        SoftTokenEnrollmentMWResponse expectedResponse = SoftTokenMWResponseFixture.withDefaultNotValidate();
        when(provider.getValidationEnrollment(any())).thenReturn(expectedResponse);

        //Act
        GenericResponse response = service.getValidationEnrollment("1234");

        assertNotNull(response);
        assertEquals(SoftTokenMiddlewareResponse.NOT_ENROLLED.getMessage(), response.getMessage());
        verify(provider).getValidationEnrollment(any());
    }


    @Test
    void givenValidDataWhenPostCodeEnrollmentResponse() throws IOException {
        //Arrange
        SoftTokenCodeEnrollmentRequest request = SoftTokenCodeEnrollmentRequestFixture.withDefault();
        SoftTokenEnrollmentMWResponse expectedResponse = SoftTokenMWResponseFixture.withDefaultNotValidate();
        when(provider.postCodeEnrollment(any())).thenReturn(expectedResponse);

        //Act
        GenericResponse response = service.postCodeEnrollment("123", request);

        //Assert
        assertNotNull(response);
        verify(provider).postCodeEnrollment(any());
    }

    @Test
    void givenValidDataWhenPostCodeEnrollmentResponseError() throws IOException {
        //Arrange
        SoftTokenCodeEnrollmentRequest request = SoftTokenCodeEnrollmentRequestFixture.withDefault();
        SoftTokenEnrollmentMWResponse expectedResponse = SoftTokenMWResponseFixture.withDefaultError();
        when(provider.postCodeEnrollment(any())).thenReturn(expectedResponse);

        //Act
        GenericException exception = assertThrows(GenericException.class, () ->
                service.postCodeEnrollment("123", request)
        );
        //Assert
        assertEquals("ERROR_PROCEDURE", exception.getCode());
    }

    @Test
    void givenValidDataWhenPostCodeEnrollmentRequestEmpty() {
        //Arrange
        SoftTokenCodeEnrollmentRequest request = SoftTokenCodeEnrollmentRequestFixture.withDefaultNullEmpty();
        // Act
        GenericException exception = assertThrows(GenericException.class, () ->
                service.postCodeEnrollment("123", request)
        );
        //Assert
        assertEquals("SHIPPING_OPTION", exception.getCode());
    }

    @Test
    void givenValidDataWhenPostCodeEnrollmentRequestNull() {
        //Arrange
        SoftTokenCodeEnrollmentRequest request = SoftTokenCodeEnrollmentRequestFixture.withDefaultNull();
        // Act
        GenericException exception = assertThrows(GenericException.class, () ->
                service.postCodeEnrollment("123", request)
        );
        //Assert
        assertEquals("SHIPPING_OPTION", exception.getCode());
    }

    @Test
    void givenValidQuestionResponse() throws IOException {
        //Arrange
        SoftTokenValidationQuestionRequest request = SoftTokenCodeEnrollmentRequestFixture.withDefaultValidationQuestion();
        SoftTokenEnrollmentMWResponse expectedResponse = SoftTokenMWResponseFixture.withDefaultNotValidate();
        when(provider.validateQuestionSecurity(any())).thenReturn(expectedResponse);

        //Act
        GenericResponse response = service.validateQuestionSecurity("123", "iphone", request);

        //Assert
        assertNotNull(response);
        verify(provider).validateQuestionSecurity(any());
    }

    @Test
    void givenQuestionResponseError() throws IOException {
        //Arrange
        SoftTokenValidationQuestionRequest request = SoftTokenCodeEnrollmentRequestFixture.withDefaultValidationQuestion();
        SoftTokenEnrollmentMWResponse expectedResponse = SoftTokenMWResponseFixture.withDefaultError();
        when(provider.validateQuestionSecurity(any())).thenReturn(expectedResponse);

        //Act
        GenericException exception = assertThrows(GenericException.class, () ->
                service.validateQuestionSecurity("123", "iphone", request)
        );
        //Assert
        assertEquals("ERROR_PROCEDURE", exception.getCode());
    }

    @Test
    void givenParametersResponse() throws IOException {
        //Arrange
        SoftTokenObtainParametersMWResponse expectedResponse = SoftTokenMWResponseFixture.withDefaultParameter();
        when(provider.getParameters(any())).thenReturn(expectedResponse);

        //Act
        SoftTokenObtainParametersResponse response = service.getParameters("123");

        //Assert
        assertNotNull(response);
        verify(provider).getParameters(any());
    }

    @Test
    void givenRegistrationTokenResponse() throws IOException {
        //Arrange
        SoftTokenCodeTokenRequest request = SoftTokenCodeEnrollmentRequestFixture.withDefaultToken();
        SoftTokenCodeTokenMWResponse expectedResponse = SoftTokenMWResponseFixture.withDefaultCod000();
        when(provider.postRegistrationToken(any())).thenReturn(expectedResponse);

        //Act
        GenericResponse response = service.postRegistrationToken("123", request);

        //Assert
        assertNotNull(response);
        verify(provider).postRegistrationToken(any());
    }

    @Test
    void givenRegistrationTokenError() throws IOException {
        //Arrange
        SoftTokenCodeTokenRequest request = SoftTokenCodeEnrollmentRequestFixture.withDefaultToken();
        SoftTokenCodeTokenMWResponse expectedResponse = SoftTokenMWResponseFixture.withDefaultErrorToken();
        when(provider.postRegistrationToken(any())).thenReturn(expectedResponse);

        //Act
        GenericException exception = assertThrows(GenericException.class, () ->
                service.postRegistrationToken("123", request)
        );
        //Assert
        assertEquals("ERROR_PROCEDURE", exception.getCode());
    }

    @Test
    void givenRegistrationValidationResponsePINDIG003() throws IOException {
        //Arrange
        SoftTokenCodeTokenMWResponse expectedResponse = SoftTokenMWResponseFixture.withDefaultPINDIG003();
        when(provider.getRegistrationValidation(any())).thenReturn(expectedResponse);

        //Act
        GenericResponse response = service.getRegistrationValidation("123");

        //Assert
        assertNotNull(response);
        verify(provider).getRegistrationValidation(any());
    }

    @Test
    void givenRegistrationValidationResponsePINDIG002() throws IOException {
        //Arrange
        SoftTokenCodeTokenMWResponse expectedResponse = SoftTokenMWResponseFixture.withDefaultPINDIG002();
        when(provider.getRegistrationValidation(any())).thenReturn(expectedResponse);

        //Act
        GenericResponse response = service.getRegistrationValidation("123");

        //Assert
        assertNotNull(response);
        verify(provider).getRegistrationValidation(any());
    }

    @Test
    void givenTokenGenerateResponse() throws IOException {
        //Arrange
        SoftTokenGenerateTokenMWResponse expectedResponse = SoftTokenMWResponseFixture.withDefaultGenerateToken();
        when(provider.postTokenGenerate(any())).thenReturn(expectedResponse);

        //Act
        SoftTokenGenerateTokenResponse response = service.postTokenGenerate("123");

        //Assert
        assertNotNull(response);
        verify(provider).postTokenGenerate(any());
    }

    @Test
    void givenEnrollmentResponse() throws IOException {
        //Arrange
        SoftTokenEnrollmentRequest request = SoftTokenCodeEnrollmentRequestFixture.withDefaultEnrollmentST();
        SoftTokenCodeTokenMWResponse expectedResponse = SoftTokenMWResponseFixture.withDefaultCod000();
        when(provider.postEnrollment(any())).thenReturn(expectedResponse);

        //Act
        GenericResponse response = service.postEnrollment("123", "MK-0215", request);

        //Assert
        assertNotNull(response);
        verify(provider).postEnrollment(any());
    }

    @Test
    void givenEnrollmentError() throws IOException {
        //Arrange
        SoftTokenEnrollmentRequest request = SoftTokenCodeEnrollmentRequestFixture.withDefaultEnrollmentST();
        SoftTokenCodeTokenMWResponse expectedResponse = SoftTokenMWResponseFixture.withDefaultErrorToken();
        when(provider.postEnrollment(any())).thenReturn(expectedResponse);

        //Act
        GenericException exception = assertThrows(GenericException.class, () ->
                service.postEnrollment("123", "MK-0215", request)
        );
        //Assert
        assertEquals("ERROR_PROCEDURE", exception.getCode());
    }

    @Test
    void givenValidationTokenResponse() throws IOException {
        //Arrange
        SoftTokenCodeTokenRequest request = SoftTokenCodeEnrollmentRequestFixture.withDefaultTokenST();
        SoftTokenCodeTokenMWResponse expectedResponse = SoftTokenMWResponseFixture.withDefaultCod000();
        when(provider.validationToken(any())).thenReturn(expectedResponse);

        //Act
        GenericResponse response = service.validationToken("123", request);

        //Assert
        assertNotNull(response);
        verify(provider).validationToken(any());
    }

    @Test
    void givenValidationTokenError() throws IOException {
        //Arrange
        SoftTokenCodeTokenRequest request = SoftTokenCodeEnrollmentRequestFixture.withDefaultTokenST();
        SoftTokenCodeTokenMWResponse expectedResponse = SoftTokenMWResponseFixture.withDefaultErrorToken();
        when(provider.validationToken(any())).thenReturn(expectedResponse);

        //Act
        GenericException exception = assertThrows(GenericException.class, () ->
                service.validationToken("123", request)
        );
        //Assert
        assertEquals("ERROR_PROCEDURE", exception.getCode());
    }
}
