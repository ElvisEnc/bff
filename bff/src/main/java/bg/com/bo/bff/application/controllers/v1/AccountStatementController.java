package bg.com.bo.bff.application.controllers.v1;

import bg.com.bo.bff.application.dtos.request.account.statement.ExtractRequest;
import bg.com.bo.bff.application.dtos.response.ErrorResponse;
import bg.com.bo.bff.application.dtos.response.ExtractDataResponse;
import bg.com.bo.bff.services.interfaces.IAccountStatementService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;


@RestController
@Validated
@RequestMapping("api/v1/account-statement")
@Tag(name = "Account Statement Controller", description = "Controlador de extractos de cuentas")
public class AccountStatementController {

    @Autowired
    IAccountStatementService iAccountStatementService;

    @Operation(summary = "Extracto", description = "Obtener el extracto de una cuenta")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Obtiene los extractos dentro de la fecha", content = @Content(schema = @Schema(implementation = ExtractDataResponse.class), mediaType = "application/json")),
            @ApiResponse(responseCode = "400", description = "Algún error en los filtros", content = @Content(schema = @Schema(implementation = ErrorResponse.class), mediaType = "application/json")),
            @ApiResponse(responseCode = "500", description = "Error interno", content = @Content(schema = @Schema(implementation = ErrorResponse.class), mediaType = "application/json"))
    })
    @PostMapping("/{accountId}/persons/{personId}")
    public ResponseEntity<ExtractDataResponse> extract(
            @PathVariable("accountId") @NotBlank @Parameter(description = "id de la cuenta", example = "654654678") String accountId,
            @PathVariable("personId") @NotBlank @Parameter(description = "código de persona", example = "12345") String personId,
            @Valid @RequestBody ExtractRequest body
    ) throws IOException {
        ExtractDataResponse response = iAccountStatementService.getAccountStatement(body, accountId);

        return ResponseEntity.ok(response);
    }
}
