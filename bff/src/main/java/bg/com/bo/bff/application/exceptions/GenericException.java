package bg.com.bo.bff.application.exceptions;

import bg.com.bo.bff.commons.enums.config.provider.AppError;
import bg.com.bo.bff.providers.models.interfaces.middleware.IMiddlewareError;
import org.springframework.http.HttpStatus;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import lombok.Getter;

@Getter
public class GenericException extends RuntimeException {
    private final HttpStatus status;
    private String method;
    private String source;
    private final String code;
    private String title = "";
    private int categoryId = 0;

    public GenericException() {
        super(AppError.DEFAULT.getMessage());
        this.status = HttpStatus.INTERNAL_SERVER_ERROR;
        this.code = AppError.DEFAULT.getCode();
        fillTrace();
    }

    public GenericException(String description) {
        super(description);
        this.status = HttpStatus.NOT_ACCEPTABLE;
        this.code = AppError.DEFAULT.getCode();
        fillTrace();
    }

    public GenericException(String description, String title) {
        super(description);
        this.status = HttpStatus.NOT_ACCEPTABLE;
        this.code = AppError.DEFAULT.getCode();
        this.title = title;
        fillTrace();
    }

    public GenericException(String description, HttpStatus status) {
        super(description);
        this.status = status;
        this.code = AppError.DEFAULT.getCode();
        fillTrace();
    }

    public GenericException(String description, HttpStatus status, String code) {
        super(description);
        this.status = status;
        this.code = code;
        fillTrace();
    }

    public GenericException(String description, HttpStatus status, String code, String title, int categoryId) {
        super(description);
        this.status = status;
        this.code = code;
        this.title = title;
        this.categoryId = categoryId;
        fillTrace();
    }

    public GenericException(IMiddlewareError error) {
        super(error.getMessage());
        this.status = error.getHttpCode();
        this.code = error.getCode();
        this.title = error.getTitle();
        this.categoryId = error.getCategoryId();
        fillTrace();
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
