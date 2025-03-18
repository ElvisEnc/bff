package bg.com.bo.bff.services.implementations.v1;

import bg.com.bo.bff.application.dtos.response.frequently.question.ListFrequentlyQuestionResponseFixture;
import bg.com.bo.bff.application.dtos.response.frequently.questions.ListFrequentlyQuestionResponse;
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

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
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
    void givenValidPetitionWhenGetListAttentionPointsThenExpectResponseDetailNull() throws IOException {
        //Arrange
        ListFrequentlyQuestionMWResponse mwResponseMock = FrequentlyQuestionMWResponseFixture.withDefaultFrequentlyQuestionMWResponseNullDetail();
        when(provider.getFrequentlyQuestions()).thenReturn(mwResponseMock);

        //Act
        ListFrequentlyQuestionResponse response = service.getFrequentlyQuestions();

        //Assert
        assertNotNull(response);
        verify(provider).getFrequentlyQuestions();
        verify(mapper).convertResponse(any(ListFrequentlyQuestionMWResponse.class));
    }
}

