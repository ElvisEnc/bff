package bg.com.bo.bff.providers.dtos.request.debit.card.mw;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@AllArgsConstructor
@Getter
public class CreateAuthorizationOnlinePurchaseMWRequest {
    private String idPci;
    private String action;
    private String initialDate;
    private String finalDate;
    private Integer amount;
    private Integer intInitial;
    private Integer intFinal;
}
