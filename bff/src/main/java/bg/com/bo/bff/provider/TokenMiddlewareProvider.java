package bg.com.bo.bff.provider;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import java.io.IOException;
import bg.com.bo.bff.config.MiddlewareConfig;
import bg.com.bo.bff.model.ClientToken;
import bg.com.bo.bff.model.exceptions.RequestException;
import bg.com.bo.bff.model.interfaces.IHttpClientFactory;
import bg.com.bo.bff.model.util.Util;
import bg.com.bo.bff.provider.interfaces.ITokenMiddlewareProvider;

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
    public ClientToken generateAccountAccessToken(String project) throws IOException {
        boolean propagateException = false;

        try (CloseableHttpClient httpClient = httpClientFactory.create()) {
            String pathPostToken = middlewareConfig.getUrl_base() + project + middlewareConfig.getToken_path();
            HttpPost postGenerateAccessToken = new HttpPost(pathPostToken);
            postGenerateAccessToken.setHeader("Authorization", middlewareConfig.getClient_transfer());

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
