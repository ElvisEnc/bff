package bg.com.bo.bff.providers.models.middleware.additional.evaluator;

import bg.com.bo.bff.providers.models.interfaces.middleware.IEvaluatorResponse;
import bg.com.bo.bff.providers.models.interfaces.middleware.IMiddlewareError;
import bg.com.bo.bff.providers.models.interfaces.middleware.IResolverResponse;

public class DefaultResultByMWErrorEvaluator<R> extends AdditionalEvaluator<R> {

    private final IMiddlewareError error;

    private DefaultResultByMWErrorEvaluator(IMiddlewareError error) {
        super();
        this.error = error;
    }

    public static <R> DefaultResultByMWErrorEvaluator<R> instance(IMiddlewareError error) {
        return new DefaultResultByMWErrorEvaluator<>(error);
    }

    @Override
    public IEvaluatorResponse getEvaluator() {
        return (json, mapper) -> {
            IMiddlewareError error = mapper.apply(json);
            return error == this.error;
        };
    }

    @Override
    public IResolverResponse<R> getResolver() {
        return (json, classType, mapper) -> {
            try {
                return classType.getDeclaredConstructor().newInstance();
            } catch (Exception e) {
                throw new UnsupportedOperationException("No existe constructor declarado para dicha clase.");
            }
        };
    }
}
