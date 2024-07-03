package bg.com.bo.bff.providers.dtos.response.debit.card.mw;


import lombok.AllArgsConstructor;

import lombok.Data;
import lombok.NoArgsConstructor;




@NoArgsConstructor
@Data
public class CreateAuthorizationOnlinePurchaseMWResponse {
    private CreateAuthorizationOnlinePurchaseMW data;

    public  CreateAuthorizationOnlinePurchaseMWResponse(String idPci,
                                                              String message,
                                                              String code) {
        this.data = new CreateAuthorizationOnlinePurchaseMW(idPci,message,code);

    }


    @NoArgsConstructor
    @Data
    @AllArgsConstructor
    public class CreateAuthorizationOnlinePurchaseMW {
        private String idPci;
        private String message;
        private String code;
    }
}

