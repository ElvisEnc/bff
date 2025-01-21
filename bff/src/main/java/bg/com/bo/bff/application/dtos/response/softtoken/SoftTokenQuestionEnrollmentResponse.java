package bg.com.bo.bff.application.dtos.response.softtoken;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SoftTokenQuestionEnrollmentResponse {
        @Schema(description = "Pregunta")
        @JsonProperty("question")
        private String question;

        @Schema(description = "Formato")
        @JsonProperty("format")
        private String format;

        @Schema(description = "Texto de ayuda")
        @JsonProperty("textHelp")
        private String textHelp;
}
