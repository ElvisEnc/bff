package bg.com.bo.bff.application.exceptions;

import bg.com.bo.bff.commons.converters.IErrorResponse;
import bg.com.bo.bff.providers.models.enums.middleware.response.IErrorControllerResponse;
import bg.com.bo.bff.providers.models.interfaces.middleware.IMiddlewareError;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Getter
@NoArgsConstructor
public class HandledException extends RuntimeException {
    private String message;
    private HttpStatus status;
    private String code;
    private String method;
    private String source;
    private String title;

    public HandledException(IErrorControllerResponse controllerResponse) {
        super(controllerResponse.getDescription());
        this.message = controllerResponse.getDescription();
        this.status = controllerResponse.getHttpCode();
        this.code = controllerResponse.getCode();
        this.title = controllerResponse.getTitle();
        fillTrace();
    }

    public HandledException(IMiddlewareError controllerResponse) {
        super(controllerResponse.getMessage());
        this.message = controllerResponse.getMessage();
        this.status = controllerResponse.getHttpCode();
        this.code = controllerResponse.getCode();
        this.title = controllerResponse.getTitle();
        fillTrace();
    }

    public HandledException(IErrorControllerResponse controllerResponse, Exception e) {
        super(e);
        this.message = controllerResponse.getDescription();
        this.status = controllerResponse.getHttpCode();
        this.code = controllerResponse.getCode();
        this.title = controllerResponse.getTitle();
    }

    public HandledException(IErrorResponse response) {
        super(response.getMessage());
        this.message = response.getMessage();
        this.status = response.getHttpCode();
        this.code = response.getCode();
        this.title = response.getTitle();
        fillTrace();
    }

    public HandledException(IErrorResponse response, Exception e) {
        super(e);
        this.message = response.getMessage();
        this.status = response.getHttpCode();
        this.code = response.getCode();
        this.title = response.getTitle();
    }

    private void fillTrace() {
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        if (stackTrace.length > 3) {
            StackTraceElement caller = stackTrace[3];
            String className = caller.getFileName();
            String methodName = caller.getMethodName() + ":" + caller.getLineNumber();
            this.source = className + ":" + methodName;
        }
        ServletRequestAttributes sra = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (sra != null) {
            this.method = sra.getRequest().getRequestURI();
        }
    }
}
