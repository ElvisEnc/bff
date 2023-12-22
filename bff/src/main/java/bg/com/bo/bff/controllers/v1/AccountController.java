package bg.com.bo.bff.controllers.v1;

import bg.com.bo.bff.model.AccountListResponse;
import bg.com.bo.bff.services.interfaces.IAccountService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("api/v1/accounts")
@Tag(name = "AccountController", description = "Controlador de cuentas")
public class AccountController {
    @Autowired
    private IAccountService iAccountService;

    @GetMapping("/persons/{personId}/document-number/{documentNumber}")
    public ResponseEntity<AccountListResponse> accounts(@PathVariable String personId, @PathVariable String documentNumber) throws IOException {
        return ResponseEntity.ok(iAccountService.getAccounts(personId, documentNumber));
    }
}
