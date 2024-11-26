package bg.com.bo.bff.providers.models.enums.middleware.point.attention;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum PointAttentionMiddlewareService {
    GET_LIST_POINTS_ATTENTION("/bs/v1/obtain-points-attention"),
    GET_DETAIL_DATA_POINT("/bs/v1/obtain-data-point?idPoint=%s"),
    GET_PENDING_TICKETS("/bs/v1/tickets-pending?idPoint=%s");

    private final String serviceURL;
}
