package bg.com.bo.bff.services.interfaces;

import bg.com.bo.bff.application.dtos.response.attention.points.DetailAttentionPointResponse;
import bg.com.bo.bff.application.dtos.response.attention.points.ListAttentionPointsResponse;
import bg.com.bo.bff.application.dtos.response.attention.points.PendingTicketResponse;

import java.io.IOException;

public interface IAttentionPointsService {
    ListAttentionPointsResponse getListAttentionPoints() throws IOException;

    DetailAttentionPointResponse getDetailAttentionPoint(String pointId) throws IOException;

    PendingTicketResponse getPendingTickets(String pointId) throws IOException;
}
