package bg.com.bo.bff.application.dtos.response.crypto.currency;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AccountExtractResponse {

    @Schema(description = "existsVoucher")
    private boolean existsVoucher;

    @Schema(description = "transactionDate")
    private String transactionDate;

    @Schema(description = "transactionTime")
    private String transactionTime;

    @Schema(description = "amount")
    private BigDecimal amount;

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
