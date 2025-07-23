package bg.com.bo.bff.application.controllers.v1;

import bg.com.bo.bff.application.config.request.tracing.AbstractBFFController;
import bg.com.bo.bff.application.dtos.request.user.ChangePasswordRequest;
import bg.com.bo.bff.application.dtos.request.user.UpdateBiometricsRequest;
import bg.com.bo.bff.application.dtos.request.user.UpdateDataUserRequest;
import bg.com.bo.bff.application.dtos.response.user.BiometricsResponse;
import bg.com.bo.bff.application.dtos.response.generic.GenericResponse;
import bg.com.bo.bff.application.dtos.response.user.UpdateBiometricsResponse;
import bg.com.bo.bff.application.dtos.response.user.apiface.DepartmentsResponse;
import bg.com.bo.bff.application.dtos.response.user.apiface.DistrictsResponse;
import bg.com.bo.bff.application.dtos.response.user.ContactResponse;
import bg.com.bo.bff.application.dtos.response.user.EconomicActivityResponse;
import bg.com.bo.bff.application.dtos.response.user.MaritalStatusResponse;
import bg.com.bo.bff.application.dtos.response.user.PersonalResponse;
import bg.com.bo.bff.services.interfaces.IUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@Validated
@RequestMapping("api/v1/users")
@Tag(name = "User Controller", description = "Controlador de usuario.")
public class UserController extends AbstractBFFController {
    private final IUserService userService;

    @Autowired
    public UserController(IUserService userService) {
        this.userService = userService;
    }

    @Operation(summary = "Cambio de contraseña de usuario con sesión iniciada.", description = "Cambia la contraseña del usuario solo si tiene la sesión iniciada.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Resultado de la operación y su descripción.", content = @Content(schema = @Schema(implementation = GenericResponse.class), mediaType = "application/json"))
    })
    @PutMapping("/{personId}/change-password")
    public ResponseEntity<GenericResponse> changePassword(
            @PathVariable("personId") @NotBlank @Parameter(description = "Este es el personId", example = "12345") String personId,
            @Valid @RequestHeader("person-role-id") String personRoleId,
            @Valid @RequestBody ChangePasswordRequest changePasswordRequest
    ) throws IOException {
        getDeviceDataHeader();
        return ResponseEntity.ok(userService.changePassword(personId, personRoleId, changePasswordRequest));
    }

    @Operation(summary = "Estado de Biometría", description = "Obtiene el estado de la biometría y el tipo")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Estado de biometría", content = @Content(schema = @Schema(implementation = BiometricsResponse.class), mediaType = "application/json"))
    })
    @SecurityRequirements()
    @GetMapping("/{personId}/biometric")
    public ResponseEntity<BiometricsResponse> getBiometricStatus(
            @PathVariable("personId") @NotNull @Parameter(description = "Código de Persona", example = "12345") Integer personId
    ) throws IOException {
        getDeviceDataHeader();
        return ResponseEntity.ok(userService.getBiometrics(personId));
    }

    @Operation(summary = "Actualizar Biometría", description = "Actualiza la biometría y el tipo")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Biometría actualizada", content = @Content(schema = @Schema(implementation = UpdateBiometricsResponse.class), mediaType = "application/json"))
    })
    @PutMapping("/{personId}/biometric")
    public ResponseEntity<UpdateBiometricsResponse> updateBiometrics(
            @PathVariable("personId") @NotNull @Parameter(description = "Código de Persona", example = "12345") Integer personId,
            @RequestHeader("json-data") @NotNull(message = "json-data must be not null.") @NotBlank(message = "json-data must be not empty.") @Parameter(description = "Información genérica en formato json encodeado en base64.", example = "50") String jsonData,
            @RequestBody @Valid UpdateBiometricsRequest request
    ) throws IOException {
        getDeviceDataHeader();
        return ResponseEntity.ok(userService.updateBiometrics(personId, request));
    }

    @Operation(summary = "Obtener la información de datos de contacto.", description = "Obtiene la información de contacto del Banco Ganadero.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Información de contacto.", content = @Content(schema = @Schema(implementation = ContactResponse.class), mediaType = "application/json"))
    })
    @SecurityRequirements()
    @GetMapping("/contact")
    public ResponseEntity<ContactResponse> getContactDetails() {
        return ResponseEntity.ok(userService.getContactInfo());
    }

    @Operation(summary = "Obtención de información del usuario", description = "Obtiene la información personal de referencia del usuario que la solicita.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Resultado de la operación y su descripción.", content = @Content(schema = @Schema(implementation = PersonalResponse.class), mediaType = "application/json"))
    })
    @GetMapping("/{personId}/info")
    public ResponseEntity<PersonalResponse> getPersonalInformation(
            @PathVariable("personId") @NotBlank @Parameter(description = "Este es el personId", example = "12345") String personId
    ) throws IOException {
        getDeviceDataHeader();
        return ResponseEntity.ok(userService.getPersonalInformation(personId));
    }

    @Operation(summary = "Actividad Economica", description = "Obtiene la información de la Actividad Economica")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de las actividades economicas", content = @Content(schema = @Schema(implementation = EconomicActivityResponse.class), mediaType = "application/json"))
    })
    @GetMapping("/{personId}/economical-activity")
    public ResponseEntity<EconomicActivityResponse> getEconomicActivity(
            @PathVariable("personId") @NotNull @Parameter(description = "Código de Persona", example = "12345") Integer personId
    ) {
        getDeviceDataHeader();
        return ResponseEntity.ok(userService.getEconomicActivity(personId));
    }

    @Operation(summary = "Obtención del listado de departamentos", description = "Obtiene el listado de los departamentos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Resultado de la operación y su descripción.", content = @Content(schema = @Schema(implementation = DepartmentsResponse.class), mediaType = "application/json"))
    })
    @GetMapping("/departments")
    public ResponseEntity<DepartmentsResponse> getDepartments() throws IOException {
        getDeviceDataHeader();
        return ResponseEntity.ok(userService.getDepartments());
    }

    @Operation(summary = "Obtención del listado de distritos", description = "Obtiene el listado de los distritos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Resultado de la operación y su descripción.", content = @Content(schema = @Schema(implementation = DistrictsResponse.class), mediaType = "application/json"))
    })
    @GetMapping("/departments/{departmentId}/dictricts")
    public ResponseEntity<DistrictsResponse> getDistricts(
            @PathVariable("departmentId") @NotNull @Parameter(description = "Código del departamento", example = "1") String departmentId
    ) throws IOException {
        getDeviceDataHeader();
        return ResponseEntity.ok(userService.getDistricts(departmentId));
    }

    @Operation(summary = "Estados Civiles", description = "Obtiene el listado de los estados civiles")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de estado civil", content = @Content(schema = @Schema(implementation = MaritalStatusResponse.class), mediaType = "application/json"))
    })
    @GetMapping("/marital-statuses")
    public ResponseEntity<MaritalStatusResponse> getMaritalStatus(
    ) {
        getDeviceDataHeader();
        return ResponseEntity.ok(userService.getMaritalStatus());
    }

    @Operation(summary = "Modificacions de datos Personales ", description = "Modificacions de datos personales")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Modificaciones de datos", content = @Content(schema = @Schema(), mediaType = "application/json"))
    })
    @PostMapping("/{personId}/info")
    public ResponseEntity<GenericResponse> updateInfo(
            @PathVariable("personId") @NotNull @Parameter(description = "Código de Persona", example = "12345") String personId,
            @RequestBody @Valid UpdateDataUserRequest request
    ) throws IOException {
        getDeviceDataHeader();
        return ResponseEntity.ok(userService.updateDataUser(personId, request));
    }
}
