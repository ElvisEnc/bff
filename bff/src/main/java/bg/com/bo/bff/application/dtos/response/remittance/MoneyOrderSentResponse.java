package bg.com.bo.bff.application.dtos.response.remittance;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MoneyOrderSentResponse {

    private String orderId;
    private String transactionId;
    private String transactionType;
    private String fromHolderName;
    private String fromLastName;
    private String toHolderName;
    private String toPaternalLastName;
    private String toMaternalLastName;
}
