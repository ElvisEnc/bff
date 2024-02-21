package bg.com.bo.bff.services.v1;

import bg.com.bo.bff.model.ClientToken;
import bg.com.bo.bff.model.ThirdAccountListMWResponse;
import bg.com.bo.bff.model.ThirdAccountListResponse;
import bg.com.bo.bff.model.enums.HttpError;
import bg.com.bo.bff.model.exceptions.BadRequestException;
import bg.com.bo.bff.model.exceptions.NotAcceptableException;
import bg.com.bo.bff.model.exceptions.RequestException;
import bg.com.bo.bff.model.interfaces.IHttpClientFactory;
import bg.com.bo.bff.model.interfaces.ThirdAccountListMapper;
import bg.com.bo.bff.services.interfaces.IThirdAccountMiddlewareService;
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
public class ThirdAccountMiddlewareService implements IThirdAccountMiddlewareService {
    @Value("${middleware}")
    private String url;
    @Value("${third.account.auth.token}")
    private String complementToken;
    @Value("${v1.third.account}")
    private String complementThirdAccounts;
    @Value("${client.secret.third-accounts}")
    private String clientSecret;

    private IHttpClientFactory httpClientFactory;
    private ThirdAccountListMapper thirdAccountListMapper;
    private static final Logger LOGGER = LogManager.getLogger(ThirdAccountMiddlewareService.class.getName());

    @Autowired
    public ThirdAccountMiddlewareService(IHttpClientFactory httpClientFactory, ThirdAccountListMapper thirdAccountListMapper) {
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
                throw new RequestException("Hubo un error no controlado al realizar el requestToken");
            }
        } catch (Exception e) {
            if (propagateException) throw e;
            LOGGER.error(e);
            throw new RuntimeException("Hubo un error no controlado al crear el clienteToken");
        }
    }

    public ThirdAccountListResponse getListThridAccounts(String token, String personId, String company) throws IOException {
        boolean propagateException = false;
        try (CloseableHttpClient httpClient = createHttpClient()) {
            String channel = "2";
            String urlGetThirdAccounts = url + complementThirdAccounts + "/companies/" + company + "/persons/" + personId;
            HttpGet getThirdAccounts = new HttpGet(urlGetThirdAccounts);
            getThirdAccounts.setHeader("Authorization", "Bearer " + token);
            getThirdAccounts.setHeader("middleware-channel", channel);
            getThirdAccounts.setHeader("Accept", "application/json");
            ObjectMapper objectMapper = new ObjectMapper();
            try (CloseableHttpResponse httpResponse = httpClient.execute(getThirdAccounts)) {
                int statusCode = httpResponse.getStatusLine().getStatusCode();
                if (statusCode == 200) {

                    String responseThirdAccounts = EntityUtils.toString(httpResponse.getEntity());
                    ThirdAccountListMWResponse responseMW = objectMapper.readValue(responseThirdAccounts, ThirdAccountListMWResponse.class);
                    ThirdAccountListResponse listResponse = thirdAccountListMapper.convert(responseMW);
                    return listResponse;
                } else {
                    propagateException = true;
                    switch (statusCode) {
                        case 400:
                            throw new BadRequestException(HttpError.Error400.getDescription());
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
                }
            } catch (Exception e) {
                if (propagateException) throw e;
                propagateException = true;
                LOGGER.error(e);
                throw new RequestException("Hubo un error no controlado al realizar el getListThridAccounts");
            }
        } catch (Exception e) {
            if (propagateException) throw e;
            LOGGER.error(e);
            throw new RuntimeException("Hubo un error no controlado al crear el clienteGetAccounts");
        }
    }
}
