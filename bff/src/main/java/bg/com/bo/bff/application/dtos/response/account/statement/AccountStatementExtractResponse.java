package bg.com.bo.bff.application.dtos.response.account.statement;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccountStatementExtractResponse {
    private List<AccountStatementExtract> data;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class AccountStatementExtract {
        private String status;
        private String type;
        private Double amount;
        private String currency;
        private String channel;
        private String dateMov;
        private String timeMov;
        private Double movBalance;
        private String seatNumber;
        private String description;
    }
}
