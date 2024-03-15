package bg.com.bo.bff.application.controllers.v1;

import bg.com.bo.bff.application.dtos.request.registry.RegistryRequest;
import bg.com.bo.bff.application.dtos.response.ErrorResponse;
import bg.com.bo.bff.application.dtos.response.RegistryResponse;
import bg.com.bo.bff.services.interfaces.IRegistryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.NoSuchAlgorithmException;

@RestController
@Validated
@RequestMapping("api/v1/registry")
@Tag(name = "Registry Controller", description = "Controlador de registro de dispositivos.")
public class RegistryController {

    @Autowired
    private IRegistryService registryService;

    @Operation(summary = "Registro de dispositivo para migración.", description = "Este endpoint registra los datos de un dispositivo que realizo la migración del AGN al NGM.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Registro exitoso.", content = @Content(schema = @Schema(implementation = RegistryResponse.class), mediaType = "application/json")),
            @ApiResponse(responseCode = "401", description = "Registro fallido por fallo en la autenticación del usuario.", content = @Content(schema = @Schema(implementation = ErrorResponse.class), mediaType = "application/json")),
            @ApiResponse(responseCode = "406", description = "Alguno de los parámetros no es válido.", content = @Content(schema = @Schema(implementation = ErrorResponse.class), mediaType = "application/json")),
            @ApiResponse(responseCode = "422", description = "Los parámetros enviados no son válidos por su formato.", content = @Content(schema = @Schema(implementation = ErrorResponse.class), mediaType = "application/json")),
            @ApiResponse(responseCode = "500", description = "Error interno.", content = @Content(schema = @Schema(implementation = ErrorResponse.class), mediaType = "application/json"))
    })
    @PostMapping("/device/migration")
    public ResponseEntity<RegistryResponse> registerDeviceByMigration(@Valid @RequestBody RegistryRequest registryRequest) throws NoSuchAlgorithmException {
        return ResponseEntity.ok(registryService.registerByMigration(registryRequest));
    }
}
