package bg.com.bo.bff.providers.implementations;

import bg.com.bo.bff.application.exceptions.GenericException;
import bg.com.bo.bff.commons.enums.AppError;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.io.IOException;

import bg.com.bo.bff.application.config.MiddlewareConfig;
import bg.com.bo.bff.models.ClientToken;
import bg.com.bo.bff.commons.interfaces.IHttpClientFactory;
import bg.com.bo.bff.commons.utils.Util;
import bg.com.bo.bff.providers.interfaces.ITokenMiddlewareProvider;

@Service
public class TokenMiddlewareProvider implements ITokenMiddlewareProvider {

    private final MiddlewareConfig middlewareConfig;
    private static final Logger LOGGER = LogManager.getLogger(TokenMiddlewareProvider.class.getName());

    private IHttpClientFactory httpClientFactory;


    public TokenMiddlewareProvider(IHttpClientFactory httpClientFactory, MiddlewareConfig middlewareConfig) {
        this.httpClientFactory = httpClientFactory;
        this.middlewareConfig = middlewareConfig;
    }


    @Override
    public ClientToken generateAccountAccessToken(String project, String clientSecret, String headerKeyToken) throws IOException {
        try (CloseableHttpClient httpClient = httpClientFactory.create()) {
            String pathPostToken = middlewareConfig.getUrlBase() + project + middlewareConfig.getTokenPath();
            HttpPost postGenerateAccessToken = new HttpPost(pathPostToken);
            postGenerateAccessToken.setHeader(headerKeyToken, clientSecret);

            try (CloseableHttpResponse httpResponse = httpClient.execute(postGenerateAccessToken)) {
                int statusCode = httpResponse.getStatusLine().getStatusCode();
                String responseToken = EntityUtils.toString(httpResponse.getEntity());
                if (statusCode == HttpStatus.SC_OK)
                    return Util.stringToObject(responseToken, ClientToken.class);
                else {
                    AppError error = Util.mapProviderError(responseToken);
                    LOGGER.error(responseToken);
                    throw new GenericException(error.getMessage(), error.getHttpCode(), error.getCode());
                }
            }
        } catch (GenericException ex) {
            LOGGER.error(ex);
            throw ex;
        } catch (Exception e) {
            LOGGER.error(e);
            throw new GenericException("Hubo un error no controlado al crear el clienteToken");
        }
    }
}
