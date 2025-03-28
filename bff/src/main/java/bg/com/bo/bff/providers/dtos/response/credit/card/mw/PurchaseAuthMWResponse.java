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
public class PurchaseAuthMWResponse {
    private List<PurchaseAuthMW> data;

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class PurchaseAuthMW {
        private String processDate;
        private String type;
        private String amount;
        private String currency;
        private String status;
        private String origin;
        private String initDate;
        private String endDate;
        private String requestType;
    }
}