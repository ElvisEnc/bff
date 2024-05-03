package bg.com.bo.bff.providers.dtos.requests;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateTransactionLimitMWRequest {
    private String availableTransaction;
    private String transactionPermitDay;

}

