package bg.com.bo.bff.providers.dtos.request.debit.card;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ActivateDebitCardMWRequest {
    private String idPci;
    private String personId;
}
