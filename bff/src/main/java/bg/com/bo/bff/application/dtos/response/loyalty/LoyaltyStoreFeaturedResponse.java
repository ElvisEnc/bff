package bg.com.bo.bff.application.dtos.response.loyalty;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class LoyaltyStoreFeaturedResponse {
    @Schema(description = "Identificador del comercio")
    @JsonProperty("identifier")
    private String identifier;

    @Schema(description = "Nombre del comercio")
    @JsonProperty("name")
    private String name;

    @Schema(description = "Descripcion del comercio")
    @JsonProperty("description")
    private String description;

    @Schema(description = "Logo")
    @JsonProperty("logo")
    private String logo;

    @Schema(description = "Valor si es destacado")
    @JsonProperty("isFeatured")
    private Integer isFeatured;

    @Schema(description = "Es el identificador de la categoria")
    @JsonProperty("categoryId")
    private String categoryId;

    @Schema(description = "Informacion de la categoria")
    @JsonProperty("category")
    private Category category;

    @Schema(description = "Valor si esta activo")
    @JsonProperty("isActive")
    private Integer isActive;

    @Schema(description = "Valor si es mas barato")
    @JsonProperty("cheaper")
    private Integer cheaper;

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Category {
        @Schema(description = "Es el identificador de la categoria")
        @JsonProperty("categoryId")
        private String categoryId;

        @Schema(description = "Es el nombre de la categoria")
        @JsonProperty("nameCategory")
        private String nameCategory;

        @Schema(description = "Es el icono de la categoria")
        @JsonProperty("iconCategory")
        private String iconCategory;
    }
}
