package bg.com.bo.bff.providers.dtos.request.debit.card;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateDebitCardSecureMWRequest {
    private String personId;
    private String debitCardNew;
    private String pciId;
    private String acceptInsurance;
    private String email;
    private String requestNumberOld;
}
