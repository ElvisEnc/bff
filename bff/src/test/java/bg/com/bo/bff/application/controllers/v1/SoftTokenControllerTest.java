package bg.com.bo.bff.application.controllers.v1;

import bg.com.bo.bff.application.config.HeadersDataFixture;
import bg.com.bo.bff.application.dtos.request.softtoken.*;
import bg.com.bo.bff.application.dtos.response.generic.GenericResponse;
import bg.com.bo.bff.application.dtos.response.softtoken.*;
import bg.com.bo.bff.commons.utils.Util;
import bg.com.bo.bff.services.interfaces.ISoftTokenService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;


import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class SoftTokenControllerTest {
    private MockMvc mockMvc;
    @Spy
    @InjectMocks
    private SoftTokenController controller;
    @Mock
    private ISoftTokenService service;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(controller)
                .build();

        MockHttpServletRequest mockRequest = HeadersDataFixture.getMockHttpServletRequest();

        controller.setHttpServletRequest(mockRequest);
    }

    @Test
    void givenPersonIdWhenGetWelcomeMessageThenReturnSuccess() throws Exception {
        // Arrange
        SoftTokenWelcomeResponse responseExpected = SoftTokenResponseFixture.withDefaultWelcome();
        when(service.getWelcomeMessage(any())).thenReturn(responseExpected);

        // Act
        String urlGetWelcome = "/api/v1/softtoken/persons/{personId}/welcome";
        MvcResult result = mockMvc.perform(get(urlGetWelcome, "123")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();
        String expected = Util.objectToString(responseExpected);
        String actual = result.getResponse().getContentAsString();

        // Assert
        assertNotNull(result);
        assertEquals(expected, actual);
        verify(service).getWelcomeMessage(any());
    }

    @Test
    void givenPersonIdWhenGetDataEnrollmentThenReturnSuccess() throws Exception {
        // Arrange
        SoftTokenDataEnrollmentResponse responseExpected = SoftTokenResponseFixture.withDefaultDataEnrollment();
        when(service.getDataEnrollment(any())).thenReturn(responseExpected);

        // Act
        String urlGetDataEnrollment = "/api/v1/softtoken/persons/{personId}/enrollment-data";
        MvcResult result = mockMvc.perform(get(urlGetDataEnrollment, "123")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();
        String expected = Util.objectToString(responseExpected);
        String actual = result.getResponse().getContentAsString();

        // Assert
        assertNotNull(result);
        assertEquals(expected, actual);
        verify(service).getDataEnrollment(any());
    }

    @Test
    void givenPersonIdWhenGetQuestionEnrollmentThenReturnSuccess() throws Exception {
        // Arrange
        SoftTokenQuestionEnrollmentResponse response = SoftTokenResponseFixture.withDefaultQuestion();
        List<SoftTokenQuestionEnrollmentResponse> responseExpected = new ArrayList<>();
        responseExpected.add(response);
        when(service.getQuestionEnrollment(any())).thenReturn(responseExpected);

        // Act
        String urlGetQuestionEnrollment = "/api/v1/softtoken/persons/{personId}/enrollment-question";
        MvcResult result = mockMvc.perform(get(urlGetQuestionEnrollment, "123")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();
        String expected = Util.objectToString(responseExpected);
        String actual = result.getResponse().getContentAsString();

        // Assert
        assertNotNull(result);
        assertEquals(expected, actual);
        verify(service).getQuestionEnrollment(any());
    }

    @Test
    void givenPersonIdWhenGetValidateEnrollmentThenReturnSuccess() throws Exception {
        // Arrange
        GenericResponse responseExpected = SoftTokenResponseFixture.withDefaultGeneric();
        when(service.getValidationEnrollment(any())).thenReturn(responseExpected);

        // Act
        String urlGetValidateEnrollment = "/api/v1/softtoken/persons/{personId}/enrollment-validate";
        MvcResult result = mockMvc.perform(get(urlGetValidateEnrollment, "123")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();
        String expected = Util.objectToString(responseExpected);
        String actual = result.getResponse().getContentAsString();

        // Assert
        assertNotNull(result);
        assertEquals(expected, actual);
        verify(service).getValidationEnrollment(any());
    }


    @Test
    void givenPersonIdWhenPostCodeEnrollmentThenReturnSuccess() throws Exception {
        // Arrange
        SoftTokenCodeEnrollmentRequest request = SoftTokenCodeEnrollmentRequestFixture.withDefault();
        GenericResponse responseExpected = SoftTokenResponseFixture.withDefaultGenericCode();
        when(service.postCodeEnrollment(any(), any())).thenReturn(responseExpected);

        // Act
        String urlCodeEnrollment = "/api/v1/softtoken/persons/{personId}/enrollment-code";
        MvcResult result = mockMvc.perform(post(urlCodeEnrollment, "123")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(Util.objectToString(request)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        // Assert
        assertNotNull(result);
        verify(service).postCodeEnrollment(any(), any());
    }

    @Test
    void givenPersonIdWhenPostValidationCodeEnrollmentThenReturnSuccess() throws Exception {
        // Arrange
        SoftTokenValidateCodeEnrollmentRequest request = SoftTokenCodeEnrollmentRequestFixture.withDefaultValidation();
        GenericResponse responseExpected = SoftTokenResponseFixture.withDefaultGenericCode();
        when(service.validateCodeEnrollment(any(), any())).thenReturn(responseExpected);

        // Act
        String urlCodeEnrollment = "/api/v1/softtoken/persons/{personId}/validate-enrollment-code";
        MvcResult result = mockMvc.perform(post(urlCodeEnrollment, "123")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(Util.objectToString(request)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        // Assert
        assertNotNull(result);
        verify(service).validateCodeEnrollment(any(), any());
    }

    @Test
    void givenPersonIdWhenPostValidationQuestionEnrollmentThenReturnSuccess() throws Exception {
        // Arrange
        SoftTokenValidationQuestionRequest request = SoftTokenCodeEnrollmentRequestFixture.withDefaultValidationQuestion();
        GenericResponse responseExpected = SoftTokenResponseFixture.withDefaultGenericCode();
        when(service.validateQuestionSecurity(any(), any(), any())).thenReturn(responseExpected);

        // Act
        String urlCodeEnrollment = "/api/v1/softtoken/persons/{personId}/question-validate";
        MvcResult result = mockMvc.perform(post(urlCodeEnrollment, "123")
                        .header("device-model", "iphone")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(Util.objectToString(request)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        // Assert
        assertNotNull(result);
        verify(service).validateQuestionSecurity(any(), any(), any());
    }

    @Test
    void givenPersonIdWhenPostObtainParameterThenReturnSuccess() throws Exception {
        // Arrange
        SoftTokenObtainParametersResponse responseExpected = SoftTokenResponseFixture.withDefaultParameter();
        when(service.getParameters(any())).thenReturn(responseExpected);

        // Act
        String urlCodeEnrollment = "/api/v1/softtoken/persons/{personId}/obtain-parameter";
        MvcResult result = mockMvc.perform(get(urlCodeEnrollment, "123")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        // Assert
        assertNotNull(result);
        verify(service).getParameters(any());
    }

    @Test
    void givenPersonIdWhenRegisterTokenThenReturnSuccess() throws Exception {
        // Arrange
        SoftTokenCodeTokenRequest request = SoftTokenCodeEnrollmentRequestFixture.withDefaultToken();
        GenericResponse responseExpected = SoftTokenResponseFixture.withDefaultGenericCode();
        when(service.postRegistrationToken(any(), any())).thenReturn(responseExpected);

        // Act
        String urlCodeEnrollment = "/api/v1/softtoken/persons/{personId}/register-token";
        MvcResult result = mockMvc.perform(post(urlCodeEnrollment, "123")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(Util.objectToString(request)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        // Assert
        assertNotNull(result);
        verify(service).postRegistrationToken(any(), any());
    }

    @Test
    void givenPersonIdWhenRegistrationValidateThenReturnSuccess() throws Exception {
        // Arrange
        GenericResponse responseExpected = SoftTokenResponseFixture.withDefaultGenericCode();
        when(service.getRegistrationValidation(any())).thenReturn(responseExpected);

        // Act
        String urlCodeEnrollment = "/api/v1/softtoken/persons/{personId}/registration-validate";
        MvcResult result = mockMvc.perform(get(urlCodeEnrollment, "123")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        // Assert
        assertNotNull(result);
        verify(service).getRegistrationValidation(any());
    }
    @Test
    void givenPersonIdWhenTokenGenerateThenReturnSuccess() throws Exception {
        // Arrange
        SoftTokenGenerateTokenResponse responseExpected = SoftTokenResponseFixture.withDefaultTokenGeneration();
        when(service.postTokenGenerate(any())).thenReturn(responseExpected);

        // Act
        String urlCodeEnrollment = "/api/v1/softtoken/persons/{personId}/token-generate";
        MvcResult result = mockMvc.perform(post(urlCodeEnrollment, "123")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        // Assert
        assertNotNull(result);
        verify(service).postTokenGenerate(any());
    }

    @Test
    void givenPersonIdWhenEnrollmentThenReturnSuccess() throws Exception {
        // Arrange
        SoftTokenEnrollmentRequest request = SoftTokenCodeEnrollmentRequestFixture.withDefaultEnrollment();
        GenericResponse responseExpected = SoftTokenResponseFixture.withDefaultGenericCode();
        when(service.postEnrollment(any(), any(), any())).thenReturn(responseExpected);

        // Act
        String urlCodeEnrollment = "/api/v1/softtoken/persons/{personId}/enrollment";
        MvcResult result = mockMvc.perform(post(urlCodeEnrollment, "123")
                        .header("device-model", "iphone")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(Util.objectToString(request)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        // Assert
        assertNotNull(result);
        verify(service).postEnrollment(any(), any(), any());
    }

    @Test
    void givenPersonIdWhenTokenValidateThenReturnSuccess() throws Exception {
        // Arrange
        SoftTokenCodeTokenRequest request = SoftTokenCodeEnrollmentRequestFixture.withDefaultCode();
        GenericResponse responseExpected = SoftTokenResponseFixture.withDefaultGenericCode();
        when(service.validationToken(any(), any())).thenReturn(responseExpected);

        // Act
        String urlCodeEnrollment = "/api/v1/softtoken/persons/{personId}/token-validate";
        MvcResult result = mockMvc.perform(post(urlCodeEnrollment, "123")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(Util.objectToString(request)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        // Assert
        assertNotNull(result);
        verify(service).validationToken(any(), any());
    }
}
