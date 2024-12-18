package bg.com.bo.bff.application.controllers.v1;

import bg.com.bo.bff.application.config.request.tracing.AbstractBFFController;
import bg.com.bo.bff.application.dtos.request.debit.card.*;
import bg.com.bo.bff.application.dtos.response.generic.GenericResponse;
import bg.com.bo.bff.application.dtos.response.debit.card.ListAccountTDResponse;
import bg.com.bo.bff.application.dtos.response.debit.card.DCDetailResponse;
import bg.com.bo.bff.application.dtos.response.debit.card.ListDebitCardResponse;
import bg.com.bo.bff.application.dtos.response.debit.card.InternetAuthorizationResponse;
import bg.com.bo.bff.services.interfaces.IDebitCardService;
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
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("api/v1/debit-cards")
@Tag(name = "Debit Card Controller", description = "Controlador de Tarjeta de Débito")
public class DebitCardController extends AbstractBFFController {
    private final IDebitCardService service;

    public DebitCardController(IDebitCardService service) {
        this.service = service;
    }

    @Operation(summary = "Modificar límites y cantidad diaria", description = "Este endpoint permite modificar los límites y transacciones diarias de una tarjeta de débito.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Resultado de la operación y su descripción.", content = @Content(schema = @Schema(implementation = GenericResponse.class), mediaType = "application/json"))
    })
    @PatchMapping("/persons/{personId}/cards/{cardId}/limits")
    public ResponseEntity<GenericResponse> changeAmount(
            @PathVariable("personId") @NotBlank @Parameter(description = "Este es el personId de la persona", example = "12345") String personId,
            @PathVariable("cardId") @NotBlank @Parameter(description = "Este es el pciId de la tarjeta", example = "12345") String cardId,
            @Valid @RequestBody DCLimitsRequest body
    ) throws IOException {
        getDeviceDataHeader();
        return ResponseEntity.ok(service.changeAmount(personId, cardId, body));
    }

    @Operation(summary = "Lista de Tarjetas de Débito", description = "Obtiene el listado de las tarjetas de débito")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de tarjetas de débito", content = @Content(schema = @Schema(implementation = ListDebitCardResponse.class), mediaType = "application/json"))
    })
    @GetMapping("/persons/{personId}/cards")
    public ResponseEntity<ListDebitCardResponse> getListDebitCard(
            @PathVariable("personId") @NotNull @Parameter(description = "Este es el personId de la persona", example = "12345") Integer personId
    ) throws IOException {
        getDeviceDataHeader();
        return ResponseEntity.ok(service.getListDebitCard(personId));
    }

    @Operation(summary = "Cuentas para Tarjeta de Débito", description = "Obtiene el listado de las cuentas asociadas para una tarjeta de débito")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de las cuentas para una TD", content = @Content(schema = @Schema(implementation = ListAccountTDResponse.class), mediaType = "application/json"))
    })
    @GetMapping("/persons/{personId}/cards/{cardId}/accounts")
    public ResponseEntity<ListAccountTDResponse> getListAccount(
            @PathVariable("personId") @NotNull @Parameter(description = "Este es el personId de la persona", example = "12345") Integer personId,
            @PathVariable() @NotNull @Parameter(description = "Este es el pciId de la tarjeta", example = "12345") Integer cardId
    ) throws IOException {
        getDeviceDataHeader();
        return ResponseEntity.ok(service.getAccountsTD(personId, cardId));
    }

    @Operation(summary = "Obtiene el listado de autorizaciones de compras por internet", description = "Este endpoint permite modificar los límites y transacciones diarias de una tarjeta de débito.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Resultado de la operación y su descripción.", content = @Content(schema = @Schema(implementation = InternetAuthorizationResponse.class), mediaType = "application/json"))
    })
    @GetMapping("/persons/{personId}/cards/{cardId}/authorizations")
    public ResponseEntity<InternetAuthorizationResponse> getListAuthorizations(
            @PathVariable("personId") @NotBlank @Parameter(description = "Este es el personId de la persona", example = "12345") String personId,
            @PathVariable("cardId") @NotBlank @Parameter(description = "Este es el pciId de la tarjeta", example = "12345") String cardId
    ) throws IOException {
        getDeviceDataHeader();
        return ResponseEntity.ok(service.getListAuthorizations(personId, cardId));
    }

    @Operation(summary = "Eliminar Compra por Internet", description = "Elimina una autorización de compras por internet")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Authorizacion de compra por internet Eliminada", content = @Content(schema = @Schema(implementation = GenericResponse.class), mediaType = "application/json"))
    })
    @DeleteMapping("/persons/{personId}/cards/{cardId}/authorizations/{authId}")
    public ResponseEntity<GenericResponse> deleteAuthOnlinePurchases(
            @PathVariable() @NotNull @Parameter(description = "Este es el personId de la persona", example = "12345") Integer personId,
            @PathVariable() @NotNull @Parameter(description = "Este es el pciId de la tarjeta", example = "12345") Integer cardId,
            @PathVariable() @NotNull @Parameter(description = "Este es el id de la compra por internet", example = "12345") Integer authId
    ) throws IOException {
        getDeviceDataHeader();
        return ResponseEntity.ok(service.deleteAuthOnlinePurchases(personId, cardId, authId));
    }

    @Operation(summary = "Detalle de la tarjeta", description = "Detalle de una tarjeta de débito.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Resultado de la operación y su descripción.", content = @Content(schema = @Schema(implementation = DCDetailResponse.class), mediaType = "application/json"))
    })
    @GetMapping("/persons/{personId}/cards/{cardId}")
    public ResponseEntity<DCDetailResponse> detail(
            @PathVariable("personId") @NotBlank @Parameter(description = "Este es el personId de la persona", example = "12345") String personId,
            @PathVariable("cardId") @NotBlank @Parameter(description = "Este es el pciId de la tarjeta", example = "12345") String cardId
    ) throws IOException {
        getDeviceDataHeader();
        return ResponseEntity.ok(service.detail(personId, cardId));
    }

    @Operation(summary = "Actualizar estado de la tarjeta", description = "Actualiza el estado de una tarjeta de débito.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Resultado de la operación y su descripción.", content = @Content(schema = @Schema(implementation = GenericResponse.class), mediaType = "application/json"))
    })
    @PatchMapping("/persons/{personId}/cards/{cardId}/lock-status")
    public ResponseEntity<GenericResponse> lockStatus(
            @PathVariable("personId") @NotBlank @Parameter(description = "Este es el personId de la persona", example = "12345") String personId,
            @PathVariable("cardId") @NotBlank @Parameter(description = "Este es el pciId de la tarjeta", example = "12345") String cardId,
            @Valid @RequestBody DCLockStatusRequest body
    ) throws IOException {
        getDeviceDataHeader();
        return ResponseEntity.ok(service.lockStatus(personId, cardId, body));
    }

    @Operation(summary = "Activar Seguro", description = "Activa el seguro de la tarjeta de débito")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Activación del seguro", content = @Content(schema = @Schema(implementation = GenericResponse.class), mediaType = "application/json"))
    })
    @PutMapping("/persons/{personId}/cards/{cardId}/assurance")
    public ResponseEntity<GenericResponse> activeDebitCardAssurance(
            @PathVariable() @NotNull @Parameter(description = "Este es el personId de la persona", example = "12345") Integer personId,
            @PathVariable() @NotNull @Parameter(description = "Este es el pciId de la tarjeta", example = "12345") Integer cardId,
            @Valid @RequestBody UpdateDebitCardAssuranceRequest request
    ) throws IOException {
        getDeviceDataHeader();
        return ResponseEntity.ok(service.activeDebitCardAssurance(personId, cardId, request));
    }

    @Operation(summary = "Activar Tarjeta de Debito", description = "Activa la tarjeta de débito")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Activación de la tarjeta de débito", content = @Content(schema = @Schema(implementation = GenericResponse.class), mediaType = "application/json"))
    })
    @PostMapping("/persons/{personId}/cards/{cardId}/activate")
    public ResponseEntity<GenericResponse> activationDebitCard(
            @PathVariable("personId") @NotNull @Parameter(description = "Este es el personId de la persona", example = "12345") Integer personId,
            @PathVariable("cardId") @NotNull @Parameter(description = "Este es el pciId de la tarjeta", example = "12345") Integer cardId,
            @Valid @RequestBody ActivateDebitCardRequest request
    ) throws IOException {
        getDeviceDataHeader();
        return ResponseEntity.ok(service.activateDebitCard(personId, cardId, request));
    }

    @Operation(summary = "Crear autorización", description = "Crear una autorización para compras por internet")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Authorización para compra por internet creada", content = @Content(schema = @Schema(implementation = GenericResponse.class), mediaType = "application/json"))
    })
    @PutMapping("/persons/{personId}/cards/{cardId}/authorizations")
    public ResponseEntity<GenericResponse> createAuthorizationOnlinePurchase(
            @PathVariable("personId") @NotBlank @Parameter(description = "Este es el personId de la persona", example = "12345") String personId,
            @PathVariable("cardId") @NotBlank @Parameter(description = "Este es el pciId de la tarjeta", example = "12345") String cardId,
            @Valid @RequestBody CreateAuthorizationOnlinePurchaseRequest request
    ) throws IOException {
        getDeviceDataHeader();
        return ResponseEntity.ok(service.createAuthorizationOnlinePurchase(personId, cardId, request));
    }

    @Operation(summary = "Modificar Orden de cuentas", description = "Modifica el orden de cuentas asociadas a una tarjeta de débito.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Resultado de la operación y su descripción.", content = @Content(schema = @Schema(implementation = GenericResponse.class), mediaType = "application/json"))
    })
    @PatchMapping("/persons/{personId}/cards/{cardId}/accounts")
    public ResponseEntity<GenericResponse> modifyAccountsOrder(
            @PathVariable("personId") @NotBlank @Parameter(description = "Este es el personId de la persona", example = "12345") String personId,
            @PathVariable("cardId") @NotBlank @Parameter(description = "Este es el pciId de la tarjeta", example = "12345") String cardId,
            @Valid @RequestBody DCAccountsOrderRequest body
    ) throws IOException {
        getDeviceDataHeader();
        return ResponseEntity.ok(service.modifyAccountsOrder(personId, cardId, body));
    }

    @Operation(summary = "Cambio de Pin", description = "Modifica el Pin de una tarjeta de débito.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Resultado de la operación y su descripción.", content = @Content(schema = @Schema(implementation = GenericResponse.class), mediaType = "application/json"))
    })
    @PatchMapping("/persons/{personId}/cards/{cardId}/change-pin")
    public ResponseEntity<GenericResponse> changePinCard(
            @PathVariable("personId") @NotBlank @Parameter(description = "Este es el personId de la persona", example = "12345") String personId,
            @PathVariable("cardId") @NotBlank @Parameter(description = "Este es el pciId de la tarjeta", example = "12345") String cardId,
            @Valid @RequestBody ChangePinRequest body
    ) throws IOException {
        getDeviceDataHeader();
        return ResponseEntity.ok(service.changePinCard(personId, cardId, body));
    }
}
