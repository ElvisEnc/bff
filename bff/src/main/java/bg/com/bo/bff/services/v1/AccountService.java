package bg.com.bo.bff.services.v1;

import bg.com.bo.bff.model.AccountListMWResponse;
import bg.com.bo.bff.model.AccountListResponse;
import bg.com.bo.bff.model.ClientToken;
import bg.com.bo.bff.model.enums.HttpError;
import bg.com.bo.bff.model.exceptions.NotAcceptableException;
import bg.com.bo.bff.model.exceptions.RequestException;
import bg.com.bo.bff.model.interfaces.IHttpClientFactory;
import bg.com.bo.bff.model.interfaces.AccountListMapper;
import bg.com.bo.bff.services.interfaces.IAccountService;
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
public class AccountService implements IAccountService {
    @Value("${middleware}")
    private String urlDev;

    @Value("${v1.account.own}")
    private String complementAccounts;

    @Value("${oauth.token}")
    private String complementToken;

    @Value("${client.secret.accounts}")
    private String clientSecret;

    private AccountListMapper accountListMapper;

    private IHttpClientFactory httpClientFactory;

    private static final Logger logger = LogManager.getLogger(AccountService.class.getName());

    @Autowired
    public AccountService(IHttpClientFactory httpClientFactory, AccountListMapper accountListMapper) {
        this.httpClientFactory = httpClientFactory;
        this.accountListMapper = accountListMapper;
    }

    public AccountListResponse getAccounts(String personId, String documentNumber) throws IOException {
        try (CloseableHttpClient httpClient = httpClientFactory.create()) {
            String channelGanamovil = "2";
            String paramsGenerarClientSecret = "?grant_type=client_credentials";
            String pathPostToken = urlDev + complementToken + paramsGenerarClientSecret;
            HttpPost postGenerateAccesToken = new HttpPost(pathPostToken);
            postGenerateAccesToken.setHeader("Authorization", clientSecret);
            ObjectMapper objectMapper = new ObjectMapper();
            ClientToken clientToken;
            try (CloseableHttpResponse httpResponse = httpClient.execute(postGenerateAccesToken)) {
                String responseToken = EntityUtils.toString(httpResponse.getEntity());
                clientToken = objectMapper.readValue(responseToken, ClientToken.class);
            } catch (Exception e) {
                logger.error(e);
                throw new RequestException("Hubo un error no controlado al realizar el request");
            }

            String pathGetAccounts = urlDev + complementAccounts + "/persons/" + personId + "/document-number/" + documentNumber;
            HttpGet getAccountsMW = new HttpGet(pathGetAccounts);
            getAccountsMW.setHeader("Authorization", "Bearer " + clientToken.getAccessToken());
            getAccountsMW.setHeader("topaz-channel", channelGanamovil);
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
                logger.error(e);
                throw new RequestException("Hubo un error no controlado al realizar el request");
            }
        } catch (RequestException e) {
            throw new RequestException(e.getMessage());
        } catch (Exception e) {
            logger.error(e);
            throw new RuntimeException("Hubo un error no controlado al crear el cliente");
        }
    }
}
