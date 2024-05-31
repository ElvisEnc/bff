
package bg.com.bo.bff.providers.dtos.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class AddAchAccountBasicRequest {


    private String personId;

    private String companyPersonId;

    private String isFavorite;

    private String isEnabled;

    private String reference;

    private String destinationAccountNumber;

    private String destinationBankCode;

    private String destinationBranchOfficeCode;

    private String destinationAccountTypeCode;

    private String destinationHolderName;

    private String destinationIDNumber;

    private String email;
}

