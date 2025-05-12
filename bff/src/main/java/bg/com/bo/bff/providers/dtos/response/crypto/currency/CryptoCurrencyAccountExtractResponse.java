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

        @Schema(description = "existsVoucher")
        private String existsVoucher;
        @Schema(description = "transactionDate")
        private String transactionDate;
        @Schema(description = "transactionTime")
        private String transactionTime;
        @Schema(description = "amount")
        private String amount;
        @Schema(description = "description")
        private String description;
        @Schema(description = "day")
        private int day;
        @Schema(description = "month")
        private String month;
        @Schema(description = "year")
        private String year;
        @Schema(description = "transactionType")
        private String transactionType;
        @Schema(description = "processDate")
        private String processDate;
        @Schema(description = "branch")
        private String branch;
        @Schema(description = "seatNumber")
        private String seatNumber;
        @Schema(description = "correlative")
        private String correlative;
        @Schema(description = "currentBalance")
        private String currentBalance;
        @Schema(description = "currencySymbol")
        private String currencySymbol;

    }
}
