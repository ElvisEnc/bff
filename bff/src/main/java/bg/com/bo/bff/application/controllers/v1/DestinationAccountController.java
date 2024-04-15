package bg.com.bo.bff.application.controllers.v1;


import bg.com.bo.bff.application.dtos.request.AddThirdAccountRequest;
import bg.com.bo.bff.application.dtos.response.GenericResponse;
import bg.com.bo.bff.commons.enums.DeviceMW;
import bg.com.bo.bff.services.interfaces.IDestinationAccountService;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.constraints.NotBlank;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
