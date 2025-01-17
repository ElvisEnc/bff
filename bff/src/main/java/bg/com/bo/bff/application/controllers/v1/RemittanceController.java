package bg.com.bo.bff.application.controllers.v1;

import bg.com.bo.bff.application.config.request.tracing.AbstractBFFController;
import bg.com.bo.bff.application.dtos.response.remittance.ListGeneralParametersResponse;
import bg.com.bo.bff.services.interfaces.IRemittanceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@Validated
@RestController
@RequestMapping("api/v1/remittances")
@Tag(name = "Remittance Controller", description = "Controlador de Giros y remesas")
public class RemittanceController extends AbstractBFFController {
    private final IRemittanceService service;

    public RemittanceController(IRemittanceService service) {
        this.service = service;
    }

    @Operation(summary = "Obtener parámetros generales", description = "Obtiene el listado de parámetros generales de giros y remesas", operationId = "getGeneralParameters")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de parámetros generales")
    })
    @GetMapping(path = "/persons/{personId}/parameters", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<ListGeneralParametersResponse> getGeneralParameters(
            @PathVariable("personId") @NotNull @Parameter(description = "Este es el personId de la persona", example = "12345") String personId
    ) throws IOException {
        getDeviceDataHeader();
        return ResponseEntity.ok(service.getGeneralParameters(personId));
    }
}
