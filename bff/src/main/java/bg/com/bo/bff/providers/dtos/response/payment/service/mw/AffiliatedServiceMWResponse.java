package bg.com.bo.bff.providers.dtos.response.payment.service.mw;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AffiliatedServiceMWResponse {
    private List<AffiliatedServiceMW> data;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class AffiliatedServiceMW {
        private String serviceCode;
        private String serviceDesc;
        private String referenceName;
        private String nameHolder;
        private String affiliationCode;
        private String internalCod;
        private String year;
        private String descriptionTag;
        private String stateContingency;
        private int idCategory;
        private int idSubCategory;
        private String subCategoryDesc;
    }
}
