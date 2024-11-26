package bg.com.bo.bff.services.implementations.v1;

import bg.com.bo.bff.application.dtos.response.generic.ErrorResponse;
import bg.com.bo.bff.commons.utils.Util;
import bg.com.bo.bff.providers.models.interfaces.middleware.IMiddlewareError;
import bg.com.bo.bff.providers.models.middleware.DefaultMiddlewareError;
import bg.com.bo.bff.services.interfaces.IErrorService;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.WebRequest;

import java.util.Map;

@Log4j2
@Service
public class ErrorService implements IErrorService {
    @Override
    public ResponseEntity<ErrorResponse> map(ErrorAttributes errorAttributes, WebRequest webRequest) {
        Map<String, Object> errorDetails = errorAttributes.getErrorAttributes(webRequest, ErrorAttributeOptions.defaults());

        Integer status = Util.tryParse(errorDetails.get("status"));
        Object errorDescription = errorDetails.get("error");
        IMiddlewareError error;

        if (errorDescription == "Forbidden" && (status != null && status == 403))
            error = DefaultMiddlewareError.FORBIDDEN;
        else if (errorDescription == "Bad Request" && (status != null && status == 400))
            error = DefaultMiddlewareError.BAD_REQUEST;
        else {
            log.error(String.format("%s:%s", "ErrorService - No mapped", errorDetails));
            error = DefaultMiddlewareError.INTERNAL_SERVER_ERROR;
        }

        ErrorResponse response = ErrorResponse.instance(error);
        return ResponseEntity.status(error.getHttpCode()).body(response);
    }
}
