package bg.com.bo.bff.mappings.providers.point.attention;

import bg.com.bo.bff.application.dtos.response.attention.points.DetailAttentionPointResponse;
import bg.com.bo.bff.application.dtos.response.attention.points.ListAttentionPointsResponse;
import bg.com.bo.bff.application.dtos.response.attention.points.PendingTicketResponse;
import bg.com.bo.bff.providers.dtos.response.point.attention.mw.DetailPointAttentionMWResponse;
import bg.com.bo.bff.providers.dtos.response.point.attention.mw.ListPointsAttentionMWResponse;
import bg.com.bo.bff.providers.dtos.response.point.attention.mw.PendingTicketMWResponse;

public interface IPointAttentionMapper {
    ListAttentionPointsResponse convertResponse(ListPointsAttentionMWResponse mwResponse);

    DetailAttentionPointResponse convertResponse(DetailPointAttentionMWResponse mwResponse);

    PendingTicketResponse convertResponse(PendingTicketMWResponse mwResponse);
}
