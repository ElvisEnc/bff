package bg.com.bo.bff.providers.implementations;

import bg.com.bo.bff.application.config.MiddlewareConfig;
import bg.com.bo.bff.application.dtos.response.GenericResponse;
import bg.com.bo.bff.application.dtos.response.ValidateAccountResponse;
import bg.com.bo.bff.application.exceptions.GenericException;
import bg.com.bo.bff.application.exceptions.HandledException;
import bg.com.bo.bff.commons.HttpDeleteWithBody;
import bg.com.bo.bff.commons.converters.DeleteThirdAccountErrorResponseConverter;
import bg.com.bo.bff.commons.converters.ErrorResponseConverter;
import bg.com.bo.bff.commons.converters.IErrorResponse;
import bg.com.bo.bff.commons.enums.*;
import bg.com.bo.bff.commons.enums.response.DeleteThirdAccountResponse;
import bg.com.bo.bff.commons.utils.Util;
import bg.com.bo.bff.models.ClientToken;
import bg.com.bo.bff.providers.dtos.request.AddThirdAccountBasicRequest;
import bg.com.bo.bff.providers.dtos.request.AddWalletAccountBasicRequest;
import bg.com.bo.bff.providers.dtos.request.DeleteThirdAccountMWRequest;
import bg.com.bo.bff.providers.dtos.response.ThirdAccountListMWResponse;
import bg.com.bo.bff.models.ThirdAccountListResponse;
import bg.com.bo.bff.application.exceptions.BadRequestException;
import bg.com.bo.bff.application.exceptions.NotAcceptableException;
import bg.com.bo.bff.application.exceptions.RequestException;
import bg.com.bo.bff.models.interfaces.IHttpClientFactory;
import bg.com.bo.bff.providers.dtos.response.accounts.AddAccountResponse;
import bg.com.bo.bff.providers.interfaces.ITokenMiddlewareProvider;
import bg.com.bo.bff.mappings.providers.account.ThirdAccountListMapper;
import bg.com.bo.bff.providers.interfaces.IThirdAccountProvider;
import bg.com.bo.bff.mappings.providers.account.ThirdAccountMWtMapper;
import bg.com.bo.bff.providers.models.middleware.HeadersMW;
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
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Map;
import java.util.Objects;

@Service
public class ThirdAccountMiddlewareProvider implements IThirdAccountProvider {
    @Value("${middleware}")
    private String url;
    @Value("${v1.third.account}")
    private String complementThirdAccounts;
    private final IHttpClientFactory httpClientFactory;
    private final ThirdAccountListMapper thirdAccountListMapper;
    private final ITokenMiddlewareProvider tokenMiddlewareProvider;
    private final MiddlewareConfig middlewareConfig;
    private final ThirdAccountMWtMapper mapper;
    private static final Logger logger = LogManager.getLogger(ThirdAccountMiddlewareProvider.class.getName());
    private static final String AUTH = "Authorization";
    private static final String MIDDLEWARE_CHANNEL = "middleware-channel";
    private static final String APPLICATION_ID = "application-id";
    private static final String PATH_ADD_THIRD_ACCOUNT = "/third-party-accounts";
    private static final String PATH_ADD_WALLET = "/wallets";

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
        return tokenMiddlewareProvider.generateAccountAccessToken(ProjectNameMW.THIRD_ACCOUNTS.getName(), middlewareConfig.getClientThirdAccount(), ProjectNameMW.ACH_ACCOUNTS.getHeaderKey());
    }

    public ThirdAccountListResponse getListThirdAccounts(String token, String personId, String company) throws IOException {
        boolean propagateException = false;
        try (CloseableHttpClient httpClient = createHttpClient()) {
            String channel = "2";
            String urlGetThirdAccounts = url + complementThirdAccounts + "/persons/" + company + "/companies/" + personId + "/devices/0/roles/5";
            HttpGet getThirdAccounts = new HttpGet(urlGetThirdAccounts);
            getThirdAccounts.setHeader(AUTH, "Bearer " + token);
            getThirdAccounts.setHeader(MIDDLEWARE_CHANNEL, channel);
            getThirdAccounts.setHeader(APPLICATION_ID, channel);
            getThirdAccounts.setHeader(HeadersMW.DEVICE_ID.getName(), "12s4d3f4s");
            getThirdAccounts.setHeader(HeadersMW.DEVICE_IP.getName(), "127.0.0.1");
            ObjectMapper objectMapper = new ObjectMapper();
            try (CloseableHttpResponse httpResponse = httpClient.execute(getThirdAccounts)) {
                int statusCode = httpResponse.getStatusLine().getStatusCode();
                String responseThirdAccounts = EntityUtils.toString(httpResponse.getEntity());
                if (statusCode == HttpStatus.SC_OK) {
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
    public GenericResponse addThirdAccount(String token, AddThirdAccountBasicRequest request, Map<String, String> parameters) throws IOException {

        String jsonMapper = Util.objectToString(request);
        return getGenericResponse(token, parameters, jsonMapper, PATH_ADD_THIRD_ACCOUNT);
    }

    @Override
    public GenericResponse addWalletAccount(String token, AddWalletAccountBasicRequest request, Map<String, String> parameters) throws IOException {

        String jsonMapper = Util.objectToString(request);
        return getGenericResponse(token, parameters, jsonMapper, PATH_ADD_WALLET);
    }

    @Override
    public GenericResponse deleteThirdAccount(String personId, long identifier, long accountId, String deviceId, String deviceIp) throws IOException {
        ClientToken clientToken = tokenMiddlewareProvider.generateAccountAccessToken(ProjectNameMW.THIRD_ACCOUNTS.getName(), middlewareConfig.getClientThirdAccount(), ProjectNameMW.THIRD_ACCOUNTS.getHeaderKey());
        DeleteThirdAccountMWRequest requestData = mapper.convert(personId, identifier, accountId);

        try (CloseableHttpClient httpClient = createHttpClient()) {

            String path = middlewareConfig.getUrlBase() + ProjectNameMW.THIRD_ACCOUNTS.getName() + "/bs/v1/third-party-accounts";
            HttpDeleteWithBody request = new HttpDeleteWithBody(path);

            ObjectMapper objectMapper = new ObjectMapper();
            String jsonMapper = objectMapper.writeValueAsString(requestData);
            StringEntity entity = new StringEntity(jsonMapper);
            request.setEntity(entity);
            request.setHeader(HeadersMW.CONTENT_TYPE.getName(), HeadersMW.APP_JSON.getName());
            request.setHeader(HeadersMW.AUT.getName(), "Bearer " + clientToken.getAccessToken());
            request.setHeader(HeadersMW.MW_CHA.getName(), CanalMW.GANAMOVIL.getCanal());
            request.setHeader(HeadersMW.APP_ID.getName(), CanalMW.GANAMOVIL.getCanal());
            request.setHeader(HeadersMW.DEVICE_ID.getName(), deviceId);
            request.setHeader(HeadersMW.DEVICE_IP.getName(), deviceIp);

            try (CloseableHttpResponse httpResponse = httpClient.execute(request)) {
                int statusCode = httpResponse.getStatusLine().getStatusCode();
                String jsonResponse = EntityUtils.toString(httpResponse.getEntity());
                if (statusCode == HttpStatus.SC_OK) return GenericResponse.instance(DeleteThirdAccountResponse.SUCCESS);
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

    @Override
    public GenericResponse deleteWalletAccount(String personId, long identifier, long accountNumber, String deviceId, String deviceIp) throws IOException {
        ClientToken clientToken = tokenMiddlewareProvider.generateAccountAccessToken(ProjectNameMW.THIRD_ACCOUNTS.getName(), middlewareConfig.getClientThirdAccount(), ProjectNameMW.THIRD_ACCOUNTS.getHeaderKey());
        IErrorResponse errorResponse = ErrorResponseConverter.GenericErrorResponse.DEFAULT;
        try (CloseableHttpClient httpClient = createHttpClient()) {
            String path = middlewareConfig.getUrlBase() + ProjectNameMW.THIRD_ACCOUNTS.getName() + "/bs/v1/wallets/" + identifier + "/persons/" + personId + "/wallet-accounts/" + accountNumber;
            HttpDeleteWithBody request = new HttpDeleteWithBody(path);
            request.setHeader(HeadersMW.CONTENT_TYPE.getName(), HeadersMW.APP_JSON.getName());
            request.setHeader(HeadersMW.AUT.getName(), "Bearer " + clientToken.getAccessToken());
            request.setHeader(HeadersMW.MW_CHA.getName(), CanalMW.GANAMOVIL.getCanal());
            request.setHeader(HeadersMW.APP_ID.getName(), CanalMW.GANAMOVIL.getCanal());
            request.setHeader(HeadersMW.DEVICE_ID.getName(), deviceId);
            request.setHeader(HeadersMW.DEVICE_IP.getName(), deviceIp);
            try (CloseableHttpResponse httpResponse = httpClient.execute(request)) {
                int statusCode = httpResponse.getStatusLine().getStatusCode();
                String jsonResponse = EntityUtils.toString(httpResponse.getEntity());
                if (statusCode == HttpStatus.SC_OK)
                    return GenericResponse.instance(DeleteThirdAccountResponse.SUCCESS);
                else {
                    logger.error(jsonResponse);
                    errorResponse = DeleteThirdAccountErrorResponseConverter.INSTANCE.convert(jsonResponse);
                    throw new HandledException(errorResponse);
                }
            }
        } catch (Exception e) {
            logger.error(e);
            throw new HandledException(errorResponse, e);
        }
    }

    private GenericResponse getGenericResponse(String token, Map<String, String> parameters, String jsonMapper, String pathUrl) throws UnsupportedEncodingException {
        StringEntity entity = new StringEntity(jsonMapper);
        try (CloseableHttpClient httpClient = createHttpClient()) {
            String urlGetThirdAccounts = url + complementThirdAccounts + pathUrl;
            HttpPost httpRequest = getHttpPost(token, parameters, urlGetThirdAccounts, entity);
            try (CloseableHttpResponse httpResponse = httpClient.execute(httpRequest)) {

                int statusCode = httpResponse.getStatusLine().getStatusCode();
                String jsonResponse = EntityUtils.toString(httpResponse.getEntity());

                if (statusCode == HttpStatus.SC_OK) {
                    return GenericResponse.instance(AddAccountResponse.SUCCESS);
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
        httpRequest.setHeader(HeadersMW.CONTENT_TYPE.getName(), HeadersMW.APP_JSON.getName());
        httpRequest.setEntity(entity);
        return httpRequest;
    }

    @Override
    public ThirdAccountListResponse getThirdAccounts(Integer personId, String token, Map<String, String> parameters) throws IOException {
        String path = middlewareConfig.getUrlBase() + ProjectNameMW.THIRD_ACCOUNTS.getName() + "/bs/v1/persons/" + personId + "/companies/" + personId + "/devices/" + PersonRol.PERSONA.getId() + "/roles/" + PersonRol.PERSONA.getId();
        return getThirdAndWalletAccounts(path, token, parameters);
    }

    @Override
    public ThirdAccountListResponse getWalletAccounts(Integer personId, String token, Map<String, String> parameters) {
        String path = middlewareConfig.getUrlBase() + ProjectNameMW.THIRD_ACCOUNTS.getName() + "/bs/v1/wallets/companies/" + personId + "/persons/" + personId;
        return getThirdAndWalletAccounts(path, token, parameters);
    }

    @Override
    public ValidateAccountResponse validateAccount(String accountNumber, String clientName, Map<String,String> parameters ) throws IOException {
        ClientToken clientToken = tokenMiddlewareProvider.generateAccountAccessToken(ProjectNameMW.THIRD_ACCOUNTS.getName(), middlewareConfig.getClientThirdAccount(), ProjectNameMW.THIRD_ACCOUNTS.getHeaderKey());

        String urlValidateAccount = url + complementThirdAccounts + "/"+accountNumber+"/party-accounts/" +clientName ;


        HttpGet httpRequest = new HttpGet(urlValidateAccount);
        httpRequest.setHeader(HeadersMW.AUT.getName(), "Bearer " + clientToken.getAccessToken());
        httpRequest.setHeader(HeadersMW.MW_CHA.getName(), CanalMW.GANAMOVIL.getCanal());
        httpRequest.setHeader(HeadersMW.APP_ID.getName(), ApplicationId.GANAMOVIL.getCode());
        httpRequest.setHeader(DeviceMW.DEVICE_ID.getCode(), parameters.get(DeviceMW.DEVICE_ID.getCode()));
        httpRequest.setHeader(DeviceMW.DEVICE_IP.getCode(), parameters.get(DeviceMW.DEVICE_IP.getCode()));
        httpRequest.setHeader(DeviceMW.DEVICE_NAME.getCode(), parameters.get(DeviceMW.DEVICE_NAME.getCode()));
        httpRequest.setHeader(DeviceMW.GEO_POSITION_X.getCode(), parameters.get(DeviceMW.GEO_POSITION_X.getCode()));
        httpRequest.setHeader(DeviceMW.GEO_POSITION_Y.getCode(), parameters.get(DeviceMW.GEO_POSITION_Y.getCode()));
        httpRequest.setHeader(DeviceMW.APP_VERSION.getCode(), parameters.get(DeviceMW.APP_VERSION.getCode()));
        httpRequest.setHeader(HeadersMW.CONTENT_TYPE.getName(), HeadersMW.APP_JSON.getName());
        try (CloseableHttpClient httpClient = createHttpClient()) {
            try (CloseableHttpResponse httpResponse = httpClient.execute(httpRequest)) {
                int statusCode = httpResponse.getStatusLine().getStatusCode();
                String jsonResponse = EntityUtils.toString(httpResponse.getEntity());
                if (statusCode == HttpStatus.SC_OK) {
                    return Util.stringToObject(jsonResponse, ValidateAccountResponse.class);
                }
                AppError error = Util.mapProviderError(jsonResponse);
                logger.error(jsonResponse);
                throw new GenericException(error.getMessage(), error.getHttpCode(), error.getCode());
            }
        } catch (GenericException e) {
            throw e;
        } catch (Exception e) {
            logger.error(e);
            throw new HandledException(ErrorResponseConverter.GenericErrorResponse.DEFAULT, e);
        }
    }

    private ThirdAccountListResponse getThirdAndWalletAccounts(String path, String token, Map<String, String> parameters) {
        try (CloseableHttpClient httpClient = createHttpClient()) {
            HttpGet httpRequest = httpGet(path, token, parameters);
            try (CloseableHttpResponse httpResponse = httpClient.execute(httpRequest)) {
                int statusCode = httpResponse.getStatusLine().getStatusCode();
                String jsonResponse = EntityUtils.toString(httpResponse.getEntity());
                if (statusCode == HttpStatus.SC_OK) {
                    return Util.stringToObject(jsonResponse, ThirdAccountListResponse.class);
                }
                AppError error = Util.mapProviderError(jsonResponse);
                String empty = error.getCodeMiddleware();
                if (Objects.equals(AppError.MDWRACTM_002.getCodeMiddleware(), empty)) {
                    return new ThirdAccountListResponse(new ArrayList<>());
                }
                logger.error(jsonResponse);
                throw new GenericException(error.getMessage(), error.getHttpCode(), error.getCode());
            }
        } catch (GenericException e) {
            throw e;
        } catch (Exception e) {
            logger.error(e);
            throw new HandledException(ErrorResponseConverter.GenericErrorResponse.DEFAULT, e);
        }
    }

    private HttpGet httpGet(String url, String token, Map<String, String> parameters) {
        HttpGet httpRequest = new HttpGet(url);
        httpRequest.setHeader(HeadersMW.AUT.getName(), "Bearer " + token);
        httpRequest.setHeader(HeadersMW.MW_CHA.getName(), CanalMW.GANAMOVIL.getCanal());
        httpRequest.setHeader(HeadersMW.APP_ID.getName(), ApplicationId.GANAMOVIL.getCode());
        httpRequest.setHeader(DeviceMW.DEVICE_ID.getCode(), parameters.get(DeviceMW.DEVICE_ID.getCode()));
        httpRequest.setHeader(DeviceMW.DEVICE_IP.getCode(), parameters.get(DeviceMW.DEVICE_IP.getCode()));
        httpRequest.setHeader(DeviceMW.DEVICE_NAME.getCode(), parameters.get(DeviceMW.DEVICE_NAME.getCode()));
        httpRequest.setHeader(DeviceMW.GEO_POSITION_X.getCode(), parameters.get(DeviceMW.GEO_POSITION_X.getCode()));
        httpRequest.setHeader(DeviceMW.GEO_POSITION_Y.getCode(), parameters.get(DeviceMW.GEO_POSITION_Y.getCode()));
        httpRequest.setHeader(DeviceMW.APP_VERSION.getCode(), parameters.get(DeviceMW.APP_VERSION.getCode()));
        httpRequest.setHeader(HeadersMW.CONTENT_TYPE.getName(), HeadersMW.APP_JSON.getName());
        return httpRequest;
    }
}
