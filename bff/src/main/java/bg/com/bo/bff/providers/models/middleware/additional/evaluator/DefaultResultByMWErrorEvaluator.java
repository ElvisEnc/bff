package bg.com.bo.bff.providers.models.middleware.additional.evaluator;

import bg.com.bo.bff.providers.models.interfaces.middleware.IEvaluatorResponse;
import bg.com.bo.bff.providers.models.interfaces.middleware.IMiddlewareError;
import bg.com.bo.bff.providers.models.interfaces.middleware.IResolverResponse;

/**
 * This class handles cases where the MW response is a specific error and returns an uninitialized object of the specified class.
 */
public class DefaultResultByMWErrorEvaluator<R> extends ResponseEvaluator<R> {

    private final IMiddlewareError error;
    private final DefaultResponseEvaluator<R> defaultResponseEvaluator;
    private boolean selfValidationPassed = false;

    private DefaultResultByMWErrorEvaluator(IMiddlewareError error) {
        super();
        this.error = error;
        this.defaultResponseEvaluator = DefaultResponseEvaluator.instance();
    }

    public static <R> DefaultResultByMWErrorEvaluator<R> instance(IMiddlewareError error) {
        return new DefaultResultByMWErrorEvaluator<>(error);
    }

    /**
     * Evaluates the DefaultResponseEvaluator behaviour first and if it's not, then evaluates if the response is a specific MW error.
     *
     * @return the result of the evaluation.
     */
    @Override
    public IEvaluatorResponse getEvaluator() {
        return (json, httpStatus, mapper) -> {
            if (defaultResponseEvaluator.getEvaluator().evaluate(json, httpStatus, mapper))
                return true;
            else {
                IMiddlewareError error = mapper.apply(json);
                boolean result = error == this.error;
                if (result)
                    selfValidationPassed = true;
                return result;
            }
        };
    }

    /**
     * Return the DefaultResponseResolver behaviour but if it's not then return a empty object of classType declared.
     *
     * @return result object.
     */
    @Override
    public IResolverResponse<R> getResolver() {
        return (json, classType, mapper) -> {
            if (!selfValidationPassed)
                return defaultResponseEvaluator.getResolver().resolve(json, classType, mapper);
            else {
                try {
                    return classType.getDeclaredConstructor().newInstance();
                } catch (Exception e) {
                    throw new UnsupportedOperationException("No existe constructor declarado para dicha clase.");
                }
            }
        };
    }
}
