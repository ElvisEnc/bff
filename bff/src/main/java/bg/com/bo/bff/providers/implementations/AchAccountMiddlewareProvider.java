package bg.com.bo.bff.providers.implementations;

import bg.com.bo.bff.application.dtos.response.GenericResponse;
import bg.com.bo.bff.application.exceptions.GenericException;
import bg.com.bo.bff.application.exceptions.HandledException;
import bg.com.bo.bff.application.exceptions.RequestException;
import bg.com.bo.bff.commons.converters.ErrorResponseConverter;
import bg.com.bo.bff.commons.enums.AppError;
import bg.com.bo.bff.commons.enums.ApplicationId;
import bg.com.bo.bff.commons.enums.CanalMW;
import bg.com.bo.bff.commons.enums.DeviceMW;
import bg.com.bo.bff.commons.enums.ErrorExceptions;
import bg.com.bo.bff.commons.enums.Headers;
import bg.com.bo.bff.commons.utils.Util;
import bg.com.bo.bff.models.ClientToken;
import bg.com.bo.bff.models.interfaces.IHttpClientFactory;
import bg.com.bo.bff.providers.dtos.requests.AddAchAccountBasicRequest;
import bg.com.bo.bff.providers.dtos.responses.accounts.AddAccountResponse;
import bg.com.bo.bff.providers.interfaces.IAchAccountProvider;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Map;

@Service
public class AchAccountMiddlewareProvider implements IAchAccountProvider {
    @Value("${middleware}")
    private String url;
    @Value("${ach.account.auth.token}")
    private String complementToken;
    @Value("${v1.ach.account}")
    private String complementAchdAccounts;
    @Value("${client.secret.ach-accounts}")
    private String clientSecret;

    private final IHttpClientFactory httpClientFactory;
    private static final Logger LOGGER = LogManager.getLogger(AchAccountMiddlewareProvider.class.getName());
    private static final String AUTH = "Authorization";
    private static final String MIDDLEWARE_CHANNEL = "middleware-channel";
    private static final String APPLICATION_ID = "application-id";

    public AchAccountMiddlewareProvider(IHttpClientFactory httpClientFactory) {
        this.httpClientFactory = httpClientFactory;
    }


    private CloseableHttpClient createHttpClient() {
        return httpClientFactory.create();
    }

    @Override
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

    @Override
    public GenericResponse addAchAccount(String token, AddAchAccountBasicRequest request, Map<String, String> parameters) throws IOException {

        String jsonMapper = Util.objectToString(request);
        StringEntity entity = new StringEntity(jsonMapper);
        try (CloseableHttpClient httpClient = createHttpClient()) {
            String urlGetThirdAccounts = url + complementAchdAccounts + "/ach";
            HttpPost httpRequest = getHttpPost(token, parameters, urlGetThirdAccounts, entity);

            try (CloseableHttpResponse httpResponse = httpClient.execute(httpRequest)) {
                int statusCode = httpResponse.getStatusLine().getStatusCode();
                String jsonResponse = EntityUtils.toString(httpResponse.getEntity());

                if (statusCode == HttpStatus.SC_OK) {
                    return GenericResponse.instance(AddAccountResponse.SUCCESS);
                }
                LOGGER.error(jsonResponse);
                AppError error = Util.mapProviderError(jsonResponse);
                throw new GenericException(error.getMessage(), error.getHttpCode(), error.getCode());
            }
        } catch (GenericException e) {
            throw e;
        } catch (Exception e) {
            LOGGER.error(e);
            throw new HandledException(ErrorResponseConverter.GenericErrorResponse.DEFAULT, e);
        }
    }

    private HttpPost getHttpPost(String token, Map<String, String> parameters, String urlGetThirdAccounts, StringEntity entity) {

        HttpPost httpRequest = new HttpPost(urlGetThirdAccounts);
        httpRequest.setHeader(AUTH, "Bearer " + token);
        httpRequest.setHeader(MIDDLEWARE_CHANNEL, CanalMW.GANAMOVIL.getCanal());
        httpRequest.setHeader(APPLICATION_ID, ApplicationId.GANAMOVIL.getCode());
        httpRequest.setHeader(DeviceMW.DEVICE_ID.getCode(), parameters.get(DeviceMW.DEVICE_ID.getCode()));
        httpRequest.setHeader(DeviceMW.DEVICE_IP.getCode(), parameters.get(DeviceMW.DEVICE_IP.getCode()));
        httpRequest.setHeader(DeviceMW.DEVICE_NAME.getCode(), parameters.get(DeviceMW.DEVICE_NAME.getCode()));
        httpRequest.setHeader(DeviceMW.GEO_POSITION_X.getCode(), parameters.get(DeviceMW.GEO_POSITION_X.getCode()));
        httpRequest.setHeader(DeviceMW.GEO_POSITION_Y.getCode(), parameters.get(DeviceMW.GEO_POSITION_Y.getCode()));
        httpRequest.setHeader(DeviceMW.APP_VERSION.getCode(), parameters.get(DeviceMW.APP_VERSION.getCode()));
        httpRequest.setHeader(Headers.CONTENT_TYPE.getName(), Headers.APP_JSON.getName());
        httpRequest.setEntity(entity);
        return httpRequest;
    }
}
