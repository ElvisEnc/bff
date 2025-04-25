package bg.com.bo.bff.providers.dtos.response.certifications;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CertificationPriceMWResponse {

    private List<PriceObj> data;

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class PriceObj{
        private Integer chargeFeeId;
        private Integer eventId;
        private Integer amount;
        private String currencyDes;
        private Integer currencyCode;
        private Integer rangeFrom;
        private Integer rangeTo;
        private String rangeType;
    }

}
