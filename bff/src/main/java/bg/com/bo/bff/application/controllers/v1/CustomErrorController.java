package bg.com.bo.bff.application.controllers.v1;

import bg.com.bo.bff.application.dtos.response.generic.ErrorResponse;
import bg.com.bo.bff.services.interfaces.IErrorService;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

@RestController
public class CustomErrorController implements ErrorController {

    private final ErrorAttributes errorAttributes;
    private final IErrorService errorService;

    public CustomErrorController(ErrorAttributes errorAttributes, IErrorService errorService) {
        this.errorAttributes = errorAttributes;
        this.errorService = errorService;
    }

    @RequestMapping(value = "/error", method = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE, RequestMethod.PATCH})
    public ResponseEntity<ErrorResponse> handleErrors(WebRequest webRequest) {
        return errorService.map(errorAttributes, webRequest);
    }
}
