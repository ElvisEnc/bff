package bg.com.bo.bff.providers.dtos.response.ach.account.mw;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BankMW {
    private String description;
    private String code;
    private String acronym;
}
