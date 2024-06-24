package bg.com.bo.bff.providers.dtos.response.payment.services;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@AllArgsConstructor
@NoArgsConstructor
@Data
public class SubcategoriesMWResponse {

    private List<SubcategoryMW> data;

    @NoArgsConstructor
    @Data
    @AllArgsConstructor
    @Builder
    public static class SubcategoryMW {

        private Integer subCategoryCod;
        private Integer categoryCod;
        private String description;
        private String hasCity;
    }
}



