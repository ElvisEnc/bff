package bg.com.bo.bff.services.v1;

import bg.com.bo.bff.model.AccountListMWResponse;
import bg.com.bo.bff.model.AccountListResponse;
import bg.com.bo.bff.model.ClientToken;
import bg.com.bo.bff.model.enums.HttpError;
import bg.com.bo.bff.model.exceptions.NotAcceptableException;
import bg.com.bo.bff.model.exceptions.RequestException;
import bg.com.bo.bff.model.interfaces.AccountListMapper;
import bg.com.bo.bff.model.interfaces.IHttpClientFactory;
import bg.com.bo.bff.services.interfaces.IAccountMiddlewareService;
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
public class AccountMiddlewareService implements IAccountMiddlewareService {
    @Value("${middleware}")
    private String url;

    @Value("${oauth.token}")
    private String complementToken;

    @Value("${v1.account.own}")
    private String complementAccounts;

    @Value("${client.secret.accounts}")
    private String clientSecret;

    private IHttpClientFactory httpClientFactory;

    private AccountListMapper accountListMapper;

    private static final Logger LOGGER = LogManager.getLogger(AccountMiddlewareService.class.getName());

    @Autowired
    public AccountMiddlewareService(IHttpClientFactory httpClientFactory, AccountListMapper accountListMapper) {
        this.httpClientFactory = httpClientFactory;
        this.accountListMapper = accountListMapper;
    }

    private CloseableHttpClient createHttpClient(){
        return httpClientFactory.create();
    }

    public ClientToken generateAccountAccessToken() throws IOException {
        try (CloseableHttpClient httpClient = createHttpClient()) {
            String paramsGenerarClientSecret = "?grant_type=client_credentials";
            String pathPostToken = url + complementToken + paramsGenerarClientSecret;
            HttpPost postGenerateAccessToken = new HttpPost(pathPostToken);
            postGenerateAccessToken.setHeader("Authorization", clientSecret);
            ObjectMapper objectMapper = new ObjectMapper();

            try (CloseableHttpResponse httpResponse = httpClient.execute(postGenerateAccessToken)) {
                String responseToken = EntityUtils.toString(httpResponse.getEntity());
                return objectMapper.readValue(responseToken, ClientToken.class);
            } catch (Exception e) {
                LOGGER.error(e);
                throw new RequestException("Hubo un error no controlado al realizar el requestToken");
            }
        } catch (Exception e) {
            LOGGER.error(e);
            throw new RuntimeException("Hubo un error no controlado al crear el clienteToken");
        }
    }

    public AccountListResponse getAccounts(String token, String personId, String documentNumber) throws IOException {
        try (CloseableHttpClient httpClient = createHttpClient()) {
            String ganamovilChannel = "2";
            String pathGetAccounts = url + complementAccounts + "/persons/" + personId + "/document-number/" + documentNumber;
            HttpGet getAccountsMW = new HttpGet(pathGetAccounts);
            getAccountsMW.setHeader("Authorization", "Bearer " + token);
            getAccountsMW.setHeader("topaz-channel", ganamovilChannel);
            ObjectMapper objectMapper = new ObjectMapper();
            try (CloseableHttpResponse httpResponse = httpClient.execute(getAccountsMW)) {
                switch (httpResponse.getStatusLine().getStatusCode()) {
                    case 200: {
                        String responseAccounts = EntityUtils.toString(httpResponse.getEntity());
                        AccountListMWResponse responseMW = objectMapper.readValue(responseAccounts, AccountListMWResponse.class);
                        AccountListResponse accountListResponse = accountListMapper.convert(responseMW);
                        return accountListResponse;
                    }
                    case 401:
                        throw new RuntimeException(HttpError.Error401.getDescription());
                    case 404:
                        throw new UnsupportedOperationException(HttpError.Error404.getDescription());
                    case 406:
                        throw new NotAcceptableException(HttpError.Error406.getDescription());
                    default: {
                        throw new UnsupportedOperationException(HttpError.Error500.getDescription());
                    }
                }
            } catch (Exception e) {
                LOGGER.error(e);
                throw new RequestException("Hubo un error no controlado al realizar el requestGetAccounts");
            }
        }catch (Exception e) {
            LOGGER.error(e);
            throw new RuntimeException("Hubo un error no controlado al crear el clienteGetAccounts");
        }
    }
}
