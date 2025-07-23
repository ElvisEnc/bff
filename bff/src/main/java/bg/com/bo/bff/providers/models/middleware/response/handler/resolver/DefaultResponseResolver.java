package bg.com.bo.bff.providers.models.middleware.response.handler.resolver;

import bg.com.bo.bff.commons.utils.Util;
import bg.com.bo.bff.providers.models.interfaces.middleware.IMiddlewareError;
import bg.com.bo.bff.providers.models.interfaces.middleware.IResponseResolver;
import org.springframework.util.function.ThrowingFunction;

import java.io.IOException;

public class DefaultResponseResolver<R> implements IResponseResolver<R> {
    private DefaultResponseResolver() {
    }

    public static <R> DefaultResponseResolver<R> instance() {
        return new DefaultResponseResolver<>();
    }

    @Override
    public R resolve(String response, Class<R> classType, ThrowingFunction<String, IMiddlewareError> mapper) throws NoSuchMethodException, IOException {
        return Util.stringToObject(response, classType);
    }
}
