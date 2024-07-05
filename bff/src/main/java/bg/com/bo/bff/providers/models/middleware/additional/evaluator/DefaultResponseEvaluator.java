package bg.com.bo.bff.providers.models.middleware.additional.evaluator;

import bg.com.bo.bff.commons.utils.Util;
import bg.com.bo.bff.providers.models.interfaces.middleware.IEvaluatorResponse;
import bg.com.bo.bff.providers.models.interfaces.middleware.IResolverResponse;
import org.apache.http.HttpStatus;

/**
 * This class handles cases where the MW response is a valid response and returns a mapped object.
 */
public class DefaultResponseEvaluator<R> extends ResponseEvaluator<R> {

    private DefaultResponseEvaluator() {
        super();
    }

    public static <R> DefaultResponseEvaluator<R> instance() {
        return new DefaultResponseEvaluator<>();
    }

    /**
     * Evaluates if the response it's valid response (200 response code group).
     * @return the evaluation of the result.
     */
    @Override
    public IEvaluatorResponse getEvaluator() {
        return (json, statusCode, mapper) -> statusCode == HttpStatus.SC_OK || statusCode == HttpStatus.SC_CREATED || statusCode == HttpStatus.SC_NO_CONTENT;
    }

    /**
     * Returns a mapped object of the specified class based on the response obtained from the MW.
     * @return the mapped object.
     */
    @Override
    public IResolverResponse<R> getResolver() {
        return (json, classType, mapper) -> Util.stringToObject(json, classType);
    }
}
