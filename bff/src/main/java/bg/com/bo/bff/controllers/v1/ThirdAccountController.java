package bg.com.bo.bff.controllers.v1;

import bg.com.bo.bff.model.ThirdAccountListResponse;
import bg.com.bo.bff.services.interfaces.IThirdAccountService;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("api/v1/third-accounts")
public class ThirdAccountController {

    @Autowired
    private IThirdAccountService iThirdAccountService;

    @GetMapping("/persons/{personId}/companies/{company}")
    public ResponseEntity<ThirdAccountListResponse> getThirdAccounts(
            @PathVariable("personId") @NotBlank @Parameter(description = "Este es el personId", example = "12345") String personId,
            @PathVariable("company") @NotBlank @Parameter(description = "Este es el número de documento de identidad", example = "1234567") String company) throws IOException {
        return ResponseEntity.ok(iThirdAccountService.getListThridAccounts(personId, company));
    }
}
