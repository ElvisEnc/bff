package bg.com.bo.bff.providers.dtos.request.debit.card;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DeleteAuthPurchaseMWRequest {
    private String identifierTD;
    private String personId;
    private String action;
    private String idPci;
}
