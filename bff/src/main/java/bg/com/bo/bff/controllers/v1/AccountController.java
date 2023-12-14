package bg.com.bo.bff.controllers.v1;

import bg.com.bo.bff.model.AccountListResponse;
import bg.com.bo.bff.services.interfaces.IAccountService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("api/v1")
@Tag(name = "AccountController", description = "Controlador de cuentas")
public class AccountController {
    @Autowired
    private IAccountService iAccountService;
    private static final Logger logger = LogManager.getLogger(AccountController.class.getName());

    @GetMapping("/accounts/{personId}")
    public ResponseEntity<AccountListResponse> accounts(@PathVariable String personId) throws IOException {
        return ResponseEntity.ok(iAccountService.getAccounts(personId));
    }
}
