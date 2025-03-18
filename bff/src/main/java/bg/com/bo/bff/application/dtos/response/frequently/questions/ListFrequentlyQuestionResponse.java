package bg.com.bo.bff.application.dtos.response.frequently.questions;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ListFrequentlyQuestionResponse {
    private List<FrequentlyQuestion> data;

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class FrequentlyQuestion {
        @Schema(description = "Identificador de la pregunta")
        private int questionId;
        @Schema(description = "Pregunta")
        private String title;
        @Schema(description = "Titulo")
        private String question;
        @Schema(description = "Respuestas")
        private List<FrequentlyQuestionResponse> answer;
    }

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class FrequentlyQuestionResponse {
        @Schema(description = "Respuesta de la pregunta")
        private String response;
    }
}
