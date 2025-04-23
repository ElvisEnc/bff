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
public class LoyaltyImageResponse {

    @Schema(description = "Identificador de la imagen")
    @JsonProperty("identifier")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer identifier;

    @Schema(description = "identificador del id de mongo")
    @JsonProperty("idImageMongo")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String idImageMongo;

    @Schema(description = "Nombre del archivo")
    @JsonProperty("filename")
    private String filename;

    @Schema(description = "Tipo de archivo")
    @JsonProperty("fileType")
    private String fileType;

    @Schema(description = "Contenido del archivo")
    @JsonProperty("fileContent")
    private String fileContent;

    @Schema(description = "Ruta del archivo")
    @JsonProperty("pathImage")
    private String pathImage;
}
