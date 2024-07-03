package bg.com.bo.bff.application.dtos.response.own.account;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetTransactionLimitResponse {
    private TransactionLimitData data;
}
