package bg.com.bo.bff.providers.dtos.request.transfers.programming;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SaveTransferMDWRequest {

    private String personId;
    private String originJtsOid;
    private String destinationAccountNumber;
    private String amount;
    private String currency;
    private String transferType;
    private String description;
    private String frequencyCode;
    private String initDate;
    private String endDate;
    private String userRegistry;
    private String destinationJtsOid;
    private String listCode;
    private String codEif;
    private String destinationFounds;
    private String originFounds;
    private String reason;
    private String destinationName;
    private String bankName;
    private String uifControl;

}
