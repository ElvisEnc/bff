package bg.com.bo.bff.application.controllers.v1;

import bg.com.bo.bff.HeaderDeviceMW;
import bg.com.bo.bff.application.dtos.request.UpdateTransactionLimitRequest;
import bg.com.bo.bff.application.dtos.response.ErrorResponse;
import bg.com.bo.bff.application.dtos.response.GenericResponse;
import bg.com.bo.bff.application.dtos.response.GetTransactionLimitResponse;
import bg.com.bo.bff.commons.utils.Headers;
import bg.com.bo.bff.models.dtos.accounts.AccountListResponse;
import bg.com.bo.bff.services.interfaces.IAccountService;
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
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@Validated
@RequestMapping("api/v1/accounts")
@Tag(name = "Account Controller", description = "Controlador de cuentas")
public class AccountController {

    private final IAccountService iAccountService;
    private final HttpServletRequest httpServletRequest;

    public AccountController(IAccountService iAccountService, HttpServletRequest httpServletRequest) {
        this.iAccountService = iAccountService;
        this.httpServletRequest = httpServletRequest;
    }

    @Operation(summary = "Own Accounts Request", description = "Este es el Endpoint donde el usuario obtendrá sus cuentas")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Obtiene todas las cuentas, para un personId con su document-number", content = @Content(schema = @Schema(implementation = AccountListResponse.class),mediaType = "application/json")),
            @ApiResponse(responseCode = "400", description = "Existe un error en los parametros otorgados.", content = @Content(schema = @Schema(implementation = ErrorResponse.class), mediaType = "application/json")),
            @ApiResponse(responseCode = "406", description = "El document no corresponde al personId, devuelve un 406 ErrorResponse", content = @Content(schema = @Schema(implementation = ErrorResponse.class), mediaType = "application/json")),
            @ApiResponse(responseCode = "500", description = "Un error interno, devuelve un 500 ErrorResponse", content = @Content(schema = @Schema(implementation = ErrorResponse.class), mediaType = "application/json"))
    })
    @GetMapping("/persons/{personId}/document-number/{document}")
    public ResponseEntity<AccountListResponse> accounts(
            @PathVariable("personId") @NotBlank @Parameter(description = "Este es el personId", example = "12345") String personId,
            @PathVariable("document") @NotBlank @Parameter(description = "Este es el número de documento de identidad", example = "1234567") String document
    ) throws IOException {
            return ResponseEntity.ok(iAccountService.getAccounts(personId, document));
    }

    @Operation(summary = "Own Accounts Request", description = "Este es el endpoint donde el usuario actualizará el límite diario y la cantidad de retiros.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "actualizará el límite diario y la cantidad de retiros", content = @Content(schema = @Schema(implementation = GenericResponse.class),mediaType = "application/json")),
            @ApiResponse(responseCode = "400", description = "Existe un error en los parametros otorgados.", content = @Content(schema = @Schema(implementation = ErrorResponse.class), mediaType = "application/json")),
            @ApiResponse(responseCode = "406", description = "El document no corresponde al personId, devuelve un 406 ErrorResponse", content = @Content(schema = @Schema(implementation = ErrorResponse.class), mediaType = "application/json")),
            @ApiResponse(responseCode = "500", description = "Un error interno, devuelve un 500 ErrorResponse", content = @Content(schema = @Schema(implementation = ErrorResponse.class), mediaType = "application/json"))
    })
    @PutMapping("/persons/{personId}/account/{accountId}/transactional-limits")
    public ResponseEntity<GenericResponse> updateTransactioLimit(
            @RequestHeader("device-id") @NotBlank @Parameter(description = "Este es el Unique deviceId", example = "42ebffbd7c30307d") String deviceId,
            @RequestHeader("device-name") @Parameter(description = "Este es el deviceName", example = "ANDROID") String deviceName,
            @RequestHeader("geo-position-x") @NotBlank @Parameter(description = "Este es el geoPositionX", example = "12.265656") String geoPositionX,
            @RequestHeader("geo-position-y") @NotBlank @Parameter(description = "Este es el geoPositionY", example = "12.454545") String geoPositionY,
            @RequestHeader("app-version") @NotBlank @Parameter(description = "Este es el appVersion", example = "1.3.3") String appVersion,
            @PathVariable("personId")
            @NotBlank
            @Parameter(description = "Este es el personId", example = "12345") String personId,
            @PathVariable("accountId")
            @NotBlank
            @Parameter(description = "Este es el personId", example = "12345") String accountId,
            @Valid @RequestBody UpdateTransactionLimitRequest request
    ) throws IOException {
        return ResponseEntity.ok(iAccountService.updateTransactionLimit(personId,accountId,request, Headers.getParameter(httpServletRequest,
                deviceId,
                deviceName,
                geoPositionX,
                geoPositionY,
                appVersion
        )));
    }

    @GetMapping("/persons/{personId}/account/{accountId}/transactional-limits")
    public ResponseEntity<GetTransactionLimitResponse> getTransactioLimit(
            @RequestHeader("device-id") @NotBlank @Parameter(description = "Este es el Unique deviceId", example = "42ebffbd7c30307d") String deviceId,
            @RequestHeader("device-name") @Parameter(description = "Este es el deviceName", example = "ANDROID") String deviceName,
            @RequestHeader("geo-position-x") @NotBlank @Parameter(description = "Este es el geoPositionX", example = "12.265656") String geoPositionX,
            @RequestHeader("geo-position-y") @NotBlank @Parameter(description = "Este es el geoPositionY", example = "12.454545") String geoPositionY,
            @RequestHeader("app-version") @NotBlank @Parameter(description = "Este es el appVersion", example = "1.3.3") String appVersion,
            @PathVariable("personId")
            @NotBlank
            @Parameter(description = "Este es el personId", example = "12345")
            String personId,
            @PathVariable("accountId")
            @NotBlank
            @Parameter(description = "Este es el personId", example = "12345") String accountId
    ) throws IOException {
        return ResponseEntity.ok(iAccountService.getTransactionLimit(personId,accountId, Headers.getParameter(httpServletRequest,
                deviceId,
                deviceName,
                geoPositionX,
                geoPositionY,
                appVersion
        )));
    }
}


