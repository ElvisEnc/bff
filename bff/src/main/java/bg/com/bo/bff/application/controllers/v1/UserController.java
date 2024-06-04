package bg.com.bo.bff.application.controllers.v1;

import bg.com.bo.bff.application.dtos.request.ChangePasswordRequest;
import bg.com.bo.bff.application.dtos.request.UpdateBiometricsRequest;
import bg.com.bo.bff.application.dtos.response.BiometricsResponse;
import bg.com.bo.bff.application.dtos.response.ErrorResponse;
import bg.com.bo.bff.application.dtos.response.GenericResponse;
import bg.com.bo.bff.application.dtos.response.UpdateBiometricsResponse;
import bg.com.bo.bff.application.dtos.response.user.ContactResponse;
import bg.com.bo.bff.application.dtos.response.apiface.DepartmentsResponse;
import bg.com.bo.bff.application.dtos.response.user.EconomicActivityResponse;
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
import jakarta.validation.constraints.NotNull;
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
    private final IUserService userService;
    private final HttpServletRequest httpServletRequest;

    @Autowired
    public UserController(IUserService userService, HttpServletRequest httpServletRequest) {
        this.userService = userService;
        this.httpServletRequest = httpServletRequest;
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
            @Valid @RequestHeader("device-id") @NotBlank @Parameter(description = "Este es el Unique deviceId", example = "42ebffbd7c30307d") String deviceId,
            @Valid @RequestHeader("device-name") @Parameter(description = "Este es el deviceName", example = "ANDROID") String deviceName,
            @Valid @RequestHeader("geo-position-x") @NotBlank @Parameter(description = "Este es el geoPositionX", example = "12.265656") String geoPositionX,
            @Valid @RequestHeader("geo-position-y") @NotBlank @Parameter(description = "Este es el geoPositionY", example = "12.454545") String geoPositionY,
            @Valid @RequestHeader("app-version") @NotBlank @Parameter(description = "Este es el appVersion", example = "1.3.3") String appVersion,
            @Valid @RequestHeader("person-role-id") String personRoleId,
            @Valid @RequestBody ChangePasswordRequest changePasswordRequest
    ) throws IOException {
        return ResponseEntity.ok(userService.changePassword(personId, personRoleId, changePasswordRequest,
                Headers.getParameter(httpServletRequest,
                        deviceId,
                        deviceName,
                        geoPositionX,
                        geoPositionY,
                        appVersion
                )));
    }

    @Operation(summary = "Estado de Biometría", description = "Obtiene el estado de la biometría y el tipo")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Estado de biometría", content = @Content(schema = @Schema(implementation = BiometricsResponse.class), mediaType = "application/json")),
            @ApiResponse(responseCode = "500", description = "Error interno", content = @Content(schema = @Schema(implementation = ErrorResponse.class), mediaType = "application/json"))
    })
    @GetMapping("/{personId}/biometric")
    public ResponseEntity<BiometricsResponse> getBiometricStatus(
            @RequestHeader("device-id") @NotBlank @Parameter(description = "deviceId del dispositivo", example = "42ebffbd7c30307d") String deviceId,
            @RequestHeader("device-name") @Parameter(description = "nombre del dispositivo", example = "ios") String deviceName,
            @RequestHeader("geo-position-x") @NotBlank @Parameter(description = "geoPositionX", example = "12.265656") String geoPositionX,
            @RequestHeader("geo-position-y") @NotBlank @Parameter(description = "geoPositionY", example = "12.454545") String geoPositionY,
            @RequestHeader("app-version") @NotBlank @Parameter(description = "versión de la App", example = "1.3.3") String appVersion,
            @PathVariable("personId") @NotNull @Parameter(description = "Código de Persona", example = "12345") Integer personId
    ) throws IOException {
        return ResponseEntity.ok(userService.getBiometrics(personId, Headers.getParameter(httpServletRequest)));
    }

    @Operation(summary = "Actualizar Biometría", description = "Actualiza la biometría y el tipo")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Biometría actualizada", content = @Content(schema = @Schema(implementation = UpdateBiometricsResponse.class), mediaType = "application/json")),
            @ApiResponse(responseCode = "400", description = "Error en los parametros", content = @Content(schema = @Schema(implementation = ErrorResponse.class), mediaType = "application/json")),
            @ApiResponse(responseCode = "500", description = "Error interno", content = @Content(schema = @Schema(implementation = ErrorResponse.class), mediaType = "application/json"))
    })
    @PutMapping("/{personId}/biometric")
    public ResponseEntity<UpdateBiometricsResponse> updateBiometrics(
            @RequestHeader("device-id") @NotBlank @Parameter(description = "deviceId del dispositivo", example = "42ebffbd7c30307d") String deviceId,
            @RequestHeader("device-name") @Parameter(description = "nombre del dispositivo", example = "ios") String deviceName,
            @RequestHeader("geo-position-x") @NotBlank @Parameter(description = "geoPositionX", example = "12.265656") String geoPositionX,
            @RequestHeader("geo-position-y") @NotBlank @Parameter(description = "geoPositionY", example = "12.454545") String geoPositionY,
            @RequestHeader("app-version") @NotBlank @Parameter(description = "versión de la App", example = "1.3.3") String appVersion,
            @PathVariable("personId") @NotNull @Parameter(description = "Código de Persona", example = "12345") Integer personId,
            @RequestBody @Valid UpdateBiometricsRequest request
    ) throws IOException {
        return ResponseEntity.ok(userService.updateBiometrics(personId, request, Headers.getParameter(httpServletRequest)));
    }

    @Operation(summary = "Obtener la información de datos de contacto.", description = "Obtiene la información de contacto del Banco Ganadero.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Información de contacto.", content = @Content(schema = @Schema(implementation = ContactResponse.class), mediaType = "application/json")),
            @ApiResponse(responseCode = "500", description = "Error interno.", content = @Content(schema = @Schema(implementation = ErrorResponse.class), mediaType = "application/json"))
    })
    @GetMapping("/contact")
    public ResponseEntity<ContactResponse> getContactDetails() {
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

    @Operation(summary = "Actividad Economica", description = "Obtiene la información de la Actividad Economica")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de las actividades economicas", content = @Content(schema = @Schema(implementation = EconomicActivityResponse.class), mediaType = "application/json")),
            @ApiResponse(responseCode = "500", description = "Error interno", content = @Content(schema = @Schema(implementation = ErrorResponse.class), mediaType = "application/json"))
    })
    @GetMapping("/{personId}/economical-activity")
    public ResponseEntity<EconomicActivityResponse> getEconomicActivity(
            @RequestHeader("device-id") @NotBlank @Parameter(description = "deviceId del dispositivo", example = "42ebffbd7c30307d") String deviceId,
            @RequestHeader("device-name") @Parameter(description = "nombre del dispositivo", example = "ios") String deviceName,
            @RequestHeader("geo-position-x") @NotBlank @Parameter(description = "geoPositionX", example = "12.265656") String geoPositionX,
            @RequestHeader("geo-position-y") @NotBlank @Parameter(description = "geoPositionY", example = "12.454545") String geoPositionY,
            @RequestHeader("app-version") @NotBlank @Parameter(description = "versión de la App", example = "1.3.3") String appVersion,
            @PathVariable("personId") @NotNull @Parameter(description = "Código de Persona", example = "12345") Integer personId
    ) {
        return ResponseEntity.ok(userService.getEconomicActivity(personId, Headers.getParameter(httpServletRequest)));
    }

    @Operation(summary = "Obtención del listado de departamentos", description = "Obtiene el listado de los departamentos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Resultado de la operación y su descripción.", content = @Content(schema = @Schema(implementation = DepartmentsResponse.class), mediaType = "application/json")),
            @ApiResponse(responseCode = "400", description = "Datos inválidos.", content = @Content(schema = @Schema(implementation = ErrorResponse.class), mediaType = "application/json")),
            @ApiResponse(responseCode = "500", description = "Error interno.", content = @Content(schema = @Schema(implementation = ErrorResponse.class), mediaType = "application/json"))
    })
    @GetMapping("/departments")
    public ResponseEntity<DepartmentsResponse> getDepartments(
            @RequestHeader("device-id") @NotBlank @Parameter(description = "Este es el Unique deviceId", example = "42ebffbd7c30307d") String deviceId,
            @RequestHeader("device-name") @Parameter(description = "Este es el deviceName", example = "ANDROID") String deviceName,
            @RequestHeader("geo-position-x") @NotBlank @Parameter(description = "Este es el geoPositionX", example = "12.265656") String geoPositionX,
            @RequestHeader("geo-position-y") @NotBlank @Parameter(description = "Este es el geoPositionY", example = "12.454545") String geoPositionY,
            @RequestHeader("app-version") @NotBlank @Parameter(description = "Este es el appVersion", example = "1.3.3") String appVersion
    ) throws IOException {
        return ResponseEntity.ok(userService.getDepartments(Headers.getParameter(httpServletRequest,
                deviceId,
                deviceName,
                geoPositionX,
                geoPositionY,
                appVersion)));
    }
}
