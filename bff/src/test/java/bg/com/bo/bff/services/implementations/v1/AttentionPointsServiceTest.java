package bg.com.bo.bff.services.implementations.v1;

import bg.com.bo.bff.application.dtos.response.attention.points.AttentionPointsResponseFixture;
import bg.com.bo.bff.application.dtos.response.attention.points.DetailAttentionPointResponse;
import bg.com.bo.bff.application.dtos.response.attention.points.ListAttentionPointsResponse;
import bg.com.bo.bff.application.dtos.response.attention.points.PendingTicketResponse;
import bg.com.bo.bff.mappings.providers.point.attention.PointAttentionMapper;
import bg.com.bo.bff.providers.dtos.response.point.attention.mw.DetailPointAttentionMWResponse;
import bg.com.bo.bff.providers.dtos.response.point.attention.mw.ListPointsAttentionMWResponse;
import bg.com.bo.bff.providers.dtos.response.point.attention.mw.PendingTicketMWResponse;
import bg.com.bo.bff.providers.dtos.response.point.attention.mw.PointAttentionMWResponseFixture;
import bg.com.bo.bff.providers.interfaces.IAttentionPointsProvider;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AttentionPointsServiceTest {
    @InjectMocks
    private AttentionPointsService service;
    @Mock
    private IAttentionPointsProvider provider;
    @Spy
    private PointAttentionMapper mapper = new PointAttentionMapper();
    @Mock
    private AttentionPointsService self;

    // List Attention Points
    @Test
    void givenValidPetitionWhenGetListAttentionPointsThenListNull() throws IOException {
        //Arrange
        ListAttentionPointsResponse expectedResponse = AttentionPointsResponseFixture.withDefaultListAttentionPointsResponseEmpty();
        when(provider.getListAttentionPoints()).thenReturn(null);

        //Act
        ListAttentionPointsResponse response = service.getListAttentionPoints();

        //Assert
        assertNotNull(response);
        assertThat(response).usingRecursiveComparison().isEqualTo(expectedResponse);
    }

    @Test
    void givenValidPetitionWhenGetListAttentionPointsThenExpectResponse() throws IOException {
        //Arrange
        ListPointsAttentionMWResponse mwResponseMock = PointAttentionMWResponseFixture.withDefaultListPointsAttentionMWResponse();
        ListAttentionPointsResponse expectedResponse = AttentionPointsResponseFixture.withDefaultListAttentionPointsResponse();
        when(provider.getListAttentionPoints()).thenReturn(mwResponseMock);

        //Act
        ListAttentionPointsResponse response = service.getListAttentionPoints();

        //Assert
        assertNotNull(response);
        assertThat(response).usingRecursiveComparison().isEqualTo(expectedResponse);
        verify(provider).getListAttentionPoints();
        verify(mapper).convertResponse(any(ListPointsAttentionMWResponse.class));
    }

    // Details Attention Points
    @Test
    void givenPointIdWhenGetDetailAttentionPointThenListNull() throws IOException {
        //Arrange
        DetailAttentionPointResponse expectedResponse = AttentionPointsResponseFixture.withDefaultDetailAttentionPointResponseEmpty();
        when(provider.getDetailAttentionPoint(any())).thenReturn(null);

        //Act
        DetailAttentionPointResponse response = service.getDetailAttentionPoint("123");

        //Assert
        assertNotNull(response);
        assertThat(response).usingRecursiveComparison().isEqualTo(expectedResponse);
    }

    @Test
    void givenPointIdWhenGetDetailAttentionPointThenExpectResponse() throws IOException {
        //Arrange
        DetailPointAttentionMWResponse mwResponseMock = PointAttentionMWResponseFixture.withDefaultDetailPointAttentionMWResponse();
        DetailAttentionPointResponse expectedResponse = AttentionPointsResponseFixture.withDefaultDetailAttentionPointResponse();
        when(provider.getDetailAttentionPoint(any())).thenReturn(mwResponseMock);

        //Act
        DetailAttentionPointResponse response = service.getDetailAttentionPoint("123");

        //Assert
        assertNotNull(response);
        assertThat(response).usingRecursiveComparison().isEqualTo(expectedResponse);
        verify(provider).getDetailAttentionPoint(any());
        verify(mapper).convertResponse(any(DetailPointAttentionMWResponse.class));
    }

    @Test
    void givenPointIdWhenGetPendingTicketsThenExpectResponse() throws IOException {
        //Arrange
        PendingTicketMWResponse mwResponseMock = PointAttentionMWResponseFixture.withDefaultPendingTicketMWResponse();
        PendingTicketResponse expectedResponse = AttentionPointsResponseFixture.withDefaultPendingTicketResponse();
        when(provider.getPendingTickets(any())).thenReturn(mwResponseMock);

        //Act
        PendingTicketResponse response = service.getPendingTickets("123");

        //Assert
        assertNotNull(response);
        assertThat(response).usingRecursiveComparison().isEqualTo(expectedResponse);
        verify(provider).getPendingTickets(any());
        verify(mapper).convertResponse(any(PendingTicketMWResponse.class));
    }
}