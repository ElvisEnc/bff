package bg.com.bo.bff.providers.models.middleware;

import bg.com.bo.bff.application.config.MiddlewareConfig;
import bg.com.bo.bff.application.exceptions.GenericException;
import bg.com.bo.bff.commons.HttpDeleteWithBody;
import bg.com.bo.bff.commons.enums.ProjectNameMW;
import bg.com.bo.bff.commons.utils.Util;
import bg.com.bo.bff.models.ClientToken;
import bg.com.bo.bff.commons.interfaces.IHttpClientFactory;
import bg.com.bo.bff.providers.dtos.response.generic.ApiErrorResponse;
import bg.com.bo.bff.providers.dtos.response.generic.ErrorDetailResponse;
import bg.com.bo.bff.providers.interfaces.ITokenMiddlewareProvider;
import bg.com.bo.bff.providers.models.interfaces.middleware.IMiddlewareError;
import bg.com.bo.bff.providers.models.middleware.additional.evaluator.ResponseEvaluator;
import bg.com.bo.bff.providers.models.middleware.additional.evaluator.DefaultResponseEvaluator;
import org.apache.http.Header;
import org.apache.http.client.methods.*;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.List;

/**
 * Base class for middleware services.
 */
public abstract class MiddlewareProvider<T extends IMiddlewareError> {

    protected ITokenMiddlewareProvider tokenMiddlewareProvider;
    protected final MiddlewareConfig middlewareConfig;
    protected static final Logger LOGGER = LogManager.getLogger(MiddlewareProvider.class.getName());
    protected final IHttpClientFactory httpClientFactory;

    private final Class<T> appErrorValue;
    private final ProjectNameMW project;
    private final String clientSecret;

    public MiddlewareProvider(ProjectNameMW project, Class<T> appErrorValue, ITokenMiddlewareProvider tokenMiddlewareProvider, MiddlewareConfig middlewareConfig, IHttpClientFactory httpClientFactory, String clientSecret) {
        this.project = project;
        this.appErrorValue = appErrorValue;
        this.tokenMiddlewareProvider = tokenMiddlewareProvider;
        this.middlewareConfig = middlewareConfig;
        this.httpClientFactory = httpClientFactory;
        this.clientSecret = clientSecret;
    }

    /**
     * Execute a HttpGet using HttpClientFactory and a token given by TokenMiddlewareProvider.
     * In case of a response other than 200, it throws a GenericException mapped by the declared IMiddlewareError class or consequently by the DefaultMiddlewareError.
     *
     * @param url       url of resource.
     * @param headers   list of headers for request.
     * @param classType type of response class.
     * @return an object of given params type.
     */
    protected <E> E get(String url, Header[] headers, Class<E> classType) throws IOException {
        return get(url, headers, classType, DefaultResponseEvaluator.instance());
    }

    /**
     * Execute a HttpGet using HttpClientFactory and a token given by TokenMiddlewareProvider.
     * In case of a response other than 200, it evaluates the response using the provided AdditionalEvaluator.
     * If the evaluation is successful, it resolves the response using the resolver from the AdditionalEvaluator.
     * Otherwise, it throws a GenericException mapped by the declared IMiddlewareError class or consequently by the DefaultMiddlewareError.
     *
     * @param url               url of the resource.
     * @param headers           list of headers for the request.
     * @param classType         type of the response class.
     * @param responseEvaluator evaluator and resolver for handling specific response scenarios.
     * @return an object of the given class type.
     */
    protected <E> E get(String url, Header[] headers, Class<E> classType, ResponseEvaluator<E> responseEvaluator) throws IOException {
        return executeRequest(new HttpGet(url), headers, null, classType, responseEvaluator);
    }

    /**
     * Execute a HttpPost using HttpClientFactory and a token given by TokenMiddlewareProvider.
     * In case of a response other than 200, it throws a GenericException mapped by the declared IMiddlewareError class or consequently by the DefaultMiddlewareError.
     *
     * @param url         url of resource.
     * @param headers     list of headers for request.
     * @param requestBody body of the request.
     * @param classType   type of response class.
     * @return an object of the given class type.
     */
    protected <E, R> E post(String url, Header[] headers, R requestBody, Class<E> classType) throws IOException {
        return post(url, headers, requestBody, classType, DefaultResponseEvaluator.instance());
    }

    /**
     * Execute a HttpPost using HttpClientFactory and a token given by TokenMiddlewareProvider.
     * In case of a response other than 200, it throws a GenericException mapped by the declared IMiddlewareError class or consequently by the DefaultMiddlewareError.
     *
     * @param url               url of resource.
     * @param headers           list of headers for request.
     * @param requestBody       body of the request.
     * @param classType         type of response class.
     * @param responseEvaluator evaluator and resolver for handling specific response scenarios.
     * @return an object of the given class type.
     */
    protected <E, R> E post(String url, Header[] headers, R requestBody, Class<E> classType, ResponseEvaluator<E> responseEvaluator) throws IOException {
        return executeRequest(new HttpPost(url), headers, requestBody, classType, responseEvaluator);
    }

    /**
     * Execute a HttpPatch using HttpClientFactory and a token given by TokenMiddlewareProvider.
     * In case of a response other than 200, it throws a GenericException mapped by the declared IMiddlewareError class or consequently by the DefaultMiddlewareError.
     *
     * @param url         url of resource.
     * @param headers     list of headers for request.
     * @param requestBody body of the request.
     * @param classType   type of response class.
     * @return an object of the given class type.
     */
    protected <E, R> E patch(String url, Header[] headers, R requestBody, Class<E> classType) throws IOException {
        return patch(url, headers, requestBody, classType, DefaultResponseEvaluator.instance());
    }

    /**
     * Execute a HttpPatch using HttpClientFactory and a token given by TokenMiddlewareProvider.
     * In case of a response other than 200, it throws a GenericException mapped by the declared IMiddlewareError class or consequently by the DefaultMiddlewareError.
     *
     * @param url               url of resource.
     * @param headers           list of headers for request.
     * @param requestBody       body of the request.
     * @param classType         type of response class.
     * @param responseEvaluator evaluator and resolver for handling specific response scenarios.
     * @return an object of the given class type.
     */
    protected <E, R> E patch(String url, Header[] headers, R requestBody, Class<E> classType, ResponseEvaluator<E> responseEvaluator) throws IOException {
        return executeRequest(new HttpPatch(url), headers, requestBody, classType, responseEvaluator);
    }

    /**
     * Execute a HttpDeleteWithBody using HttpClientFactory and a token given by TokenMiddlewareProvider.
     * In case of a response other than 200, it throws a GenericException mapped by the declared IMiddlewareError class or consequently by the DefaultMiddlewareError.
     *
     * @param url         url of resource.
     * @param headers     list of headers for request.
     * @param requestBody body of the request.
     * @param classType   type of response class.
     * @return an object of given params type.
     */
    protected <E, R> E deleteWithBody(String url, Header[] headers, R requestBody, Class<E> classType) throws IOException {
        return deleteWithBody(url, headers, requestBody, classType, DefaultResponseEvaluator.instance());
    }

    /**
     * Execute a HttpDeleteWithBody using HttpClientFactory and a token given by TokenMiddlewareProvider.
     * In case of a response other than 200, it throws a GenericException mapped by the declared IMiddlewareError class or consequently by the DefaultMiddlewareError.
     *
     * @param url               url of resource.
     * @param headers           list of headers for request.
     * @param requestBody       body of the request.
     * @param classType         type of response class.
     * @param responseEvaluator evaluator and resolver for handling specific response scenarios.
     * @return an object of the given class type.
     */
    protected <E, R> E deleteWithBody(String url, Header[] headers, R requestBody, Class<E> classType, ResponseEvaluator<E> responseEvaluator) throws IOException {
        return executeRequest(new HttpDeleteWithBody(url), headers, requestBody, classType, responseEvaluator);
    }

    /**
     * Execute a HttpPut using HttpClientFactory and a token given by TokenMiddlewareProvider.
     * In case of a response other than 200, it throws a GenericException mapped by the declared IMiddlewareError class or consequently by the DefaultMiddlewareError.
     *
     * @param url         url of resource.
     * @param headers     list of headers for request.
     * @param requestBody body of the request.
     * @param classType   type of response class.
     * @return an object of the given class type.
     */
    protected <E, R> E put(String url, Header[] headers, R requestBody, Class<E> classType) throws IOException {
        return put(url, headers, requestBody, classType, DefaultResponseEvaluator.instance());
    }

    /**
     * Execute a HttpPut using HttpClientFactory and a token given by TokenMiddlewareProvider.
     * In case of a response other than 200, it throws a GenericException mapped by the declared IMiddlewareError class or consequently by the DefaultMiddlewareError.
     *
     * @param url               url of resource.
     * @param headers           list of headers for request.
     * @param requestBody       body of the request.
     * @param classType         type of response class.
     * @param responseEvaluator evaluator and resolver for handling specific response scenarios.
     * @return an object of the given class type.
     */
    protected <E, R> E put(String url, Header[] headers, R requestBody, Class<E> classType, ResponseEvaluator<E> responseEvaluator) throws IOException {
        return executeRequest(new HttpPut(url), headers, requestBody, classType, responseEvaluator);
    }

    private <E, R> E executeRequest(HttpUriRequest request, Header[] headers, R requestBody, Class<E> classType, ResponseEvaluator<E> responseEvaluator) throws IOException {
        ClientToken clientToken = tokenMiddlewareProvider.generateAccountAccessToken(project.getName(), clientSecret, project.getHeaderKey());
        try (CloseableHttpClient httpClient = httpClientFactory.create()) {
            if (headers != null && headers.length > 0) {
                request.setHeaders(headers);
            }
            request.setHeader(HeadersMW.AUT.getName(), "Bearer " + clientToken.getAccessToken());

            if (request instanceof HttpEntityEnclosingRequestBase httpEntityEnclosingRequestBase) {
                StringEntity entity = new StringEntity(Util.objectToString(requestBody));
                httpEntityEnclosingRequestBase.setEntity(entity);
                request.setHeader(HeadersMW.CONTENT_TYPE.getName(), HeadersMW.APP_JSON.getName());
            }

            try (CloseableHttpResponse httpResponse = httpClient.execute(request)) {
                int statusCode = httpResponse.getStatusLine().getStatusCode();
                String jsonResponse = EntityUtils.toString(httpResponse.getEntity());

                if (responseEvaluator != null && responseEvaluator.getEvaluator().evaluate(jsonResponse, statusCode, this::mapProviderIError))
                    return responseEvaluator.getResolver().resolve(jsonResponse, classType, this::mapProviderIError);

                IMiddlewareError error = this.mapProviderIError(jsonResponse);
                if (error.equals(DefaultMiddlewareError.DEFAULT))
                    LOGGER.error(String.format("Not Mapped Error:%s", jsonResponse));

                throw new GenericException(error.getMessage(), error.getHttpCode(), error.getCode());
            }
        } catch (GenericException ex) {
            throw ex;
        } catch (Exception e) {
            LOGGER.error(e);
            throw new GenericException(DefaultMiddlewareError.DEFAULT.getMessage(), DefaultMiddlewareError.DEFAULT.getHttpCode(), DefaultMiddlewareError.DEFAULT.getCode());
        }
    }

    /**
     * Maps the response in json format on error to an IMiddlewareError instance.
     *
     * @param jsonResponse response in json format.
     * @return a mapped IMiddlewareError instance.
     */
    protected IMiddlewareError mapProviderIError(String jsonResponse) throws IOException {
        ApiErrorResponse response = Util.stringToObject(jsonResponse, ApiErrorResponse.class);
        List<ErrorDetailResponse> listError = response.getErrorDetailResponse();
        ErrorDetailResponse errorDetail = listError.get(0);
        String providerErrorCode = errorDetail.getCode();
        return this.findByCode(providerErrorCode);
    }

    protected CloseableHttpClient createHttpClient() {
        return httpClientFactory.create();
    }

    /**
     * Find IMiddlewareError instance in declared IMiddlewareError class or consequently by the DefaultMiddlewareError.
     *
     * @param code code of error.
     * @return IMiddlewareError instance founded.
     */
    private IMiddlewareError findByCode(String code) {
        for (IMiddlewareError constant : appErrorValue.getEnumConstants())
            if (constant.getCodeMiddleware() != null && constant.getCodeMiddleware().equals(code))
                return constant;
        for (IMiddlewareError constant : DefaultMiddlewareError.values())
            if (constant.getCodeMiddleware() != null && constant.getCodeMiddleware().equals(code))
                return constant;

        return DefaultMiddlewareError.DEFAULT;
    }
}
