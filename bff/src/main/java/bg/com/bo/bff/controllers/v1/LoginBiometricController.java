package bg.com.bo.bff.controllers.v1;

import bg.com.bo.bff.model.dtos.ErrorResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

import bg.com.bo.bff.controllers.request.LoginBiometricRequest;
import bg.com.bo.bff.controllers.response.TransferResponse;
import bg.com.bo.bff.services.interfaces.ILoginBiometricService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@Validated
@RequestMapping("api/v1/login-biometric")
@Tag(name = "Login Biometric", description = "Login Biometric GanaMoovil")
public class LoginBiometricController {


    @Autowired
    private ILoginBiometricService service;

    @Operation(summary = "Login Biometric Request", description = "Login Biometric GanaMovil")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Devulve los datos del cliente", content = @Content(schema = @Schema(implementation = Object.class),mediaType = "application/json")),
            @ApiResponse(responseCode = "400", description = "Existe un error en los parametros otorgados.", content = @Content(schema = @Schema(implementation = ErrorResponse.class), mediaType = "application/json")),
            @ApiResponse(responseCode = "401", description = "Token expirado, devuelve un 401 ErrorResponse", content = @Content(schema = @Schema(implementation = ErrorResponse.class), mediaType = "application/json")),
            @ApiResponse(responseCode = "406", description = "Error del sistema", content = @Content(schema = @Schema(implementation = ErrorResponse.class), mediaType = "application/json")),
            @ApiResponse(responseCode = "500", description = "Un error interno, devuelve un 500 ErrorResponse", content = @Content(schema = @Schema(implementation = ErrorResponse.class), mediaType = "application/json"))
    })
    @PostMapping("/")
    public ResponseEntity<Object> transfer(
            @Valid @RequestBody LoginBiometricRequest body

    ) throws IOException {
        return ResponseEntity.ok(service.loginBiometric(body));
    }



}
