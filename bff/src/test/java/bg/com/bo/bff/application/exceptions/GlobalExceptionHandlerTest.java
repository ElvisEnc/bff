package bg.com.bo.bff.application.exceptions;

import bg.com.bo.bff.application.exceptions.fixture.MockedController;
import bg.com.bo.bff.application.dtos.response.generic.ErrorResponse;
import bg.com.bo.bff.commons.utils.Util;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.method.annotation.HandlerMethodValidationException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.nio.charset.StandardCharsets;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class GlobalExceptionHandlerTest {
    private MockMvc mockMvc;
    @InjectMocks
    private MockedController controller;
    private GlobalExceptionHandler spyHandler;

    @BeforeEach
    void setUp() {
        spyHandler = Mockito.spy(new GlobalExceptionHandler());
        mockMvc = MockMvcBuilders.standaloneSetup(controller)
                .setControllerAdvice(spyHandler)
                .build();
    }

    @Test
    void notGivenRequiredHeaderWhenHandlingGlobalExceptionThenReturnBadRequest() throws Exception {
        // Arrange
        ErrorResponse expectedResponse = new ErrorResponse(HttpStatus.BAD_REQUEST.name(), "Required request header 'request-header' for method parameter type String is not present", "Bad request", 1200);
        String path = String.format("/%s/%s", MockedController.MOCKED_CONTROLLER, MockedController.EP_WITH_REQUIRED_AND_NON_BLANK_HEADER);

        // Act
        MvcResult result = mockMvc.perform(get(path)
                )
                .andExpect(status().is4xxClientError())
                .andReturn();

        String response = result.getResponse().getContentAsString(StandardCharsets.UTF_8);
        ErrorResponse errorResponse = Util.stringToObject(response, ErrorResponse.class);

        // Assert
        verify(spyHandler).handleValidationException(any(MissingRequestHeaderException.class));
        assertEquals(expectedResponse, errorResponse);
    }

    @Test
    void givenBlankHeaderOnRequiredHeaderWhenHandlingGlobalExceptionThenReturnBadRequest() throws Exception {
        // Arrange
        ErrorResponse expectedResponse = new ErrorResponse(HttpStatus.BAD_REQUEST.name(), MockedController.NOT_BLANK, "Bad request", 1200);
        String path = String.format("/%s/%s", MockedController.MOCKED_CONTROLLER, MockedController.EP_WITH_REQUIRED_AND_NON_BLANK_HEADER);

        // Act
        MvcResult result = mockMvc.perform(get(path).
                        header(MockedController.REQUEST_HEADER, " ")
                )
                .andExpect(status().is4xxClientError())
                .andReturn();

        String response = result.getResponse().getContentAsString(StandardCharsets.UTF_8);
        ErrorResponse errorResponse = Util.stringToObject(response, ErrorResponse.class);

        // Assert
        verify(spyHandler).handleHandlerMethodValidationException(any(HandlerMethodValidationException.class));
        assertEquals(expectedResponse, errorResponse);
    }

    @Test
    void givenInvalidHeaderOnRequiredAndWithMinNumericHeaderWhenHandlingGlobalExceptionThenReturnBadRequest() throws Exception {
        // Arrange
        ErrorResponse expectedResponse = new ErrorResponse(HttpStatus.BAD_REQUEST.name(), "Failed to convert value of type 'java.lang.String' to required type 'java.lang.Integer'; For input string: \"s\"", "Bad request", 1200);
        String path = String.format("/%s/%s", MockedController.MOCKED_CONTROLLER, MockedController.EP_WITH_REQUIRED_AND_NUMERIC_HEADER);

        // Act
        MvcResult result = mockMvc.perform(get(path).
                        header(MockedController.REQUEST_HEADER, "s")
                )
                .andExpect(status().is4xxClientError())
                .andReturn();

        String response = result.getResponse().getContentAsString(StandardCharsets.UTF_8);
        ErrorResponse errorResponse = Util.stringToObject(response, ErrorResponse.class);

        // Assert
        verify(spyHandler).handleValidationException(any(MethodArgumentTypeMismatchException.class));
        assertEquals(expectedResponse, errorResponse);
    }

    @Test
    void givenInvalidBodyParameterWhenHandlingGlobalExceptionThenReturnBadRequest() throws Exception {
        // Arrange
        String request = "{\"stringParam\":\"ab\",\"decimalParam\":\"s\"}";

        ErrorResponse expectedResponse = new ErrorResponse(HttpStatus.BAD_REQUEST.name(), "El campo 'decimalParam' debe ser un Integer válido", "Bad request", 1200);
        String path = String.format("/%s/%s", MockedController.MOCKED_CONTROLLER, MockedController.EP_WITH_REQUIRED_BODY);

        // Act
        MvcResult result = mockMvc.perform(post(path)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                .andReturn();

        String response = result.getResponse().getContentAsString(StandardCharsets.UTF_8);
        ErrorResponse errorResponse = Util.stringToObject(response, ErrorResponse.class);

        // Assert
        verify(spyHandler).handleHttpMessageNotReadableException(any(HttpMessageNotReadableException.class));
        assertEquals(expectedResponse, errorResponse);
    }

    @Test
    void notGivenRequiredRequestParamWhenHandlingGlobalExceptionThenReturnBadRequest() throws Exception {
        // Arrange
        ErrorResponse expectedResponse = new ErrorResponse(HttpStatus.BAD_REQUEST.name(),
                String.format("%s: El campo es requerido.", MockedController.BIG_DECIMAL_REQUEST_PARAM), "Bad request", 1200);
        String path = String.format("/%s/%s?",
                MockedController.MOCKED_CONTROLLER,
                MockedController.EP_WITH_REQUIRED_BIG_DECIMAL_REQUEST_PARAM);

        // Act
        MvcResult result = mockMvc.perform(get(path)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        String response = result.getResponse().getContentAsString(StandardCharsets.UTF_8);
        ErrorResponse errorResponse = Util.stringToObject(response, ErrorResponse.class);

        // Assert
        verify(spyHandler).handleMissingServletRequestParameterException(any(MissingServletRequestParameterException.class));
        assertEquals(expectedResponse, errorResponse);
    }
}
