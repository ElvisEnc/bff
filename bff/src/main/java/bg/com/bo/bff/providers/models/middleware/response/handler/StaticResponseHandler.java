package bg.com.bo.bff.providers.models.middleware.response.handler;

import bg.com.bo.bff.providers.models.middleware.response.handler.evaluator.DefaultResponseEvaluator;
import bg.com.bo.bff.providers.models.middleware.response.handler.resolver.StaticResponseResolver;

/**
 * This class handles cases where the MW response is a valid response and returns a mapped object.
 */
public class StaticResponseHandler<R> extends ResponseHandler<R> {
    private StaticResponseHandler(R response) {
        super();
        this.evaluator = DefaultResponseEvaluator.instance();
        this.resolver = StaticResponseResolver.instance(response);
    }

    public static <R> StaticResponseHandler<R> instance(R response) {
        return new StaticResponseHandler<>(response);
    }
}
