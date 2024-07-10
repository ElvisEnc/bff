package bg.com.bo.bff.providers.models.middleware.response.handler;

import bg.com.bo.bff.providers.models.middleware.response.handler.evaluator.DefaultResponseEvaluator;
import bg.com.bo.bff.providers.models.middleware.response.handler.resolver.DefaultResponseResolver;

/**
 * This class handles cases where the MW response is a valid response and returns a mapped object.
 */
public class DefaultResponseHandler<R> extends ResponseHandler<R> {

    protected DefaultResponseHandler() {
        super();
        this.evaluator = DefaultResponseEvaluator.instance();
        this.resolver = DefaultResponseResolver.instance();
    }

    public static <R> DefaultResponseHandler<R> instance() {
        return new DefaultResponseHandler<>();
    }
}
