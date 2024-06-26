package bg.com.bo.bff.providers.dtos.response.debit.card;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateSecureMWResponse {
    private UpdateSecureMW data;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UpdateSecureMW {
        private Integer idPci;
    }
}
