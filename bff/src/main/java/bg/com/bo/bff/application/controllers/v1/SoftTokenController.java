package bg.com.bo.bff.application.controllers.v1;

import bg.com.bo.bff.application.config.request.tracing.AbstractBFFController;
import bg.com.bo.bff.application.dtos.response.softtoken.SoftTokenDataEnrollmentResponse;
import bg.com.bo.bff.application.dtos.response.softtoken.SoftTokenQuestionEnrollmentResponse;
import bg.com.bo.bff.application.dtos.response.softtoken.SoftTokenValidationEnrollmentResponse;
import bg.com.bo.bff.application.dtos.response.softtoken.SoftTokenWelcomeResponse;
import bg.com.bo.bff.commons.annotations.OnlyNumber;
import bg.com.bo.bff.services.interfaces.ISoftTokenService;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@Validated
@RestController
@RequestMapping("api/v1/softtoken")
@Tag(name = "SoftToken Controller", description = "Controlador de GanaPin Digital")
public class SoftTokenController extends AbstractBFFController {
    private final ISoftTokenService service;

    public SoftTokenController(ISoftTokenService service) {
        this.service = service;
    }

    @GetMapping("/persons/{personId}/welcome")
    public ResponseEntity<SoftTokenWelcomeResponse> getWelcomeMessage(
            @PathVariable("personId") @OnlyNumber @Parameter(description = "Este es el personId de la persona", example = "12345") String personId
    ) throws IOException {
        getDeviceDataHeader();
        return ResponseEntity.ok(service.getWelcomeMessage(personId));
    }

    @GetMapping("/persons/{personId}/data-enrollment")
    public ResponseEntity<SoftTokenDataEnrollmentResponse> getDataEnrollment(
            @PathVariable("personId") @OnlyNumber @Parameter(description = "Este es el personId de la persona", example = "12345") String personId
    ) throws IOException {
        getDeviceDataHeader();
        return ResponseEntity.ok(service.getDataEnrollment(personId));
    }

    @GetMapping("/persons/{personId}/question-enrollment")
    public ResponseEntity<List<SoftTokenQuestionEnrollmentResponse>> getQuestionEnrollment(
            @PathVariable("personId") @OnlyNumber @Parameter(description = "Este es el personId de la persona", example = "12345") String personId
    ) throws IOException {
        getDeviceDataHeader();
        return ResponseEntity.ok(service.getQuestionEnrollment(personId));
    }

    @GetMapping("/persons/{personId}/validation-enrollment")
    public ResponseEntity<SoftTokenValidationEnrollmentResponse> getValidationEnrollment(
            @PathVariable("personId") @OnlyNumber @Parameter(description = "Este es el personId de la persona", example = "12345") String personId
    ) throws IOException {
        getDeviceDataHeader();
        return ResponseEntity.ok(service.getValidationEnrollment(personId));
    }

}
