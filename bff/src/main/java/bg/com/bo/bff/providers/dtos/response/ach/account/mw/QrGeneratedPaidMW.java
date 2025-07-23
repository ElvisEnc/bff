package bg.com.bo.bff.providers.dtos.response.ach.account.mw;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class QrGeneratedPaidMW {
    private String customField;
    private String masterQrId;
    private String qrId;
    private String identificationNumber;
    private String bussinesName;
    private String bankCode;
    private String currency;
    private String expiryDate;
    private String reference;
    private BigDecimal monto;
    private String singleUse;
    private String serviceCode;
    private String serialNumber;
    private String operationTypeId;
    private String accountNumber;
    private String status;
    private String registrationDate;
    private String description;
    private String currencyDescription;
}
