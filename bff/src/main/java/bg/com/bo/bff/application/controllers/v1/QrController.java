package bg.com.bo.bff.application.controllers.v1;

import bg.com.bo.bff.application.dtos.request.QRCodeGenerateRequest;
import bg.com.bo.bff.application.dtos.request.QRCodeRegenerateRequest;
import bg.com.bo.bff.application.dtos.request.qr.QrDecryptRequest;
import bg.com.bo.bff.application.dtos.request.qr.QrListRequest;
import bg.com.bo.bff.application.dtos.response.ErrorResponse;
import bg.com.bo.bff.application.dtos.response.qr.QrDecryptResponse;
import bg.com.bo.bff.application.dtos.response.qr.QrListResponse;
import bg.com.bo.bff.commons.utils.Headers;
import bg.com.bo.bff.providers.dtos.responses.qr.QRCodeGenerateResponse;
import bg.com.bo.bff.services.interfaces.IQrService;
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


@RestController
@Validated
@RequestMapping("api/v1/qrs")
@Tag(name = "QR Controller", description = "Controlador del módulo de QR")
public class QrController {
    private final HttpServletRequest httpServletRequest;
    private IQrService iQrService;

    public QrController(HttpServletRequest httpServletRequest, IQrService iQrService) {
        this.httpServletRequest = httpServletRequest;
        this.iQrService = iQrService;
    }

    @Operation(summary = "List of QR Generated and Paid", description = "Lista de QR Generados y Pagados")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Obtiene todos los QR generados y Pagados", content = @Content(schema = @Schema(implementation = QrListResponse.class), mediaType = "application/json")),
            @ApiResponse(responseCode = "400", description = "Error en los parametros", content = @Content(schema = @Schema(implementation = ErrorResponse.class), mediaType = "application/json")),
            @ApiResponse(responseCode = "500", description = "Un error interno", content = @Content(schema = @Schema(implementation = ErrorResponse.class), mediaType = "application/json"))
    })
    @PostMapping("/persons/{personId}")
    public ResponseEntity<QrListResponse> getQrsGeneratedAndPaid(
            @PathVariable("personId") @NotNull @Parameter(description = "Este es el código de persona", example = "1234567") Integer personId,
            @RequestHeader("device-id") @NotBlank @Parameter(description = "Este es el Unique deviceId", example = "42ebffbd7c30307d") String deviceId,
            @RequestHeader("device-name") @NotBlank @Parameter(description = "Este es el deviceName", example = "ANDROID") String deviceName,
            @RequestHeader("geo-position-x") @NotBlank @Parameter(description = "Este es el geoPositionX", example = "12.265656") String geoPositionX,
            @RequestHeader("geo-position-y") @NotBlank @Parameter(description = "Este es el geoPositionY", example = "12.454545") String geoPositionY,
            @RequestHeader("app-version") @NotBlank @Parameter(description = "Este es el appVersion", example = "1.3.3") String appVersion,
            @Valid @RequestBody QrListRequest request
    ) throws IOException {
        return ResponseEntity.ok(iQrService.getQrGeneratedPaid(request, personId, Headers.getParameter(httpServletRequest)));
    }

    @Operation(summary = "Generate QR", description = "Este es el Endpoint donde el usuario ganamovil hará su petición para generar QR de cobro")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Generate QR Success, devuelve QRCodeGenerateResponse", content = @Content(schema = @Schema(implementation = QRCodeGenerateResponse.class), mediaType = "application/json")),
            @ApiResponse(responseCode = "401", description = "Generate QR Failed, devuelve un 401 ErrorResponse", content = @Content(schema = @Schema(implementation = ErrorResponse.class), mediaType = "application/json")),
            @ApiResponse(responseCode = "406", description = "Los parámetros proporcionados no son válidos.", content = @Content(schema = @Schema(implementation = ErrorResponse.class), mediaType = "application/json")),
            @ApiResponse(responseCode = "500", description = "Error interno.", content = @Content(schema = @Schema(implementation = ErrorResponse.class), mediaType = "application/json"))
    })
    @PostMapping("/generate")
    public ResponseEntity<QRCodeGenerateResponse> generate(
            @RequestHeader("device-id") @NotBlank @Parameter(description = "Este es el Unique deviceId", example = "42ebffbd7c30307d") String deviceId,
            @RequestHeader("device-name") @Parameter(description = "Este es el deviceName", example = "ANDROID") String deviceName,
            @RequestHeader("geo-position-x") @NotBlank @Parameter(description = "Este es el geoPositionX", example = "12.265656") String geoPositionX,
            @RequestHeader("geo-position-y") @NotBlank @Parameter(description = "Este es el geoPositionY", example = "12.454545") String geoPositionY,
            @RequestHeader("app-version") @NotBlank @Parameter(description = "Este es el appVersion", example = "1.3.3") String appVersion,

            @RequestBody
            final @Valid QRCodeGenerateRequest request
    ) throws IOException {

        return ResponseEntity.ok(iQrService.generateQR(request, Headers.getParameter(httpServletRequest)));
    }

    @Operation(summary = "Regenerate QR", description = "Este es el Endpoint donde el usuario ganamovil hará su petición para regenerar QR de cobro")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Regenerate QR Success, devuelve QRCodeGenerateResponse", content = @Content(schema = @Schema(implementation = QRCodeGenerateResponse.class), mediaType = "application/json")),
            @ApiResponse(responseCode = "401", description = "Regenerate QR Failed, devuelve un 401 ErrorResponse", content = @Content(schema = @Schema(implementation = ErrorResponse.class), mediaType = "application/json")),
            @ApiResponse(responseCode = "406", description = "Los parámetros proporcionados no son válidos.", content = @Content(schema = @Schema(implementation = ErrorResponse.class), mediaType = "application/json")),
            @ApiResponse(responseCode = "500", description = "Error interno.", content = @Content(schema = @Schema(implementation = ErrorResponse.class), mediaType = "application/json"))
    })
    @PostMapping("/regenerate")
    public ResponseEntity<QRCodeGenerateResponse> generate(
            @RequestHeader("device-id") @NotBlank @Parameter(description = "Este es el Unique deviceId", example = "42ebffbd7c30307d") String deviceId,
            @RequestHeader("device-name") @Parameter(description = "Este es el deviceName", example = "ANDROID") String deviceName,
            @RequestHeader("geo-position-x") @NotBlank @Parameter(description = "Este es el geoPositionX", example = "12.265656") String geoPositionX,
            @RequestHeader("geo-position-y") @NotBlank @Parameter(description = "Este es el geoPositionY", example = "12.454545") String geoPositionY,
            @RequestHeader("app-version") @NotBlank @Parameter(description = "Este es el appVersion", example = "1.3.3") String appVersion,

            @RequestBody
            final @Valid QRCodeRegenerateRequest request
    ) throws IOException {

        return ResponseEntity.ok(iQrService.regenerateQR(request, Headers.getParameter(httpServletRequest)));
    }

    @Operation(summary = "Obtención de datos QR", description = "Este es el endpoint donde se podrá obtener los datos de un QR")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Obtener datos QR Success, retorna los valores del QR", content = @Content(schema = @Schema(implementation = QrDecryptResponse.class), mediaType = "application/json")),
            @ApiResponse(responseCode = "401", description = "Obtener datos QR Failed, devuelve un 401 ErrorResponse", content = @Content(schema = @Schema(implementation = ErrorResponse.class), mediaType = "application/json")),
            @ApiResponse(responseCode = "406", description = "Los parámetros proporcionados no son válidos.", content = @Content(schema = @Schema(implementation = ErrorResponse.class), mediaType = "application/json")),
            @ApiResponse(responseCode = "500", description = "Error interno.", content = @Content(schema = @Schema(implementation = ErrorResponse.class), mediaType = "application/json"))
    })
    @PostMapping("/info")
    public ResponseEntity<QrDecryptResponse> decrypt(
            @RequestHeader("device-id") @NotBlank @Parameter(description = "Este es el Unique deviceId", example = "42ebffbd7c30307d") String deviceId,
            @RequestHeader("device-name") @Parameter(description = "Este es el deviceName", example = "ANDROID") String deviceName,
            @RequestHeader("geo-position-x") @NotBlank @Parameter(description = "Este es el geoPositionX", example = "12.265656") String geoPositionX,
            @RequestHeader("geo-position-y") @NotBlank @Parameter(description = "Este es el geoPositionY", example = "12.454545") String geoPositionY,
            @RequestHeader("app-version") @NotBlank @Parameter(description = "Este es el appVersion", example = "1.3.3") String appVersion,

            @RequestBody
            final @Valid QrDecryptRequest request
    ) throws IOException {
        return ResponseEntity.ok(iQrService.decryptQR(request, Headers.getParameter(httpServletRequest)));
    }
}
