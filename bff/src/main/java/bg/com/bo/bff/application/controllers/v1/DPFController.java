package bg.com.bo.bff.application.controllers.v1;

import bg.com.bo.bff.application.dtos.response.DPFListResponse;
import bg.com.bo.bff.application.dtos.response.ErrorResponse;
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
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/dpfs")
@Tag(name = "DPF Controller", description = "Controlador de DPFs")
public class DPFController {

    private final HttpServletRequest httpServletRequest;

    private final IDPFService service;

    public DPFController(HttpServletRequest httpServletRequest, IDPFService service) {
        this.httpServletRequest = httpServletRequest;
        this.service = service;
    }

    @Operation(summary = "Listar los DPFs de una persona.", description = "Este endpoint obtiene el listado de DPFs de una persona.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Resultado de la operación y su descripción.", content = @Content(schema = @Schema(implementation = DPFListResponse.class), mediaType = "application/json")),
            @ApiResponse(responseCode = "400", description = "Datos inválidos.", content = @Content(schema = @Schema(implementation = ErrorResponse.class), mediaType = "application/json")),
            @ApiResponse(responseCode = "500", description = "Error interno.", content = @Content(schema = @Schema(implementation = ErrorResponse.class), mediaType = "application/json"))
    })
    @GetMapping("persons/{personId}")
    public ResponseEntity<DPFListResponse> getListDPFs(
            @Parameter(description = "Este es el deviceId", example = "123")
            @Valid @RequestHeader("device-id") String deviceId,
            @Parameter(description = "Este es el personId", example = "1234567")
            @PathVariable("personId")
            @NotBlank
            @NotNull
            String personId) throws Exception {
        return ResponseEntity.ok(service.getDPFsList(personId, deviceId, Headers.getParameter(httpServletRequest)));
    }
}
