package bg.com.bo.bff.providers.dtos.request.debit.card;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;

@Builder
@AllArgsConstructor
@Getter
public class CreateAuthorizationOnlinePurchaseMWRequest {
        private String idPci;
        private String action;
        private String initialDate;
        private String finalDate;
        private BigDecimal amount;
        private Integer intInitial;
        private Integer intFinal;

}
