package bg.com.bo.bff.application.controllers.v1;

import bg.com.bo.bff.application.config.request.tracing.AbstractBFFController;
import bg.com.bo.bff.application.dtos.response.generic.GenericResponse;
import bg.com.bo.bff.commons.annotations.OnlyNumber;
import bg.com.bo.bff.services.interfaces.ICryptoCurrencyService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@Validated
@RestController
@RequestMapping("api/v1/cryptocurrency")
@Tag(name = "Crypto Currency Controller", description = "Controlador de GanaCripto")
public class CryptoCurrencyController extends AbstractBFFController {
    private final ICryptoCurrencyService service;

    public CryptoCurrencyController(ICryptoCurrencyService service) {
        this.service = service;
    }

    @Operation(summary = "Registrar una cuenta", description = "Registrar una cuenta en GanaCripto",
            operationId = "registerAccount")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Registrar una cuenta en GanaCripto")
    })
    @PostMapping("/persons/{personId}/account-create")
    public ResponseEntity<GenericResponse> registerAccount(
            @PathVariable("personId") @OnlyNumber @Parameter(description = "Este es el personId de la persona", example = "12345") String personId
    ) throws IOException {
        getDeviceDataHeader();
       return ResponseEntity.ok(service.registerAccount(personId));
    }
}
