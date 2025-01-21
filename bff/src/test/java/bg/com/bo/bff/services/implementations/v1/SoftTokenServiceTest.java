package bg.com.bo.bff.services.implementations.v1;

import bg.com.bo.bff.application.dtos.response.loans.ListLoansResponse;
import bg.com.bo.bff.application.dtos.response.softtoken.SoftTokenDataEnrollmentResponse;
import bg.com.bo.bff.application.dtos.response.softtoken.SoftTokenQuestionEnrollmentResponse;
import bg.com.bo.bff.application.dtos.response.softtoken.SoftTokenValidationEnrollmentResponse;
import bg.com.bo.bff.application.dtos.response.softtoken.SoftTokenWelcomeResponse;
import bg.com.bo.bff.mappings.providers.softtoken.SoftTokenMapper;
import bg.com.bo.bff.providers.dtos.response.loans.mw.ListLoansMWResponse;
import bg.com.bo.bff.providers.dtos.response.loans.mw.LoansMWResponseFixture;
import bg.com.bo.bff.providers.dtos.response.softtoken.SoftTokenMWResponseFixture;
import bg.com.bo.bff.providers.dtos.response.softtoken.mw.SoftTokenDataEnrollmentMWResponse;
import bg.com.bo.bff.providers.dtos.response.softtoken.mw.SoftTokenQuestionEnrollmentMWResponse;
import bg.com.bo.bff.providers.dtos.response.softtoken.mw.SoftTokenValidationEnrollmentMWResponse;
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
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
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
        SoftTokenValidationEnrollmentMWResponse expectedResponse = SoftTokenMWResponseFixture.withDefaultValidate();
        when(provider.getValidationEnrollment(any())).thenReturn(expectedResponse);

        //Act
        SoftTokenValidationEnrollmentResponse response = service.getValidationEnrollment("1234");

        assertNotNull(response);
        assertEquals(SoftTokenMiddlewareResponse.ENROLLMENT.getMessage(), response.getStatus());
        verify(provider).getValidationEnrollment(any());
        verify(mapper).convertResponse(any(SoftTokenValidationEnrollmentMWResponse.class));
    }

    @Test
    void givenValidDataWhenGetValidationNotEnrollment() throws IOException {
        //Arrange
        SoftTokenValidationEnrollmentMWResponse expectedResponse = SoftTokenMWResponseFixture.withDefaultNotValidate();
        when(provider.getValidationEnrollment(any())).thenReturn(expectedResponse);

        //Act
        SoftTokenValidationEnrollmentResponse response = service.getValidationEnrollment("1234");

        assertNotNull(response);
        assertEquals(SoftTokenMiddlewareResponse.NOT_ENROLLMENT.getMessage(), response.getStatus());
        verify(provider).getValidationEnrollment(any());
        verify(mapper).convertResponse(any(SoftTokenValidationEnrollmentMWResponse.class));
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

}
