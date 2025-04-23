package bg.com.bo.bff.providers.dtos.request.loyalty;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoyaltyGetImagesRequest {
    private List<Ruta> rutas;

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Ruta {
        @JsonProperty("ruta")
        private String filePath;
    }
}
