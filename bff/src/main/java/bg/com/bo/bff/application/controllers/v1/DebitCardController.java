package bg.com.bo.bff.application.controllers.v1;

import bg.com.bo.bff.application.dtos.request.debit.card.*;
import bg.com.bo.bff.application.dtos.response.ErrorResponse;
import bg.com.bo.bff.application.dtos.response.GenericResponse;
import bg.com.bo.bff.application.dtos.response.debit.card.ListAccountTDResponse;
import bg.com.bo.bff.application.dtos.response.debit.card.DCDetailResponse;
import bg.com.bo.bff.application.dtos.response.debit.card.ListDebitCardResponse;
import bg.com.bo.bff.application.dtos.response.debitcard.InternetAuthorizationResponse;
import bg.com.bo.bff.commons.utils.Headers;
import bg.com.bo.bff.services.interfaces.IDebitCardService;
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
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("api/v1/debit-cards")
@Tag(name = "Debit Card Controller", description = "Controlador de Tarjeta de Débito")
public class DebitCardController {
    private final HttpServletRequest httpServletRequest;
    private final IDebitCardService service;

    public DebitCardController(HttpServletRequest httpServletRequest, IDebitCardService service) {
        this.httpServletRequest = httpServletRequest;
        this.service = service;
    }

    @Operation(summary = "Modificar límites y cantidad diaria", description = "Este endpoint permite modificar los límites y transacciones diarias de una tarjeta de débito.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Resultado de la operación y su descripción.", content = @Content(schema = @Schema(implementation = GenericResponse.class), mediaType = "application/json")),
            @ApiResponse(responseCode = "400", description = "Datos inválidos.", content = @Content(schema = @Schema(implementation = ErrorResponse.class), mediaType = "application/json")),
            @ApiResponse(responseCode = "500", description = "Error interno.", content = @Content(schema = @Schema(implementation = ErrorResponse.class), mediaType = "application/json"))
    })
    @PatchMapping("/persons/{personId}/cards/{cardId}/limits")
    public ResponseEntity<GenericResponse> changeAmount(
            @RequestHeader("device-id") @NotBlank @Parameter(description = "Este es el Unique deviceId", example = "42ebffbd7c30307d") String deviceId,
            @RequestHeader("device-name") @Parameter(description = "Este es el deviceName", example = "ANDROID") String deviceName,
            @RequestHeader("geo-position-x") @NotBlank @Parameter(description = "Este es el geoPositionX", example = "12.265656") String geoPositionX,
            @RequestHeader("geo-position-y") @NotBlank @Parameter(description = "Este es el geoPositionY", example = "12.454545") String geoPositionY,
            @RequestHeader("app-version") @NotBlank @Parameter(description = "Este es el appVersion", example = "1.3.3") String appVersion,
            @PathVariable("personId") @NotBlank @Parameter(description = "Este es el personId de la persona", example = "12345") String personId,
            @PathVariable("cardId") @NotBlank @Parameter(description = "Este es el pciId de la tarjeta", example = "12345") String cardId,
            @Valid @RequestBody DCLimitsRequest body
    ) throws IOException {
        return ResponseEntity.ok(service.changeAmount(personId, cardId, body, Headers.getParameter(httpServletRequest,
                deviceId,
                deviceName,
                geoPositionX,
                geoPositionY,
                appVersion)));
    }

    @Operation(summary = "Lista de Tarjetas de Débito", description = "Obtiene el listado de las tarjetas de débito")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de tarjetas de débito", content = @Content(schema = @Schema(implementation = ListDebitCardResponse.class), mediaType = "application/json")),
            @ApiResponse(responseCode = "500", description = "Error interno", content = @Content(schema = @Schema(implementation = ErrorResponse.class), mediaType = "application/json"))
    })
    @GetMapping("/persons/{personId}/cards")
    public ResponseEntity<ListDebitCardResponse> getListDebitCard(
            @RequestHeader("device-id") @NotBlank @Parameter(description = "Este es el Unique deviceId", example = "42ebffbd7c30307d") String deviceId,
            @RequestHeader("device-name") @NotBlank @Parameter(description = "Este es el deviceName", example = "ANDROID") String deviceName,
            @RequestHeader("geo-position-x") @NotBlank @Parameter(description = "Este es el geoPositionX", example = "12.265656") String geoPositionX,
            @RequestHeader("geo-position-y") @NotBlank @Parameter(description = "Este es el geoPositionY", example = "12.454545") String geoPositionY,
            @RequestHeader("app-version") @NotBlank @Parameter(description = "Este es el appVersion", example = "1.3.3") String appVersion,
            @PathVariable("personId") @NotNull @Parameter(description = "Este es el personId de la persona", example = "12345") Integer personId
    ) throws IOException {
        return ResponseEntity.ok(service.getListDebitCard(personId, Headers.getParameter(httpServletRequest)));
    }

    @Operation(summary = "Cuentas para Tarjeta de Débito", description = "Obtiene el listado de las cuentas asociadas para una tarjeta de débito")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de las cuentas para una TD", content = @Content(schema = @Schema(implementation = ListAccountTDResponse.class), mediaType = "application/json")),
            @ApiResponse(responseCode = "500", description = "Error interno", content = @Content(schema = @Schema(implementation = ErrorResponse.class), mediaType = "application/json"))
    })
    @GetMapping("/persons/{personId}/cards/{cardId}/accounts")
    public ResponseEntity<ListAccountTDResponse> getListAccount(
            @RequestHeader("device-id") @NotBlank @Parameter(description = "Este es el Unique deviceId", example = "42ebffbd7c30307d") String deviceId,
            @RequestHeader("device-name") @NotBlank @Parameter(description = "Este es el deviceName", example = "ANDROID") String deviceName,
            @RequestHeader("geo-position-x") @NotBlank @Parameter(description = "Este es el geoPositionX", example = "12.265656") String geoPositionX,
            @RequestHeader("geo-position-y") @NotBlank @Parameter(description = "Este es el geoPositionY", example = "12.454545") String geoPositionY,
            @RequestHeader("app-version") @NotBlank @Parameter(description = "Este es el appVersion", example = "1.3.3") String appVersion,
            @PathVariable("personId") @NotNull @Parameter(description = "Este es el personId de la persona", example = "12345") Integer personId,
            @PathVariable() @NotNull @Parameter(description = "Este es el pciId de la tarjeta", example = "12345") Integer cardId
    ) throws IOException {
        return ResponseEntity.ok(service.getAccountsTD(personId, cardId, Headers.getParameter(httpServletRequest)));
    }

    @Operation(summary = "Obtiene el listado de autorizaciones de compras por internet", description = "Este endpoint permite modificar los límites y transacciones diarias de una tarjeta de débito.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Resultado de la operación y su descripción.", content = @Content(schema = @Schema(implementation = InternetAuthorizationResponse.class), mediaType = "application/json")),
            @ApiResponse(responseCode = "400", description = "Datos inválidos.", content = @Content(schema = @Schema(implementation = ErrorResponse.class), mediaType = "application/json")),
            @ApiResponse(responseCode = "500", description = "Error interno.", content = @Content(schema = @Schema(implementation = ErrorResponse.class), mediaType = "application/json"))
    })
    @GetMapping("/persons/{personId}/cards/{cardId}/authorizations")
    public ResponseEntity<InternetAuthorizationResponse> getListAuthorizations(
            @RequestHeader("device-id") @NotBlank @Parameter(description = "Este es el Unique deviceId", example = "42ebffbd7c30307d") String deviceId,
            @RequestHeader("device-name") @Parameter(description = "Este es el deviceName", example = "ANDROID") String deviceName,
            @RequestHeader("geo-position-x") @NotBlank @Parameter(description = "Este es el geoPositionX", example = "12.265656") String geoPositionX,
            @RequestHeader("geo-position-y") @NotBlank @Parameter(description = "Este es el geoPositionY", example = "12.454545") String geoPositionY,
            @RequestHeader("app-version") @NotBlank @Parameter(description = "Este es el appVersion", example = "1.3.3") String appVersion,
            @PathVariable("personId") @NotBlank @Parameter(description = "Este es el personId de la persona", example = "12345") String personId,
            @PathVariable("cardId") @NotBlank @Parameter(description = "Este es el pciId de la tarjeta", example = "12345") String cardId
    ) throws IOException {
        return ResponseEntity.ok(service.getListAuthorizations(personId, cardId, Headers.getParameter(httpServletRequest,
                deviceId,
                deviceName,
                geoPositionX,
                geoPositionY,
                appVersion)));
    }

    @Operation(summary = "Eliminar Compra por Internet", description = "Elimina una autorización de compras por internet")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Authorizacion de compra por internet Eliminada", content = @Content(schema = @Schema(implementation = GenericResponse.class), mediaType = "application/json")),
            @ApiResponse(responseCode = "400", description = "Datos inválidos", content = @Content(schema = @Schema(implementation = ErrorResponse.class), mediaType = "application/json")),
            @ApiResponse(responseCode = "406", description = "Errores de negocio", content = @Content(schema = @Schema(implementation = ErrorResponse.class), mediaType = "application/json")),
            @ApiResponse(responseCode = "500", description = "Error interno", content = @Content(schema = @Schema(implementation = ErrorResponse.class), mediaType = "application/json"))
    })
    @DeleteMapping("/persons/{personId}/cards/{cardId}/authorizations/{authId}")
    public ResponseEntity<GenericResponse> deleteAuthOnlinePurchases(
            @RequestHeader("device-id") @NotBlank @Parameter(description = "Este es el Unique deviceId", example = "42ebffbd7c30307d") String deviceId,
            @RequestHeader("device-name") @NotBlank @Parameter(description = "Este es el deviceName", example = "ANDROID") String deviceName,
            @RequestHeader("geo-position-x") @NotBlank @Parameter(description = "Este es el geoPositionX", example = "12.265656") String geoPositionX,
            @RequestHeader("geo-position-y") @NotBlank @Parameter(description = "Este es el geoPositionY", example = "12.454545") String geoPositionY,
            @RequestHeader("app-version") @NotBlank @Parameter(description = "Este es el appVersion", example = "1.3.3") String appVersion,
            @PathVariable() @NotNull @Parameter(description = "Este es el personId de la persona", example = "12345") Integer personId,
            @PathVariable() @NotNull @Parameter(description = "Este es el pciId de la tarjeta", example = "12345") Integer cardId,
            @PathVariable() @NotNull @Parameter(description = "Este es el id de la compra por internet", example = "12345") Integer authId
    ) throws IOException {
        return ResponseEntity.ok(service.deleteAuthOnlinePurchases(personId, cardId, authId, Headers.getParameter(httpServletRequest)));
    }

    @Operation(summary = "Detalle de la tarjeta", description = "Detalle de una tarjeta de débito.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Resultado de la operación y su descripción.", content = @Content(schema = @Schema(implementation = DCDetailResponse.class), mediaType = "application/json")),
            @ApiResponse(responseCode = "400", description = "Datos inválidos.", content = @Content(schema = @Schema(implementation = ErrorResponse.class), mediaType = "application/json")),
            @ApiResponse(responseCode = "500", description = "Error interno.", content = @Content(schema = @Schema(implementation = ErrorResponse.class), mediaType = "application/json"))
    })
    @GetMapping("/persons/{personId}/cards/{cardId}")
    public ResponseEntity<DCDetailResponse> detail(
            @RequestHeader("device-id") @NotBlank @Parameter(description = "Este es el Unique deviceId", example = "42ebffbd7c30307d") String deviceId,
            @RequestHeader("device-name") @Parameter(description = "Este es el deviceName", example = "ANDROID") String deviceName,
            @RequestHeader("geo-position-x") @NotBlank @Parameter(description = "Este es el geoPositionX", example = "12.265656") String geoPositionX,
            @RequestHeader("geo-position-y") @NotBlank @Parameter(description = "Este es el geoPositionY", example = "12.454545") String geoPositionY,
            @RequestHeader("app-version") @NotBlank @Parameter(description = "Este es el appVersion", example = "1.3.3") String appVersion,
            @PathVariable("personId") @NotBlank @Parameter(description = "Este es el personId de la persona", example = "12345") String personId,
            @PathVariable("cardId") @NotBlank @Parameter(description = "Este es el pciId de la tarjeta", example = "12345") String cardId
    ) throws IOException {
        return ResponseEntity.ok(service.detail(personId, cardId, Headers.getParameter(httpServletRequest,
                deviceId,
                deviceName,
                geoPositionX,
                geoPositionY,
                appVersion)));
    }

    @Operation(summary = "Actualizar estado de la tarjeta", description = "Actualiza el estado de una tarjeta de débito.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Resultado de la operación y su descripción.", content = @Content(schema = @Schema(implementation = GenericResponse.class), mediaType = "application/json")),
            @ApiResponse(responseCode = "400", description = "Datos inválidos.", content = @Content(schema = @Schema(implementation = ErrorResponse.class), mediaType = "application/json")),
            @ApiResponse(responseCode = "500", description = "Error interno.", content = @Content(schema = @Schema(implementation = ErrorResponse.class), mediaType = "application/json"))
    })
    @PatchMapping("/persons/{personId}/cards/{cardId}/lock-status")
    public ResponseEntity<GenericResponse> lockStatus(
            @RequestHeader("device-id") @NotBlank @Parameter(description = "Este es el Unique deviceId", example = "42ebffbd7c30307d") String deviceId,
            @RequestHeader("device-name") @Parameter(description = "Este es el deviceName", example = "ANDROID") String deviceName,
            @RequestHeader("geo-position-x") @NotBlank @Parameter(description = "Este es el geoPositionX", example = "12.265656") String geoPositionX,
            @RequestHeader("geo-position-y") @NotBlank @Parameter(description = "Este es el geoPositionY", example = "12.454545") String geoPositionY,
            @RequestHeader("app-version") @NotBlank @Parameter(description = "Este es el appVersion", example = "1.3.3") String appVersion,
            @PathVariable("personId") @NotBlank @Parameter(description = "Este es el personId de la persona", example = "12345") String personId,
            @PathVariable("cardId") @NotBlank @Parameter(description = "Este es el pciId de la tarjeta", example = "12345") String cardId,
            @Valid @RequestBody DCLockStatusRequest body
    ) throws IOException {
        return ResponseEntity.ok(service.lockStatus(personId, cardId, body, Headers.getParameter(httpServletRequest,
                deviceId,
                deviceName,
                geoPositionX,
                geoPositionY,
                appVersion)));
    }

    @Operation(summary = "Activar Seguro", description = "Activa el seguro de la tarjeta de débito")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Activación del seguro", content = @Content(schema = @Schema(implementation = GenericResponse.class), mediaType = "application/json")),
            @ApiResponse(responseCode = "400", description = "Datos inválidos", content = @Content(schema = @Schema(implementation = ErrorResponse.class), mediaType = "application/json")),
            @ApiResponse(responseCode = "406", description = "Errores de negocio", content = @Content(schema = @Schema(implementation = ErrorResponse.class), mediaType = "application/json")),
            @ApiResponse(responseCode = "500", description = "Error interno", content = @Content(schema = @Schema(implementation = ErrorResponse.class), mediaType = "application/json"))
    })
    @PutMapping("/persons/{personId}/cards/{cardId}/assurance")
    public ResponseEntity<GenericResponse> activeDebitCardAssurance(
            @RequestHeader("device-id") @NotBlank @Parameter(description = "Este es el Unique deviceId", example = "42ebffbd7c30307d") String deviceId,
            @RequestHeader("device-name") @NotBlank @Parameter(description = "Este es el deviceName", example = "ANDROID") String deviceName,
            @RequestHeader("geo-position-x") @NotBlank @Parameter(description = "Este es el geoPositionX", example = "12.265656") String geoPositionX,
            @RequestHeader("geo-position-y") @NotBlank @Parameter(description = "Este es el geoPositionY", example = "12.454545") String geoPositionY,
            @RequestHeader("app-version") @NotBlank @Parameter(description = "Este es el appVersion", example = "1.3.3") String appVersion,
            @PathVariable() @NotNull @Parameter(description = "Este es el personId de la persona", example = "12345") Integer personId,
            @PathVariable() @NotNull @Parameter(description = "Este es el pciId de la tarjeta", example = "12345") Integer cardId,
            @Valid @RequestBody UpdateDebitCardAssuranceRequest request
    ) throws IOException {
        return ResponseEntity.ok(service.activeDebitCardAssurance(personId, cardId, request, Headers.getParameter(httpServletRequest)));
    }

    @Operation(summary = "Activar Tarjeta de Debito", description = "Activa la tarjeta de débito")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Activación de la tarjeta de débito", content = @Content(schema = @Schema(implementation = GenericResponse.class), mediaType = "application/json")),
            @ApiResponse(responseCode = "400", description = "Datos inválidos", content = @Content(schema = @Schema(implementation = ErrorResponse.class), mediaType = "application/json")),
            @ApiResponse(responseCode = "406", description = "Errores de negocio", content = @Content(schema = @Schema(implementation = ErrorResponse.class), mediaType = "application/json")),
            @ApiResponse(responseCode = "500", description = "Error interno", content = @Content(schema = @Schema(implementation = ErrorResponse.class), mediaType = "application/json"))
    })
    @PostMapping("/persons/{personId}/cards/{cardId}/activate")
    public ResponseEntity<GenericResponse> activationDebitCard(
            @RequestHeader("device-id") @NotBlank @Parameter(description = "Este es el Unique deviceId", example = "42ebffbd7c30307d") String deviceId,
            @RequestHeader("device-name") @NotBlank @Parameter(description = "Este es el deviceName", example = "ANDROID") String deviceName,
            @RequestHeader("geo-position-x") @NotBlank @Parameter(description = "Este es el geoPositionX", example = "12.265656") String geoPositionX,
            @RequestHeader("geo-position-y") @NotBlank @Parameter(description = "Este es el geoPositionY", example = "12.454545") String geoPositionY,
            @RequestHeader("app-version") @NotBlank @Parameter(description = "Este es el appVersion", example = "1.3.3") String appVersion,
            @PathVariable("personId") @NotNull @Parameter(description = "Este es el personId de la persona", example = "12345") Integer personId,
            @PathVariable("cardId") @NotNull @Parameter(description = "Este es el pciId de la tarjeta", example = "12345") Integer cardId,
            @Valid @RequestBody ActivateDebitCardRequest request
    ) throws IOException {
        return ResponseEntity.ok(service.activateDebitCard(personId, cardId, request, Headers.getParameter(httpServletRequest)));
    }

    @Operation(summary = "Crear autorización", description = "Crear una autorización para compras por internet")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Authorización para compra por internet creada", content = @Content(schema = @Schema(implementation = GenericResponse.class), mediaType = "application/json")),
            @ApiResponse(responseCode = "400", description = "Datos inválidos.", content = @Content(schema = @Schema(implementation = ErrorResponse.class), mediaType = "application/json")),
            @ApiResponse(responseCode = "500", description = "Error interno.", content = @Content(schema = @Schema(implementation = ErrorResponse.class), mediaType = "application/json"))
    })
    @PutMapping("/persons/{personId}/cards/{cardId}/authorizations")
    public ResponseEntity<GenericResponse> createAuthorizationOnlinePurchase(
            @RequestHeader("device-id") @NotBlank @Parameter(description = "Este es el Unique deviceId", example = "42ebffbd7c30307d") String deviceId,
            @RequestHeader("device-name") @Parameter(description = "Este es el deviceName", example = "ANDROID") String deviceName,
            @RequestHeader("geo-position-x") @NotBlank @Parameter(description = "Este es el geoPositionX", example = "12.265656") String geoPositionX,
            @RequestHeader("geo-position-y") @NotBlank @Parameter(description = "Este es el geoPositionY", example = "12.454545") String geoPositionY,
            @RequestHeader("app-version") @NotBlank @Parameter(description = "Este es el appVersion", example = "1.3.3") String appVersion,
            @PathVariable("personId") @NotBlank @Parameter(description = "Este es el personId de la persona", example = "12345") String personId,
            @PathVariable("cardId") @NotBlank @Parameter(description = "Este es el pciId de la tarjeta", example = "12345") String cardId,
            @Valid @RequestBody CreateAuthorizationOnlinePurchaseRequest request
    ) throws IOException {
        return ResponseEntity.ok(service.createAuthorizationOnlinePurchase(personId, cardId, request, Headers.getParameter(httpServletRequest,
                deviceId,
                deviceName,
                geoPositionX,
                geoPositionY,
                appVersion)));
    }

    @Operation(summary = "Modificar Orden de cuentas", description = "Modifica el orden de cuentas asociadas a una tarjeta de débito.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Resultado de la operación y su descripción.", content = @Content(schema = @Schema(implementation = GenericResponse.class), mediaType = "application/json")),
            @ApiResponse(responseCode = "400", description = "Datos inválidos.", content = @Content(schema = @Schema(implementation = ErrorResponse.class), mediaType = "application/json")),
            @ApiResponse(responseCode = "500", description = "Error interno.", content = @Content(schema = @Schema(implementation = ErrorResponse.class), mediaType = "application/json"))
    })
    @PatchMapping("/persons/{personId}/cards/{cardId}/accounts")
    public ResponseEntity<GenericResponse> modifyAccountsOrder(
            @RequestHeader("device-id") @NotBlank @Parameter(description = "Este es el Unique deviceId", example = "42ebffbd7c30307d") String deviceId,
            @RequestHeader("device-name") @Parameter(description = "Este es el deviceName", example = "ANDROID") String deviceName,
            @RequestHeader("geo-position-x") @NotBlank @Parameter(description = "Este es el geoPositionX", example = "12.265656") String geoPositionX,
            @RequestHeader("geo-position-y") @NotBlank @Parameter(description = "Este es el geoPositionY", example = "12.454545") String geoPositionY,
            @RequestHeader("app-version") @NotBlank @Parameter(description = "Este es el appVersion", example = "1.3.3") String appVersion,
            @PathVariable("personId") @NotBlank @Parameter(description = "Este es el personId de la persona", example = "12345") String personId,
            @PathVariable("cardId") @NotBlank @Parameter(description = "Este es el pciId de la tarjeta", example = "12345") String cardId,
            @Valid @RequestBody DCAccountsOrderRequest body
    ) throws IOException {
        return ResponseEntity.ok(service.modifyAccountsOrder(personId, cardId, body, Headers.getParameter(httpServletRequest,
                deviceId,
                deviceName,
                geoPositionX,
                geoPositionY,
                appVersion)));
    }
}
