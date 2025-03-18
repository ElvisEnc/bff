package bg.com.bo.bff.services.implementations.v1;

import bg.com.bo.bff.application.dtos.response.frequently.question.ListFrequentlyQuestionResponseFixture;
import bg.com.bo.bff.application.dtos.response.frequently.questions.ListFrequentlyQuestionResponse;
import bg.com.bo.bff.application.exceptions.GenericException;
import bg.com.bo.bff.mappings.providers.frequentlyquestion.FrequentlyQuestionMapper;
import bg.com.bo.bff.providers.dtos.response.frequently.question.mw.FrequentlyQuestionMWResponseFixture;
import bg.com.bo.bff.providers.dtos.response.frequently.question.mw.ListFrequentlyQuestionMWResponse;
import bg.com.bo.bff.providers.interfaces.IFrequentlyQuestionProvider;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.util.ArrayList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FrequentlyQuestionServiceTest {
    @InjectMocks
    private FrequentlyQuestionService service;
    @Mock
    private IFrequentlyQuestionProvider provider;
    @Spy
    private FrequentlyQuestionMapper mapper = new FrequentlyQuestionMapper();

    @Test
    void givenValidPetitionWhenGetFrequentlyQuestionThenListNull() throws IOException {
        //Arrange
        ListFrequentlyQuestionResponse expectedResponse = ListFrequentlyQuestionResponseFixture.withDefaultListFrequentlyQuestionResponseEmpty();
        when(provider.getFrequentlyQuestions()).thenReturn(null);

        //Act
        ListFrequentlyQuestionResponse response = service.getFrequentlyQuestions();

        //Assert
        assertNotNull(response);
        assertThat(response).usingRecursiveComparison().isEqualTo(expectedResponse);
    }


    @Test
    void givenValidPetitionWhenGetListAttentionPointsThenExpectResponse() throws IOException {
        //Arrange
        ListFrequentlyQuestionMWResponse mwResponseMock = FrequentlyQuestionMWResponseFixture.withDefaultFrequentlyQuestionMWResponse();
        when(provider.getFrequentlyQuestions()).thenReturn(mwResponseMock);

        //Act
        ListFrequentlyQuestionResponse response = service.getFrequentlyQuestions();

        //Assert
        assertNotNull(response);
        verify(provider).getFrequentlyQuestions();
        verify(mapper).convertResponse(any(ListFrequentlyQuestionMWResponse.class));
    }

    @Test
    void givenNullDetailsWhenConvertResponseThenAnswersListEmpty() {
        // Arrange
        ListFrequentlyQuestionMWResponse mwResponse = FrequentlyQuestionMWResponseFixture.withDefaultFrequentlyQuestionMWResponseNullDetail();
        // Act
        ListFrequentlyQuestionResponse response = mapper.convertResponse(mwResponse);

        // Assert
        assertNotNull(response);
        assertEquals(1, response.getData().size());
        assertNotNull(response.getData().get(0).getAnswer());
        assertTrue(response.getData().get(0).getAnswer().isEmpty());
    }

    @Test
    void givenNullResponseWhenConvertResponseThenReturnEmptyList() {
        // Arrange
        ListFrequentlyQuestionResponse expectedResponse = ListFrequentlyQuestionResponse.builder()
                .data(new ArrayList<>())
                .build();

        // Act
        ListFrequentlyQuestionResponse response = mapper.convertResponse(null);

        // Assert
        assertNotNull(response);
        assertThat(response).usingRecursiveComparison().isEqualTo(expectedResponse);
    }
 
}

