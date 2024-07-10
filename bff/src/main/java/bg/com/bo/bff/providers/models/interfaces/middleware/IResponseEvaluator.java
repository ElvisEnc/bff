package bg.com.bo.bff.providers.models.interfaces.middleware;

import org.springframework.util.function.ThrowingFunction;

import java.io.IOException;

@FunctionalInterface
public interface IResponseEvaluator {
    Boolean evaluate(String response, int httpStatus, ThrowingFunction<String, IMiddlewareError> mapper) throws IOException;
}
