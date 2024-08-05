package bg.com.bo.bff.application.controllers.v1;

import bg.com.bo.bff.application.dtos.request.account.statement.AccountStatementsRequest;
import bg.com.bo.bff.application.dtos.response.generic.ErrorResponse;
import bg.com.bo.bff.application.dtos.response.account.statement.AccountStatementsResponse;
import bg.com.bo.bff.commons.utils.Headers;
import bg.com.bo.bff.providers.dtos.response.generic.ApiDataResponse;
import bg.com.bo.bff.services.interfaces.IAccountStatementService;
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
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;


@RestController
@Validated
@RequestMapping("api/v1/account-statement")
@Tag(name = "Account Statement Controller", description = "Controlador de extractos de cuentas")
public class AccountStatementController {
    private final HttpServletRequest httpServletRequest;
    private final IAccountStatementService iAccountStatementService;

    public AccountStatementController(HttpServletRequest httpServletRequest, IAccountStatementService iAccountStatementService) {
        this.httpServletRequest = httpServletRequest;
        this.iAccountStatementService = iAccountStatementService;
    }

    @Operation(summary = "Extractos", description = "Obtiene los extractos de una cuenta")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de extractos"),
            @ApiResponse(responseCode = "400", description = "Algún error en los filtros", content = @Content(schema = @Schema(implementation = ErrorResponse.class), mediaType = "application/json")),
            @ApiResponse(responseCode = "500", description = "Error interno", content = @Content(schema = @Schema(implementation = ErrorResponse.class), mediaType = "application/json"))
    })
    @PostMapping("/{accountId}/persons/{personId}")
    public ResponseEntity<ApiDataResponse<List<AccountStatementsResponse>>> getAccountStatements(
            @RequestHeader("device-id") @NotBlank @Parameter(description = "Este es el Unique deviceId", example = "42ebffbd7c30307d") String deviceId,
            @RequestHeader("device-name") @NotBlank @Parameter(description = "Este es el deviceName", example = "ANDROID") String deviceName,
            @RequestHeader("geo-position-x") @NotBlank @Parameter(description = "Este es el geoPositionX", example = "12.265656") String geoPositionX,
            @RequestHeader("geo-position-y") @NotBlank @Parameter(description = "Este es el geoPositionY", example = "12.454545") String geoPositionY,
            @RequestHeader("app-version") @NotBlank @Parameter(description = "Este es el appVersion", example = "1.3.3") String appVersion,
            @PathVariable("accountId") @NotBlank @Parameter(description = "id de la cuenta", example = "654654678") String accountId,
            @PathVariable("personId") @NotBlank @Parameter(description = "código de persona", example = "12345") String personId,
            @Valid @RequestBody AccountStatementsRequest request
    ) throws IOException {
        return ResponseEntity.ok(ApiDataResponse.of(iAccountStatementService.getAccountStatement( accountId, request, Headers.getParameter(httpServletRequest,
                deviceId,
                deviceName,
                geoPositionX,
                geoPositionY,
                appVersion))));
    }
}
