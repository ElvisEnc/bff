package bg.com.bo.bff.providers.implementations;

import bg.com.bo.bff.providers.mappings.login.LoginMWMapper;
import bg.com.bo.bff.application.dtos.request.LoginRequest;
import bg.com.bo.bff.models.dtos.middleware.ClientMWToken;
import bg.com.bo.bff.models.dtos.login.LoginValidationServiceResponse;
import bg.com.bo.bff.application.exceptions.NotHandledResponseException;
import bg.com.bo.bff.application.exceptions.RequestException;
import bg.com.bo.bff.application.exceptions.UnauthorizedException;
import bg.com.bo.bff.models.interfaces.IHttpClientFactory;
import bg.com.bo.bff.providers.dtos.requests.login.LoginMWRequest;
import bg.com.bo.bff.providers.dtos.responses.login.LoginMWResponse;
import bg.com.bo.bff.providers.interfaces.ILoginMiddlewareProvider;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;


@Service
public class LoginMiddlewareProvider implements ILoginMiddlewareProvider {
    @Value("${middleware}")
    private String url;

    @Value("${login.oauth.middleware}")
    private String complementToken;

    @Value("${login.url.complement}")
    private String complementLogin;

    @Value("${client.secret.login.api}")
    private String clientSecret;

    private IHttpClientFactory httpClientFactory;

    private LoginMWMapper loginMWMapper;

    private static final String TOPAZ_CHANNEL = "1";
    private static final String APPLICATION_ID = "1";

    private static final Logger logger = LogManager.getLogger(LoginMiddlewareProvider.class.getName());

    @Autowired
    public LoginMiddlewareProvider(IHttpClientFactory httpClientFactory, LoginMWMapper loginMWMapper) {
        this.httpClientFactory = httpClientFactory;
        this.loginMWMapper = loginMWMapper;
    }

    private CloseableHttpClient createHttpClient() {
        return httpClientFactory.create();
    }

    public ClientMWToken generateAccessToken() {
        try (CloseableHttpClient httpClient = createHttpClient()) {
            String paramsGenerateClientSecret = "?grant_type=client_credentials";
            String pathPostToken = url + complementToken + paramsGenerateClientSecret;
            HttpPost postGenerateAccessToken = new HttpPost(pathPostToken);
            postGenerateAccessToken.setHeader("Secret", clientSecret);
            ObjectMapper objectMapper = new ObjectMapper();

            try (CloseableHttpResponse httpResponse = httpClient.execute(postGenerateAccessToken)) {
                String responseToken = EntityUtils.toString(httpResponse.getEntity());
                return objectMapper.readValue(responseToken, ClientMWToken.class);
            } catch (Exception e) {
                logger.error(e);
                throw new RequestException("Hubo un error no controlado al realizar el requestToken");
            }
        } catch (RequestException e) {
            throw e;
        } catch (Exception e) {
            logger.error(e);
            throw new RequestException("Hubo un error no controlado al crear el clienteToken");
        }
    }

    public LoginValidationServiceResponse validateCredentials(String token, LoginRequest loginRequest) {
        try (CloseableHttpClient httpClient = createHttpClient()) {
            LoginMWRequest loginMWRequest = loginMWMapper.convert(loginRequest);

            String path = url + complementLogin;
            HttpPost request = new HttpPost(path);
            ObjectMapper objectMapper = new ObjectMapper();
            String jsonMapper = objectMapper.writeValueAsString(loginMWRequest);
            StringEntity entity = new StringEntity(jsonMapper);
            request.setEntity(entity);
            request.setHeader("Content-Type", "application/json");
            request.setHeader("Authorization", "Bearer " + token);
            request.setHeader("topaz-channel", TOPAZ_CHANNEL);
            request.setHeader("application-id", APPLICATION_ID);

            try (CloseableHttpResponse httpResponse = httpClient.execute(request)) {
                int statusCode = httpResponse.getStatusLine().getStatusCode();
                String jsonResponse = EntityUtils.toString(httpResponse.getEntity());
                switch (statusCode) {
                    case 200: {
                        LoginMWResponse loginMWResponse = objectMapper.readValue(jsonResponse, LoginMWResponse.class);

                        LoginValidationServiceResponse loginResult = new LoginValidationServiceResponse();
                        loginResult.setPersonId(loginMWResponse.getData().getPersonId());
                        loginResult.setStatusCode("SUCCESS");

                        return loginResult;
                    }
                    case 401:
                        logger.error(jsonResponse);
                        throw new UnauthorizedException(HttpStatus.UNAUTHORIZED.name());
                    default:
                        logger.error(jsonResponse);
                        throw new NotHandledResponseException();
                }
            } catch (UnauthorizedException | NotHandledResponseException e) {
                throw e;
            } catch (Exception e) {
                logger.error(e);
                throw new RequestException("Hubo un error al realizar el validateCredentials.");
            }
        } catch (NotHandledResponseException | UnauthorizedException e) {
            throw e;
        } catch (Exception e) {
            logger.error(e);
            throw new RequestException("Hubo un error no controlado al crear el clienteValidateCredentials");
        }
    }
}
