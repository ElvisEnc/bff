package bg.com.bo.bff.providers.dtos.request.remittance.mw;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ValidateAccountMWRequest {

    private String codPerson;
    private int codLanguage;
    private int codApplication;
    private String jtsOidAccount;
}
