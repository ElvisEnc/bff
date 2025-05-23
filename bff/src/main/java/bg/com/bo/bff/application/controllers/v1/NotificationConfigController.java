package bg.com.bo.bff.application.controllers.v1;

import bg.com.bo.bff.application.config.request.tracing.AbstractBFFController;
import bg.com.bo.bff.application.dtos.request.notification.config.RegisterNotificationRequest;
import bg.com.bo.bff.application.dtos.request.notification.config.SubscribeNotificationRequest;
import bg.com.bo.bff.application.dtos.response.notification.config.NotificationConfigResponse;
import bg.com.bo.bff.application.dtos.response.notification.config.NotificationResponse;
import bg.com.bo.bff.services.interfaces.INotificationConfigService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("api/v1/notifications")
@Tag(name = "Notification Config Controller", description = "Controlador de configuracion de notificaciones")
public class NotificationConfigController extends AbstractBFFController {
    private INotificationConfigService service;

    public NotificationConfigController(HttpServletRequest httpServletRequest, INotificationConfigService iNpsService) {
        this.setHttpServletRequest(httpServletRequest);
        this.service = iNpsService;
    }

    @Operation(
            summary = "Endpoint para suscribir una cuenta a notificaciones",
            description = "Endpoint que permite suscribir una cuenta para las notificaciones, puede optar por " +
                    "habilitar (ON) o puede optar por no habilitar (OFF).")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Indica que la suscripcion fue realizada con exito.",
                    content = @Content(schema = @Schema(implementation = NotificationConfigResponse.class)))
    })
    @PostMapping("/persons/{personId}/subscribe")
    public ResponseEntity<NotificationResponse> subscribeNotification(
            @PathVariable("personId") @NotNull
            @Parameter(description = "Este es el personId de la persona", example = "12345") Integer personId,
            @Valid @RequestBody SubscribeNotificationRequest request
    ) throws IOException {
        getDeviceDataHeader();
        return ResponseEntity.ok(service.subscribeNotification(personId, request));
    }

    @Operation(
            summary = "",
            description = "Obtiene la lista de las configuraciones que se registró para el dispositivo.")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Lista de configuraciones de notificación",
                    content = @Content(schema = @Schema(implementation = NotificationConfigResponse.class)))
    })
    @GetMapping("/persons/{personId}")
    public ResponseEntity<NotificationConfigResponse> getNotificationConfig(
            @PathVariable("personId") @NotNull
            @Parameter(description = "Este es el personId de la persona", example = "12345") Integer personId
    ) throws IOException {
        getDeviceDataHeader();
        return ResponseEntity.ok(service.getNotificationConfig(personId));
    }

    @Operation(
            summary = "Endpoint para activar una notificacion (ON).",
            description = "Endpoint que permite Activar una configuracion/notificación ( ON)")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Indica que la suscripcion fue realizada con exito.",
                    content = @Content(schema = @Schema(implementation = NotificationConfigResponse.class)))
    })
    @PatchMapping("/persons/{personId}/enabled")
    public ResponseEntity<NotificationResponse> enableNotification(
            @PathVariable("personId") @NotNull
            @Parameter(description = "Este es el personId de la persona", example = "12345") Integer personId,
            @Valid @RequestBody RegisterNotificationRequest request
    ) throws IOException {
        getDeviceDataHeader();
        return ResponseEntity.ok(service.enableNotification(personId, request));
    }

    @Operation(
            summary = "Endpoint para desactivar/deshabilitar una notificacion ( OFF ).",
            description = "Endpoint que permite desactivar/deshbailitar una configuracion/notificación ( OFF )")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Indica que la solicitud fue realizada con exito.",
                    content = @Content(schema = @Schema(implementation = NotificationConfigResponse.class)))
    })
    @PatchMapping("/persons/{personId}/configurations/{configurationId}/disabled")
    public ResponseEntity<NotificationResponse> disableNotification(
            @PathVariable("personId") @NotNull
            @Parameter(description = "Este es el personId de la persona", example = "12345") Integer personId,
            @PathVariable("configurationId") @NotNull
            @Parameter(description = "ID de la configuracion a deshabilitar", example = "12345") Integer configurationId
    ) throws IOException {
        getDeviceDataHeader();
        return ResponseEntity.ok(service.disableNotification(personId, configurationId));
    }


}
