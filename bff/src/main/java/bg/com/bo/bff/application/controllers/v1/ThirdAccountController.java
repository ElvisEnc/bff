package bg.com.bo.bff.application.controllers.v1;

import bg.com.bo.bff.application.dtos.response.ErrorResponse;
import bg.com.bo.bff.models.ThirdAccountListResponse;
import bg.com.bo.bff.services.interfaces.IThirdAccountService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("api/v1/accounts")
@Tag(name = "Account Controller", description = "Controlador de cuentas")
public class ThirdAccountController {
    @Autowired
    private IThirdAccountService iThirdAccountService;

    @Operation(summary = "Third Accounts Request", description = "Este es el Endpoint donde el usuario obtendrá sus cuentas de terceros")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Obtiene todas las cuentas de terceros, para un personId con su companyId", content = @Content(schema = @Schema(implementation = ThirdAccountListResponse.class),mediaType = "application/json")),
            @ApiResponse(responseCode = "400", description = "Existe un error en los parametros otorgados.", content = @Content(schema = @Schema(implementation = ErrorResponse.class), mediaType = "application/json")),
            @ApiResponse(responseCode = "406", description = "El document no corresponde al personId o el companyId, devuelve un 406 ErrorResponse", content = @Content(schema = @Schema(implementation = ErrorResponse.class), mediaType = "application/json")),
            @ApiResponse(responseCode = "500", description = "Un error interno, devuelve un 500 ErrorResponse", content = @Content(schema = @Schema(implementation = ErrorResponse.class), mediaType = "application/json"))
    })
    @GetMapping("/persons/{personId}/companies/{company}/third-accounts")
    public ResponseEntity<ThirdAccountListResponse> getThirdAccounts(
            @PathVariable("personId") @NotBlank @Parameter(description = "Este es el personId", example = "1234567") String personId,
            @PathVariable("company") @NotBlank @Parameter(description = "Este es el companyId, para ganamovil llega a ser el mismo personId", example = "1234567") String company) throws IOException {
        return ResponseEntity.ok(iThirdAccountService.getListThirdAccounts(personId, company));
    }
}
