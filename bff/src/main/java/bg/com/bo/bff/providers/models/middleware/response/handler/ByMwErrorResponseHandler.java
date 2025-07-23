package bg.com.bo.bff.providers.models.middleware.response.handler;

import bg.com.bo.bff.providers.models.interfaces.middleware.IResponseEvaluator;
import bg.com.bo.bff.providers.models.interfaces.middleware.IMiddlewareError;
import bg.com.bo.bff.providers.models.interfaces.middleware.IResponseResolver;
import bg.com.bo.bff.providers.models.middleware.response.handler.evaluator.ByMwErrorResponseEvaluator;
import bg.com.bo.bff.providers.models.middleware.response.handler.evaluator.DefaultResponseEvaluator;
import bg.com.bo.bff.providers.models.middleware.response.handler.resolver.DefaultResponseResolver;
import bg.com.bo.bff.providers.models.middleware.response.handler.resolver.EmptyResponseResolver;

import java.util.List;

public class ByMwErrorResponseHandler<R> extends ResponseHandler<R> {
    private final DefaultResponseEvaluator defaultResponseEvaluator;
    private final ByMwErrorResponseEvaluator byMwErrorEvaluator;
    private final IResponseResolver<R> onSuccessResponseResolver;
    private final IResponseResolver<R> onErrorResponseResolver;
    protected boolean byDefaultResponse = false;

    private ByMwErrorResponseHandler(
            List<IMiddlewareError> errors,
            IResponseResolver<R> onSuccessResponseResolver,
            IResponseResolver<R> onErrorResponseResolver) {
        super();
        this.defaultResponseEvaluator = DefaultResponseEvaluator.instance();
        this.byMwErrorEvaluator = ByMwErrorResponseEvaluator.instance(errors);
        this.onSuccessResponseResolver = onSuccessResponseResolver;
        this.onErrorResponseResolver = onErrorResponseResolver;
    }

    public static <R> ByMwErrorResponseHandler<R> instance(IMiddlewareError error) {
        List<IMiddlewareError> errors = List.of(error);
        return new ByMwErrorResponseHandler<>(errors,
                DefaultResponseResolver.instance(),
                EmptyResponseResolver.instance());
    }

    public static <R> ByMwErrorResponseHandler<R> instance(IMiddlewareError error,
                                                           IResponseResolver<R> onSuccessResponseResolver,
                                                           IResponseResolver<R> onErrorResponseResolver) {
        List<IMiddlewareError> errors = List.of(error);
        return new ByMwErrorResponseHandler<>(errors, onSuccessResponseResolver, onErrorResponseResolver);
    }

    public static <R> ByMwErrorResponseHandler<R> instance(IMiddlewareError error,
                                                           IResponseResolver<R> onErrorResponseResolver) {
        List<IMiddlewareError> errors = List.of(error);
        return new ByMwErrorResponseHandler<>(errors, DefaultResponseResolver.instance(), onErrorResponseResolver);
    }

    public static <R> ByMwErrorResponseHandler<R> instance(List<IMiddlewareError> errors,
                                                           IResponseResolver<R> onErrorResponseResolver) {
        return new ByMwErrorResponseHandler<>(errors, DefaultResponseResolver.instance(), onErrorResponseResolver);
    }

    public static <R> ByMwErrorResponseHandler<R> instance(List<IMiddlewareError> errors,
                                                           IResponseResolver<R> onSuccessResponseResolver,
                                                           IResponseResolver<R> onErrorResponseResolver) {
        return new ByMwErrorResponseHandler<>(errors, onSuccessResponseResolver, onErrorResponseResolver);
    }

    @Override
    public IResponseEvaluator getEvaluator() {
        return (json, httpStatus, mapper) -> {
            if (defaultResponseEvaluator.evaluate(json, httpStatus, mapper)) {
                byDefaultResponse = true;
                return true;
            }

            return byMwErrorEvaluator.evaluate(json, httpStatus, mapper);
        };
    }

    @Override
    public IResponseResolver<R> getResolver() {
        return (json, classType, mapper) -> {
            if (byDefaultResponse)
                return onSuccessResponseResolver.resolve(json, classType, mapper);
            else
                return onErrorResponseResolver.resolve(json, classType, mapper);
        };
    }
}
