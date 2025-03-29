package bg.com.bo.bff.application.dtos.response.remittance;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CheckRemittanceResponse {

    private String remittanceId;
    private int consultationId;
    private BigDecimal amount;
    private String currencyCode;
    private String originCountry;
    private String originCity;
    private String holderName;
    private String phone;
    private String recipientPhone;
    private String recipientCountry;
    private String recipientCity;
    private String recipientName;
    private String documentNumber;
    private String documentType;
    private String documentExtension;
}
