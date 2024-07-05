package bg.com.bo.bff.providers.models.interfaces.middleware;

import org.springframework.http.HttpStatus;
import org.springframework.util.function.ThrowingFunction;

import java.io.IOException;
import java.util.function.Function;

@FunctionalInterface
public interface IEvaluatorResponse {
    Boolean evaluate(String response, int httpStatus, ThrowingFunction<String, IMiddlewareError> mapper) throws IOException;
}
