package bg.com.bo.bff.application.dtos.response.attention.points;

import java.util.ArrayList;
import java.util.Collections;

public class AttentionPointsResponseFixture {
    public static ListAttentionPointsResponse withDefaultListAttentionPointsResponseEmpty() {
        return ListAttentionPointsResponse.builder()
                .data(new ArrayList<>())
                .build();
    }

    public static DetailAttentionPointResponse withDefaultDetailAttentionPointResponseEmpty() {
        return DetailAttentionPointResponse.builder()
                .data(new ArrayList<>())
                .build();
    }

    public static DetailAttentionPointResponse withDefaultDetailAttentionPointResponse() {
        return DetailAttentionPointResponse.builder()
                .data(Collections.singletonList(
                        createDetailAttentionPointData()
                ))
                .build();
    }

    public static DetailAttentionPointResponse.DetailPointResponse createDetailAttentionPointData() {
        return DetailAttentionPointResponse.DetailPointResponse.builder()
                .pointId(1)
                .referenceId("123")
                .propertyId(5)
                .type("type")
                .phone("78904564")
                .address("address")
                .description("description")
                .build();
    }

    public static ListAttentionPointsResponse withDefaultListAttentionPointsResponse() {
        return ListAttentionPointsResponse.builder()
                .data(Collections.singletonList(
                        createListAttentionPointsData()
                ))
                .build();
    }

    public static ListAttentionPointsResponse.AttentionPoint createListAttentionPointsData() {
        return ListAttentionPointsResponse.AttentionPoint.builder()
                .pointId(1)
                .referenceId("123")
                .type("AGENCY")
                .name("name")
                .latitude("40.123")
                .longitude("-70.123")
                .central(true)
                .depositary(true)
                .description("description")
                .phone("78904564")
                .address("address")
                .cityCode(1)
                .subType("DRIVE_THRU_BANK")
                .handicapped(true)
                .openAllDay(true)
                .currencyCode("840")
                .build();
    }

    public static PendingTicketResponse withDefaultPendingTicketResponse() {
        return PendingTicketResponse.builder()
                .pointId(1)
                .status(1)
                .name("name")
                .startTime("2021-01-01")
                .endTime("2021-01-01")
                .message("message")
                .description("description")
                .waitBoxes(1)
                .waitExecutive(1)
                .waitPlatform(1)
                .maticIp("192.168.0.1")
                .maticPWD("123")
                .maticUser("user")
                .averageBoxes(1.0)
                .averageExecutive(1.0)
                .averagePlatform(1.0)
                .activeBoxes(1)
                .activePlatform(1)
                .activeExecutive(1)
                .result(1)
                .build();
    }
}
