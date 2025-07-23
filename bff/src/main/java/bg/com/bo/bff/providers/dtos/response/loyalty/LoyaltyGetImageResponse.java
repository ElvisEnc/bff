package bg.com.bo.bff.providers.dtos.response.loyalty;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoyaltyGetImageResponse {

    @JsonProperty("id")
    private int identifier;

    @JsonProperty("idImagenMongo")
    private String idImageMongo;

    @JsonProperty("nombreArchivo")
    private String filename;

    @JsonProperty("tipoArchivo")
    private String fileType;

    @JsonProperty("archivoContenido")
    private String fileContent;

    @JsonProperty("rutaImagen")
    private String pathImage;
}
