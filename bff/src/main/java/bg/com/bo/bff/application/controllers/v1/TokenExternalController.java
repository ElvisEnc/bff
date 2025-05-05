package bg.com.bo.bff.application.controllers.v1;

import bg.com.bo.bff.application.dtos.request.user.ChangePasswordRequest;
import bg.com.bo.bff.application.dtos.response.generic.GenericResponse;
import bg.com.bo.bff.models.ClientToken;
import bg.com.bo.bff.providers.dtos.request.token.external.TokenAuthenticationRequestDto;
import bg.com.bo.bff.providers.interfaces.ITokenExternalProvider;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/api/v1/crypto-activos")
@RequiredArgsConstructor
public class TokenExternalController {

    private final ITokenExternalProvider tokenService;


    @PostMapping("/authentication")
    public ResponseEntity<ClientToken> authentication(
            @Valid @RequestBody TokenAuthenticationRequestDto tokenAuthenticationRequestDto
    ) throws IOException {
        return ResponseEntity.ok(tokenService.generateAccountAccessToken(tokenAuthenticationRequestDto));
    }
}