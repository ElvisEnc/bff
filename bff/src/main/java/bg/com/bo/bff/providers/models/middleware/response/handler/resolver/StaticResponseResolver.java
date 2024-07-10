package bg.com.bo.bff.providers.models.middleware.response.handler.resolver;

import bg.com.bo.bff.providers.models.interfaces.middleware.IMiddlewareError;
import bg.com.bo.bff.providers.models.interfaces.middleware.IResponseResolver;
import org.springframework.util.function.ThrowingFunction;

import java.io.IOException;

public class StaticResponseResolver<R> implements IResponseResolver<R> {

    private final R result;

    private StaticResponseResolver(R result) {
        this.result = result;
    }

    public static <R> StaticResponseResolver<R> instance(R result) {
        return new StaticResponseResolver<>(result);
    }

    @Override
    public R resolve(String response, Class<R> classType, ThrowingFunction<String, IMiddlewareError> mapper) throws NoSuchMethodException, IOException {
        return result;
    }
}
