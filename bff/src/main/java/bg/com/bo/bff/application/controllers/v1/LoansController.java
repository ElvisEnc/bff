package bg.com.bo.bff.application.controllers.v1;

import bg.com.bo.bff.application.dtos.request.loans.ListLoansRequest;
import bg.com.bo.bff.application.dtos.request.loans.LoanPaymentsRequest;
import bg.com.bo.bff.application.dtos.response.generic.ErrorResponse;
import bg.com.bo.bff.application.dtos.response.loans.ListLoansResponse;
import bg.com.bo.bff.application.dtos.response.loans.LoanInsurancePaymentsResponse;
import bg.com.bo.bff.application.dtos.response.loans.LoanPaymentsResponse;
import bg.com.bo.bff.application.dtos.response.loans.LoanPlanResponse;
import bg.com.bo.bff.commons.annotations.OnlyNumber;
import bg.com.bo.bff.commons.utils.Headers;
import bg.com.bo.bff.providers.dtos.response.generic.ApiDataResponse;
import bg.com.bo.bff.services.interfaces.ILoansService;
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
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@Validated
@RestController
@RequestMapping("api/v1/loans")
@Tag(name = "Loans Controller", description = "Controlador de Prestamos")
public class LoansController {
    private final HttpServletRequest httpServletRequest;
    private final ILoansService service;

    public LoansController(HttpServletRequest httpServletRequest, ILoansService service) {
        this.httpServletRequest = httpServletRequest;
        this.service = service;
    }

    @Operation(summary = "Lista de Prestamos", description = "Obtiene el listado de prestamos relacionados a una persona")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de prestamos"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos.", content = @Content(schema = @Schema(implementation = ErrorResponse.class), mediaType = "application/json")),
            @ApiResponse(responseCode = "500", description = "Error interno", content = @Content(schema = @Schema(implementation = ErrorResponse.class), mediaType = "application/json"))
    })
    @PostMapping("/persons/{personId}")
    public ResponseEntity<ApiDataResponse<List<ListLoansResponse>>> getListLoans(
            @RequestHeader("device-id") @NotBlank @Parameter(description = "Este es el Unique deviceId", example = "42ebffbd7c30307d") String deviceId,
            @RequestHeader("device-name") @NotBlank @Parameter(description = "Este es el deviceName", example = "ANDROID") String deviceName,
            @RequestHeader("geo-position-x") @NotBlank @Parameter(description = "Este es el geoPositionX", example = "12.265656") String geoPositionX,
            @RequestHeader("geo-position-y") @NotBlank @Parameter(description = "Este es el geoPositionY", example = "12.454545") String geoPositionY,
            @RequestHeader("app-version") @NotBlank @Parameter(description = "Este es el appVersion", example = "1.3.3") String appVersion,
            @PathVariable("personId") @NotNull @Parameter(description = "Este es el personId de la persona", example = "12345") String personId,
            @Valid @RequestBody ListLoansRequest request
    ) throws IOException {
        return ResponseEntity.ok(ApiDataResponse.of(service.getListLoansByPerson(personId, request, Headers.getParameter(httpServletRequest))));
    }

    @Operation(summary = "Lista de Prestamos Pagados", description = "Obtiene el listado de prestamos pagados")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de prestamos pagados"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos.", content = @Content(schema = @Schema(implementation = ErrorResponse.class), mediaType = "application/json")),
            @ApiResponse(responseCode = "500", description = "Error interno", content = @Content(schema = @Schema(implementation = ErrorResponse.class), mediaType = "application/json"))
    })
    @PostMapping("{loanId}/persons/{personId}/payments")
    public ResponseEntity<ApiDataResponse<List<LoanPaymentsResponse>>> getListLoanPayments(
            @RequestHeader("device-id") @NotBlank @Parameter(description = "Este es el Unique deviceId", example = "42ebffbd7c30307d") String deviceId,
            @RequestHeader("device-name") @NotBlank @Parameter(description = "Este es el deviceName", example = "ANDROID") String deviceName,
            @RequestHeader("geo-position-x") @NotBlank @Parameter(description = "Este es el geoPositionX", example = "12.265656") String geoPositionX,
            @RequestHeader("geo-position-y") @NotBlank @Parameter(description = "Este es el geoPositionY", example = "12.454545") String geoPositionY,
            @RequestHeader("app-version") @NotBlank @Parameter(description = "Este es el appVersion", example = "1.3.3") String appVersion,
            @PathVariable("loanId") @OnlyNumber @Parameter(description = "Este es el personId de la persona", example = "12345") String loanId,
            @PathVariable("personId") @OnlyNumber @Parameter(description = "Este es el personId de la persona", example = "12345") String personId,
            @Valid @RequestBody LoanPaymentsRequest request
    ) throws IOException {
        return ResponseEntity.ok(ApiDataResponse.of(service.getLoanPayments(loanId, personId, request, Headers.getParameter(httpServletRequest,
                deviceId,
                deviceName,
                geoPositionX,
                geoPositionY,
                appVersion))));
    }

    @Operation(summary = "Lista de pagos de Seguro", description = "Obtiene el listado de los pagos del seguro")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de seguros pagados"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos.", content = @Content(schema = @Schema(implementation = ErrorResponse.class), mediaType = "application/json")),
            @ApiResponse(responseCode = "500", description = "Error interno", content = @Content(schema = @Schema(implementation = ErrorResponse.class), mediaType = "application/json"))
    })
    @PostMapping("{loanId}/persons/{personId}/insurance-payments")
    public ResponseEntity<ApiDataResponse<List<LoanInsurancePaymentsResponse>>> getListLoanInsurancePayments(
            @RequestHeader("device-id") @NotBlank @Parameter(description = "Este es el Unique deviceId", example = "42ebffbd7c30307d") String deviceId,
            @RequestHeader("device-name") @NotBlank @Parameter(description = "Este es el deviceName", example = "ANDROID") String deviceName,
            @RequestHeader("geo-position-x") @NotBlank @Parameter(description = "Este es el geoPositionX", example = "12.265656") String geoPositionX,
            @RequestHeader("geo-position-y") @NotBlank @Parameter(description = "Este es el geoPositionY", example = "12.454545") String geoPositionY,
            @RequestHeader("app-version") @NotBlank @Parameter(description = "Este es el appVersion", example = "1.3.3") String appVersion,
            @PathVariable("loanId") @OnlyNumber @Parameter(description = "Este es el personId de la persona", example = "12345") String loanId,
            @PathVariable("personId") @OnlyNumber @Parameter(description = "Este es el personId de la persona", example = "12345") String personId,
            @Valid @RequestBody LoanPaymentsRequest request
    ) throws IOException {
        return ResponseEntity.ok(ApiDataResponse.of(service.getLoanInsurancePayments(loanId, personId, request, Headers.getParameter(httpServletRequest,
                deviceId,
                deviceName,
                geoPositionX,
                geoPositionY,
                appVersion))));
    }

    @Operation(summary = "Plan de pagos", description = "Obtiene una lista de planes de pago del prestamo")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Plan de pagos"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos.", content = @Content(schema = @Schema(implementation = ErrorResponse.class), mediaType = "application/json")),
            @ApiResponse(responseCode = "500", description = "Error interno", content = @Content(schema = @Schema(implementation = ErrorResponse.class), mediaType = "application/json"))
    })
    @GetMapping("{loanId}/persons/{personId}")
    public ResponseEntity<ApiDataResponse<List<LoanPlanResponse>>> getLoanPlans(
            @RequestHeader("device-id") @NotBlank @Parameter(description = "Este es el Unique deviceId", example = "42ebffbd7c30307d") String deviceId,
            @RequestHeader("device-name") @NotBlank @Parameter(description = "Este es el deviceName", example = "ANDROID") String deviceName,
            @RequestHeader("geo-position-x") @NotBlank @Parameter(description = "Este es el geoPositionX", example = "12.265656") String geoPositionX,
            @RequestHeader("geo-position-y") @NotBlank @Parameter(description = "Este es el geoPositionY", example = "12.454545") String geoPositionY,
            @RequestHeader("app-version") @NotBlank @Parameter(description = "Este es el appVersion", example = "1.3.3") String appVersion,
            @PathVariable("loanId") @OnlyNumber @Parameter(description = "Este es el personId de la persona", example = "12345") String loanId,
            @PathVariable("personId") @OnlyNumber @Parameter(description = "Este es el personId de la persona", example = "12345") String personId
    ) throws IOException {
        return ResponseEntity.ok(ApiDataResponse.of(service.getLoanPlans(loanId, personId, Headers.getParameter(httpServletRequest,
                deviceId,
                deviceName,
                geoPositionX,
                geoPositionY,
                appVersion))));
    }
}
