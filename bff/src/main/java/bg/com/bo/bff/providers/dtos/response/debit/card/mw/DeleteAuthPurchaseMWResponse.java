package bg.com.bo.bff.providers.dtos.response.debit.card.mw;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DeleteAuthPurchaseMWResponse {
    private DeleteAuthData data;

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class DeleteAuthData {
        private String idPci;
        private String message;
        private String code;
    }
}
