package bg.com.bo.bff.providers.models.middleware.additional.evaluator;

import bg.com.bo.bff.providers.models.interfaces.middleware.IEvaluatorResponse;
import bg.com.bo.bff.providers.models.interfaces.middleware.IResolverResponse;

@lombok.Getter
@lombok.Builder
@lombok.NoArgsConstructor
@lombok.AllArgsConstructor
public class ResponseEvaluator<R> {
    private IEvaluatorResponse evaluator;
    private IResolverResponse<R> resolver;
}
