package bg.com.bo.bff.application.controllers.v1;

import bg.com.bo.bff.application.dtos.request.registry.RegistryRequest;
import bg.com.bo.bff.application.dtos.response.registry.BffHandshakeResponse;
import bg.com.bo.bff.application.dtos.response.generic.ErrorResponse;
import bg.com.bo.bff.application.dtos.response.registry.RegistryResponse;
import bg.com.bo.bff.services.interfaces.IRegistryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.security.NoSuchAlgorithmException;

@RestController
@Validated
@RequestMapping("api/v1/registry")
@Tag(name = "Registry Controller", description = "Controlador de registro de dispositivos.")
public class RegistryController {

    private IRegistryService registryService;

    @Autowired
    public RegistryController(IRegistryService registryService) {
        this.registryService = registryService;
    }

    @Operation(summary = "Registro de dispositivo para migración.", description = "Este endpoint registra los datos de un dispositivo que realizo la migración del AGN al NGM.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Registro exitoso.", content = @Content(schema = @Schema(implementation = RegistryResponse.class), mediaType = "application/json")),
            @ApiResponse(responseCode = "401", description = "Registro fallido por fallo en la autenticación del usuario.", content = @Content(schema = @Schema(implementation = ErrorResponse.class), mediaType = "application/json")),
            @ApiResponse(responseCode = "406", description = "Alguno de los parámetros no es válido.", content = @Content(schema = @Schema(implementation = ErrorResponse.class), mediaType = "application/json")),
            @ApiResponse(responseCode = "422", description = "Los parámetros enviados no son válidos por su formato.", content = @Content(schema = @Schema(implementation = ErrorResponse.class), mediaType = "application/json")),
            @ApiResponse(responseCode = "500", description = "Error interno.", content = @Content(schema = @Schema(implementation = ErrorResponse.class), mediaType = "application/json"))
    })
    @SecurityRequirements()
    @PostMapping("/device/migration")
    public ResponseEntity<RegistryResponse> registerDeviceByMigration(@Valid @RequestBody RegistryRequest registryRequest) throws NoSuchAlgorithmException {
        return ResponseEntity.ok(registryService.registerByMigration(registryRequest));
    }

    @SecurityRequirements()
    @GetMapping("/device/handshake")
    public ResponseEntity<BffHandshakeResponse> handshake() {
        return ResponseEntity.ok(registryService.handshake());
    }
}
