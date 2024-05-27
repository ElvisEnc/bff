package bg.com.bo.bff.application.controllers.v1;

import bg.com.bo.bff.application.dtos.request.ChangePasswordRequest;
import bg.com.bo.bff.application.dtos.response.ErrorResponse;
import bg.com.bo.bff.application.dtos.response.GenericResponse;
import bg.com.bo.bff.application.dtos.response.user.ContactResponse;
import bg.com.bo.bff.application.dtos.response.user.PersonalResponse;
import bg.com.bo.bff.commons.utils.Headers;
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
    private HttpServletRequest httpServletRequest;
    @Autowired
    public UserController(IUserService userService, HttpServletRequest httpServletRequest) {
        this.httpServletRequest = httpServletRequest;
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
            @Valid @RequestHeader("user-device-id") String userDeviceId,
            @Valid @RequestBody ChangePasswordRequest changePasswordRequest, HttpServletRequest servletRequest
    ) throws IOException {
        String ip = servletRequest.getRemoteAddr();
        return ResponseEntity.ok(userService.changePassword(personId, ip, deviceId, userDeviceId, rolePersonId, changePasswordRequest));
    }

    @Operation(summary = "Obtener la información de datos de contacto.", description = "Obtiene la información de contacto del Banco Ganadero.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Información de contacto.", content = @Content(schema = @Schema(implementation = ContactResponse.class), mediaType = "application/json")),
            @ApiResponse(responseCode = "500", description = "Error interno.", content = @Content(schema = @Schema(implementation = ErrorResponse.class), mediaType = "application/json"))
    })
    @GetMapping("/contact")
    public ResponseEntity<ContactResponse> getContactDetails(

    ) throws IOException {
        return ResponseEntity.ok(userService.getContactInfo());
    }

    @Operation(summary = "Obtención de información del usuario", description = "Obtiene la información personal de referencia del usuario que la solicita.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Resultado de la operación y su descripción.", content = @Content(schema = @Schema(implementation = PersonalResponse.class), mediaType = "application/json")),
            @ApiResponse(responseCode = "400", description = "Datos inválidos.", content = @Content(schema = @Schema(implementation = ErrorResponse.class), mediaType = "application/json")),
            @ApiResponse(responseCode = "500", description = "Error interno.", content = @Content(schema = @Schema(implementation = ErrorResponse.class), mediaType = "application/json"))
    })
    @GetMapping("/{personId}/info")
    public ResponseEntity<PersonalResponse> getPersonalInformation(
            @PathVariable("personId") @NotBlank @Parameter(description = "Este es el personId", example = "12345") String personId,
            @RequestHeader("device-id") @NotBlank @Parameter(description = "Este es el Unique deviceId", example = "42ebffbd7c30307d") String deviceId,
            @RequestHeader("device-name") @Parameter(description = "Este es el deviceName", example = "ANDROID") String deviceName,
            @RequestHeader("geo-position-x") @NotBlank @Parameter(description = "Este es el geoPositionX", example = "12.265656") String geoPositionX,
            @RequestHeader("geo-position-y") @NotBlank @Parameter(description = "Este es el geoPositionY", example = "12.454545") String geoPositionY,
            @RequestHeader("app-version") @NotBlank @Parameter(description = "Este es el appVersion", example = "1.3.3") String appVersion
    ) throws IOException {
        return ResponseEntity.ok(userService.getPersonalInformation(personId, Headers.getParameter(httpServletRequest,
                deviceId,
                deviceName,
                geoPositionX,
                geoPositionY,
                appVersion)));
    }
}
