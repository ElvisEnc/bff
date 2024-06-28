package bg.com.bo.bff.providers.dtos.response.payment.service;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class DeleteAffiliateServiceMWResponse {

    private Response data;

    @AllArgsConstructor
    @NoArgsConstructor
    @Data
    public static class Response {
        private String affiliationNewCod;
    }
}
