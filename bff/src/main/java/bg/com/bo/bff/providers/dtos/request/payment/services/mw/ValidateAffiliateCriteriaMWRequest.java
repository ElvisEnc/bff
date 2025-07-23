package bg.com.bo.bff.providers.dtos.request.payment.services.mw;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ValidateAffiliateCriteriaMWRequest {

    private Integer serviceCode;
    private Integer year;
    private String searchCriteriaId;
    private String searchCriteriaIdAbbreviation;
    private Integer personId;
    private List<SearchField> searchFields;

    @Builder
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class SearchField {

        private String code;
        private String value;
    }
}


