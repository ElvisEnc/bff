package bg.com.bo.bff.application.controllers.v1;

import bg.com.bo.bff.application.config.request.tracing.AbstractBFFController;
import bg.com.bo.bff.application.dtos.response.certifications.CertificationTypesResponse;
import bg.com.bo.bff.application.dtos.response.transfers.programming.PaymentsPlanResponse;
import bg.com.bo.bff.application.dtos.response.transfers.programming.ProgrammedTransfersResponse;
import bg.com.bo.bff.commons.annotations.OnlyNumber;
import bg.com.bo.bff.providers.dtos.response.generic.ApiDataResponse;
import bg.com.bo.bff.services.interfaces.ITransferProgrammingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
@Validated
@RequestMapping("api/v1/transfer-programming")
@Tag(name = "Transfer Programming Controller", description = "Controlador de Programación de Transferencias")
public class TransfersProgrammingController extends AbstractBFFController {

    private final ITransferProgrammingService service;

    public TransfersProgrammingController(ITransferProgrammingService service) {
        this.service = service;
    }

    @Operation(summary = "Obtener Transferencias Programadas", description = "Lista de transferencias programadas")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Programmed transfers list",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = ProgrammedTransfersResponse.class))))
    })
    @GetMapping("/persons/{personId}")
    public ResponseEntity<List<ProgrammedTransfersResponse>> getTransfers(
            @PathVariable("personId") @OnlyNumber @Parameter(description = "Este es el numero de persona", example = "12345") String personId
    ) throws IOException {
        getDeviceDataHeader();
        return ResponseEntity.ok(
                service.getTransfers(
                        personId
                )
        );
    }

    @Operation(summary = "Obtener Plan de Pagos", description = "Obtener plan de pagos de una transferencia programada")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Programmed transfers list",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = PaymentsPlanResponse.class))))
    })
    @GetMapping("/payments-plan/persons/{personId}/transfer/{transferId}")
    public ResponseEntity<List<PaymentsPlanResponse>> getPaymentsPlan(
            @PathVariable("personId") @OnlyNumber @Parameter(description = "Este es el numero de persona", example = "12345") String personId,
            @PathVariable("transferId") @OnlyNumber @Parameter(description = "Identificador de la transferencia", example = "54321") String transferId
    ) throws IOException {
        getDeviceDataHeader();
        return ResponseEntity.ok(
                service.getPaymentsPlan(
                        transferId
                )
        );
    }

}
