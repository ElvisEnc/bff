package bg.com.bo.bff.application.dtos.response.account.statement;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TransferMovementsResponse {

    @JsonProperty("movementDate")
    private String movementDate;
    @JsonProperty("movementTime")
    private String movementTime;
    @JsonProperty("operationNumber")
    private String operationNumber;
    @JsonProperty("status")
    private String status;
    @JsonProperty("clientId")
    private String clientId;
    @JsonProperty("amount")
    private BigDecimal amount;
    @JsonProperty("abbreviated")
    private String abbreviated;
    @JsonProperty("accountEntry")
    private String accountEntry;
    @JsonProperty("accountId")
    private String accountId;
    @JsonProperty("toAccountNumber")
    private String toAccountNumber;
    @JsonProperty("toBankCode")
    private String toBankCode;
    @JsonProperty("toHolder")
    private String toHolder;
    @JsonProperty("toBankName")
    private String toBankName;
    @JsonProperty("description")
    private String description;
}
