package bg.com.bo.bff.application.controllers.v1;

import bg.com.bo.bff.application.dtos.response.frequently.question.ListFrequentlyQuestionResponseFixture;
import bg.com.bo.bff.application.dtos.response.frequently.questions.ListFrequentlyQuestionResponse;
import bg.com.bo.bff.commons.utils.Util;
import bg.com.bo.bff.services.interfaces.IFrequentlyQuestionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class FrequentlyQuestionControllerTest {
    private MockMvc mockMvc;
    @InjectMocks
    private FrequentlyQuestionController controller;
    @Mock
    private IFrequentlyQuestionService service;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(controller)
                .build();
    }


    @Test
    void givenPersonIdWhenGetWelcomeMessageThenReturnSuccess() throws Exception {
        // Arrange
        ListFrequentlyQuestionResponse responseExpected = ListFrequentlyQuestionResponseFixture.withDefaultListFrequentlyQuestionResponseEmpty();
        when(service.getFrequentlyQuestions()).thenReturn(responseExpected);

        // Act
        String urlGetFrequentlyQuestion = "/api/v1/frequently-question/questions";
        MvcResult result = mockMvc.perform(get(urlGetFrequentlyQuestion, "123")
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
        verify(service).getFrequentlyQuestions();
    }
}
