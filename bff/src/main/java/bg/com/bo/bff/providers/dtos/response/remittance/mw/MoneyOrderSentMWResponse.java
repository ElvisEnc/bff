package bg.com.bo.bff.providers.dtos.response.remittance.mw;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MoneyOrderSentMWResponse {
    private List<MoneyOrderSentMWResponse.MoneyOrderSentMW> data;

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class MoneyOrderSentMW {
        @JsonProperty("idNumber")
        private String idNumber;
        @JsonProperty("mtcn")
        private String mtcn;
        @JsonProperty("rNameTypeReceiver")
        private String rNameTypeReceiver;
        @JsonProperty("rFirstNameReceiver")
        private String rFirstNameReceiver;
        @JsonProperty("rLastNameReceiver")
        private String rLastNameReceiver;
        @JsonProperty("rGivenNameReceiver")
        private String rGivenNameReceiver;
        @JsonProperty("rPaternalNameReceiver")
        private String rPaternalNameReceiver;
        @JsonProperty("rMaternalNameReceiver")
        private String rMaternalNameReceiver;
    }
}
