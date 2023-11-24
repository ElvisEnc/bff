package bg.com.bo.bff.controllers;

import bg.com.bo.bff.model.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1")
@Tag(name = "LoginController", description = "Controlador del Login")
public class LoginController {

    @Operation(summary = "Login Request", description = "Este es el Endpoint donde el usuario ganamovil hará su petición login y se le devolverá si fue exitoso o fallido")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Login Success, devuelve LoginResponse", content = @Content(schema = @Schema(implementation = LoginResponse.class), mediaType = "application/json")),
            @ApiResponse(responseCode = "401", description = "Login Failed, devuelve un 401 ErrorResponse", content = @Content(schema = @Schema(implementation = ErrorResponse.class), mediaType = "application/json"))
    })
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        if (loginRequest.getCedula().equals("0000")) {
            ErrorResponse errorResponse = new ErrorResponse(HttpStatus.UNAUTHORIZED.name(), HttpStatus.UNAUTHORIZED.name());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);
        }
        User codPersona = new User(10);
        LoginResponse response = new LoginResponse("SUCCESS", codPersona);
        return ResponseEntity.ok(response);
    }
}
