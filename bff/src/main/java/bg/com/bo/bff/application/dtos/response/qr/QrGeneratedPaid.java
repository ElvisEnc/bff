package bg.com.bo.bff.application.dtos.response.qr;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class QrGeneratedPaid {
    private String customField;
    private String qrId;
    private String masterQrId;
    private String identificationNumber;
    private String businessName;
    private String bankCode;
    private String bank;
    private String currencyCode;
    private String currencyDescription;
    private String expiryDate;
    private BigDecimal amount;
    private String description;
    private Boolean singleUse;
    private String serviceCode;
    private String serialNumber;
    private String operationType;
    private Long destinationAccountNumber;
    private String status;
    private String registrationDate;
}
