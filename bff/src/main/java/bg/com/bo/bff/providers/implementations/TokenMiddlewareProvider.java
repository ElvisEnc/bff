package bg.com.bo.bff.providers.implementations;

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
import bg.com.bo.bff.application.exceptions.RequestException;
import bg.com.bo.bff.models.interfaces.IHttpClientFactory;
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
        boolean propagateException = false;

        try (CloseableHttpClient httpClient = httpClientFactory.create()) {
            String pathPostToken = middlewareConfig.getUrlBase() + project + middlewareConfig.getTokenPath();
            HttpPost postGenerateAccessToken = new HttpPost(pathPostToken);
            postGenerateAccessToken.setHeader(headerKeyToken, clientSecret);

            try (CloseableHttpResponse httpResponse = httpClient.execute(postGenerateAccessToken)) {
                String responseToken = EntityUtils.toString(httpResponse.getEntity());
                return Util.stringToObject(responseToken, ClientToken.class);
            } catch (Exception e) {
                propagateException = true;
                LOGGER.error(e);
                throw new RequestException("Hubo un error no controlado al realizar el requestToken");
            }
        } catch (Exception e) {
            if (propagateException)
                throw e;
            LOGGER.error(e);
            throw new RuntimeException("Hubo un error no controlado al crear el clienteToken");
        }
    }
}
