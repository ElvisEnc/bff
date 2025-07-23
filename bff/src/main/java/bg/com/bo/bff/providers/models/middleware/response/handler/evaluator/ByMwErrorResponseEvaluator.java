package bg.com.bo.bff.providers.models.middleware.response.handler.evaluator;

import bg.com.bo.bff.providers.models.interfaces.middleware.IResponseEvaluator;
import bg.com.bo.bff.providers.models.interfaces.middleware.IMiddlewareError;
import org.springframework.util.function.ThrowingFunction;

import java.io.IOException;
import java.util.List;

public class ByMwErrorResponseEvaluator implements IResponseEvaluator {
    private final List<IMiddlewareError> errors;

    private ByMwErrorResponseEvaluator(List<IMiddlewareError> errors) {
        this.errors = errors;
    }

    public static ByMwErrorResponseEvaluator instance(List<IMiddlewareError> errors) {
        return new ByMwErrorResponseEvaluator(errors);
    }

    @Override
    public Boolean evaluate(String response, int httpStatus, ThrowingFunction<String, IMiddlewareError> mapper) throws IOException {
        try {
            IMiddlewareError error = mapper.apply(response);
            return this.errors.contains(error);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
