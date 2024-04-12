package bg.com.bo.bff.application.exceptions;

import bg.com.bo.bff.commons.converters.IErrorResponse;
import bg.com.bo.bff.commons.enums.response.IErrorControllerResponse;
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

    public HandledException(IErrorControllerResponse controllerResponse) {
        super(controllerResponse.getDescription());
        this.message = controllerResponse.getDescription();
        this.status = controllerResponse.getHttpCode();
        this.code = controllerResponse.getCode();
        fillTrace();
    }
    public HandledException(IErrorControllerResponse controllerResponse, Exception e) {
        super(e);
        this.message = controllerResponse.getDescription();
        this.status = controllerResponse.getHttpCode();
        this.code = controllerResponse.getCode();
    }

    public HandledException(IErrorResponse response) {
        super(response.getMessage());
        this.message = response.getMessage();
        this.status = response.getHttpCode();
        this.code = response.getCode();
        fillTrace();
    }

    public HandledException(IErrorResponse response, Exception e) {
        super(e);
        this.message = response.getMessage();
        this.status = response.getHttpCode();
        this.code = response.getCode();
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
