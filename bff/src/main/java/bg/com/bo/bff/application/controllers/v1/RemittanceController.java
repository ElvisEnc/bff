package bg.com.bo.bff.application.controllers.v1;

import bg.com.bo.bff.application.config.request.tracing.AbstractBFFController;
import bg.com.bo.bff.application.dtos.request.remittance.DepositRemittanceRequest;
import bg.com.bo.bff.application.dtos.request.remittance.DepositRemittanceWURequest;
import bg.com.bo.bff.application.dtos.response.generic.GenericResponse;
import bg.com.bo.bff.application.dtos.response.remittance.CheckRemittanceResponse;
import bg.com.bo.bff.application.dtos.response.remittance.DepositRemittanceResponse;
import bg.com.bo.bff.application.dtos.response.remittance.ListGeneralParametersResponse;
import bg.com.bo.bff.application.dtos.response.remittance.MoneyOrderSentResponse;
import bg.com.bo.bff.providers.dtos.response.generic.ApiDataResponse;
import bg.com.bo.bff.services.interfaces.IRemittanceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@Validated
@RestController
@RequestMapping("api/v1/remittances")
@Tag(name = "Remittance Controller", description = "Controlador de Giros y remesas")
public class RemittanceController extends AbstractBFFController {
    private final IRemittanceService service;

    public RemittanceController(IRemittanceService service) {
        this.service = service;
    }

    @Operation(summary = "Obtener parámetros generales", description = "Obtiene el listado de parámetros generales de giros y remesas", operationId = "getGeneralParameters")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de parámetros generales")
    })
    @GetMapping(path = "/persons/{personId}/parameters", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<ListGeneralParametersResponse> getGeneralParameters(
            @PathVariable("personId") @NotNull @Parameter(description = "Este es el personId de la persona", example = "12345") String personId
    ) throws IOException {
        getDeviceDataHeader();
        return ResponseEntity.ok(service.getGeneralParameters(personId));
    }

    @Operation(summary = "Validar cuenta", description = "Valida si la cuenta esta bloqueada o disponible", operationId = "validateAccount")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Valida la cuenta")
    })
    @GetMapping(path = "/persons/{personId}/accounts/{accountId}/validate", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<GenericResponse> validateAccount(
            @PathVariable("personId") @NotNull @Parameter(description = "Este es el personId de la persona", example = "12345") String personId,
            @PathVariable("accountId") @NotNull @Parameter(description = "Este es el accountId de la persona", example = "12345") String accountId
    ) throws IOException {
        getDeviceDataHeader();
        return ResponseEntity.ok(service.validateAccount(personId, accountId));
    }

    @Operation(summary = "Obtener giros enviados", description = "Obtiene los giros enviados de una persona", operationId = "getMoneyOrdersSent")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Obtener giros enviados")
    })
    @GetMapping(path = "/persons/{personId}/money-orders", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<ApiDataResponse<List<MoneyOrderSentResponse>>> getMoneyOrdersSent(
            @PathVariable("personId") @NotNull @Parameter(description = "Este es el personId de la persona", example = "12345") String personId
    ) throws IOException {
        getDeviceDataHeader();
        return ResponseEntity.ok(ApiDataResponse.of(service.getMoneyOrdersSent(personId)));
    }

    @Operation(summary = "Consultar remesa cliente", description = "Consulta la remesa de una persona", operationId = "checkRemittance")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Obtener giros enviados")
    })
    @GetMapping(path = "/persons/{personId}/remittance/{remittanceId}/check", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<ApiDataResponse<List<CheckRemittanceResponse>>> checkRemittance(
            @PathVariable("personId") @NotNull @Parameter(description = "Este es el personId de la persona", example = "12345") String personId,
            @PathVariable("remittanceId") @NotNull @Parameter(description = "Este es el personId de la persona", example = "123456789") String remittanceId
    ) throws IOException {
        getDeviceDataHeader();
        return ResponseEntity.ok(ApiDataResponse.of(service.checkRemittance(personId, remittanceId)));
    }

    @Operation(summary = "Depositar remesa cliente", description = "Deposita en cuenta la remesa de una persona", operationId = "depositRemittance")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Obtener la informacion del deposito realizado")
    })
    @PostMapping(path = "/persons/{personId}/remittance/{remittanceId}/deposit", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<ApiDataResponse<List<DepositRemittanceResponse>>> depositRemittance(
            @PathVariable("personId") @NotNull @Parameter(description = "Este es el personId de la persona", example = "12345") String personId,
            @PathVariable("remittanceId") @NotNull @Parameter(description = "Este es el remmitanceId de la remesa", example = "123456789") String remittanceId,
            @Valid @RequestBody DepositRemittanceRequest request
    ) throws IOException {
        getDeviceDataHeader();
        return ResponseEntity.ok(ApiDataResponse.of(service.depositRemittance(personId, remittanceId, request)));
    }

    @Operation(summary = "Depositar remesa wester union", description = "Deposita en cuenta la remesa wester union de una persona", operationId = "depositRemittanceWU")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Obtener la informacion del deposito realizado")
    })
    @PostMapping(path = "/persons/{personId}/remittance/{remittanceId}/deposit/wester-union", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<ApiDataResponse<List<DepositRemittanceResponse>>> depositRemittanceWU(
            @PathVariable("personId") @NotNull @Parameter(description = "Este es el personId de la persona", example = "12345") String personId,
            @PathVariable("remittanceId") @NotNull @Parameter(description = "Este es el remmitanceId de la remesa", example = "123456789") String remittanceId,
            @Valid @RequestBody DepositRemittanceWURequest request
    ) throws IOException {
        getDeviceDataHeader();
        return ResponseEntity.ok(ApiDataResponse.of(service.depositRemittanceWU(personId, remittanceId, request)));
    }
}
