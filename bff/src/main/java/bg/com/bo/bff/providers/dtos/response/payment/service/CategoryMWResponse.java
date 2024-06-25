package bg.com.bo.bff.providers.dtos.response.payment.service;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CategoryMWResponse {
    private List<CategoryMW> data;

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class CategoryMW{
        private String idType;
        private String description;
        private String idCategory;
        private String detail;
    }
}
