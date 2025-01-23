package bg.com.bo.bff.application.controllers.v1;

import bg.com.bo.bff.application.config.HeadersDataFixture;
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
        String URL_GET_WELCOME = "/api/v1/softtoken/persons/{personId}/welcome";
        MvcResult result = mockMvc.perform(get(URL_GET_WELCOME, "123")
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
        String URL_GET_DATA_ENROLLMENT = "/api/v1/softtoken/persons/{personId}/data-enrollment";
        MvcResult result = mockMvc.perform(get(URL_GET_DATA_ENROLLMENT, "123")
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
        String URL_GET_QUESTION_ENROLLMENT = "/api/v1/softtoken/persons/{personId}/question-enrollment";
        MvcResult result = mockMvc.perform(get(URL_GET_QUESTION_ENROLLMENT, "123")
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
        SoftTokenValidationEnrollmentResponse responseExpected = SoftTokenResponseFixture.withDefaultValidate();
        when(service.getValidationEnrollment(any())).thenReturn(responseExpected);

        // Act
        String URL_GET_VALIDATE_ENROLLMENT = "/api/v1/softtoken/persons/{personId}/validation-enrollment";
        MvcResult result = mockMvc.perform(get(URL_GET_VALIDATE_ENROLLMENT, "123")
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

}
