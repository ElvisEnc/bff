package bg.com.bo.bff.application.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class GenericException extends RuntimeException {
    private HttpStatus status;
    private String method;
    private String source;

    public GenericException(String description) {
        super(description);
        this.status = HttpStatus.NOT_ACCEPTABLE;
        fillTrace();
    }
    public GenericException(String description, HttpStatus status) {
        super(description);
        this.status = status;
        fillTrace();
    }


    private void fillTrace() {
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        if (stackTrace.length > 3) {
            StackTraceElement caller = stackTrace[3];
            String className = caller.getFileName();
            String methodName = caller.getMethodName()+":"+caller.getLineNumber();
            this.source = className + ":" + methodName;
        }
        ServletRequestAttributes sra = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (sra != null) {
            this.method = sra.getRequest().getRequestURI();
        }
    }
}
