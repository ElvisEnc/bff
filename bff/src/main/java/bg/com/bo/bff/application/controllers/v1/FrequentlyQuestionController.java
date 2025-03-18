package bg.com.bo.bff.application.controllers.v1;

import bg.com.bo.bff.application.dtos.response.frequently.questions.ListFrequentlyQuestionResponse;
import bg.com.bo.bff.services.interfaces.IFrequentlyQuestionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@Validated
@RestController
@RequestMapping("api/v1/frequently-question")
@Tag(name = "User Question Controller", description = "Controlador de Preguntas Frecuentes")
public class FrequentlyQuestionController {
    private final IFrequentlyQuestionService service;

    public FrequentlyQuestionController(IFrequentlyQuestionService service) {
        this.service = service;
    }

    @Operation(summary = "Obtener las preguntas frecuentes", description = "Obtiene la lista de preguntas frecuentes")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Preguntas frecuentes", content = @Content(schema = @Schema(implementation = ListFrequentlyQuestionResponse.class)))
    })
    @SecurityRequirements()
    @GetMapping("/questions")
    public ResponseEntity<ListFrequentlyQuestionResponse> getFrequentlyQuestion(
    ) throws IOException {
        return ResponseEntity.ok(service.getFrequentlyQuestions());
    }
}