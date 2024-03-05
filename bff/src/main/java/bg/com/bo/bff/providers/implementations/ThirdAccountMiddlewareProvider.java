package bg.com.bo.bff.providers.implementations;

import bg.com.bo.bff.commons.enums.ErrorExceptions;
import bg.com.bo.bff.models.ClientToken;
import bg.com.bo.bff.providers.dtos.responses.ThirdAccountListMWResponse;
import bg.com.bo.bff.models.ThirdAccountListResponse;
import bg.com.bo.bff.commons.enums.HttpError;
import bg.com.bo.bff.application.exceptions.BadRequestException;
import bg.com.bo.bff.application.exceptions.NotAcceptableException;
import bg.com.bo.bff.application.exceptions.RequestException;
import bg.com.bo.bff.models.interfaces.IHttpClientFactory;
import bg.com.bo.bff.providers.mappings.third.account.ThirdAccountListMapper;
import bg.com.bo.bff.providers.interfaces.IThirdAccountProvider;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class ThirdAccountMiddlewareProvider implements IThirdAccountProvider {
    @Value("${middleware}")
    private String url;
    @Value("${third.account.auth.token}")
    private String complementToken;
    @Value("${v1.third.account}")
    private String complementThirdAccounts;
    @Value("${client.secret.third-accounts}")
    private String clientSecret;

    private final IHttpClientFactory httpClientFactory;
    private final ThirdAccountListMapper thirdAccountListMapper;
    private static final Logger LOGGER = LogManager.getLogger(ThirdAccountMiddlewareProvider.class.getName());
    private static final String AUTH = "Authorization";
    private static final String MIDDLEWARE_CHANNEL = "middleware-channel";
    private static final String APPLICATION_ID = "application-id";

    @Autowired
    public ThirdAccountMiddlewareProvider(IHttpClientFactory httpClientFactory, ThirdAccountListMapper thirdAccountListMapper) {
        this.httpClientFactory = httpClientFactory;
        this.thirdAccountListMapper = thirdAccountListMapper;
    }

    private CloseableHttpClient createHttpClient() {
        return httpClientFactory.create();
    }

    public ClientToken generateAccessToken() throws IOException {
        boolean propagateException = false;
        try (CloseableHttpClient httpClient = createHttpClient()) {
            String paramsGenerarClientSecret = "?grant_type=client_credentials";
            String pathPostToken = url + complementToken + paramsGenerarClientSecret;
            HttpPost postGenerateAccessToken = new HttpPost(pathPostToken);
            postGenerateAccessToken.setHeader("Secret", clientSecret);
            ObjectMapper objectMapper = new ObjectMapper();

            try (CloseableHttpResponse httpResponse = httpClient.execute(postGenerateAccessToken)) {
                String responseToken = EntityUtils.toString(httpResponse.getEntity());
                return objectMapper.readValue(responseToken, ClientToken.class);
            } catch (Exception e) {
                propagateException = true;
                LOGGER.error(e);
                throw new RequestException(ErrorExceptions.ERROR_REQUEST_TOKEN.getMessage());
            }
        } catch (Exception e) {
            if (propagateException) throw e;
            LOGGER.error(e);
            throw new RuntimeException(ErrorExceptions.ERROR_CLIENT_TOKEN.getMessage());
        }
    }

    public ThirdAccountListResponse getListThirdAccounts(String token, String personId, String company) throws IOException {
        boolean propagateException = false;
        try (CloseableHttpClient httpClient = createHttpClient()) {
            String channel = "2";
            String urlGetThirdAccounts = url + complementThirdAccounts + "/persons/" + company + "/companies/" + personId;
            HttpGet getThirdAccounts = new HttpGet(urlGetThirdAccounts);
            getThirdAccounts.setHeader(AUTH, "Bearer " + token);
            getThirdAccounts.setHeader(MIDDLEWARE_CHANNEL, channel);
            getThirdAccounts.setHeader(APPLICATION_ID, channel);
            ObjectMapper objectMapper = new ObjectMapper();
            try (CloseableHttpResponse httpResponse = httpClient.execute(getThirdAccounts)) {
                int statusCode = httpResponse.getStatusLine().getStatusCode();
                if (statusCode == HttpStatus.SC_OK) {
                    String responseThirdAccounts = EntityUtils.toString(httpResponse.getEntity());
                    ThirdAccountListMWResponse responseMW = objectMapper.readValue(responseThirdAccounts, ThirdAccountListMWResponse.class);
                    ThirdAccountListResponse listResponse = thirdAccountListMapper.convert(responseMW);
                    return listResponse;
                } else {
                    propagateException = true;
                    switch (statusCode) {
                        case 400:
                            throw new BadRequestException(HttpError.ERROR_400.getDescription());
                        case 401:
                            throw new RuntimeException(HttpError.ERROR_401.getDescription());
                        case 404:
                            throw new UnsupportedOperationException(HttpError.ERROR_404.getDescription());
                        case 406:
                            throw new NotAcceptableException(HttpError.ERROR_406.getDescription());
                        default: {
                            throw new UnsupportedOperationException(HttpError.ERROR_500.getDescription());
                        }
                    }
                }
            } catch (Exception e) {
                if (propagateException) throw e;
                propagateException = true;
                LOGGER.error(e);
                throw new RequestException(ErrorExceptions.ERROR_THIRD_ACCOUNT.getMessage());
            }
        } catch (Exception e) {
            if (propagateException) throw e;
            LOGGER.error(e);
            throw new RuntimeException(ErrorExceptions.ERROR_CLIENT.getMessage());
        }
    }
}
