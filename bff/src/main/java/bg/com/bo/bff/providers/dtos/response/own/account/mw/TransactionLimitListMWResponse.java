package bg.com.bo.bff.providers.dtos.response.own.account.mw;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class TransactionLimitListMWResponse {
    private TransactionLimit data;
}
