package bg.com.bo.bff.application.controllers.v1;

import bg.com.bo.bff.application.dtos.request.ExtractRequest;
import bg.com.bo.bff.application.dtos.response.ErrorResponse;
import bg.com.bo.bff.application.dtos.response.ExtractDataResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@RestController
@Validated
@RequestMapping("api/v1/account-statement")
@Tag(name = "Account Statement Controller", description = "Controlador de extractos de cuentas")
public class AccountStatementController {

    @Operation(summary = "Extracto", description = "Obtener el extracto de una cuenta")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Obtiene los extractos dentro de la fecha", content = @Content(schema = @Schema(implementation = ExtractDataResponse.class), mediaType = "application/json")),
            @ApiResponse(responseCode = "406", description = "Algún error en los filtros", content = @Content(schema = @Schema(implementation = ErrorResponse.class), mediaType = "application/json")),
            @ApiResponse(responseCode = "500", description = "Error interno", content = @Content(schema = @Schema(implementation = ErrorResponse.class), mediaType = "application/json"))
    })
    @PostMapping("/{accountId}/persons/{personId}")
    public ResponseEntity<ExtractDataResponse> extract(
            @PathVariable("accountId") @NotBlank @Parameter(description = "id de la cuenta", example = "654654678") String accountId,
            @PathVariable("personId") @NotBlank @Parameter(description = "código de persona", example = "12345") String personId,
            @Valid @RequestBody ExtractRequest body
    ) {
        ExtractDataResponse response = new ExtractDataResponse();
        response.setData(generateExtractResponses(5));

        return ResponseEntity.ok(response);
    }

    private static List<ExtractDataResponse.ExtractResponse> generateExtractResponses(int count) {
        List<ExtractDataResponse.ExtractResponse> responses = new ArrayList();
        Random random = new Random();
        String[] statuses = {"1", "2", "3"};
        String[] types = {"1", "2"};
        String[] currencies = {"068", "225", "840"};
        String[] channels = {"Online", "ATM", "Mobile"};
        DecimalFormat decimalFormat = new DecimalFormat("#.##");
        for (int i = 0; i < count; i++) {
            ExtractDataResponse.ExtractResponse extractResponse = new ExtractDataResponse.ExtractResponse();
            extractResponse.setStatus(statuses[random.nextInt(statuses.length)]);
            extractResponse.setType(types[random.nextInt(types.length)]);
            extractResponse.setAmount(Double.parseDouble(decimalFormat.format(random.nextDouble() * 1000)));
            extractResponse.setCurrency(currencies[random.nextInt(currencies.length)]);
            extractResponse.setChannel(channels[random.nextInt(channels.length)]);
            extractResponse.setDateMov("2024-03-15");
            extractResponse.setTimeMov("12:24:48");
            extractResponse.setMovBalance(Double.parseDouble(decimalFormat.format(random.nextDouble() * 10000)));
            responses.add(extractResponse);
        }
        return responses;
    }
}
