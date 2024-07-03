package bg.com.bo.bff.providers.dtos.response.ach.account.mw;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BanksMWResponse {
    private List<BankMW> data;
}


