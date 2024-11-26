package bg.com.bo.bff.services.interfaces;

import bg.com.bo.bff.application.dtos.response.generic.ErrorResponse;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.WebRequest;

public interface IErrorService {
    ResponseEntity<ErrorResponse> map(ErrorAttributes errorAttributes, WebRequest webRequest);
}
