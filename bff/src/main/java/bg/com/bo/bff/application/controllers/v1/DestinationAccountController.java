package bg.com.bo.bff.application.controllers.v1;


import bg.com.bo.bff.application.dtos.request.AddThirdAccountRequest;
import bg.com.bo.bff.application.dtos.request.DeleteThirdAccountRequest;
import bg.com.bo.bff.application.dtos.response.ErrorResponse;
import bg.com.bo.bff.application.dtos.response.GenericResponse;
import bg.com.bo.bff.commons.enums.DeviceMW;
import bg.com.bo.bff.services.interfaces.IDestinationAccountService;
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

import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("api/v1/destination-accounts")
@Tag(name = "Destination Account Controller", description = "Controlador de cuentas destinoo")
public class DestinationAccountController {

    private final HttpServletRequest httpServletRequest;

    private final IDestinationAccountService service;

    public DestinationAccountController(HttpServletRequest httpServletRequest, IDestinationAccountService service) {
        this.httpServletRequest = httpServletRequest;
        this.service = service;
    }

    @PutMapping("/{personId}/third-accounts")
    public ResponseEntity<GenericResponse> addThirdAccounts(
            @Parameter(description = "Este es el personId", example = "1234567")
            @PathVariable("personId")
            @NotBlank
            String personId,
            @RequestBody AddThirdAccountRequest addThirdAccountRequest) throws IOException {
        return ResponseEntity.ok(service.addThirdAccount(personId, addThirdAccountRequest,getParameter(httpServletRequest)));
    }

    @Operation(summary = "Eliminación de cuenta de terceros.", description = "Elimina cuenta de terceros.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Resultado de la operación y su descripción.", content = @Content(schema = @Schema(implementation = GenericResponse.class), mediaType = "application/json")),
            @ApiResponse(responseCode = "400", description = "Datos inválidos.", content = @Content(schema = @Schema(implementation = ErrorResponse.class), mediaType = "application/json")),
            @ApiResponse(responseCode = "500", description = "Error interno.", content = @Content(schema = @Schema(implementation = ErrorResponse.class), mediaType = "application/json"))
    })
    @DeleteMapping("/{personId}/third-accounts/{identifier}/delete")
    public ResponseEntity<GenericResponse> deleteThirdAccount(
            @Valid @RequestHeader("device-id") String deviceId,
            @PathVariable("personId") @NotBlank @Parameter(description = "Este es el personId", example = "12345") String personId,
            @PathVariable("identifier") @NotNull @Parameter(description = "Este es el identificador de la cuenta", example = "12345") int identifier,
            @Valid @RequestBody DeleteThirdAccountRequest request,
            HttpServletRequest servletRequest
    ) throws IOException {
        String ip = servletRequest.getRemoteAddr();
        return ResponseEntity.ok(service.delete(personId, identifier, deviceId, ip, request));
    }

    public Map<String, String> getParameter(HttpServletRequest request) {
        Map<String, String> parameters = new HashMap<>();

        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String headerName = headerNames.nextElement();
            String headerValue = request.getHeader(headerName);
            parameters.put(headerName, headerValue);
        }
        parameters.put(DeviceMW.DEVICE_IP.getCode(),request.getRemoteAddr());

        return parameters;
    }
}
