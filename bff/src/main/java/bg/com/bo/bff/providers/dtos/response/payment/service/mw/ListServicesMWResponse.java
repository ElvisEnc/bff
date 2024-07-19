package bg.com.bo.bff.providers.dtos.response.payment.service.mw;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class ListServicesMWResponse {
    private List<Service> data;

    @NoArgsConstructor
    @Data
    @AllArgsConstructor
    @Builder
    public static class Service {
        private String serviceCode;
        private String serviceName;
    }
}
