package bg.com.bo.bff.application.dtos.response.attention.points;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PendingTicketResponse {
    private Integer pointId;
    private Integer status;
    private String name;
    private String startTime;
    private String endTime;
    private String message;
    private String description;
    private Integer waitBoxes;
    private Integer waitExecutive;
    private Integer waitPlatform;
    private String maticIp;
    private String maticPWD;
    private String maticUser;
    private Double averageBoxes;
    private Double averageExecutive;
    private Double averagePlatform;
    private Integer activeBoxes;
    private Integer activePlatform;
    private Integer activeExecutive;
    private Integer result;
}
