package bg.com.bo.bff.application.controllers.v1;

import bg.com.bo.bff.application.dtos.response.generic.ErrorResponse;
import bg.com.bo.bff.providers.models.interfaces.middleware.IMiddlewareError;
import bg.com.bo.bff.providers.models.middleware.DefaultMiddlewareError;
import bg.com.bo.bff.services.interfaces.IErrorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class CustomErrorControllerTest {
    private MockMvc mockMvc;
    @Spy
    @InjectMocks
    private CustomErrorController controller;

    @Mock
    private IErrorService service;

    private static final String URL = "/error";

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    void givenValidaDataWhenErrorThenResponse() throws Exception {
        // Arrange
        IMiddlewareError error = DefaultMiddlewareError.FORBIDDEN;
        ErrorResponse errorResponse = ErrorResponse.instance(error);
        ResponseEntity<ErrorResponse> response = ResponseEntity.status(error.getHttpCode()).body(errorResponse);
        when(service.map(any(), any())).thenReturn(response);

        //Act
        mockMvc.perform(get(URL))
                .andExpect(status().isForbidden())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();
    }
}
