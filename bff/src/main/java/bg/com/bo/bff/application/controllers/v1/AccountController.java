package bg.com.bo.bff.application.controllers.v1;

import bg.com.bo.bff.models.dtos.accounts.AccountListResponse;
import bg.com.bo.bff.application.dtos.response.ErrorResponse;
import bg.com.bo.bff.services.interfaces.IAccountService;
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
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@Validated
@RequestMapping("api/v1/accounts")
@Tag(name = "Account Controller", description = "Controlador de cuentas")
public class AccountController {
    @Autowired
    private IAccountService iAccountService;

    @Operation(summary = "Own Accounts Request", description = "Este es el Endpoint donde el usuario obtendrá sus cuentas")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Obtiene todas las cuentas, para un personId con su document-number", content = @Content(schema = @Schema(implementation = AccountListResponse.class),mediaType = "application/json")),
            @ApiResponse(responseCode = "400", description = "Existe un error en los parametros otorgados.", content = @Content(schema = @Schema(implementation = ErrorResponse.class), mediaType = "application/json")),
            @ApiResponse(responseCode = "401", description = "Token expirado, devuelve un 401 ErrorResponse", content = @Content(schema = @Schema(implementation = ErrorResponse.class), mediaType = "application/json")),
            @ApiResponse(responseCode = "406", description = "El document no corresponde al personId, devuelve un 406 ErrorResponse", content = @Content(schema = @Schema(implementation = ErrorResponse.class), mediaType = "application/json")),
            @ApiResponse(responseCode = "500", description = "Un error interno, devuelve un 500 ErrorResponse", content = @Content(schema = @Schema(implementation = ErrorResponse.class), mediaType = "application/json"))
    })
    @GetMapping("/persons/{personId}/document-number/{document}")
    public ResponseEntity<AccountListResponse> accounts(
            @PathVariable("personId") @NotBlank @Parameter(description = "Este es el personId", example = "12345") String personId,
            @PathVariable("document") @NotBlank @Parameter(description = "Este es el número de documento de identidad", example = "1234567") String document) throws IOException {

//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        if (authentication != null && !(authentication instanceof AnonymousAuthenticationToken)) {
//            UserData userData = (UserData) authentication.getPrincipal();
//            if (!Objects.equals(userData.getPersonId(), personId))
//                throw new BadCredentialsException("Las credenciales no corresponden con el persona id enviado.");
            return ResponseEntity.ok(iAccountService.getAccounts(personId, document));
//        }
//        throw new UnauthorizedException("Not valid authorization data.");
    }
}
