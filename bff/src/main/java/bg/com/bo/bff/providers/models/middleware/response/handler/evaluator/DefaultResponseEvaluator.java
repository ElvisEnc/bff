package bg.com.bo.bff.providers.models.middleware.response.handler.evaluator;

import bg.com.bo.bff.providers.models.interfaces.middleware.IResponseEvaluator;
import bg.com.bo.bff.providers.models.interfaces.middleware.IMiddlewareError;
import org.apache.http.HttpStatus;
import org.springframework.util.function.ThrowingFunction;

public class DefaultResponseEvaluator implements IResponseEvaluator {

    public static DefaultResponseEvaluator instance() {
        return new DefaultResponseEvaluator();
    }

    @Override
    public Boolean evaluate(String response, int httpStatus, ThrowingFunction<String, IMiddlewareError> mapper) {
        return httpStatus == HttpStatus.SC_OK || httpStatus == HttpStatus.SC_CREATED || httpStatus == HttpStatus.SC_NO_CONTENT;
    }
}
