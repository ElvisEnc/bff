package bg.com.bo.bff.providers.implementations;

import bg.com.bo.bff.application.config.MiddlewareConfig;
import bg.com.bo.bff.application.dtos.response.GenericResponse;
import bg.com.bo.bff.providers.dtos.TransactionLimitListMWResponse;
import bg.com.bo.bff.application.exceptions.BadRequestException;
import bg.com.bo.bff.application.exceptions.GenericException;
import bg.com.bo.bff.application.exceptions.HandledException;
import bg.com.bo.bff.application.exceptions.NotAcceptableException;
import bg.com.bo.bff.application.exceptions.RequestException;
import bg.com.bo.bff.commons.converters.ErrorResponseConverter;
import bg.com.bo.bff.commons.enums.AppError;
import bg.com.bo.bff.commons.enums.CanalMW;
import bg.com.bo.bff.commons.enums.DeviceMW;
import bg.com.bo.bff.commons.enums.Headers;
import bg.com.bo.bff.commons.enums.HttpError;
import bg.com.bo.bff.commons.enums.PersonRol;
import bg.com.bo.bff.commons.enums.ProjectNameMW;
import bg.com.bo.bff.commons.utils.Util;
import bg.com.bo.bff.models.ClientToken;
import bg.com.bo.bff.models.dtos.accounts.AccountListResponse;
import bg.com.bo.bff.models.dtos.middleware.ClientMWToken;
import bg.com.bo.bff.models.interfaces.IHttpClientFactory;
import bg.com.bo.bff.providers.dtos.requests.UpdateTransactionLimitMWRequest;
import bg.com.bo.bff.providers.dtos.responses.accounts.AccountListMWResponse;
import bg.com.bo.bff.providers.dtos.responses.accounts.TransactionLimitUpdateAccountResponse;
import bg.com.bo.bff.providers.interfaces.IAccountProvider;
import bg.com.bo.bff.providers.interfaces.ITokenMiddlewareProvider;
import bg.com.bo.bff.providers.mappings.own.account.AccountListMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Map;


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
    private final ITokenMiddlewareProvider tokenMiddlewareProvider;
    private final IHttpClientFactory httpClientFactory;

    private final AccountListMapper accountListMapper;
    private static final String URL_GET_TRANSACTION_LIMIT = "%s%s/bs/v1/accounts/limits/persons/%s/accounts/%s/companies/%s";
    private static final String URL_PUT_TRANSACTION_LIMIT = "%s%s/bs/v1/accounts/%s/persons/%s/companies/%s/limits";

    private static final Logger logger = LogManager.getLogger(AccountMiddlewareProvider.class.getName());

    @Autowired
    public AccountMiddlewareProvider(MiddlewareConfig middlewareConfig, ITokenMiddlewareProvider tokenMiddlewareProvider, IHttpClientFactory httpClientFactory, AccountListMapper accountListMapper) {
        this.middlewareConfig = middlewareConfig;
        this.tokenMiddlewareProvider = tokenMiddlewareProvider;
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
            String path = middlewareConfig.getUrlBase() + ProjectNameMW.OWN_ACCOUNT_MANAGER.getName() + "/bs/v1/accounts/persons/" + personId + "/companies/" + personId + "/devices/0/roles/" + PersonRol.PERSONA.getId();
            HttpGet get = new HttpGet(path);
            get.setHeader(Headers.AUT.getName(), "Bearer " + token);
            get.setHeader(Headers.MW_CHA.getName(), CanalMW.GANAMOVIL.getCanal());
            get.setHeader(Headers.APP_ID.getName(), CanalMW.GANAMOVIL.getCanal());
            try (CloseableHttpResponse httpResponse = httpClient.execute(get)) {
                int statusCode = httpResponse.getStatusLine().getStatusCode();
                String response = EntityUtils.toString(httpResponse.getEntity());

                if (statusCode == 200) {
                    AccountListMWResponse responseMW = Util.stringToObject(response, AccountListMWResponse.class);
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

    @Override
    public GenericResponse updateTransactionLimit(String personId, String accountId,
                                                  UpdateTransactionLimitMWRequest request,
                                                  Map<String, String> parameter) throws IOException {

        String path =String.format(URL_PUT_TRANSACTION_LIMIT,
                middlewareConfig.getUrlBase(), ProjectNameMW.OWN_ACCOUNT_MANAGER.getName(),accountId,personId,personId);

        ClientToken clientToken = tokenMiddlewareProvider.generateAccountAccessToken(ProjectNameMW.OWN_ACCOUNT_MANAGER.getName(), middlewareConfig.getClientOwnManager(), ProjectNameMW.OWN_ACCOUNT_MANAGER.getHeaderKey());

        String jsonMapper = Util.objectToString(request);
        StringEntity entity = new StringEntity(jsonMapper);
        HttpPut httpRequest = new HttpPut(path);
        httpRequest.setHeader(Headers.AUT.getName(), "Bearer " + clientToken.getAccessToken());
        httpRequest.setHeader(Headers.MW_CHA.getName(), CanalMW.GANAMOVIL.getCanal());
        httpRequest.setHeader(Headers.APP_ID.getName(), CanalMW.GANAMOVIL.getCanal());
        httpRequest.setHeader(DeviceMW.DEVICE_ID.getCode(), parameter.get(DeviceMW.DEVICE_ID.getCode()));
        httpRequest.setHeader(DeviceMW.DEVICE_IP.getCode(), parameter.get(DeviceMW.DEVICE_IP.getCode()));
        httpRequest.setHeader(DeviceMW.DEVICE_NAME.getCode(), parameter.get(DeviceMW.DEVICE_NAME.getCode()));
        httpRequest.setHeader(DeviceMW.GEO_POSITION_X.getCode(), parameter.get(DeviceMW.GEO_POSITION_X.getCode()));
        httpRequest.setHeader(DeviceMW.GEO_POSITION_Y.getCode(), parameter.get(DeviceMW.GEO_POSITION_Y.getCode()));
        httpRequest.setHeader(DeviceMW.APP_VERSION.getCode(), parameter.get(DeviceMW.APP_VERSION.getCode()));
        httpRequest.setHeader(Headers.CONTENT_TYPE.getName(), Headers.APP_JSON.getName());
        httpRequest.setEntity(entity);

        try (CloseableHttpClient httpClient = createHttpClient()) {

            try (CloseableHttpResponse httpResponse = httpClient.execute(httpRequest)) {
                int statusCode = httpResponse.getStatusLine().getStatusCode();
                String jsonResponse = EntityUtils.toString(httpResponse.getEntity());

                if (statusCode == HttpStatus.SC_OK) {
                    return GenericResponse.instance(TransactionLimitUpdateAccountResponse.SUCCESS);
                }
                logger.error(jsonResponse);
                AppError error = Util.mapProviderError(jsonResponse);
                throw new GenericException(error.getMessage(), error.getHttpCode(), error.getCode());
            }
        } catch (GenericException e) {
            throw e;
        } catch (Exception e) {
            logger.error(e);
            throw new HandledException(ErrorResponseConverter.GenericErrorResponse.DEFAULT, e);
        }
    }

    @Override
    public TransactionLimitListMWResponse getTransactionLimit(String personId, String accountId, Map<String, String> parameter) throws IOException {
        String path =String.format(URL_GET_TRANSACTION_LIMIT,
                middlewareConfig.getUrlBase(),
                ProjectNameMW.OWN_ACCOUNT_MANAGER.getName(),personId,accountId,personId);

        ClientToken clientToken = tokenMiddlewareProvider.generateAccountAccessToken(ProjectNameMW.OWN_ACCOUNT_MANAGER.getName(), middlewareConfig.getClientOwnManager(), ProjectNameMW.OWN_ACCOUNT_MANAGER.getHeaderKey());

        HttpGet httpRequest = new HttpGet(path);
        httpRequest.setHeader(Headers.AUT.getName(), "Bearer " + clientToken.getAccessToken());
        httpRequest.setHeader(Headers.MW_CHA.getName(), CanalMW.GANAMOVIL.getCanal());
        httpRequest.setHeader(Headers.APP_ID.getName(), CanalMW.GANAMOVIL.getCanal());
        httpRequest.setHeader(DeviceMW.DEVICE_ID.getCode(), parameter.get(DeviceMW.DEVICE_ID.getCode()));
        httpRequest.setHeader(DeviceMW.DEVICE_IP.getCode(), parameter.get(DeviceMW.DEVICE_IP.getCode()));
        httpRequest.setHeader(DeviceMW.DEVICE_NAME.getCode(), parameter.get(DeviceMW.DEVICE_NAME.getCode()));
        httpRequest.setHeader(DeviceMW.GEO_POSITION_X.getCode(), parameter.get(DeviceMW.GEO_POSITION_X.getCode()));
        httpRequest.setHeader(DeviceMW.GEO_POSITION_Y.getCode(), parameter.get(DeviceMW.GEO_POSITION_Y.getCode()));
        httpRequest.setHeader(DeviceMW.APP_VERSION.getCode(), parameter.get(DeviceMW.APP_VERSION.getCode()));
        httpRequest.setHeader(Headers.CONTENT_TYPE.getName(), Headers.APP_JSON.getName());

        try (CloseableHttpClient httpClient = createHttpClient()) {

            try (CloseableHttpResponse httpResponse = httpClient.execute(httpRequest)) {
                int statusCode = httpResponse.getStatusLine().getStatusCode();
                String jsonResponse = EntityUtils.toString(httpResponse.getEntity());

                if (statusCode == HttpStatus.SC_OK) {
                  return Util.stringToObject(jsonResponse, TransactionLimitListMWResponse.class);
                }
                logger.error(jsonResponse);
                AppError error = Util.mapProviderError(jsonResponse);
                throw new GenericException(error.getMessage(), error.getHttpCode(), error.getCode());
            }
        } catch (GenericException e) {
            throw e;
        } catch (Exception e) {
            logger.error(e);
            throw new HandledException(ErrorResponseConverter.GenericErrorResponse.DEFAULT, e);
        }
    }
}
