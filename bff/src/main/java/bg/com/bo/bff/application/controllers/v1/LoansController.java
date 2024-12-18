package bg.com.bo.bff.application.controllers.v1;

import bg.com.bo.bff.application.config.request.tracing.AbstractBFFController;
import bg.com.bo.bff.application.dtos.request.loans.ListLoansRequest;
import bg.com.bo.bff.application.dtos.request.loans.LoanPaymentsRequest;
import bg.com.bo.bff.application.dtos.response.loans.*;
import bg.com.bo.bff.commons.annotations.OnlyNumber;
import bg.com.bo.bff.providers.dtos.response.generic.ApiDataResponse;
import bg.com.bo.bff.services.interfaces.ILoansService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
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
@RequestMapping("api/v1/loans")
@Tag(name = "Loans Controller", description = "Controlador de Prestamos")
public class LoansController extends AbstractBFFController {
    private final ILoansService service;

    public LoansController(ILoansService service) {
        this.service = service;
    }

    @Operation(summary = "Lista de Prestamos", description = "Obtiene el listado de prestamos relacionados a una persona", operationId = "getListLoans")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de prestamos")
    })
    @PostMapping(path = "/persons/{personId}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<ApiDataResponse<List<ListLoansResponse>>> getListLoans(
            @PathVariable("personId") @NotNull @Parameter(description = "Este es el personId de la persona", example = "12345") String personId,
            @Valid @RequestBody ListLoansRequest request
    ) throws IOException {
        getDeviceDataHeader();
        return ResponseEntity.ok(ApiDataResponse.of(service.getListLoansByPerson(personId, request)));
    }

    @Operation(summary = "Lista de Prestamos Pagados", description = "Obtiene el listado de prestamos pagados")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de prestamos pagados")
    })
    @PostMapping("{loanId}/persons/{personId}/payments")
    public ResponseEntity<ApiDataResponse<List<LoanPaymentsResponse>>> getListLoanPayments(
            @PathVariable("loanId") @OnlyNumber @Parameter(description = "Este es el personId de la persona", example = "12345") String loanId,
            @PathVariable("personId") @OnlyNumber @Parameter(description = "Este es el personId de la persona", example = "12345") String personId,
            @Valid @RequestBody LoanPaymentsRequest request
    ) throws IOException {
        getDeviceDataHeader();
        return ResponseEntity.ok(ApiDataResponse.of(service.getLoanPayments(loanId, personId, request)));
    }

    @Operation(summary = "Lista de pagos de Seguro", description = "Obtiene el listado de los pagos del seguro")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de seguros pagados")
    })
    @PostMapping("{loanId}/persons/{personId}/insurance-payments")
    public ResponseEntity<ApiDataResponse<List<LoanInsurancePaymentsResponse>>> getListLoanInsurancePayments(
            @PathVariable("loanId") @OnlyNumber @Parameter(description = "Este es el personId de la persona", example = "12345") String loanId,
            @PathVariable("personId") @OnlyNumber @Parameter(description = "Este es el personId de la persona", example = "12345") String personId,
            @Valid @RequestBody LoanPaymentsRequest request
    ) throws IOException {
        getDeviceDataHeader();
        return ResponseEntity.ok(ApiDataResponse.of(service.getLoanInsurancePayments(loanId, personId, request)));
    }

    @Operation(summary = "Plan de pagos", description = "Obtiene una lista de planes de pago del prestamo")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Plan de pagos")
    })
    @GetMapping("{loanId}/persons/{personId}")
    public ResponseEntity<ApiDataResponse<List<LoanPlanResponse>>> getLoanPlans(
            @PathVariable("loanId") @OnlyNumber @Parameter(description = "Este es el personId de la persona", example = "12345") String loanId,
            @PathVariable("personId") @OnlyNumber @Parameter(description = "Este es el personId de la persona", example = "12345") String personId
    ) throws IOException {
        getDeviceDataHeader();
        return ResponseEntity.ok(ApiDataResponse.of(service.getLoanPlans(loanId, personId)));
    }

    @Operation(summary = "Solicitud de pago préstamo", description = "Obtiene la solicitud de un pago de préstamo")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Solicitud de pago de préstamo", content = @Content(schema = @Schema(implementation = LoanDetailPaymentResponse.class), mediaType = "application/json"))
    })
    @GetMapping("{loanId}/persons/{personId}/payments/{clientId}")
    public ResponseEntity<LoanDetailPaymentResponse> getLoanDetailPayment(
            @PathVariable("loanId") @OnlyNumber @Parameter(description = "Este es el personId de la persona", example = "12345") String loanId,
            @PathVariable("personId") @OnlyNumber @Parameter(description = "Este es el personId de la persona", example = "12345") String personId,
            @PathVariable("clientId") @OnlyNumber @Parameter(description = "Este es el clientId de la persona", example = "12345") String clientId
    ) throws IOException {
        getDeviceDataHeader();
        return ResponseEntity.ok(service.getLoanDetailPayment(loanId, personId, clientId));
    }

    @Operation(summary = "Pago de préstamo", description = "Pagar un préstamo con una cuenta propia")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Resultado de la operación y su descripción.", content = @Content(schema = @Schema(implementation = LoanPaymentResponse.class), mediaType = "application/json"))
    })
    @PutMapping("/persons/{personId}/accounts/{accountId}/payments/{correlativeId}")
    public ResponseEntity<LoanPaymentResponse> payLoanInstallment(
            @PathVariable("personId") @OnlyNumber @Parameter(description = "Este es el personId de la persona", example = "12345") String personId,
            @PathVariable("accountId") @OnlyNumber @Parameter(description = "Este es el accountId de la cuenta de la persona", example = "12345") String accountId,
            @PathVariable("correlativeId") @OnlyNumber @Parameter(description = "Este es el correlativeId del préstamo", example = "12345") String correlativeId
    ) throws IOException {
        getDeviceDataHeader();
        return ResponseEntity.ok(service.payLoanInstallment(personId, accountId, correlativeId));
    }
}
