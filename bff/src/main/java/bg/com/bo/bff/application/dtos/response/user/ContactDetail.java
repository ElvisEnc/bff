package bg.com.bo.bff.application.dtos.response.user;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
public class ContactDetail {
    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class SocialNetworks {
        @Schema(description = "URL a la página de Facebook", example = "https://www.facebook.com/BancoGanadero")
        private String facebook;

        @Schema(description = "URL al perfil de LinkedIn", example = "https://www.linkedin.com/company/banco-ganadero-s-a-/")
        private String likedin;

        @Schema(description = "URL al canal de YouTube", example = "https://www.youtube.com/user/BancoGanadero")
        private String youtube;
    }
    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class AttentionLines {
        @Schema(description = "Número de teléfono para ayuda", example = "800 10 10 10")
        private String helpNumber;

        @Schema(description = "Número de teléfono para consultas de crédito", example = "800 10 10 10")
        private String creditNumber;
    }
    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Contact {
        @Schema(description = "Número de contacto de WhatsApp", example = "800 10 10 10")
        private String whatsapp;

        @Schema(description = "Código de país para el número de contacto", example = "591")
        private String countryNumberCode;
    }
}
