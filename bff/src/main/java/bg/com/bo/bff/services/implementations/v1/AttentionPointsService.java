package bg.com.bo.bff.services.implementations.v1;

import bg.com.bo.bff.application.dtos.response.attention.points.DetailAttentionPointResponse;
import bg.com.bo.bff.application.dtos.response.attention.points.ListAttentionPointsResponse;
import bg.com.bo.bff.application.dtos.response.attention.points.PendingTicketResponse;
import bg.com.bo.bff.mappings.providers.point.attention.IPointAttentionMapper;
import bg.com.bo.bff.providers.interfaces.IAttentionPointsProvider;
import bg.com.bo.bff.services.interfaces.IAttentionPointsService;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class AttentionPointsService implements IAttentionPointsService {
    private final IAttentionPointsProvider provider;
    private final IPointAttentionMapper mapper;

    public AttentionPointsService(IAttentionPointsProvider provider, IPointAttentionMapper mapper) {
        this.provider = provider;
        this.mapper = mapper;
    }

    @Override
    public ListAttentionPointsResponse getListAttentionPoints() throws IOException {
        return mapper.convertResponse(provider.getListAttentionPoints());
    }

    @Override
    public DetailAttentionPointResponse getDetailAttentionPoint(String pointId) throws IOException {
        return mapper.convertResponse(provider.getDetailAttentionPoint(pointId));
    }

    @Override
    public PendingTicketResponse getPendingTickets(String pointId) throws IOException {
        return mapper.convertResponse(provider.getPendingTickets(pointId));
    }
}
