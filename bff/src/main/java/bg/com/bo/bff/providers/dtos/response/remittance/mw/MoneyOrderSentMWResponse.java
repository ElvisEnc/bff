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
        @JsonProperty("senderNumberId")
        private String senderNumberId;
        @JsonProperty("mtcn")
        private String mtcn;
        @JsonProperty("nameTypeReceiver")
        private String nameTypeReceiver;
        @JsonProperty("firstNameReceiver")
        private String firstNameReceiver;
        @JsonProperty("lastNameReceiver")
        private String lastNameReceiver;
        @JsonProperty("givenNameReceiver")
        private String givenNameReceiver;
        @JsonProperty("paternalNameReceiver")
        private String paternalNameReceiver;
        @JsonProperty("maternalNameReceiver")
        private String maternalNameReceiver;
    }
}
