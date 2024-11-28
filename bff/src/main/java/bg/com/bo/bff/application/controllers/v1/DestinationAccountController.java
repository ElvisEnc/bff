package bg.com.bo.bff.application.controllers.v1;

import bg.com.bo.bff.application.dtos.request.destination.account.*;
import bg.com.bo.bff.application.dtos.response.destination.account.*;
import bg.com.bo.bff.application.dtos.response.generic.ErrorResponse;
import bg.com.bo.bff.application.dtos.response.generic.GenericResponse;
import bg.com.bo.bff.commons.annotations.OnlyNumber;
import bg.com.bo.bff.commons.annotations.ValidText;
import bg.com.bo.bff.commons.annotations.destination.account.ValidAccountType;
import bg.com.bo.bff.commons.utils.Headers;
import bg.com.bo.bff.services.interfaces.IDestinationAccountService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@Validated
@RequestMapping("api/v1/destination-accounts")
@Tag(name = "Destination Account Controller", description = "Controlador de cuentas destinoo")
public class DestinationAccountController {

    private final HttpServletRequest httpServletRequest;

    private final IDestinationAccountService service;

    public DestinationAccountController(HttpServletRequest httpServletRequest, IDestinationAccountService service) {
        this.httpServletRequest = httpServletRequest;
        this.service = service;
    }

    @Operation(summary = "Agendar nueva cuenta de destino terceros.", description = "Agendar nueva cuenta de destino terceros.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Resultado de la operación y su descripción.", content = @Content(schema = @Schema(implementation = AddAccountResponse.class), mediaType = "application/json")),
            @ApiResponse(responseCode = "400", description = "Datos inválidos.", content = @Content(schema = @Schema(implementation = ErrorResponse.class), mediaType = "application/json")),
            @ApiResponse(responseCode = "500", description = "Error interno.", content = @Content(schema = @Schema(implementation = ErrorResponse.class), mediaType = "application/json"))
    })
    @PutMapping("/{personId}/third-accounts")
    public ResponseEntity<AddAccountResponse> addThirdAccounts(
            @RequestHeader("device-id") @NotBlank @Parameter(description = "Este es el Unique deviceId", example = "42ebffbd7c30307d") String deviceId,
            @RequestHeader("device-name") @Parameter(description = "Este es el deviceName", example = "ANDROID") String deviceName,
            @RequestHeader("geo-position-x") @NotBlank @Parameter(description = "Este es el geoPositionX", example = "12.265656") String geoPositionX,
            @RequestHeader("geo-position-y") @NotBlank @Parameter(description = "Este es el geoPositionY", example = "12.454545") String geoPositionY,
            @RequestHeader("app-version") @NotBlank @Parameter(description = "Este es el appVersion", example = "1.3.3") String appVersion,
            @Parameter(description = "Este es el personId", example = "1234567")
            @PathVariable("personId")
            @NotBlank
            String personId,
            @Valid @RequestBody AddThirdAccountRequest addThirdAccountRequest) throws IOException {
        return ResponseEntity.ok(service.addThirdAccount(personId, addThirdAccountRequest, Headers.getParameter(httpServletRequest,
                deviceId,
                deviceName,
                geoPositionX,
                geoPositionY,
                appVersion
        )));
    }

    @Operation(summary = "Agendar nueva cuenta de destino Billetera.", description = "Agendar nueva cuenta de destino Billetera.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Resultado de la operación y su descripción.", content = @Content(schema = @Schema(implementation = AddAccountResponse.class), mediaType = "application/json")),
            @ApiResponse(responseCode = "400", description = "Datos inválidos.", content = @Content(schema = @Schema(implementation = ErrorResponse.class), mediaType = "application/json")),
            @ApiResponse(responseCode = "500", description = "Error interno.", content = @Content(schema = @Schema(implementation = ErrorResponse.class), mediaType = "application/json"))
    })
    @PutMapping("/{personId}/wallets")
    public ResponseEntity<AddAccountResponse> addWalletAccounts(
            @RequestHeader("device-id") @NotBlank @Parameter(description = "Este es el Unique deviceId", example = "42ebffbd7c30307d") String deviceId,
            @RequestHeader("device-name") @Parameter(description = "Este es el deviceName", example = "ANDROID") String deviceName,
            @RequestHeader("geo-position-x") @NotBlank @Parameter(description = "Este es el geoPositionX", example = "12.265656") String geoPositionX,
            @RequestHeader("geo-position-y") @NotBlank @Parameter(description = "Este es el geoPositionY", example = "12.454545") String geoPositionY,
            @RequestHeader("app-version") @NotBlank @Parameter(description = "Este es el appVersion", example = "1.3.3") String appVersion,
            @Parameter(description = "Este es el personId", example = "1234567")
            @PathVariable("personId")
            @NotBlank
            String personId,
            @Valid @RequestBody AddWalletAccountRequest addWalletAccountRequest) throws IOException {
        return ResponseEntity.ok(service.addWalletAccount(personId, addWalletAccountRequest, Headers.getParameter(httpServletRequest,
                deviceId,
                deviceName,
                geoPositionX,
                geoPositionY,
                appVersion
        )));
    }

    @Operation(summary = "Agendar nueva cuenta de destino ACH.", description = "Agendar nueva cuenta de destino ACH.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Resultado de la operación y su descripción.", content = @Content(schema = @Schema(implementation = AddAccountResponse.class), mediaType = "application/json")),
            @ApiResponse(responseCode = "400", description = "Datos inválidos.", content = @Content(schema = @Schema(implementation = ErrorResponse.class), mediaType = "application/json")),
            @ApiResponse(responseCode = "500", description = "Error interno.", content = @Content(schema = @Schema(implementation = ErrorResponse.class), mediaType = "application/json"))
    })
    @PutMapping("/{personId}/ach-accounts")
    public ResponseEntity<AddAccountResponse> addAchAccounts(
            @RequestHeader("device-id") @NotBlank @Parameter(description = "Este es el Unique deviceId", example = "42ebffbd7c30307d") String deviceId,
            @RequestHeader("device-name") @Parameter(description = "Este es el deviceName", example = "ANDROID") String deviceName,
            @RequestHeader("geo-position-x") @NotBlank @Parameter(description = "Este es el geoPositionX", example = "12.265656") String geoPositionX,
            @RequestHeader("geo-position-y") @NotBlank @Parameter(description = "Este es el geoPositionY", example = "12.454545") String geoPositionY,
            @RequestHeader("app-version") @NotBlank @Parameter(description = "Este es el appVersion", example = "1.3.3") String appVersion,
            @Parameter(description = "Este es el personId", example = "1234567")
            @PathVariable("personId")
            @NotBlank
            String personId,
            @Valid @RequestBody AddAchAccountRequest addAchAccountRequest) throws IOException {
        return ResponseEntity.ok(service.addAchAccount(personId, addAchAccountRequest, Headers.getParameter(httpServletRequest,
                deviceId,
                deviceName,
                geoPositionX,
                geoPositionY,
                appVersion
        )));
    }

    @Operation(summary = "Agendar nueva cuenta desde QR.", description = "Agendar nueva cuenta al leer un QR.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Resultado de la operación y su descripción.", content = @Content(schema = @Schema(implementation = GenericResponse.class), mediaType = "application/json")),
            @ApiResponse(responseCode = "400", description = "Datos inválidos.", content = @Content(schema = @Schema(implementation = ErrorResponse.class), mediaType = "application/json")),
            @ApiResponse(responseCode = "500", description = "Error interno.", content = @Content(schema = @Schema(implementation = ErrorResponse.class), mediaType = "application/json"))
    })
    @PostMapping("/{personId}/qrs/{bankType}")
    public ResponseEntity<GenericResponse> addQRAccounts(
            @RequestHeader("device-id") @NotBlank @Parameter(description = "Este es el Unique deviceId", example = "42ebffbd7c30307d") String deviceId,
            @RequestHeader("device-name") @Parameter(description = "Este es el deviceName", example = "ANDROID") String deviceName,
            @RequestHeader("geo-position-x") @NotBlank @Parameter(description = "Este es el geoPositionX", example = "12.265656") String geoPositionX,
            @RequestHeader("geo-position-y") @NotBlank @Parameter(description = "Este es el geoPositionY", example = "12.454545") String geoPositionY,
            @RequestHeader("app-version") @NotBlank @Parameter(description = "Este es el appVersion", example = "1.3.3") String appVersion,
            @PathVariable("personId") @NotBlank @Parameter(description = "Este es el id de la persona", example = "12345") String personId,
            @PathVariable("bankType") @NotBlank @Parameter(description = "Este es el tipo de banco", example = "Third") String bankType,
            @RequestBody AddQRAccountRequest addQRAccountRequest) throws IOException {
        return ResponseEntity.ok(service.addQRAccount(personId, bankType, addQRAccountRequest, Headers.getParameter(httpServletRequest,
                deviceId,
                deviceName,
                geoPositionX,
                geoPositionY,
                appVersion
        )));
    }

    @Operation(summary = "Lista de entidades financieras.", description = "Lista de entidades financieras.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Resultado de la operación y su descripción.", content = @Content(schema = @Schema(implementation = BanksResponse.class), mediaType = "application/json")),
            @ApiResponse(responseCode = "400", description = "Datos inválidos.", content = @Content(schema = @Schema(implementation = ErrorResponse.class), mediaType = "application/json")),
            @ApiResponse(responseCode = "500", description = "Error interno.", content = @Content(schema = @Schema(implementation = ErrorResponse.class), mediaType = "application/json"))
    })
    @GetMapping("/banks")
    public ResponseEntity<BanksResponse> getBanks(
            @RequestHeader("device-id") @NotBlank @Parameter(description = "Este es el Unique deviceId", example = "42ebffbd7c30307d") String deviceId,
            @RequestHeader("device-name") @NotBlank @Parameter(description = "Este es el deviceName", example = "ANDROID") String deviceName,
            @RequestHeader("geo-position-x") @NotBlank @Parameter(description = "Este es el geoPositionX", example = "12.265656") String geoPositionX,
            @RequestHeader("geo-position-y") @NotBlank @Parameter(description = "Este es el geoPositionY", example = "12.454545") String geoPositionY,
            @RequestHeader("app-version") @NotBlank @Parameter(description = "Este es el appVersion", example = "1.3.3") String appVersion
    ) throws IOException {
        return ResponseEntity.ok(service.getBanks(Headers.getParameter(httpServletRequest,
                deviceId,
                deviceName,
                geoPositionX,
                geoPositionY,
                appVersion
        )));
    }

    @Operation(summary = "Obtener el listado de Sucursales", description = "Este endpoint obtiene el listado de Sucursales de Otros Bancos.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Resultado de la operación y su descripción.", content = @Content(schema = @Schema(implementation = BranchOfficeResponse.class), mediaType = "application/json")),
            @ApiResponse(responseCode = "400", description = "Datos inválidos.", content = @Content(schema = @Schema(implementation = ErrorResponse.class), mediaType = "application/json")),
            @ApiResponse(responseCode = "500", description = "Error interno.", content = @Content(schema = @Schema(implementation = ErrorResponse.class), mediaType = "application/json"))
    })
    @GetMapping("/banks/{bankCode}/branch-offices")
    public ResponseEntity<BranchOfficeResponse> getListBranchOffice(
            @RequestHeader("device-id") @NotBlank @Parameter(description = "Este es el Unique deviceId", example = "42ebffbd7c30307d") String deviceId,
            @RequestHeader("device-name") @NotBlank @Parameter(description = "Este es el deviceName", example = "ANDROID") String deviceName,
            @RequestHeader("geo-position-x") @NotBlank @Parameter(description = "Este es el geoPositionX", example = "12.265656") String geoPositionX,
            @RequestHeader("geo-position-y") @NotBlank @Parameter(description = "Este es el geoPositionY", example = "12.454545") String geoPositionY,
            @RequestHeader("app-version") @NotBlank @Parameter(description = "Este es el appVersion", example = "1.3.3") String appVersion,
            @PathVariable("bankCode") @OnlyNumber @Parameter(description = "Este es el código del banco", example = "1017") String bankCode
    ) throws IOException {
        return ResponseEntity.ok(service.getBranchOffice(bankCode, Headers.getParameter(httpServletRequest,
                deviceId,
                deviceName,
                geoPositionX,
                geoPositionY,
                appVersion
        )));
    }

    @Operation(summary = "Obtiener lista de tipos de cuenta.", description = "Obtiene un listado de todos los tipos de cuentas.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Resultado de la operación y su descripción.", content = @Content(schema = @Schema(implementation = AccountTypeListResponse.class), mediaType = "application/json")),
            @ApiResponse(responseCode = "500", description = "Error interno.", content = @Content(schema = @Schema(implementation = ErrorResponse.class), mediaType = "application/json"))
    })
    @GetMapping("/account-types")
    public ResponseEntity<AccountTypeListResponse> accountTypes() {
        return ResponseEntity.ok(service.accountTypes());
    }

    @Operation(summary = "Eliminación de cuenta de terceros.", description = "Elimina cuenta de terceros.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Resultado de la operación y su descripción.", content = @Content(schema = @Schema(implementation = GenericResponse.class), mediaType = "application/json")),
            @ApiResponse(responseCode = "400", description = "Datos inválidos.", content = @Content(schema = @Schema(implementation = ErrorResponse.class), mediaType = "application/json")),
            @ApiResponse(responseCode = "500", description = "Error interno.", content = @Content(schema = @Schema(implementation = ErrorResponse.class), mediaType = "application/json"))
    })
    @PostMapping("/{personId}/third-accounts/{identifier}/delete")
    public ResponseEntity<GenericResponse> deleteThirdAccount(
            @RequestHeader("device-id") @NotBlank @Parameter(description = "Este es el Unique deviceId", example = "42ebffbd7c30307d") String deviceId,
            @RequestHeader("device-name") @NotBlank @Parameter(description = "Este es el deviceName", example = "ANDROID") String deviceName,
            @RequestHeader("geo-position-x") @NotBlank @Parameter(description = "Este es el geoPositionX", example = "12.265656") String geoPositionX,
            @RequestHeader("geo-position-y") @NotBlank @Parameter(description = "Este es el geoPositionY", example = "12.454545") String geoPositionY,
            @RequestHeader("app-version") @NotBlank @Parameter(description = "Este es el appVersion", example = "1.3.3") String appVersion,
            @PathVariable("personId") @OnlyNumber @Parameter(description = "Este es el personId", example = "12345") String personId,
            @PathVariable("identifier") @NotNull @Min(value = 1) @Parameter(description = "Este es el id de la cuenta", example = "12345") long identifier,
            @RequestBody DeleteAccountRequest request) throws IOException {
        return ResponseEntity.ok(service.deleteThirdAccount(personId, identifier, request, Headers.getParameter(httpServletRequest,
                deviceId,
                deviceName,
                geoPositionX,
                geoPositionY,
                appVersion
        )));
    }

    @Operation(summary = "Eliminación de cuenta billetera.", description = "Elimina cuenta de billetera.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Resultado de la operación y su descripción.", content = @Content(schema = @Schema(implementation = GenericResponse.class), mediaType = "application/json")),
            @ApiResponse(responseCode = "400", description = "Datos inválidos.", content = @Content(schema = @Schema(implementation = ErrorResponse.class), mediaType = "application/json")),
            @ApiResponse(responseCode = "500", description = "Error interno.", content = @Content(schema = @Schema(implementation = ErrorResponse.class), mediaType = "application/json"))
    })
    @PostMapping("/{personId}/wallets/{identifier}/delete")
    public ResponseEntity<GenericResponse> deleteWalletAccount(
            @RequestHeader("device-id") @NotBlank @Parameter(description = "Este es el Unique deviceId", example = "42ebffbd7c30307d") String deviceId,
            @RequestHeader("device-name") @NotBlank @Parameter(description = "Este es el deviceName", example = "ANDROID") String deviceName,
            @RequestHeader("geo-position-x") @NotBlank @Parameter(description = "Este es el geoPositionX", example = "12.265656") String geoPositionX,
            @RequestHeader("geo-position-y") @NotBlank @Parameter(description = "Este es el geoPositionY", example = "12.454545") String geoPositionY,
            @RequestHeader("app-version") @NotBlank @Parameter(description = "Este es el appVersion", example = "1.3.3") String appVersion,
            @PathVariable("personId") @OnlyNumber @Parameter(description = "Este es el personId", example = "12345") String personId,
            @PathVariable("identifier") @NotNull @Min(value = 1) @Parameter(description = "Este es el identificador de la cuenta", example = "12345") long identifier,
            @RequestBody DeleteAccountRequest request) throws IOException {
        return ResponseEntity.ok(service.deleteWalletAccount(personId, identifier, request, Headers.getParameter(httpServletRequest,
                deviceId,
                deviceName,
                geoPositionX,
                geoPositionY,
                appVersion
        )));
    }

    @Operation(summary = "Eliminación de cuenta ACH.", description = "Elimina cuenta ACH.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Resultado de la operación y su descripción.", content = @Content(schema = @Schema(implementation = GenericResponse.class), mediaType = "application/json")),
            @ApiResponse(responseCode = "400", description = "Datos inválidos.", content = @Content(schema = @Schema(implementation = ErrorResponse.class), mediaType = "application/json")),
            @ApiResponse(responseCode = "500", description = "Error interno.", content = @Content(schema = @Schema(implementation = ErrorResponse.class), mediaType = "application/json"))
    })
    @PostMapping("/{personId}/ach-accounts/{identifier}/delete")
    public ResponseEntity<GenericResponse> deleteAchAccount(
            @RequestHeader("device-id") @NotBlank @Parameter(description = "Este es el Unique deviceId", example = "42ebffbd7c30307d") String deviceId,
            @RequestHeader("device-name") @NotBlank @Parameter(description = "Este es el deviceName", example = "ANDROID") String deviceName,
            @RequestHeader("geo-position-x") @NotBlank @Parameter(description = "Este es el geoPositionX", example = "12.265656") String geoPositionX,
            @RequestHeader("geo-position-y") @NotBlank @Parameter(description = "Este es el geoPositionY", example = "12.454545") String geoPositionY,
            @RequestHeader("app-version") @NotBlank @Parameter(description = "Este es el appVersion", example = "1.3.3") String appVersion,
            @PathVariable("personId") @NotBlank @Parameter(description = "Este es el personId", example = "12345") String personId,
            @PathVariable("identifier") @NotNull @Min(value = 1) @Parameter(description = "Este es el identificador de la cuenta", example = "12345") long identifier,
            @RequestBody DeleteAccountRequest request) throws IOException {
        return ResponseEntity.ok(service.deleteAchAccount(personId, identifier, request, Headers.getParameter(httpServletRequest,
                deviceId,
                deviceName,
                geoPositionX,
                geoPositionY,
                appVersion
        )));
    }

    @Operation(summary = "Destination Accounts", description = "Listado de todas las cuentas de Destino para el módulo de Transferencia")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Obtiene todas las cuentas propias, terceros, billeteras y ACH", content = @Content(schema = @Schema(implementation = DestinationAccountResponse.class), mediaType = "application/json")),
            @ApiResponse(responseCode = "400", description = "Error en los parametros", content = @Content(schema = @Schema(implementation = ErrorResponse.class), mediaType = "application/json")),
            @ApiResponse(responseCode = "500", description = "Un error interno", content = @Content(schema = @Schema(implementation = ErrorResponse.class), mediaType = "application/json"))
    })
    @PostMapping("/persons/{personId}")
    public ResponseEntity<DestinationAccountResponse> getDestinationAccounts(
            @RequestHeader("device-id") @NotBlank @Parameter(description = "Este es el Unique deviceId", example = "42ebffbd7c30307d") String deviceId,
            @RequestHeader("device-name") @NotBlank @Parameter(description = "Este es el deviceName", example = "ANDROID") String deviceName,
            @RequestHeader("geo-position-x") @NotBlank @Parameter(description = "Este es el geoPositionX", example = "12.265656") String geoPositionX,
            @RequestHeader("geo-position-y") @NotBlank @Parameter(description = "Este es el geoPositionY", example = "12.454545") String geoPositionY,
            @RequestHeader("app-version") @NotBlank @Parameter(description = "Este es el appVersion", example = "1.3.3") String appVersion,
            @PathVariable("personId") @OnlyNumber @Parameter(description = "Este es el código de persona", example = "1234567") String personId,
            @Valid @RequestBody DestinationAccountRequest request
    ) throws IOException {
        return ResponseEntity.ok(service.getDestinationAccounts(personId, request, Headers.getParameter(httpServletRequest,
                deviceId,
                deviceName,
                geoPositionX,
                geoPositionY,
                appVersion
        )));
    }

    @Operation(summary = "Validate Accounts", description = "Valida las cuentas de terceros y billetera")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Valida las cuentas de terceros y billetera,", content = @Content(schema = @Schema(implementation = ValidateAccountResponse.class), mediaType = "application/json")),
            @ApiResponse(responseCode = "400", description = "Error en los parametros", content = @Content(schema = @Schema(implementation = ErrorResponse.class), mediaType = "application/json")),
            @ApiResponse(responseCode = "500", description = "Un error interno", content = @Content(schema = @Schema(implementation = ErrorResponse.class), mediaType = "application/json"))
    })
    @GetMapping("")
    public ResponseEntity<ValidateAccountResponse> getValidationDestinationAccount(
            @Parameter(description = "Nro. de Cuenta", example = "1234567", required = true)
            @RequestParam(name = "accountNumber")
            @NotBlank(message = "No debe tener espacios en blanco")
            @NotNull(message = "No debe ser nulo")
            @OnlyNumber
            @Size(min = 2, max = 15, message = "El campo accountNumber debe tener un mínimo 2 y un máximo de 15 caracteres")
            @Pattern(regexp = "\\d+", message = "El campo accountNumber solo debe tener números") final String accountNumber,

            @Parameter(description = "Nombre de cliente", example = "Gutierrez")
            @RequestParam(value = "clientName")
            @NotBlank(message = "No debe tener espacios en blanco")
            @NotNull(message = "No debe ser nulo")
            @ValidText
            @Size(min = 1, max = 100, message = "Debe tener un mínimo de 1 y un máximo de 100 caracteres") final String clientName,

            @RequestHeader("device-id") @NotBlank @Parameter(description = "Este es el Unique deviceId", example = "42ebffbd7c30307d") String deviceId,
            @RequestHeader("device-name") @NotBlank @Parameter(description = "Este es el deviceName", example = "ANDROID") String deviceName,
            @RequestHeader("geo-position-x") @NotBlank @Parameter(description = "Este es el geoPositionX", example = "12.265656") String geoPositionX,
            @RequestHeader("geo-position-y") @NotBlank @Parameter(description = "Este es el geoPositionY", example = "12.454545") String geoPositionY,
            @RequestHeader("app-version") @NotBlank @Parameter(description = "Este es el appVersion", example = "1.3.3") String appVersion
    ) throws IOException {
        return ResponseEntity.ok(service.getValidateDestinationAccounts(accountNumber,
                clientName, Headers.getParameter(httpServletRequest,
                        deviceId,
                        deviceName,
                        geoPositionX,
                        geoPositionY,
                        appVersion
                )));
    }

    @Operation(summary = "Obtener cuenta.", description = "Obtiene una cuenta destinataria del usuario.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Resultado de la operación y su descripción.", content = @Content(schema = @Schema(implementation = DestinationAccount.class), mediaType = "application/json")),
            @ApiResponse(responseCode = "400", description = "Datos inválidos.", content = @Content(schema = @Schema(implementation = ErrorResponse.class), mediaType = "application/json")),
            @ApiResponse(responseCode = "500", description = "Error interno.", content = @Content(schema = @Schema(implementation = ErrorResponse.class), mediaType = "application/json"))
    })
    @GetMapping("/persons/{personId}/account-types/{accountType}/account/{accountId}")
    public ResponseEntity<DestinationAccount> getAccount(
            @RequestHeader("device-id") @NotBlank @Parameter(description = "Este es el Unique deviceId", example = "42ebffbd7c30307d") String deviceId,
            @RequestHeader("device-name") @NotBlank @Parameter(description = "Este es el deviceName", example = "ANDROID") String deviceName,
            @RequestHeader("geo-position-x") @NotBlank @Parameter(description = "Este es el geoPositionX", example = "12.265656") String geoPositionX,
            @RequestHeader("geo-position-y") @NotBlank @Parameter(description = "Este es el geoPositionY", example = "12.454545") String geoPositionY,
            @RequestHeader("app-version") @NotBlank @Parameter(description = "Este es el appVersion", example = "1.3.3") String appVersion,
            @PathVariable("personId") @OnlyNumber @Parameter(description = "Este es el código de persona", example = "1234567") String personId,
            @PathVariable("accountType") @ValidAccountType @Parameter(description = "Este es el tipo de la cuenta", example = "1") String accountType,
            @PathVariable("accountId") @OnlyNumber @Parameter(description = "Este es identificador de la lista de contactos", example = "1017") String accountId
    ) throws IOException {
        return ResponseEntity.ok(service.getAccount(personId, accountType, accountId, Headers.getParameter(httpServletRequest,
                deviceId,
                deviceName,
                geoPositionX,
                geoPositionY,
                appVersion
        )));
    }
}
