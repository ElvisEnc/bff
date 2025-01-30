package bg.com.bo.bff.services.implementations.v1;

import bg.com.bo.bff.application.dtos.request.softtoken.SoftTokenCodeEnrollmentRequest;
import bg.com.bo.bff.application.dtos.request.softtoken.SoftTokenCodeEnrollmentRequestFixture;
import bg.com.bo.bff.application.dtos.response.generic.GenericResponse;
import bg.com.bo.bff.application.dtos.response.softtoken.SoftTokenDataEnrollmentResponse;
import bg.com.bo.bff.application.dtos.response.softtoken.SoftTokenQuestionEnrollmentResponse;
import bg.com.bo.bff.application.dtos.response.softtoken.SoftTokenWelcomeResponse;
import bg.com.bo.bff.application.exceptions.GenericException;
import bg.com.bo.bff.mappings.providers.softtoken.SoftTokenMapper;
import bg.com.bo.bff.providers.dtos.response.softtoken.SoftTokenMWResponseFixture;
import bg.com.bo.bff.providers.dtos.response.softtoken.mw.SoftTokenDataEnrollmentMWResponse;
import bg.com.bo.bff.providers.dtos.response.softtoken.mw.SoftTokenQuestionEnrollmentMWResponse;
import bg.com.bo.bff.providers.dtos.response.softtoken.mw.SoftTokenEnrollmentMWResponse;
import bg.com.bo.bff.providers.dtos.response.softtoken.mw.SoftTokenWelcomeMWResponse;
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
        assertEquals(SoftTokenMiddlewareResponse.NOT_ENROLLMENT.getMessage(), response.getMessage());
        verify(provider).getValidationEnrollment(any());
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
    void givenValidDataWhenPostCodeEnrollmentResponseNull() throws IOException {
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
    void givenValidDataWhenPostCodeEnrollmentResponse() throws IOException {
        //Arrange
        SoftTokenCodeEnrollmentRequest request = SoftTokenCodeEnrollmentRequestFixture.withDefault();
        SoftTokenEnrollmentMWResponse expectedResponse = SoftTokenMWResponseFixture.withDefaultError();
        when(provider.postCodeEnrollment(any())).thenReturn(expectedResponse);

        //Act
        GenericException exception = assertThrows(GenericException.class, () ->
                service.postCodeEnrollment("123", request)
        );
        //Assert
        assertEquals("DATA_NOT_FOUND", exception.getCode());
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
        assertEquals("BAD_REQUEST", exception.getCode());
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
        assertEquals("BAD_REQUEST", exception.getCode());
    }
}
