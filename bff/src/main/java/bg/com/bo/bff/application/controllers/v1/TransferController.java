package bg.com.bo.bff.application.controllers.v1;

import bg.com.bo.bff.application.dtos.request.Pcc01Request;
import bg.com.bo.bff.application.dtos.request.TransferRequest;
import bg.com.bo.bff.application.dtos.response.Pcc01Response;
import bg.com.bo.bff.application.dtos.response.TransferResponse;
import bg.com.bo.bff.application.dtos.response.ErrorResponse;
import bg.com.bo.bff.commons.utils.Headers;
import bg.com.bo.bff.services.interfaces.IOtherAccountTransferService;
import bg.com.bo.bff.services.interfaces.ITransferService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

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
@RequestMapping("api/v1/transfers")
@Tag(name = "Transfer Controller", description = "Controlador de transferencia")
public class TransferController {
    @Autowired
    private HttpServletRequest httpServletRequest;

    @Autowired
    private IOwnAccountTransferService transferService;

    @Autowired
    private IOtherAccountTransferService thirdTransferService;

    @Autowired
    private ITransferService pcc01Service;


    @Operation(summary = "Transfer Own Accounts Request", description = "Transferencias")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Devulve el identificador de un comprobante", content = @Content(schema = @Schema(implementation = TransferResponse.class),mediaType = "application/json")),
            @ApiResponse(responseCode = "400", description = "Existe un error en los parametros otorgados.", content = @Content(schema = @Schema(implementation = ErrorResponse.class), mediaType = "application/json")),
            @ApiResponse(responseCode = "401", description = "Token expirado, devuelve un 401 ErrorResponse", content = @Content(schema = @Schema(implementation = ErrorResponse.class), mediaType = "application/json")),
            @ApiResponse(responseCode = "406", description = "El document no corresponde al personId, devuelve un 406 ErrorResponse", content = @Content(schema = @Schema(implementation = ErrorResponse.class), mediaType = "application/json")),
            @ApiResponse(responseCode = "500", description = "Un error interno, devuelve un 500 ErrorResponse", content = @Content(schema = @Schema(implementation = ErrorResponse.class), mediaType = "application/json"))
    })
    @PostMapping("/persons/{personId}/accounts/{accountId}/own-account")
    public ResponseEntity<TransferResponse> ownTransfer(
            @RequestHeader("device-id") @NotBlank @Parameter(description = "Este es el Unique deviceId", example = "42ebffbd7c30307d") String deviceId,
            @RequestHeader("device-name") @Parameter(description = "Este es el deviceName", example = "ANDROID") String deviceName,
            @RequestHeader("geo-position-x") @NotBlank @Parameter(description = "Este es el geoPositionX", example = "12.265656") String geoPositionX,
            @RequestHeader("geo-position-y") @NotBlank @Parameter(description = "Este es el geoPositionY", example = "12.454545") String geoPositionY,
            @RequestHeader("app-version") @NotBlank @Parameter(description = "Este es el appVersion", example = "1.3.3") String appVersion,
            @PathVariable("personId") @NotBlank @Parameter(description = "Este es el personId", example = "12345") String personId,
            @PathVariable("accountId") @NotBlank @Parameter(description = "Este es el accountId", example = "12345") String accountId,
            @Valid @RequestBody TransferRequest body

    ) throws IOException {
        return ResponseEntity.ok(transferService.transfer(personId, accountId, body, Headers.getParameter(httpServletRequest,
                deviceId,
                deviceName,
                geoPositionX,
                geoPositionY,
                appVersion)));
    }


    @Operation(summary = "Transfer Third Accounts Request", description = "Transferencias a cuentas de terceros")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Devulve el identificador de un comprobante", content = @Content(schema = @Schema(implementation = TransferResponse.class),mediaType = "application/json")),
            @ApiResponse(responseCode = "400", description = "Existe un error en los parametros otorgados.", content = @Content(schema = @Schema(implementation = ErrorResponse.class), mediaType = "application/json")),
            @ApiResponse(responseCode = "401", description = "Token expirado, devuelve un 401 ErrorResponse", content = @Content(schema = @Schema(implementation = ErrorResponse.class), mediaType = "application/json")),
            @ApiResponse(responseCode = "406", description = "El document no corresponde al personId, devuelve un 406 ErrorResponse", content = @Content(schema = @Schema(implementation = ErrorResponse.class), mediaType = "application/json")),
            @ApiResponse(responseCode = "500", description = "Un error interno, devuelve un 500 ErrorResponse", content = @Content(schema = @Schema(implementation = ErrorResponse.class), mediaType = "application/json"))
    })
    @PostMapping("/persons/{personId}/third-accounts")
    public ResponseEntity<TransferResponse> thirdTransfer(
            @PathVariable("personId") @NotBlank @Parameter(description = "Este es el personId", example = "12345") String personId,
            @Valid @RequestBody TransferRequest body

    ) throws IOException {
        return ResponseEntity.ok(thirdTransferService.transfer(personId, body));
    }

    @Operation(summary = "Validación del lavado de dinero", description = "Se realiza el control del lavado de dinero.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Devuelve si requiere o no el control de lavado de dinero.", content = @Content(schema = @Schema(implementation = Pcc01Response.class), mediaType = "application/json")),
            @ApiResponse(responseCode = "400", description = "Existe un error en los datos otorgados.", content = @Content(schema = @Schema(implementation = ErrorResponse.class), mediaType = "application/json")),
            @ApiResponse(responseCode = "500", description = "Ocurrio un error no controlado.", content = @Content(schema = @Schema(implementation = ErrorResponse.class), mediaType = "application/json"))
    })
    @PostMapping("/validate-digital")
    public ResponseEntity<Pcc01Response> control(@Valid @RequestBody Pcc01Request request) throws IOException {
        return ResponseEntity.ok(pcc01Service.makeControl(request));
    }
}
