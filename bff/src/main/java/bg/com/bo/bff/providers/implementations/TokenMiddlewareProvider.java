package bg.com.bo.bff.providers.implementations;

import bg.com.bo.bff.application.exceptions.GenericException;
import bg.com.bo.bff.commons.enums.config.provider.AppError;
import bg.com.bo.bff.providers.models.middleware.DefaultMiddlewareError;
import com.github.benmanes.caffeine.cache.LoadingCache;
import lombok.extern.log4j.Log4j2;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.UnknownHostException;
import java.time.Duration;
import java.time.LocalDateTime;

import bg.com.bo.bff.application.config.MiddlewareConfig;
import bg.com.bo.bff.models.ClientToken;
import bg.com.bo.bff.commons.interfaces.IHttpClientFactory;
import bg.com.bo.bff.commons.utils.Util;
import bg.com.bo.bff.providers.interfaces.ITokenMiddlewareProvider;

@Service
@Log4j2
public class TokenMiddlewareProvider implements ITokenMiddlewareProvider {
    private final IHttpClientFactory httpClientFactory;
    private final MiddlewareConfig middlewareConfig;
    private final LoadingCache<String, ClientToken> tokenCache;

    public TokenMiddlewareProvider(IHttpClientFactory httpClientFactory, MiddlewareConfig middlewareConfig, LoadingCache<String, ClientToken> tokenCache) {
        this.httpClientFactory = httpClientFactory;
        this.middlewareConfig = middlewareConfig;
        this.tokenCache = tokenCache;
    }

    private ClientToken generateMDWToken(String project, String clientSecret, String headerKeyToken) throws IOException {
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
                    log.error(responseToken);
                    throw new GenericException(error);
                }
            }
        } catch (GenericException ex) {
            log.error(ex);
            throw ex;
        } catch (UnknownHostException e){
            log.error(e);
            throw new GenericException(DefaultMiddlewareError.MW_SERVICE_UNAVAILABLE);
        } catch (Exception e) {
            log.error(e);
            throw new GenericException(DefaultMiddlewareError.MW_FAILURE);
        }
    }

    @Override
    public ClientToken generateAccountAccessToken(String project, String clientSecret, String headerKeyToken) throws IOException {
        String sanitizedProjectName = Util.sanitizeProjectName(project);
        tokenCache.cleanUp();

        ClientToken cachedToken = getCachedToken(sanitizedProjectName);
        if (cachedToken != null) {
            return cachedToken;
        }

        ClientToken newToken = generateMDWToken(project, clientSecret, headerKeyToken);
        cacheToken(sanitizedProjectName, newToken);
        return newToken;
    }

    private ClientToken getCachedToken(String projectName) {
        return tokenCache.getIfPresent(projectName);
    }

    private void cacheToken(String projectName, ClientToken token) {
        LocalDateTime expirationTime = LocalDateTime.now();
        token.setRequiredAt(expirationTime.toString());
        token.setExpiredAt(expirationTime.plus(Duration.ofSeconds(token.getExpiresIn())).toString());
        tokenCache.put(projectName, token);
    }
}
