package bg.com.bo.bff.providers.dtos.response.crypto.currency;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CryptoCurrencyAccountExtractResponse {
    @JsonProperty("errorCode")
    private String codeError;

    @JsonProperty("errorMessage")
    private String message;

    @Schema(description = "data")
    private List<ExtractResponse> data;

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ExtractResponse {

        @JsonProperty("existsVoucher")
        private boolean existsVoucher;

        @JsonProperty("transactionDate")
        private String transactionDate;

        @JsonProperty("transactionTime")
        private String transactionTime;

        @JsonProperty("amount")
        private String amount;

        @JsonProperty("description")
        private String description;

        @JsonProperty("day")
        private int day;

        @JsonProperty("month")
        private String month;

        @JsonProperty("year")
        private String year;

        @JsonProperty("transactionType")
        private String transactionType;

        @JsonProperty("processDate")
        private String processDate;

        @JsonProperty("branch")
        private String branch;

        @JsonProperty("seatNumber")
        private String seatNumber;

        @JsonProperty("correlative")
        private String correlative;

        @JsonProperty("currentBalance")
        private String currentBalance;

        @JsonProperty("currencySymbol")
        private String currencySymbol;

    }
}
