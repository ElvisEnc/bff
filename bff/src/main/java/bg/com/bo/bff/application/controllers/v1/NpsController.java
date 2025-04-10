package bg.com.bo.bff.application.controllers.v1;

import bg.com.bo.bff.application.config.request.tracing.AbstractBFFController;
import bg.com.bo.bff.application.dtos.request.nps.ResponseNpsRequest;
import bg.com.bo.bff.application.dtos.response.nps.NpsResponse;
import bg.com.bo.bff.application.dtos.response.nps.RegisterNpsResponse;
import bg.com.bo.bff.services.interfaces.INpsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@Validated
@RequestMapping("api/v1/nps")
@Tag(name = "NPS Controller", description = "Controlador del módulo de NPS")
public class NpsController extends AbstractBFFController {
    private final HttpServletRequest httpServletRequest;
    private INpsService service;

    public NpsController(HttpServletRequest httpServletRequest, INpsService iNpsService) {
        this.httpServletRequest = httpServletRequest;
        this.service = iNpsService;
    }

    @Operation(
            summary = "Registro de dispositivo NPS",
            description = "Endpoint para el registro de dispositivo para NPS")
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200", description = "Registro exitoso.")}
    )
    @PostMapping("/persons/{personId}")
    public ResponseEntity<RegisterNpsResponse> registerDevice(
            @RequestHeader("device-id")
            @NotBlank
            @Parameter(description = "Este es el Unique deviceId", example = "42ebffbd7c30307d")
            String deviceId,
            @PathVariable("personId")
            @NotNull
            @Parameter(description = "Este es el código de persona", example = "1234567")
            String personId
    ) throws IOException {
        return ResponseEntity.ok(service.registerDevice(personId, deviceId));
    }

    @PostMapping("/persons/{personId}/response-nps")
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200", description = "Servicio para el envío de respuestas de NPS.")}
    )
    public ResponseEntity<NpsResponse> answerNps(
            @PathVariable("personId")
            @NotNull
            @Parameter(description = "Este es el código de persona", example = "1234567")
            int personId,
            @RequestHeader("device-id")
            @NotBlank
            @Parameter(description = "Este es el Unique deviceId", example = "42ebffbd7c30307d")
            String deviceId,
            @Valid @RequestBody ResponseNpsRequest request
    ) throws IOException {
        return ResponseEntity.ok(service.sendAnswerNps(personId, deviceId, request));
    }

}
