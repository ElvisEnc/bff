package bg.com.bo.bff.services.implementations.v1;

import bg.com.bo.bff.application.dtos.request.nps.ResponseNpsRequest;
import bg.com.bo.bff.application.dtos.response.nps.NpsResponse;
import bg.com.bo.bff.application.dtos.response.nps.RegisterNpsResponse;
import bg.com.bo.bff.mappings.providers.nps.NpsMapper;
import bg.com.bo.bff.providers.dtos.request.nps.mw.AnswerNpsMWRequest;
import bg.com.bo.bff.providers.dtos.response.nps.NpsMWResponseFixture;
import bg.com.bo.bff.providers.dtos.response.nps.mw.AnswerNpsMWResponse;
import bg.com.bo.bff.providers.dtos.response.nps.mw.RegisterNpsMWResponse;
import bg.com.bo.bff.providers.interfaces.INpsProvider;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class NpsServiceTest {
    @InjectMocks
    private NpsService service;
    @Spy
    private NpsMapper mapper = new NpsMapper();

    @Mock
    private INpsProvider provider;

    @Test
    void testRegisterDevice() throws IOException {
        //Arrange
        RegisterNpsMWResponse mwResponseMock = NpsMWResponseFixture.withDefaultRegisterNps();
        RegisterNpsResponse expectedResponse = NpsMWResponseFixture.withDefaultRegisterNpsResponse();

        //Act
        when(provider.registerDevice(any(), any())).thenReturn(mwResponseMock);
        RegisterNpsResponse response = service.registerDevice("10", "2525");

        //Assert
        assertNotNull(response);
        assertEquals(expectedResponse.getNpsId(), response.getNpsId());
        assertEquals(expectedResponse.getQuestionIds().get(0).getQuestionIds(),
                response.getQuestionIds().get(0).getQuestionIds());
        assertThat(response).usingRecursiveComparison().isEqualTo(expectedResponse);
        verify(provider).registerDevice(any(), any());

    }


    @Test
    void testSendAnswerNps() throws IOException {
        // Arrange
        ResponseNpsRequest request = NpsMWResponseFixture.withDefaultSendAnswerRequest();
        AnswerNpsMWResponse mwResponseMock = NpsMWResponseFixture.answerNpsMWResponseWithDefault();
        NpsResponse expectedResponse = NpsMWResponseFixture.withDefaultNpsResponse();

        // Act
        when(provider.answerNps(eq(15), eq("device001"), any(AnswerNpsMWRequest.class)))
                .thenReturn(mwResponseMock);
        NpsResponse response = service.sendAnswerNps(15, "device001", request);

        // Assert
        assertNotNull(expectedResponse);
        assertEquals(mwResponseMock.getData().getCodeResponse(), response.getCodeError());
        assertEquals(expectedResponse.getMessage(), response.getMessage());
        assertThat(response).usingRecursiveComparison().isEqualTo(expectedResponse);
        verify(provider).answerNps(anyInt(), anyString(), any(AnswerNpsMWRequest.class));

    }


}
