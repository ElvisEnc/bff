package bg.com.bo.bff.providers.implementations;

import bg.com.bo.bff.application.config.MiddlewareConfig;
import bg.com.bo.bff.commons.enums.CanalMW;
import bg.com.bo.bff.commons.enums.Headers;
import bg.com.bo.bff.commons.enums.ProjectNameMW;
import bg.com.bo.bff.providers.dtos.responses.accounts.AccountListMWResponse;
import bg.com.bo.bff.models.dtos.accounts.AccountListResponse;
import bg.com.bo.bff.models.dtos.middleware.ClientMWToken;
import bg.com.bo.bff.commons.enums.HttpError;
import bg.com.bo.bff.application.exceptions.BadRequestException;
import bg.com.bo.bff.application.exceptions.NotAcceptableException;
import bg.com.bo.bff.application.exceptions.RequestException;
import bg.com.bo.bff.providers.mappings.account.AccountListMapper;
import bg.com.bo.bff.models.interfaces.IHttpClientFactory;
import bg.com.bo.bff.providers.interfaces.IAccountProvider;
import com.fasterxml.jackson.databind.ObjectMapper;
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
public class AccountMiddlewareProvider implements IAccountProvider {
    @Value("${middleware}")
    private String url;

    @Value("${oauth.token}")
    private String complementToken;

    @Value("${v1.account.own}")
    private String complementAccounts;

    @Value("${client.secret.accounts}")
    private String clientSecret;

    private final MiddlewareConfig middlewareConfig;
    private IHttpClientFactory httpClientFactory;

    private AccountListMapper accountListMapper;
    private static final String AUTH = "Authorization";
    private static final String MIDDLEWARE_CHANNEL = "middleware-channel";
    private static final String APPLICATION_ID = "application-id";

    private static final Logger logger = LogManager.getLogger(AccountMiddlewareProvider.class.getName());

    @Autowired
    public AccountMiddlewareProvider(MiddlewareConfig middlewareConfig, IHttpClientFactory httpClientFactory, AccountListMapper accountListMapper) {
        this.middlewareConfig = middlewareConfig;
        this.httpClientFactory = httpClientFactory;
        this.accountListMapper = accountListMapper;
    }

    private CloseableHttpClient createHttpClient() {
        return httpClientFactory.create();
    }

    public ClientMWToken generateAccountAccessToken() throws IOException {
        boolean propagateException = false;

        try (CloseableHttpClient httpClient = createHttpClient()) {
            String paramsGenerarClientSecret = "?grant_type=client_credentials";
            String pathPostToken = url + complementToken + paramsGenerarClientSecret;
            HttpPost postGenerateAccessToken = new HttpPost(pathPostToken);
            postGenerateAccessToken.setHeader("Secret", clientSecret);
            ObjectMapper objectMapper = new ObjectMapper();

            try (CloseableHttpResponse httpResponse = httpClient.execute(postGenerateAccessToken)) {
                String responseToken = EntityUtils.toString(httpResponse.getEntity());
                return objectMapper.readValue(responseToken, ClientMWToken.class);
            } catch (Exception e) {
                propagateException = true;
                logger.error(e);
                throw new RequestException("Hubo un error no controlado al realizar el requestToken");
            }
        } catch (Exception e) {
            if (propagateException)
                throw e;
            logger.error(e);
            throw new RuntimeException("Hubo un error no controlado al crear el clienteToken");
        }
    }

    public AccountListResponse getAccounts(String token, String personId, String documentNumber) throws IOException {
        boolean propagateException = false;

        try (CloseableHttpClient httpClient = createHttpClient()) {
            String path = middlewareConfig.getUrlBase() + ProjectNameMW.OWN_ACCOUNT_MANAGER.getName() + "/bs/v1/accounts/persons/" + personId + "/companies/" + personId + "/devices/0/roles/0";
            HttpGet get = new HttpGet(path);
            get.setHeader(Headers.AUT.getName(), "Bearer " + token);
            get.setHeader(Headers.MW_CHA.getName(), CanalMW.GANAMOVIL.getCanal());
            get.setHeader(Headers.APP_ID.getName(), CanalMW.GANAMOVIL.getCanal());
            ObjectMapper objectMapper = new ObjectMapper();
            try (CloseableHttpResponse httpResponse = httpClient.execute(get)) {
                int statusCode = httpResponse.getStatusLine().getStatusCode();
                String response = EntityUtils.toString(httpResponse.getEntity());

                if (statusCode == 200) {
                    AccountListMWResponse responseMW = objectMapper.readValue(response, AccountListMWResponse.class);
                    return accountListMapper.convert(responseMW);
                } else {
                    propagateException = true;
                    logger.error(response);
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
                if (propagateException)
                    throw e;
                propagateException = true;
                logger.error(e);
                throw new RequestException("Hubo un error no controlado al realizar el requestGetAccounts");
            }
        } catch (Exception e) {
            if (propagateException)
                throw e;
            logger.error(e);
            throw new RuntimeException("Hubo un error no controlado al crear el clienteGetAccounts");
        }
    }
}
