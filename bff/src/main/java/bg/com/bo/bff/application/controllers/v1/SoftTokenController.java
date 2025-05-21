package bg.com.bo.bff.application.controllers.v1;

import bg.com.bo.bff.application.config.request.tracing.AbstractBFFController;
import bg.com.bo.bff.application.dtos.request.softtoken.*;
import bg.com.bo.bff.application.dtos.response.generic.GenericResponse;
import bg.com.bo.bff.application.dtos.response.softtoken.*;
import bg.com.bo.bff.commons.annotations.OnlyNumber;
import bg.com.bo.bff.services.interfaces.ISoftTokenService;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
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

    @GetMapping("/persons/{personId}/enrollment-data")
    public ResponseEntity<SoftTokenDataEnrollmentResponse> getDataEnrollment(
            @PathVariable("personId") @OnlyNumber @Parameter(description = "Este es el personId de la persona", example = "12345") String personId
    ) throws IOException {
        getDeviceDataHeader();
        return ResponseEntity.ok(service.getDataEnrollment(personId));
    }

    @GetMapping("/persons/{personId}/enrollment-question")
    public ResponseEntity<List<SoftTokenQuestionEnrollmentResponse>> getQuestionEnrollment(
            @PathVariable("personId") @OnlyNumber @Parameter(description = "Este es el personId de la persona", example = "12345") String personId
    ) throws IOException {
        getDeviceDataHeader();
        return ResponseEntity.ok(service.getQuestionEnrollment(personId));
    }

    @GetMapping("/persons/{personId}/enrollment-validate")
    public ResponseEntity<GenericResponse> getValidationEnrollment(
            @PathVariable("personId") @OnlyNumber @Parameter(description = "Este es el personId de la persona", example = "12345") String personId
        ) throws IOException {
        getDeviceDataHeader();
        return ResponseEntity.ok(service.getValidationEnrollment(personId));
    }

    @PostMapping("/persons/{personId}/enrollment-code")
    public ResponseEntity<GenericResponse> postCodeEnrollment(
            @PathVariable("personId") @OnlyNumber @Parameter(description = "Este es el personId de la persona", example = "12345") String personId,
            @Valid @RequestBody SoftTokenCodeEnrollmentRequest request
        ) throws IOException {
        getDeviceDataHeader();
        return ResponseEntity.ok(service.postCodeEnrollment(personId, request));
    }

    @PostMapping("/persons/{personId}/validate-enrollment-code")
    public ResponseEntity<GenericResponse> validateCodeEnrollment(
            @PathVariable("personId") @OnlyNumber @Parameter(description = "Este es el personId de la persona", example = "12345") String personId,
            @Valid @RequestBody SoftTokenValidateCodeEnrollmentRequest request
        ) throws IOException {
        getDeviceDataHeader();
        return ResponseEntity.ok(service.validateCodeEnrollment(personId, request));
    }

    @PostMapping("/persons/{personId}/question-validate")
    public ResponseEntity<GenericResponse> validateQuestionSecurity(
            @RequestHeader("device-model") @Parameter(description = "Modelo del dispositivo", example = "iphone13") String deviceModel,
            @PathVariable("personId") @OnlyNumber @Parameter(description = "Este es el personId de la persona", example = "12345") String personId,
            @Valid @RequestBody SoftTokenValidationQuestionRequest request
        ) throws IOException {
        getDeviceDataHeader();
        return ResponseEntity.ok(service.validateQuestionSecurity(personId, deviceModel, request));
    }

    @GetMapping("/persons/{personId}/obtain-parameter")
    public ResponseEntity<SoftTokenObtainParametersResponse> getParameters(
        @PathVariable("personId") @OnlyNumber @Parameter(description = "Este es el personId de la persona", example = "12345") String personId
        ) throws IOException {
            getDeviceDataHeader();
            return ResponseEntity.ok(service.getParameters(personId));
    }

    @PostMapping("/persons/{personId}/register-token")
    public ResponseEntity<GenericResponse> postRegistrationToken(
            @PathVariable("personId") @OnlyNumber @Parameter(description = "Este es el personId de la persona", example = "12345") String personId,
            @Valid @RequestBody SoftTokenCodeTokenRequest request
        ) throws IOException {
            getDeviceDataHeader();
            return ResponseEntity.ok(service.postRegistrationToken(personId, request));
    }

    @GetMapping("/persons/{personId}/registration-validate")
    public ResponseEntity<GenericResponse> getRegistrationValidation(
            @PathVariable("personId") @OnlyNumber @Parameter(description = "Este es el personId de la persona", example = "12345") String personId
            ) throws IOException {
            getDeviceDataHeader();
            return ResponseEntity.ok(service.getRegistrationValidation(personId));
    }

    @PostMapping("/persons/{personId}/token-generate")
    public ResponseEntity<SoftTokenGenerateTokenResponse> postTokenGenerate(
            @PathVariable("personId") @OnlyNumber @Parameter(description = "Este es el personId de la persona", example = "12345") String personId
            ) throws IOException {
            getDeviceDataHeader();
            return ResponseEntity.ok(service.postTokenGenerate(personId));
    }

    @PostMapping("/persons/{personId}/enrollment")
    public ResponseEntity<GenericResponse> postEnrollment(
            @RequestHeader("device-model") @Parameter(description = "Modelo del dispositivo", example = "iphone13") String deviceModel,
            @PathVariable("personId") @OnlyNumber @Parameter(description = "Este es el personId de la persona", example = "12345") String personId,
            @Valid @RequestBody SoftTokenEnrollmentRequest request
    ) throws IOException {
        getDeviceDataHeader();
        return ResponseEntity.ok(service.postEnrollment(personId, deviceModel, request));
    }

    @PostMapping("/persons/{personId}/token-validate")
    public ResponseEntity<GenericResponse> validationToken(
            @PathVariable("personId") @OnlyNumber @Parameter(description = "Este es el personId de la persona", example = "12345") String personId,
            @Valid @RequestBody SoftTokenCodeTokenRequest request
    ) throws IOException {
        getDeviceDataHeader();
        return ResponseEntity.ok(service.validationToken(personId, request));
    }

}
