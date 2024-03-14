package bg.com.bo.bff.application.controllers.v1;

import bg.com.bo.bff.application.dtos.request.Device;
import bg.com.bo.bff.application.dtos.response.DeviceEnrollmentResponse;
import bg.com.bo.bff.application.dtos.response.LoginResponse;
import bg.com.bo.bff.application.dtos.response.TokenDataResponse;
import bg.com.bo.bff.application.dtos.request.LoginRequest;
import bg.com.bo.bff.application.dtos.request.RefreshSessionRequest;
import bg.com.bo.bff.application.mappings.login.LoginMapper;
import bg.com.bo.bff.application.dtos.response.ErrorResponse;
import bg.com.bo.bff.models.dtos.login.*;
import bg.com.bo.bff.application.exceptions.NotAcceptableException;
import bg.com.bo.bff.application.exceptions.NotHandledResponseException;
import bg.com.bo.bff.services.interfaces.IDeviceEnrollmentService;
import bg.com.bo.bff.services.interfaces.ILoginServices;
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
@RequestMapping("api/v1/login")
@Tag(name = "Login Controller", description = "Controlador del Login")
public class LoginController {

    private ILoginServices iLoginServices;
    private IDeviceEnrollmentService iDeviceEnrollmentService;
    private LoginMapper loginMapper;

    @Autowired
    public LoginController(ILoginServices iLoginServices, IDeviceEnrollmentService iDeviceEnrollmentService, LoginMapper loginMapper) {
        this.iLoginServices = iLoginServices;
        this.iDeviceEnrollmentService=iDeviceEnrollmentService;
        this.loginMapper = loginMapper;
    }

    @Operation(summary = "Login Request", description = "Este es el Endpoint donde el usuario ganamovil hará su petición login y se le devolverá si fue exitoso o fallido")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Login Success, devuelve LoginResponse", content = @Content(schema = @Schema(implementation = LoginResult.class), mediaType = "application/json")),
            @ApiResponse(responseCode = "401", description = "Login Failed, devuelve un 401 ErrorResponse", content = @Content(schema = @Schema(implementation = ErrorResponse.class), mediaType = "application/json")),
            @ApiResponse(responseCode = "406", description = "Los parámetros proporcionados no son válidos.", content = @Content(schema = @Schema(implementation = ErrorResponse.class), mediaType = "application/json")),
            @ApiResponse(responseCode = "500", description = "Error interno.", content = @Content(schema = @Schema(implementation = ErrorResponse.class), mediaType = "application/json"))
    })
    @PostMapping
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest loginRequest, HttpServletRequest servletRequest) throws IOException {
        String ip = servletRequest.getRemoteAddr();
        LoginResult loginResult = iLoginServices.login(loginRequest, ip);

        switch (loginResult.getStatusCode()) {
            case SUCCESS:
                LoginResponse tokenDataResponse = loginMapper.convert(loginResult);
                tokenDataResponse.getUserData().setDocumentNumber("5403164");
                return ResponseEntity.ok(tokenDataResponse);
        }

        throw new NotHandledResponseException("Se recibio una respuesta no controlada.");
    }

    @Operation(summary = "Refresh session", description = "Realiza una extension de la sesion, a través de realizar un refresh token.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Se realizo un \"Refresh Token\" satisfactorio.", content = @Content(schema = @Schema(implementation = TokenDataResponse.class), mediaType = "application/json")),
            @ApiResponse(responseCode = "406", description = "Los parámetros proporcionados no son válidos.", content = @Content(schema = @Schema(implementation = ErrorResponse.class), mediaType = "application/json"))
    })
    @PostMapping("/{personId}/refresh")
    public ResponseEntity<TokenDataResponse> refresh(
            @PathVariable("personId") @NotBlank @Parameter(description = "Este es el personId", example = "12345") String personId,
            @Valid @RequestBody RefreshSessionRequest refreshSessionRequest) {
        RefreshSessionResult refreshSessionResult = iLoginServices.refreshSession(personId, refreshSessionRequest);
        switch (refreshSessionResult.getStatusCode()) {
            case SUCCESS:
                TokenDataResponse tokenDataResponse = loginMapper.convert(refreshSessionResult);
                return ResponseEntity.ok(tokenDataResponse);
            case INVALID_DATA:
                throw new NotAcceptableException("Los campos brindados son invalidos.");
        }

        throw new NotHandledResponseException("Se recibio una respuesta no controlada.");
    }

    @Operation(summary = "Device Enrollment", description = "Se realiza la validación si un dispositivo está enrrolado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Dispositivo enrrolado con un usuario", content = @Content(schema = @Schema(implementation = DeviceEnrollmentResponse.class), mediaType = "application/json")),
            @ApiResponse(responseCode = "500", description = "Error interno", content = @Content(schema = @Schema(implementation = ErrorResponse.class), mediaType = "application/json"))
    })
    @PostMapping("/validate-device")
    public ResponseEntity<DeviceEnrollmentResponse> validateEnrollment(@Valid @RequestBody Device device) throws IOException {
        DeviceEnrollmentResponse response = iDeviceEnrollmentService.validation(device);
        return ResponseEntity.ok(response);
    }
}
