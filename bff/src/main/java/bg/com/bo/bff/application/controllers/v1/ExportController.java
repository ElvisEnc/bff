package bg.com.bo.bff.application.controllers.v1;

import bg.com.bo.bff.application.dtos.request.ExportRequest;
import bg.com.bo.bff.application.dtos.response.ErrorResponse;
import bg.com.bo.bff.application.dtos.response.ExportResponse;
import bg.com.bo.bff.services.interfaces.IExportService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private IExportService exportService;

    @Operation(summary = "Exportar Extractos", description = "Obtener el extracto de una cuenta en formato PDF, CSV")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Exporta los extractos de un rago de fecha"),
            @ApiResponse(responseCode = "404", description = "Erro en el url path"),
            @ApiResponse(responseCode = "400", description = "Algún error en los filtros", content = @Content(schema = @Schema(implementation = ErrorResponse.class), mediaType = "application/json")),
            @ApiResponse(responseCode = "500", description = "Error interno", content = @Content(schema = @Schema(implementation = ErrorResponse.class), mediaType = "application/json"))
    })
    @PostMapping(value = "/accounts/{accountId}", produces = {"application/pdf", "text/csv"})
    public ResponseEntity<ExportResponse> generateExtractReport(
            @PathVariable("accountId") @NotNull @Parameter(description = "id de la cuenta", example = "7456455") Integer accountId,
            @Valid @RequestBody ExportRequest body
    ) throws IOException {

        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(exportService.generateReport(body, String.valueOf(accountId)));

    }
}
