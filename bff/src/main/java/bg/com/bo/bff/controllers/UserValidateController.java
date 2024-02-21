package bg.com.bo.bff.controllers;

import bg.com.bo.bff.model.UserValidateRequest;
import bg.com.bo.bff.services.interfaces.IUserValidateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/validate")
public class UserValidateController {
    @Autowired
    private IUserValidateService userValidateService;

    @PostMapping("/user")
    public ResponseEntity<Object> validarUsuario(@RequestBody UserValidateRequest request) {
        return ResponseEntity.ok(userValidateService.validarUsuario(request));
    }
}
