package bg.com.bo.bff.providers.dtos.response.point.attention.mw;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PendingTicketMWResponse {
    @JsonProperty("Status")
    private Integer status;
    @JsonProperty("EndTime")
    private String endTime;
    @JsonProperty("StartTime")
    private String startTime;
    @JsonProperty("Message")
    private String message;
    @JsonProperty("Description")
    private String description;
    @JsonProperty("WaitBoxes")
    private Integer waitBoxes;
    @JsonProperty("WaitExecutive")
    private Integer waitExecutive;
    @JsonProperty("WaitPlatform")
    private Integer waitPlatform;
    @JsonProperty("IPMatic")
    private String ipMatic;
    @JsonProperty("IdPoint")
    private Integer idPoint;
    @JsonProperty("Name")
    private String name;
    @JsonProperty("PWDMatic")
    private String pwdMatic;
    @JsonProperty("AverageBoxes")
    private Double averageBoxes;
    @JsonProperty("AverageExecutive")
    private Double averageExecutive;
    @JsonProperty("AveragePlatform")
    private Double averagePlatform;
    @JsonProperty("ActiveBoxes")
    private Integer activeBoxes;
    @JsonProperty("ActivePlatform")
    private Integer activePlatform;
    @JsonProperty("ActiveExecutive")
    private Integer activeExecutive;
    @JsonProperty("Result")
    private Integer result;
    @JsonProperty("UserMatic")
    private String userMatic;
}
