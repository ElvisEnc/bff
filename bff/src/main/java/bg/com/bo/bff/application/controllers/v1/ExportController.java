package bg.com.bo.bff.application.controllers.v1;

import bg.com.bo.bff.application.dtos.request.account.statement.ExportRequest;
import bg.com.bo.bff.application.dtos.response.generic.ErrorResponse;
import bg.com.bo.bff.application.dtos.response.account.statement.AccountStatementExportResponse;
import bg.com.bo.bff.commons.utils.Headers;
import bg.com.bo.bff.services.interfaces.IExportService;
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
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.*;

@RestController
@Validated
@RequestMapping("api/v1/export")
@Tag(name = "Export Controller", description = "Controlador para exportar los extractos")
public class ExportController {
    private final HttpServletRequest httpServletRequest;
    private final IExportService exportService;

    public ExportController(HttpServletRequest httpServletRequest, IExportService exportService) {
        this.httpServletRequest = httpServletRequest;
        this.exportService = exportService;
    }

    @Operation(summary = "Exportar Extractos", description = "Obtener el extracto de una cuenta en formato PDF, CSV")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Exporta los extractos de un rago de fecha"),
            @ApiResponse(responseCode = "400", description = "Algún error en los filtros", content = @Content(schema = @Schema(implementation = ErrorResponse.class), mediaType = "application/json")),
            @ApiResponse(responseCode = "500", description = "Error interno", content = @Content(schema = @Schema(implementation = ErrorResponse.class), mediaType = "application/json"))
    })
    @PostMapping(value = "/accounts/{accountId}", produces = {"application/pdf", "text/csv"})
    public ResponseEntity<AccountStatementExportResponse> generateExtractReport(
            @RequestHeader("device-id") @NotBlank @Parameter(description = "Este es el Unique deviceId", example = "42ebffbd7c30307d") String deviceId,
            @RequestHeader("device-name") @NotBlank @Parameter(description = "Este es el deviceName", example = "ANDROID") String deviceName,
            @RequestHeader("geo-position-x") @NotBlank @Parameter(description = "Este es el geoPositionX", example = "12.265656") String geoPositionX,
            @RequestHeader("geo-position-y") @NotBlank @Parameter(description = "Este es el geoPositionY", example = "12.454545") String geoPositionY,
            @RequestHeader("app-version") @NotBlank @Parameter(description = "Este es el appVersion", example = "1.3.3") String appVersion,
            @PathVariable("accountId") @NotBlank @Parameter(description = "id de la cuenta", example = "654654678") String accountId,
            @Valid @RequestBody ExportRequest body
    ) throws IOException {
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(exportService.generateReport(body, accountId, Headers.getParameter(httpServletRequest,
                deviceId,
                deviceName,
                geoPositionX,
                geoPositionY,
                appVersion)));
    }
}
