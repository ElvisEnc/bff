package bg.com.bo.bff.application.controllers.v1;

import bg.com.bo.bff.application.config.request.tracing.AbstractBFFController;
import bg.com.bo.bff.application.dtos.request.loyalty.RegisterRedeemVoucherRequest;
import bg.com.bo.bff.application.dtos.request.loyalty.RegisterSubscriptionRequest;
import bg.com.bo.bff.application.dtos.response.generic.GenericResponse;
import bg.com.bo.bff.application.dtos.response.loyalty.LoyaltyLevel;
import bg.com.bo.bff.application.dtos.response.loyalty.LoyaltyRedeemVoucherResponse;
import bg.com.bo.bff.application.dtos.response.loyalty.LoyaltySumPointResponse;
import bg.com.bo.bff.providers.dtos.response.loyalty.LoyaltySystemCodeResponse;
import bg.com.bo.bff.commons.annotations.OnlyNumber;
import bg.com.bo.bff.services.interfaces.ILoyaltyService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@Validated
@RestController
@RequestMapping("api/v1/loyalty")
@Tag(name = "Loyalty Controller", description = "Controlador del programa VAMOS")
public class LoyaltyController extends AbstractBFFController {
    private final ILoyaltyService service;

    public LoyaltyController(ILoyaltyService service) {
        this.service = service;
    }

    @Operation(summary = "Obtener codigo de sistema", description = "Obtener el codigo de sistema de la persona",
            operationId = "getSystemCode")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Obtener el codigo de sistema de la persona")
    })
    @GetMapping("/persons/{personId}/system-code")
    public ResponseEntity<LoyaltySystemCodeResponse> getSystemCode(
            @PathVariable("personId") @OnlyNumber @Parameter(description = "Este es el personId de la persona", example = "12345") String personId
    ) throws IOException {
        getDeviceDataHeader();
        return ResponseEntity.ok(service.getSystemCode(personId));
    }

    @Operation(summary = "Obtener la sumatoria de los puntos", description = "Obtener la sumatoria de los puntos acumulados",
            operationId = "getSumPoint")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Obtener la sumatoria de los puntos acumulados")
    })
    @GetMapping("/persons/{personId}/system-code/{codeSystem}/sum-points")
    public ResponseEntity<LoyaltySumPointResponse> getSumPoint(
            @PathVariable("personId") @OnlyNumber @Parameter(description = "Este es el personId de la persona", example = "12345") String personId,
            @PathVariable("codeSystem") @Parameter(description = "Este es el codigo de sistema de la persona", example = "12345") String codeSystem
    ) throws IOException {
        getDeviceDataHeader();
        return ResponseEntity.ok(service.getSumPoint(personId, codeSystem));
    }

    @Operation(summary = "Registrar suscripción", description = "Realizar la suscripción al programa VAMOS",
            operationId = "registerSubscription")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Realizar la suscripción al programa VAMOS")
    })
    @PostMapping(path = "/persons/{personId}/accounts/{accountId}/register-subscription", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<GenericResponse> registerSubscription(
            @PathVariable("personId") @OnlyNumber @Parameter(description = "Este es el personId de la persona", example = "12345") String personId,
            @PathVariable("accountId") @OnlyNumber @Parameter(description = "Este es la cuenta de la persona (jts_oid)", example = "12345") String accountId,
            @Valid @RequestBody RegisterSubscriptionRequest request
    ) throws IOException {
        getDeviceDataHeader();
        return ResponseEntity.ok(service.registerSubscription(personId, accountId, request));
    }

    @Operation(summary = "Registra el canje de vale", description = "Registra el canje de vale del programa VAMOS",
            operationId = "registerRedeemVoucher")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Registra el canje de vale del programa VAMOS")
    })
    @PostMapping(path = "/persons/{personId}/system-code/{codeSystem}/redeem-voucher", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<LoyaltyRedeemVoucherResponse> registerRedeemVoucher(
            @PathVariable("personId") @OnlyNumber @Parameter(description = "Este es el personId de la persona", example = "12345") String personId,
            @PathVariable("codeSystem") @OnlyNumber @Parameter(description = "Este es el codigo de sistema de la persona", example = "12345") String codeSystem,
            @Valid @RequestBody RegisterRedeemVoucherRequest request
    ) throws IOException {
        getDeviceDataHeader();
        return ResponseEntity.ok(service.registerRedeemVoucher(personId, codeSystem, request));
    }

    @Operation(summary = "Obtener el nivel", description = "Obtener el nivel de la persona en el programa",
            operationId = "getLevel")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Obtener el nivel de la persona en el programa")
    })
    @GetMapping("/persons/{personId}/level")
    public ResponseEntity<LoyaltyLevel> getLevel(
            @PathVariable("personId") @OnlyNumber @Parameter(description = "Este es el personId de la persona", example = "12345") String personId
    ) throws IOException {
        getDeviceDataHeader();
        return ResponseEntity.ok(service.getLevel(personId));
    }

}
