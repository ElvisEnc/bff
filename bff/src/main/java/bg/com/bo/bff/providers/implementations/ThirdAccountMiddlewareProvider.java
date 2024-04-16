package bg.com.bo.bff.providers.implementations;

import bg.com.bo.bff.application.config.MiddlewareConfig;
import bg.com.bo.bff.application.dtos.response.GenericResponse;
import bg.com.bo.bff.application.exceptions.HandledException;
import bg.com.bo.bff.commons.HttpDeleteWithBody;
import bg.com.bo.bff.commons.converters.DeleteThirdAccountErrorResponseConverter;
import bg.com.bo.bff.commons.converters.ErrorResponseConverter;
import bg.com.bo.bff.commons.converters.IErrorResponse;
import bg.com.bo.bff.commons.enums.*;
import bg.com.bo.bff.commons.enums.response.DeleteThirdAccountResponse;
import bg.com.bo.bff.models.ClientToken;
import bg.com.bo.bff.providers.dtos.requests.DeleteThirdAccountMWRequest;
import bg.com.bo.bff.providers.dtos.responses.ThirdAccountListMWResponse;
import bg.com.bo.bff.models.ThirdAccountListResponse;
import bg.com.bo.bff.application.exceptions.BadRequestException;
import bg.com.bo.bff.application.exceptions.NotAcceptableException;
import bg.com.bo.bff.application.exceptions.RequestException;
import bg.com.bo.bff.models.interfaces.IHttpClientFactory;
import bg.com.bo.bff.providers.interfaces.ITokenMiddlewareProvider;
import bg.com.bo.bff.providers.mappings.third.account.ThirdAccountListMapper;
import bg.com.bo.bff.providers.interfaces.IThirdAccountProvider;
import bg.com.bo.bff.providers.mappings.third.account.ThirdAccountMWtMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.*;
import org.apache.http.entity.StringEntity;
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
    private ITokenMiddlewareProvider tokenMiddlewareProvider;
    private MiddlewareConfig middlewareConfig;
    private ThirdAccountMWtMapper mapper;
    private static final Logger logger = LogManager.getLogger(ThirdAccountMiddlewareProvider.class.getName());
    private static final String AUTH = "Authorization";
    private static final String MIDDLEWARE_CHANNEL = "middleware-channel";
    private static final String APPLICATION_ID = "application-id";

    @Autowired
    public ThirdAccountMiddlewareProvider(IHttpClientFactory httpClientFactory, ThirdAccountListMapper thirdAccountListMapper, MiddlewareConfig middlewareConfig, ITokenMiddlewareProvider tokenMiddlewareProvider, ThirdAccountMWtMapper mapper) {
        this.httpClientFactory = httpClientFactory;
        this.thirdAccountListMapper = thirdAccountListMapper;
        this.middlewareConfig = middlewareConfig;
        this.tokenMiddlewareProvider = tokenMiddlewareProvider;
        this.mapper = mapper;
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
                logger.error(e);
                throw new RequestException(ErrorExceptions.ERROR_REQUEST_TOKEN.getMessage());
            }
        } catch (Exception e) {
            if (propagateException) throw e;
            logger.error(e);
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
                logger.error(e);
                throw new RequestException(ErrorExceptions.ERROR_THIRD_ACCOUNT.getMessage());
            }
        } catch (Exception e) {
            if (propagateException) throw e;
            logger.error(e);
            throw new RuntimeException(ErrorExceptions.ERROR_CLIENT.getMessage());
        }
    }

    @Override
    public GenericResponse delete(String personId, int identifier, int accountId, String deviceId, String deviceIp) throws IOException {
        ClientToken clientToken = tokenMiddlewareProvider.generateAccountAccessToken(ProjectNameMW.THIRD_ACCOUNTS.getName(), middlewareConfig.getClientThirdAccount(), ProjectNameMW.THIRD_ACCOUNTS.getHeaderKey());
        DeleteThirdAccountMWRequest requestData = mapper.convert(personId, identifier, accountId);

        try (CloseableHttpClient httpClient = createHttpClient()) {
            String path = middlewareConfig.getUrlBase() + ProjectNameMW.THIRD_ACCOUNTS.getName() + "/bs/v1/third-party-accounts";
            HttpDeleteWithBody request = new HttpDeleteWithBody(path);

            ObjectMapper objectMapper = new ObjectMapper();
            String jsonMapper = objectMapper.writeValueAsString(requestData);
            StringEntity entity = new StringEntity(jsonMapper);
            request.setEntity(entity);

            request.setHeader(Headers.CONTENT_TYPE.getName(), Headers.APP_JSON.getName());
            request.setHeader(Headers.AUT.getName(), "Bearer " + clientToken.getAccessToken());
            request.setHeader(Headers.MW_CHA.getName(), CanalMW.GANAMOVIL.getCanal());
            request.setHeader(Headers.APP_ID.getName(), CanalMW.GANAMOVIL.getCanal());
            request.setHeader(Headers.DEVICE_ID.getName(), deviceId);
            request.setHeader(Headers.DEVICE_IP.getName(), deviceIp);

            try (CloseableHttpResponse httpResponse = httpClient.execute(request)) {
                int statusCode = httpResponse.getStatusLine().getStatusCode();
                String jsonResponse = EntityUtils.toString(httpResponse.getEntity());
                if (statusCode == HttpStatus.SC_OK)
                    return GenericResponse.instance(DeleteThirdAccountResponse.SUCCESS);
                else {
                    logger.error(jsonResponse);
                    IErrorResponse errorResponse = DeleteThirdAccountErrorResponseConverter.INSTANCE.convert(jsonResponse);
                    throw new HandledException(errorResponse);
                }
            }
        } catch (HandledException e) {
            throw e;
        } catch (Exception e) {
            logger.error(e);
            throw new HandledException(ErrorResponseConverter.GenericErrorResponse.DEFAULT, e);
        }
    }
}
