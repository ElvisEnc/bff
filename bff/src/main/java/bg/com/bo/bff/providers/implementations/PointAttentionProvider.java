package bg.com.bo.bff.providers.implementations;

import bg.com.bo.bff.application.config.MiddlewareConfig;
import bg.com.bo.bff.commons.enums.config.provider.ProjectNameMW;
import bg.com.bo.bff.commons.interfaces.IHttpClientFactory;
import bg.com.bo.bff.commons.utils.Util;
import bg.com.bo.bff.providers.dtos.response.generic.ApiDataResponse;
import bg.com.bo.bff.providers.dtos.response.point.attention.mw.DetailPointAttentionMWResponse;
import bg.com.bo.bff.providers.dtos.response.point.attention.mw.ListPointsAttentionMWResponse;
import bg.com.bo.bff.providers.dtos.response.point.attention.mw.PendingTicketMWResponse;
import bg.com.bo.bff.providers.interfaces.IAttentionPointsProvider;
import bg.com.bo.bff.providers.interfaces.ITokenMiddlewareProvider;
import bg.com.bo.bff.providers.models.enums.middleware.point.attention.PointAttentionMiddlewareError;
import bg.com.bo.bff.providers.models.enums.middleware.point.attention.PointAttentionMiddlewareService;
import bg.com.bo.bff.providers.models.middleware.MiddlewareProvider;
import bg.com.bo.bff.providers.models.middleware.response.handler.ByMwErrorResponseHandler;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class PointAttentionProvider extends MiddlewareProvider<PointAttentionMiddlewareError> implements IAttentionPointsProvider {

    private final String baseUrl;

    public PointAttentionProvider(ITokenMiddlewareProvider tokenMiddlewareProvider, MiddlewareConfig middlewareConfig, IHttpClientFactory httpClientFactory) {
        super(ProjectNameMW.POINT_ATTENTION_MANAGER, PointAttentionMiddlewareError.class, tokenMiddlewareProvider, middlewareConfig, httpClientFactory, middlewareConfig.getClientPointAttentionManager() );
        this.baseUrl = middlewareConfig.getUrlBase() + ProjectNameMW.POINT_ATTENTION_MANAGER.getName();
    }

    @Override
    public ListPointsAttentionMWResponse getListAttentionPoints() throws IOException {
        String url = baseUrl + PointAttentionMiddlewareService.GET_LIST_POINTS_ATTENTION.getServiceURL();
        ListPointsAttentionMWResponse mwResponse = get(url, null, ListPointsAttentionMWResponse.class);
        return Util.stringToObject(Util.objectToString(mwResponse), ListPointsAttentionMWResponse.class);
    }

    @Override
    public DetailPointAttentionMWResponse getDetailAttentionPoint(String pointId) throws IOException {
        String url = baseUrl + String.format(PointAttentionMiddlewareService.GET_DETAIL_DATA_POINT.getServiceURL(), pointId);
        ByMwErrorResponseHandler<DetailPointAttentionMWResponse> responseHandler = ByMwErrorResponseHandler.instance(PointAttentionMiddlewareError.PAM_015);
        DetailPointAttentionMWResponse mwResponse = get(url, null, DetailPointAttentionMWResponse.class, responseHandler);
        return Util.stringToObject(Util.objectToString(mwResponse), DetailPointAttentionMWResponse.class);
    }

    @Override
    public PendingTicketMWResponse getPendingTickets(String pointId) throws IOException {
        String url = baseUrl + String.format(PointAttentionMiddlewareService.GET_PENDING_TICKETS.getServiceURL(), pointId);
        ApiDataResponse<PendingTicketMWResponse> mwResponse = get(url, null, ApiDataResponse.class);
        return Util.stringToObject(Util.objectToString(mwResponse.getData()), PendingTicketMWResponse.class);
    }
}
