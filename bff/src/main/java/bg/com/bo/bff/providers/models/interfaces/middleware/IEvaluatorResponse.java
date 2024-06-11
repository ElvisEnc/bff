package bg.com.bo.bff.providers.models.interfaces.middleware;

import org.springframework.util.function.ThrowingFunction;

import java.io.IOException;
import java.util.function.Function;

@FunctionalInterface
public interface IEvaluatorResponse {
    Boolean evaluate(String response, ThrowingFunction<String, IMiddlewareError> mapper) throws IOException;
}
