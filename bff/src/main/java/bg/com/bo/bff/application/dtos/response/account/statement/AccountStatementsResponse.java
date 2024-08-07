package bg.com.bo.bff.application.dtos.response.account.statement;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccountStatementsResponse {
    private String status;
    private String movementType;
    private BigDecimal amount;
    private String currencyCode;
    private String description;
    private BigDecimal balance;
    private String movementDate;
    private String movementTime;
    private String channel;
    private String seatNumber;
}
