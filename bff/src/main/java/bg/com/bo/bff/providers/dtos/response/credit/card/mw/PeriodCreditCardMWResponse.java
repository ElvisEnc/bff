package bg.com.bo.bff.providers.dtos.response.credit.card.mw;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PeriodCreditCardMWResponse {
    private List<PeriodMW> data;

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class PeriodMW {
        private Integer idPeriod;
        private String month;
        private String year;
        private String initPeriod;
        private String endPeriod;
    }
}
