package bg.com.bo.bff.providers.models.middleware;

import bg.com.bo.bff.application.config.MiddlewareConfig;
import bg.com.bo.bff.application.exceptions.GenericException;
import bg.com.bo.bff.commons.enums.ProjectNameMW;
import bg.com.bo.bff.commons.utils.Util;
import bg.com.bo.bff.models.ClientToken;
import bg.com.bo.bff.models.interfaces.IHttpClientFactory;
import bg.com.bo.bff.providers.dtos.responses.ApiErrorResponse;
import bg.com.bo.bff.providers.dtos.responses.ErrorDetailResponse;
import bg.com.bo.bff.providers.interfaces.ITokenMiddlewareProvider;
import bg.com.bo.bff.providers.models.interfaces.middleware.IMiddlewareError;
import org.apache.http.Header;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
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

    public MiddlewareProvider(ProjectNameMW project, Class<T> appErrorValue, ITokenMiddlewareProvider tokenMiddlewareProvider, MiddlewareConfig middlewareConfig, IHttpClientFactory httpClientFactory) {
        this.project = project;
        this.appErrorValue = appErrorValue;
        this.tokenMiddlewareProvider = tokenMiddlewareProvider;
        this.middlewareConfig = middlewareConfig;
        this.httpClientFactory = httpClientFactory;
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
        ClientToken clientToken = tokenMiddlewareProvider.generateAccountAccessToken(project.getName(), middlewareConfig.getDpfManager(), ProjectNameMW.DPF_MANAGER.getHeaderKey());
        try (CloseableHttpClient httpClient = createHttpClient()) {
            HttpGet request = new HttpGet(url);

            if (headers != null && headers.length > 0)
                request.setHeaders(headers);
            request.setHeader(HeadersMW.AUT.getName(), "Bearer " + clientToken.getAccessToken());

            try (CloseableHttpResponse httpResponse = httpClient.execute(request)) {
                int statusCode = httpResponse.getStatusLine().getStatusCode();
                String jsonResponse = EntityUtils.toString(httpResponse.getEntity());
                if (statusCode == HttpStatus.SC_OK)
                    return Util.stringToObject(jsonResponse, classType);

                LOGGER.error(jsonResponse);
                IMiddlewareError error = this.mapProviderIError(jsonResponse);
                throw new GenericException(error.getMessage(), error.getHttpCode(), error.getCode());
            }
        } catch (GenericException ex) {
            LOGGER.error(ex);
            throw ex;
        } catch (Exception e) {
            LOGGER.error(e);
            throw new GenericException(DefaultMiddlewareError.DEFAULT.getMessage(), DefaultMiddlewareError.DEFAULT.getHttpCode(), DefaultMiddlewareError.DEFAULT.getCode());
        }
    }

    /**
     * Maps the response in json format on error to an IMiddlewareError instance.
     * @param jsonResponse response in json format.
     * @return a mapped IMiddlewareError instance.
     */
    private IMiddlewareError mapProviderIError(String jsonResponse) throws IOException {
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
     * @param code code of error.
     * @return IMiddlewareError instance founded.
     */
    private IMiddlewareError findByCode(String code) {
        for (IMiddlewareError constant : appErrorValue.getEnumConstants())
            if (constant.getCodeMiddleware().equals(code))
                return constant;

        for (IMiddlewareError constant : DefaultMiddlewareError.values())
            if (constant.getCodeMiddleware().equals(code))
                return constant;

        return DefaultMiddlewareError.DEFAULT;
    }
}
