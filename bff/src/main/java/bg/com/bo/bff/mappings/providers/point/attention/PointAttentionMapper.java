package bg.com.bo.bff.mappings.providers.point.attention;

import bg.com.bo.bff.application.dtos.response.attention.points.DetailAttentionPointResponse;
import bg.com.bo.bff.application.dtos.response.attention.points.ListAttentionPointsResponse;
import bg.com.bo.bff.application.dtos.response.attention.points.PendingTicketResponse;
import bg.com.bo.bff.commons.enums.attention.points.SubTypeAttentionPoints;
import bg.com.bo.bff.providers.dtos.response.point.attention.mw.DetailPointAttentionMWResponse;
import bg.com.bo.bff.providers.dtos.response.point.attention.mw.ListPointsAttentionMWResponse;
import bg.com.bo.bff.providers.dtos.response.point.attention.mw.PendingTicketMWResponse;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Component
public class PointAttentionMapper implements IPointAttentionMapper {

    @Override
    public ListAttentionPointsResponse convertResponse(ListPointsAttentionMWResponse mwResponse) {
        if (mwResponse == null)
            return ListAttentionPointsResponse.builder()
                    .data(new ArrayList<>())
                    .build();

        List<ListAttentionPointsResponse.AttentionPoint> attentionPointResponses = Optional.ofNullable(mwResponse.getData())
                .orElseGet(Collections::emptyList)
                .stream()
                .map(this::mapAttentionPoint)
                .toList();
        return ListAttentionPointsResponse.builder()
                .data(attentionPointResponses)
                .build();
    }

    @Override
    public DetailAttentionPointResponse convertResponse(DetailPointAttentionMWResponse mwResponse) {
        if (mwResponse == null)
            return DetailAttentionPointResponse.builder()
                    .data(new ArrayList<>())
                    .build();

        List<DetailAttentionPointResponse.DetailPointResponse> detailPointResponses = Optional.ofNullable(mwResponse.getData())
                .orElseGet(Collections::emptyList)
                .stream()
                .map(this::mapDetailPoints)
                .toList();
        return DetailAttentionPointResponse.builder()
                .data(detailPointResponses)
                .build();
    }

    @Override
    public PendingTicketResponse convertResponse(PendingTicketMWResponse mwResponse) {
        return PendingTicketResponse.builder()
                .pointId(mwResponse.getIdPoint())
                .status(mwResponse.getStatus())
                .startTime(mwResponse.getStartTime())
                .endTime(mwResponse.getEndTime())
                .message(mwResponse.getMessage())
                .description(mwResponse.getDescription())
                .waitBoxes(mwResponse.getWaitBoxes())
                .waitExecutive(mwResponse.getWaitExecutive())
                .waitPlatform(mwResponse.getWaitPlatform())
                .maticIp(mwResponse.getIpMatic())
                .name(mwResponse.getName())
                .maticPWD(mwResponse.getPwdMatic())
                .averageBoxes(mwResponse.getAverageBoxes())
                .averageExecutive(mwResponse.getAverageExecutive())
                .averagePlatform(mwResponse.getAveragePlatform())
                .activeBoxes(mwResponse.getActiveBoxes())
                .activePlatform(mwResponse.getActivePlatform())
                .activeExecutive(mwResponse.getActiveExecutive())
                .result(mwResponse.getResult())
                .maticUser(mwResponse.getUserMatic())
                .build();
    }

    private ListAttentionPointsResponse.AttentionPoint mapAttentionPoint(ListPointsAttentionMWResponse.PointAttentionMW pamw) {
        return ListAttentionPointsResponse.AttentionPoint.builder()
                .pointId(pamw.getIdPoint())
                .referenceId(pamw.getIdReference())
                .type(pamw.getTypePoint() == 'A' ? "AGENCY" : "SELF_SERVICE")
                .name(pamw.getName())
                .latitude(pamw.getLatitude())
                .longitude(pamw.getLongitude())
                .central(pamw.getCentral() == 'S')
                .depositary(pamw.getDepositary() == 'S')
                .description(pamw.getDescription())
                .address(pamw.getDirection())
                .phone(pamw.getTelephone())
                .address(pamw.getDirection())
                .cityCode(pamw.getPlaza())
//                .subType(pamw.getPointSubType())
                .subType(SubTypeAttentionPoints.getCodeBySubType(pamw.getPointSubType()))

                .handicapped(pamw.getHandicapped() == 'S')
                .openAllDay(pamw.getHourHand() == 'S')
                .currencyCode(pamw.getCurrency() == 'A' ? "840" : "068")
                .build();
    }

    private DetailAttentionPointResponse.DetailPointResponse mapDetailPoints(DetailPointAttentionMWResponse.PoinAttention pamw) {
        return DetailAttentionPointResponse.DetailPointResponse.builder()
                .pointId(pamw.getIdPoint())
                .referenceId(pamw.getIdReference())
                .propertyId(pamw.getIdProperty())
                .address(pamw.getDirection())
                .phone(pamw.getPhone())
                .type(pamw.getIdClave())
                .description(pamw.getValor())
                .build();
    }
}
