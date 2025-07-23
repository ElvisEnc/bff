package bg.com.bo.bff.providers.dtos.response.point.attention.mw;

import java.util.Collections;

public class PointAttentionMWResponseFixture {
    public static ListPointsAttentionMWResponse withDefaultListPointsAttentionMWResponse() {
        return ListPointsAttentionMWResponse.builder()
                .data(Collections.singletonList(
                        createListPointAttentionMWData()
                ))
                .build();
    }

    public static ListPointsAttentionMWResponse.PointAttentionMW createListPointAttentionMWData() {
        return ListPointsAttentionMWResponse.PointAttentionMW.builder()
                .idPoint(1)
                .idReference("123")
                .typePoint('A')
                .name("name")
                .description("description")
                .plaza(1)
                .direction("address")
                .telephone("78904564")
                .latitude("40.123")
                .longitude("-70.123")
                .userRegistration("user")
                .registrationDate("2021-01-01")
                .tzLock(0)
                .pointSubType('B')
                .central('S')
                .depositary('S')
                .handicapped('S')
                .hourHand('S')
                .currency('A')
                .build();
    }

    public static DetailPointAttentionMWResponse withDefaultDetailPointAttentionMWResponse() {
        return DetailPointAttentionMWResponse.builder()
                .data(Collections.singletonList(
                        createDetailPointAttentionMWData()
                ))
                .build();
    }

    public static DetailPointAttentionMWResponse.PoinAttention createDetailPointAttentionMWData() {
        return DetailPointAttentionMWResponse.PoinAttention.builder()
                .idPoint(1)
                .idReference("123")
                .idProperty(5)
                .idClave("type")
                .phone("78904564")
                .direction("address")
                .valor("description")
                .build();
    }

    public  static PendingTicketMWResponse withDefaultPendingTicketMWResponse() {
        return PendingTicketMWResponse.builder()
                .status(1)
                .endTime("2021-01-01")
                .startTime("2021-01-01")
                .message("message")
                .description("description")
                .waitBoxes(1)
                .waitExecutive(1)
                .waitPlatform(1)
                .ipMatic("192.168.0.1")
                .idPoint(1)
                .name("name")
                .pwdMatic("123")
                .averageBoxes(1.0)
                .averageExecutive(1.0)
                .averagePlatform(1.0)
                .activeBoxes(1)
                .activePlatform(1)
                .activeExecutive(1)
                .result(1)
                .userMatic("user")
                .build();
    }
}
