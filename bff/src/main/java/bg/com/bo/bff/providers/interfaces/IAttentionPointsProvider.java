package bg.com.bo.bff.providers.interfaces;

import bg.com.bo.bff.providers.dtos.response.point.attention.mw.DetailPointAttentionMWResponse;
import bg.com.bo.bff.providers.dtos.response.point.attention.mw.ListPointsAttentionMWResponse;
import bg.com.bo.bff.providers.dtos.response.point.attention.mw.PendingTicketMWResponse;

import java.io.IOException;

public interface IAttentionPointsProvider {
    ListPointsAttentionMWResponse getListAttentionPoints() throws IOException;

    DetailPointAttentionMWResponse getDetailAttentionPoint(String pointId) throws IOException;

    PendingTicketMWResponse getPendingTickets(String pointId) throws IOException;
}
