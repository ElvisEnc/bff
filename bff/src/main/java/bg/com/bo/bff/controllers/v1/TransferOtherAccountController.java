package bg.com.bo.bff.controllers.v1;

import bg.com.bo.bff.model.dtos.ErrorResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

import bg.com.bo.bff.controllers.request.TransferRequest;
import bg.com.bo.bff.controllers.response.TransferResponse;
import bg.com.bo.bff.services.interfaces.IOtherAccountTransferService;
import bg.com.bo.bff.services.interfaces.IOwnAccountTransferService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;

@RestController
@Validated
@RequestMapping("api/v1/other-account/transfer")
@Tag(name = "Own Account Transfer", description = "Controlador de transferencia a otras cuentas")
public class TransferOtherAccountController {
    @Autowired
    private IOtherAccountTransferService transferService;

    @Operation(summary = "Transfer Own Accounts Request", description = "Transferencias a otros bancos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Devulve el identificador de un comprobante", content = @Content(schema = @Schema(implementation = TransferResponse.class),mediaType = "application/json")),
            @ApiResponse(responseCode = "400", description = "Existe un error en los parametros otorgados.", content = @Content(schema = @Schema(implementation = ErrorResponse.class), mediaType = "application/json")),
            @ApiResponse(responseCode = "401", description = "Token expirado, devuelve un 401 ErrorResponse", content = @Content(schema = @Schema(implementation = ErrorResponse.class), mediaType = "application/json")),
            @ApiResponse(responseCode = "406", description = "El document no corresponde al personId, devuelve un 406 ErrorResponse", content = @Content(schema = @Schema(implementation = ErrorResponse.class), mediaType = "application/json")),
            @ApiResponse(responseCode = "500", description = "Un error interno, devuelve un 500 ErrorResponse", content = @Content(schema = @Schema(implementation = ErrorResponse.class), mediaType = "application/json"))
    })
    @PostMapping("/persons/{personId}")
    public ResponseEntity<TransferResponse> transfer(
            @PathVariable("personId") @NotBlank @Parameter(description = "Este es el personId", example = "12345") String personId,
            @Valid @RequestBody TransferRequest body

    ) throws IOException {
        return ResponseEntity.ok(transferService.transfer(personId, body));
    }
}
