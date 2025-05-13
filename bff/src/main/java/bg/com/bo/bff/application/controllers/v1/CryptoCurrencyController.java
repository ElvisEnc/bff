package bg.com.bo.bff.application.controllers.v1;

import bg.com.bo.bff.application.config.request.tracing.AbstractBFFController;
import bg.com.bo.bff.application.dtos.request.crypto.currency.AccountExtractRequest;
import bg.com.bo.bff.application.dtos.request.crypto.currency.ExchangeOperationRequest;
import bg.com.bo.bff.application.dtos.request.crypto.currency.GenerateVoucherRequest;
import bg.com.bo.bff.application.dtos.response.crypto.currency.AccountEmailResponse;
import bg.com.bo.bff.application.dtos.response.crypto.currency.AccountExtractResponse;
import bg.com.bo.bff.application.dtos.response.crypto.currency.AvailableBalanceResponse;
import bg.com.bo.bff.application.dtos.response.crypto.currency.ExchangeOperationResponse;
import bg.com.bo.bff.application.dtos.response.crypto.currency.ExchangeRateResponse;
import bg.com.bo.bff.application.dtos.response.crypto.currency.GenerateVoucherResponse;
import bg.com.bo.bff.application.dtos.response.generic.GenericResponse;
import bg.com.bo.bff.commons.annotations.OnlyNumber;
import bg.com.bo.bff.services.interfaces.ICryptoCurrencyService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@Validated
@RestController
@RequestMapping("api/v1/cryptocurrency")
@Tag(name = "Crypto Currency Controller", description = "Controlador de GanaCripto")
public class CryptoCurrencyController extends AbstractBFFController {
    private final ICryptoCurrencyService service;

    public CryptoCurrencyController(ICryptoCurrencyService service) {
        this.service = service;
    }

    @Operation(summary = "Registrar una cuenta", description = "Registrar una cuenta en GanaCripto",
            operationId = "registerAccount")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Registrar una cuenta en GanaCripto")
    })
    @PostMapping("/persons/{personId}/account-create")
    public ResponseEntity<GenericResponse> registerAccount(
            @PathVariable("personId") @OnlyNumber @Parameter(description = "Este es el personId de la persona", example = "12345") String personId
    ) throws IOException {
        getDeviceDataHeader();
       return ResponseEntity.ok(service.registerAccount(personId));
    }

    @Operation(summary = "Obtiene el saldo disponible", description = "Obtiene el saldo disponible",
            operationId = "getAvailableBalance")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Obtiene el saldo disponible")
    })
    @GetMapping("/persons/{personId}/available-balance")
    public ResponseEntity<AvailableBalanceResponse> getAvailableBalance(
            @PathVariable("personId") @OnlyNumber @Parameter(description = "Este es el personId de la persona", example = "12345") String personId
    ) throws IOException {
        getDeviceDataHeader();
       return ResponseEntity.ok(service.getAvailableBalance(personId));
    }

    @Operation(summary = "Cuenta email", description = "Cuenta email",
            operationId = "getAccountEmail")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cuenta email")
    })
    @GetMapping("/persons/{personId}/account-email")
    public ResponseEntity<AccountEmailResponse> getAccountEmail(
            @PathVariable("personId") @OnlyNumber @Parameter(description = "Este es el personId de la persona", example = "12345") String personId
    ) throws IOException {
        getDeviceDataHeader();
       return ResponseEntity.ok(service.getAccountEmail(personId));
    }

    @Operation(summary = "Genera los extractos desde una fecha predeterminada", description = "Genera los extractos desde una fecha predeterminada",
            operationId = "getAccountExtract")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Genera los extractos desde una fecha predeterminada")
    })
    @PostMapping(path = "/persons/{personId}/accounts/{accountId}/account-extract", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<List<AccountExtractResponse>> getAccountExtract(
            @PathVariable("personId") @OnlyNumber @Parameter(description = "Este es el personId de la persona", example = "12345") String personId,
            @PathVariable("accountId") @OnlyNumber @Parameter(description = "Este es el accountId de la persona", example = "12345") String accountId,
            @Valid @RequestBody AccountExtractRequest request
    ) throws IOException {
        getDeviceDataHeader();
       return ResponseEntity.ok(service.getAccountExtract(personId, accountId, request));
    }

    @Operation(summary = "Obtiene el tipo de cambio", description = "Obtiene el tipo de cambio",
            operationId = "getExchangeRate")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Obtiene el tipo de cambio")
    })
    @GetMapping("/persons/{personId}/currency/{currencyId}/exchange-rate")
    public ResponseEntity<ExchangeRateResponse> getExchangeRate(
            @PathVariable("personId") @OnlyNumber @Parameter(description = "Este es el personId de la persona", example = "12345") String personId,
            @PathVariable("currencyId") @OnlyNumber @Parameter(description = "Este es el currencyId de la moneda", example = "12345") String currencyId
    ) throws IOException {
        getDeviceDataHeader();
        return ResponseEntity.ok(service.getExchangeRate(personId, currencyId));
    }

    @Operation(summary = "Genera la transferencia en ganacripto", description = "Genera la transferencia en ganacripto",
            operationId = "exchangeOperation")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Genera la transferencia en ganacripto")
    })
    @PostMapping(path = "/persons/{personId}/accounts/{accountId}/exchange-operation", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<ExchangeOperationResponse> exchangeOperation(
            @PathVariable("personId") @OnlyNumber @Parameter(description = "Este es el personId de la persona", example = "12345") String personId,
            @PathVariable("accountId") @OnlyNumber @Parameter(description = "Este es el accountId de la persona", example = "12345") String accountId,
            @Valid @RequestBody ExchangeOperationRequest request
    ) throws IOException {
        getDeviceDataHeader();
        return ResponseEntity.ok(service.exchangeOperation(personId, accountId, request));
    }

    @Operation(summary = "Genera el voucher del pago", description = "Genera el voucher del pago",
            operationId = "postGenerateVoucher")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Genera el voucher del pago")
    })
    @PostMapping(path = "/persons/{personId}/generate-voucher", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<GenerateVoucherResponse> postGenerateVoucher(
            @PathVariable("personId") @OnlyNumber @Parameter(description = "Este es el personId de la persona", example = "12345") String personId,
            @Valid @RequestBody GenerateVoucherRequest request
    ) throws IOException {
        getDeviceDataHeader();
        return ResponseEntity.ok(service.postGenerateVoucher(personId, request));
    }

}
