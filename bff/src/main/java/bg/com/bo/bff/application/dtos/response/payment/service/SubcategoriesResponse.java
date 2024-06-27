package bg.com.bo.bff.application.dtos.response.payment.service;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class SubcategoriesResponse {
    private List<Subcategory> data;

    @AllArgsConstructor
    @NoArgsConstructor
    @Data
    @Builder
    public static class Subcategory {
        @Schema(example = "1", description = " ID de la subcategoria.")
        private Integer id;
        @Schema(example = "1", description = "ID de categoria.")
        private Integer categoryId;
        @Schema(example = "Colegio", description = "Descripción de subcategoria.")
        private String description;
        @Schema(example = "true", description = "Indica si el servicio esta agrupado por ciudad.")
        private boolean hasCity;
    }
}
