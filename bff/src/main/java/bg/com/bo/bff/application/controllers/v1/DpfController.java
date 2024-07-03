package bg.com.bo.bff.application.controllers.v1;

import bg.com.bo.bff.application.dtos.response.dpf.DpfListResponse;
import bg.com.bo.bff.application.dtos.response.generic.ErrorResponse;
import bg.com.bo.bff.commons.utils.Headers;
import bg.com.bo.bff.services.interfaces.IDPFService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.constraints.NotBlank;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/dpfs")
@Tag(name = "DPF Controller", description = "Controlador de DPFs")
public class DpfController {

    private final HttpServletRequest httpServletRequest;

    private final IDPFService service;

    public DpfController(HttpServletRequest httpServletRequest, IDPFService service) {
        this.httpServletRequest = httpServletRequest;
        this.service = service;
    }

    @Operation(summary = "Listar los DPFs de una persona.", description = "Este endpoint obtiene el listado de DPFs de una persona.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Resultado de la operación y su descripción.", content = @Content(schema = @Schema(implementation = DpfListResponse.class), mediaType = "application/json")),
            @ApiResponse(responseCode = "400", description = "Datos inválidos.", content = @Content(schema = @Schema(implementation = ErrorResponse.class), mediaType = "application/json")),
            @ApiResponse(responseCode = "500", description = "Error interno.", content = @Content(schema = @Schema(implementation = ErrorResponse.class), mediaType = "application/json"))
    })
    @GetMapping("persons/{personId}")
    public ResponseEntity<DpfListResponse> getListDPFs(
            @RequestHeader("device-id") @NotBlank @Parameter(description = "Este es el Unique deviceId", example = "42ebffbd7c30307d") String deviceId,
            @RequestHeader("device-name") @Parameter(description = "Este es el deviceName", example = "ANDROID") String deviceName,
            @RequestHeader("geo-position-x") @NotBlank @Parameter(description = "Este es el geoPositionX", example = "12.265656") String geoPositionX,
            @RequestHeader("geo-position-y") @NotBlank @Parameter(description = "Este es el geoPositionY", example = "12.454545") String geoPositionY,
            @RequestHeader("app-version") @NotBlank @Parameter(description = "Este es el appVersion", example = "1.3.3") String appVersion,
            @PathVariable("personId")
            @NotBlank
            @Parameter(description = "Este es el personId", example = "12345") String personId
    ) throws Exception {
        return ResponseEntity.ok(service.getDPFsList(personId, deviceId, Headers.getParameter(httpServletRequest,
                deviceId,
                deviceName,
                geoPositionX,
                geoPositionY,
                appVersion)));
    }
}
