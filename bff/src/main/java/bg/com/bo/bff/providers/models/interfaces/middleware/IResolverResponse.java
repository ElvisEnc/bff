package bg.com.bo.bff.providers.models.interfaces.middleware;

import org.springframework.util.function.ThrowingFunction;

import java.io.IOException;

@FunctionalInterface
public interface IResolverResponse<R> {
    R resolve(String response, Class<R> classType, ThrowingFunction<String, IMiddlewareError> mapper) throws NoSuchMethodException, IOException;
}
