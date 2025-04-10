package bg.com.bo.bff.application.controllers.v1;

import bg.com.bo.bff.application.config.request.tracing.AbstractBFFController;
import bg.com.bo.bff.application.dtos.response.generic.GenericResponse;
import bg.com.bo.bff.application.dtos.response.onboarding.manager.OnboardingManagerResponse;
import bg.com.bo.bff.providers.dtos.response.generic.ApiDataResponse;
import bg.com.bo.bff.services.interfaces.IOnboardingManagerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
@Validated
@RequestMapping("api/v1/onboarding-manager")
@Tag(name = "QR Controller", description = "Controlador del módulo de Nps")
public class OnboardingManagerController extends AbstractBFFController {
    private final HttpServletRequest httpServletRequest;
    private IOnboardingManagerService service;

    public OnboardingManagerController(HttpServletRequest httpServletRequest,
                                       IOnboardingManagerService iOnboardingManagerService) {
        this.httpServletRequest = httpServletRequest;
        this.service = iOnboardingManagerService;
    }

    @Operation(
            summary = "Obtener dispositivos",
            description = "Endpoint para obtener el listado de dispositivo.")
    @ApiResponses(
            value = {@ApiResponse(responseCode = "200")}
    )
    @GetMapping("/persons/{personId}/devices")
    public ResponseEntity<ApiDataResponse<List<OnboardingManagerResponse>>> getAllDevices(
            @PathVariable("personId")
            @NotNull
            @Parameter(description = "Este es el código de persona", example = "1234567")
            int personId
    ) throws IOException {
        return ResponseEntity.ok(ApiDataResponse.of(service.getAllDevices(personId)));
    }

    @Operation(summary = "Deshabilitar dispositivos.", description = "Endpoint para el Desactivar el dispositivo.")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Transaccion exitos.")})
    @PostMapping("/persons/{personId}/devices/{deviceId}/disable")
    public ResponseEntity<GenericResponse> disableDevice(
            @PathVariable("personId")
            @NotNull
            @Parameter(description = "Código de la persona", example = "1234567")
            int personId,
            @PathVariable("deviceId")
            @NotNull
            @Parameter(description = "Id del dispositivo a deshabilitar.", example = "9864531245sa")
            String deviceId
    ) throws IOException {
        return ResponseEntity.ok(service.disableDevice(personId, deviceId));
    }


}
