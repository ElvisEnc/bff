package bg.com.bo.bff.application.controllers.v1;

import bg.com.bo.bff.application.dtos.request.ChangePasswordRequest;
import bg.com.bo.bff.application.dtos.request.ExportRequest;
import bg.com.bo.bff.application.dtos.response.ErrorResponse;
import bg.com.bo.bff.application.dtos.response.GenericResponse;
import bg.com.bo.bff.models.dtos.accounts.AccountListResponse;
import bg.com.bo.bff.providers.interfaces.IEncryptionProvider;
import bg.com.bo.bff.providers.interfaces.ILoginAGNProvider;
import bg.com.bo.bff.services.interfaces.IAccountService;
import bg.com.bo.bff.services.interfaces.IUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@Validated
@RequestMapping("api/v1/users")
@Tag(name = "User Controller", description = "Controlador de usuario.")
public class UserController {
    private IUserService userService;

    @Autowired
    public UserController(IUserService userService) {
        this.userService = userService;
    }

    @Operation(summary = "Cambio de contraseña de usuario con sesión iniciada.", description = "Cambia la contraseña del usuario solo si tiene la sesión iniciada.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Resultado de la operación y su descripción.", content = @Content(schema = @Schema(implementation = GenericResponse.class), mediaType = "application/json")),
            @ApiResponse(responseCode = "400", description = "Datos inválidos.", content = @Content(schema = @Schema(implementation = ErrorResponse.class), mediaType = "application/json")),
            @ApiResponse(responseCode = "500", description = "Error interno.", content = @Content(schema = @Schema(implementation = ErrorResponse.class), mediaType = "application/json"))
    })
    @PutMapping("/{personId}/change-password")
    public ResponseEntity<GenericResponse> changePassword(
            @PathVariable("personId") @NotBlank @Parameter(description = "Este es el personId", example = "12345") String personId,
            @Valid @RequestHeader("device-id") String deviceId,
            @Valid @RequestHeader("role-person-id") String rolePersonId,
            @Valid @RequestHeader("device-unique-id") String deviceUniqueId,
            @Valid @RequestBody ChangePasswordRequest changePasswordRequest, HttpServletRequest servletRequest
    ) throws IOException {
        String ip = servletRequest.getRemoteAddr();
        return ResponseEntity.ok(userService.changePassword(personId, ip, deviceId, deviceUniqueId, rolePersonId, changePasswordRequest));
    }
}
