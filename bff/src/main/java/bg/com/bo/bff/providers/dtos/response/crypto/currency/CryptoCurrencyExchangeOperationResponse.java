package bg.com.bo.bff.providers.dtos.response.crypto.currency;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CryptoCurrencyExchangeOperationResponse {
    @JsonProperty("errorCode")
    private String codeError;

    @JsonProperty("errorMessage")
    private String message;

    @JsonProperty("data")
    private OperationResponse data;

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class OperationResponse {

        @JsonProperty("importeDebitado")
        private Double importDebited;

        @JsonProperty("codMoneda")
        private int currency;

        @JsonProperty("nroAsiento")
        private Long seatNo;

        @JsonProperty("idComprobante")
        private int receiptId;

        @JsonProperty("tcCredito")
        private int tcCredit;

        @JsonProperty("tcDebito")
        private Double tcDebit;

        @JsonProperty("nroSucursal")
        private int branch;

        @JsonProperty("fechaAsiento")
        private String dateSeat;

        @JsonProperty("importeItf")
        private Double importItf;

        @JsonProperty("importeAcreditado")
        private Double importAccredited;

    }
}
