package bg.com.bo.bff.providers.dtos;

import bg.com.bo.bff.models.dtos.TransactionLimit;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class TransactionLimitListMWResponse {
    private TransactionLimit data;
}
