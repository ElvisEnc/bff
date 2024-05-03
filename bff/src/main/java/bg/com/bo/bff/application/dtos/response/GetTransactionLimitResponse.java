package bg.com.bo.bff.application.dtos.response;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@lombok.Data
@AllArgsConstructor
@NoArgsConstructor
public class GetTransactionLimitResponse {
   private TransactionLimitData data;

}


