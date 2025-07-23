package bg.com.bo.bff.providers.models.middleware.response.handler.resolver;

import bg.com.bo.bff.providers.models.interfaces.middleware.IMiddlewareError;
import bg.com.bo.bff.providers.models.interfaces.middleware.IResponseResolver;
import org.springframework.util.function.ThrowingFunction;

import java.io.IOException;

public class EmptyResponseResolver<R> implements IResponseResolver<R> {

    private EmptyResponseResolver() {
    }

    public static <R> EmptyResponseResolver<R> instance() {
        return new EmptyResponseResolver<>();
    }

    @Override
    public R resolve(String response, Class<R> classType, ThrowingFunction<String, IMiddlewareError> mapper) throws NoSuchMethodException, IOException {
        try {
            return classType.getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            throw new UnsupportedOperationException("No existe constructor declarado para dicha clase.");
        }
    }
}
