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
    private String type;
    private BigDecimal amount;
    private String currency;
    private String channel;
    private String dateMov;
    private String timeMov;
    private BigDecimal movBalance;
    private String seatNumber;
    private String description;
}
