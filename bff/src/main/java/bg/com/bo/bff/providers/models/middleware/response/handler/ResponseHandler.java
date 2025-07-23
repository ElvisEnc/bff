package bg.com.bo.bff.providers.models.middleware.response.handler;

import bg.com.bo.bff.providers.models.interfaces.middleware.IResponseEvaluator;
import bg.com.bo.bff.providers.models.interfaces.middleware.IResponseResolver;

@lombok.Getter
@lombok.Builder
@lombok.NoArgsConstructor
@lombok.AllArgsConstructor
public class ResponseHandler<R> {
    protected IResponseEvaluator evaluator;
    protected IResponseResolver<R> resolver;
}
