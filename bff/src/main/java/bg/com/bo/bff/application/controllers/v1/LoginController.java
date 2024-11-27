package bg.com.bo.bff.application.controllers.v1;

import bg.com.bo.bff.application.config.request.tracing.AbstractBFFController;
import bg.com.bo.bff.application.dtos.request.login.LogoutRequest;
import bg.com.bo.bff.application.dtos.request.login.LoginRequest;
import bg.com.bo.bff.application.dtos.request.login.RefreshSessionRequest;
import bg.com.bo.bff.application.dtos.response.generic.ErrorResponse;
import bg.com.bo.bff.application.dtos.response.generic.GenericResponse;
import bg.com.bo.bff.application.dtos.response.login.DeviceEnrollmentResponse;
import bg.com.bo.bff.application.dtos.response.login.LoginResponse;
import bg.com.bo.bff.application.dtos.response.login.LoginResult;
import bg.com.bo.bff.application.dtos.response.login.TokenDataResponse;
import bg.com.bo.bff.commons.annotations.OnlyNumber;
import bg.com.bo.bff.commons.annotations.login.ValidBase64;
import bg.com.bo.bff.services.interfaces.ILoginServices;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("api/v1/login")
@Tag(name = "Login Controller", description = "Controlador del Login")
@Validated
public class LoginController extends AbstractBFFController {
    private final ILoginServices iLoginServices;

    @Autowired
    public LoginController(ILoginServices iLoginServices) {
        this.iLoginServices = iLoginServices;
    }

    @Operation(summary = "Login Request", description = "Este es el Endpoint donde el usuario ganamovil hará su petición login y se le devolverá si fue exitoso o fallido")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Login Success, devuelve LoginResponse", content = @Content(schema = @Schema(implementation = LoginResponse.class), mediaType = "application/json")),
            @ApiResponse(responseCode = "401", description = "Login Failed, devuelve un 401 ErrorResponse", content = @Content(schema = @Schema(implementation = ErrorResponse.class), mediaType = "application/json")),
            @ApiResponse(responseCode = "400", description = "Los parámetros proporcionados no son válidos.", content = @Content(schema = @Schema(implementation = ErrorResponse.class), mediaType = "application/json")),
            @ApiResponse(responseCode = "500", description = "Error interno.", content = @Content(schema = @Schema(implementation = ErrorResponse.class), mediaType = "application/json"))
    })
    @PostMapping
    public ResponseEntity<LoginResponse> login(
            @RequestHeader("json-data") @ValidBase64 @Parameter(description = "Json Dispositivo en Base64", example = "IntcImRhdG9zXCI6XCJ0b2Rvc2xvc2RhdG9zZGVsZGlzcG9zaXRpdm9cIn0i") String jsonData,
            @Valid @RequestBody LoginRequest loginRequest
    ) throws IOException {
        getDeviceDataHeader();
        return ResponseEntity.ok(iLoginServices.login(loginRequest));
    }

    @Operation(summary = "Refresh session", description = "Realiza una extension de la sesion, a través de realizar un refresh token.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Se realizo un \"Refresh Token\" satisfactorio.", content = @Content(schema = @Schema(implementation = TokenDataResponse.class), mediaType = "application/json")),
            @ApiResponse(responseCode = "406", description = "Los parámetros proporcionados no son válidos.", content = @Content(schema = @Schema(implementation = ErrorResponse.class), mediaType = "application/json"))
    })
    @PostMapping("/{personId}/refresh")
    public ResponseEntity<TokenDataResponse> refresh(
            @RequestHeader("Authorization") @NotNull(message = "Access JWT must be not null.") @NotBlank(message = "Access JWT must be not empty.") String accessJwt,
            @PathVariable("personId") @OnlyNumber @NotBlank @Parameter(description = "Este es el personId", example = "12345") String personId,
            @Valid @RequestBody RefreshSessionRequest refreshSessionRequest) {
        getDeviceDataHeader();
        return ResponseEntity.ok(iLoginServices.refreshSession(personId, refreshSessionRequest, accessJwt));
    }

    @Operation(summary = "Device Enrollment", description = "Se realiza la validación si un dispositivo está enrrolado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Dispositivo enrolado con un usuario", content = @Content(schema = @Schema(implementation = DeviceEnrollmentResponse.class), mediaType = "application/json")),
            @ApiResponse(responseCode = "500", description = "Error interno", content = @Content(schema = @Schema(implementation = ErrorResponse.class), mediaType = "application/json"))
    })
    @GetMapping("/validate-device")
    public ResponseEntity<DeviceEnrollmentResponse> validateEnrollment() throws IOException {
        getDeviceDataHeader();
        return ResponseEntity.ok(iLoginServices.validation());
    }

    @Operation(summary = "Logout Request", description = "Endpoint para cerrar sesión")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Logout Success", content = @Content(schema = @Schema(implementation = LoginResult.class), mediaType = "application/json")),
            @ApiResponse(responseCode = "400", description = "Error en los parámetros", content = @Content(schema = @Schema(implementation = ErrorResponse.class), mediaType = "application/json")),
            @ApiResponse(responseCode = "500", description = "Error Interno.", content = @Content(schema = @Schema(implementation = ErrorResponse.class), mediaType = "application/json"))
    })
    @PostMapping("/{personId}/logout")
    public ResponseEntity<GenericResponse> logout(
            @PathVariable("personId") @NotNull @Parameter(description = "Este es el código de persona", example = "13021") Integer personId,
            @RequestHeader("person-role-id") @NotNull @Parameter(description = "Este es el personRoleId", example = "50") Integer personRoleId,
            @RequestHeader("Authorization") String authorization,
            @Valid @RequestBody LogoutRequest request
    ) throws IOException {
        getDeviceDataHeader();
        return ResponseEntity.ok(iLoginServices.logout(
                String.valueOf(personId),
                authorization,
                String.valueOf(personRoleId),
                request));
    }
}

