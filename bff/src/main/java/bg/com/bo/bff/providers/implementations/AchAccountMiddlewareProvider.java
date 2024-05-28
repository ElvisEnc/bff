package bg.com.bo.bff.providers.implementations;

import bg.com.bo.bff.application.config.MiddlewareConfig;
import bg.com.bo.bff.application.dtos.request.qr.QrListRequest;
import bg.com.bo.bff.application.dtos.response.GenericResponse;
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
import bg.com.bo.bff.models.dtos.BanksMWResponse;
import bg.com.bo.bff.models.interfaces.IHttpClientFactory;
import bg.com.bo.bff.providers.dtos.requests.AddAchAccountBasicRequest;
import bg.com.bo.bff.providers.dtos.requests.DeleteAchAccountMWRequest;
import bg.com.bo.bff.providers.dtos.requests.QrListMWRequest;
import bg.com.bo.bff.providers.dtos.responses.BranchOfficeMWResponse;
import bg.com.bo.bff.providers.dtos.responses.account.ach.AchAccountMWResponse;
import bg.com.bo.bff.providers.dtos.responses.accounts.AddAccountResponse;
import bg.com.bo.bff.providers.dtos.responses.qr.QrListMWResponse;
import bg.com.bo.bff.providers.interfaces.IAchAccountProvider;
import bg.com.bo.bff.providers.interfaces.ITokenMiddlewareProvider;
import bg.com.bo.bff.providers.mappings.ach.account.AchAccountMWtMapper;
import bg.com.bo.bff.providers.models.middleware.HeadersMW;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.Objects;

@Service
public class AchAccountMiddlewareProvider implements IAchAccountProvider {
    @Value("${middleware}")
    private String url;
    @Value("${ach.account.auth.token}")
    private String complementToken;
    @Value("${v1.ach.account}")
    private String complementAchAccounts;
    @Value("${client.secret.ach-accounts}")
    private String clientSecret;

    ITokenMiddlewareProvider tokenMiddlewareProvider;
    private MiddlewareConfig middlewareConfig;
    private IHttpClientFactory httpClientFactory;
    private final AchAccountMWtMapper mapper;
    private static final Logger LOGGER = LogManager.getLogger(AchAccountMiddlewareProvider.class.getName());
    private static final String AUTH = "Authorization";
    private static final String MIDDLEWARE_CHANNEL = "middleware-channel";
    private static final String APPLICATION_ID = "application-id";

    public AchAccountMiddlewareProvider(ITokenMiddlewareProvider tokenMiddlewareProvider, MiddlewareConfig middlewareConfig, IHttpClientFactory httpClientFactory, AchAccountMWtMapper mapper) {
        this.tokenMiddlewareProvider = tokenMiddlewareProvider;
        this.middlewareConfig = middlewareConfig;
        this.httpClientFactory = httpClientFactory;
        this.mapper = mapper;
    }

    private CloseableHttpClient createHttpClient() {
        return httpClientFactory.create();
    }

    @Override
    public ClientToken generateAccessToken() throws IOException {
        return tokenMiddlewareProvider.generateAccountAccessToken(ProjectNameMW.ACH_ACCOUNTS.getName(), middlewareConfig.getClientAchAccount(), ProjectNameMW.ACH_ACCOUNTS.getHeaderKey());
    }

    @Override
    public GenericResponse addAchAccount(String token, AddAchAccountBasicRequest request, Map<String, String> parameters) throws IOException {

        String jsonMapper = Util.objectToString(request);
        StringEntity entity = new StringEntity(jsonMapper);
        try (CloseableHttpClient httpClient = createHttpClient()) {
            String urlGetThirdAccounts = url + complementAchAccounts + "/ach";
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

    @Override
    public GenericResponse deleteAchAccount(String personId, long identifier, String deviceId, String deviceIp) throws IOException {
        ClientToken clientToken = tokenMiddlewareProvider.generateAccountAccessToken(ProjectNameMW.ACH_ACCOUNTS.getName(), middlewareConfig.getClientAchAccount(), ProjectNameMW.ACH_ACCOUNTS.getHeaderKey());
        DeleteAchAccountMWRequest requestData = mapper.convert(personId, identifier);

        try (CloseableHttpClient httpClient = createHttpClient()) {

            String path = middlewareConfig.getUrlBase() + ProjectNameMW.ACH_ACCOUNTS.getName() + "/bs/v1/ach/delete";
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
                if (statusCode == HttpStatus.SC_OK)
                    return GenericResponse.instance(DeleteThirdAccountResponse.SUCCESS);
                else {
                    LOGGER.error(jsonResponse);
                    IErrorResponse errorResponse = DeleteThirdAccountErrorResponseConverter.INSTANCE.convert(jsonResponse);
                    throw new HandledException(errorResponse);
                }
            }
        } catch (HandledException e) {
            throw e;
        } catch (Exception e) {
            LOGGER.error(e);
            throw new HandledException(ErrorResponseConverter.GenericErrorResponse.DEFAULT, e);
        }
    }

    @Override
    public BanksMWResponse getBanks() throws IOException {
        ClientToken clientToken = tokenMiddlewareProvider.generateAccountAccessToken(ProjectNameMW.ACH_ACCOUNTS.getName(), middlewareConfig.getClientAchAccount(), ProjectNameMW.ACH_ACCOUNTS.getHeaderKey());
        try (CloseableHttpClient httpClient = createHttpClient()) {
            String urlGetThirdAccounts = middlewareConfig.getUrlBase() + ProjectNameMW.ACH_ACCOUNTS.getName() + "/bs/v1/ach/others-bank-list";
            HttpGet httpRequest = new HttpGet(urlGetThirdAccounts);
            httpRequest.setHeader(AUTH, "Bearer " + clientToken.getAccessToken());
            httpRequest.setHeader(MIDDLEWARE_CHANNEL, CanalMW.GANAMOVIL.getCanal());
            httpRequest.setHeader(APPLICATION_ID, ApplicationId.GANAMOVIL.getCode());
            ObjectMapper objectMapper = new ObjectMapper();

            try (CloseableHttpResponse httpResponse = httpClient.execute(httpRequest)) {
                int statusCode = httpResponse.getStatusLine().getStatusCode();
                String jsonResponse = EntityUtils.toString(httpResponse.getEntity());

                if (statusCode == HttpStatus.SC_OK) {
                    return objectMapper.readValue(jsonResponse, BanksMWResponse.class);
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
        httpRequest.setHeader(HeadersMW.CONTENT_TYPE.getName(), HeadersMW.APP_JSON.getName());
        httpRequest.setEntity(entity);
        return httpRequest;
    }

    @Override
    public BranchOfficeMWResponse getAllBranchOfficeBank(Integer code) throws IOException {
        String token = generateAccessToken().getAccessToken();
        try (CloseableHttpClient httpClient = createHttpClient()) {
            String path = middlewareConfig.getUrlBase() + ProjectNameMW.ACH_ACCOUNTS.getName() + "/bs/v1/ach/branch-offices/companies/" + code;
            HttpGet request = new HttpGet(path);
            request.setHeader(HeadersMW.CONTENT_TYPE.getName(), HeadersMW.APP_JSON.getName());
            request.setHeader(HeadersMW.AUT.getName(), "Bearer " + token);
            request.setHeader(HeadersMW.MW_CHA.getName(), CanalMW.GANAMOVIL.getCanal());
            request.setHeader(HeadersMW.APP_ID.getName(), CanalMW.GANAMOVIL.getCanal());

            try (CloseableHttpResponse httpResponse = httpClient.execute(request)) {
                int statusCode = httpResponse.getStatusLine().getStatusCode();
                String jsonResponse = EntityUtils.toString(httpResponse.getEntity());
                if (statusCode == HttpStatus.SC_OK) {
                    return Util.stringToObject(jsonResponse, BranchOfficeMWResponse.class);
                } else {
                    AppError error = Util.mapProviderError(jsonResponse);
                    String empty = error.getCodeMiddleware();
                    if (Objects.equals(AppError.MDWAAM_001.getCodeMiddleware(), empty)) {
                        return BranchOfficeMWResponse.builder()
                                .data(BranchOfficeMWResponse.BranchOfficeMWData.builder()
                                        .response(new ArrayList<>())
                                        .build())
                                .build();
                    }
                    LOGGER.error(jsonResponse);
                    throw new GenericException(error.getMessage(), error.getHttpCode(), error.getCode());
                }
            }
        } catch (GenericException ex) {
            LOGGER.error(ex);
            throw ex;
        } catch (Exception e) {
            LOGGER.error(e);
            throw new GenericException(AppError.DEFAULT.getMessage(), AppError.DEFAULT.getHttpCode(), AppError.DEFAULT.getCode());
        }
    }

    @Override
    public AchAccountMWResponse getAchAccounts(Integer personId, Map<String, String> parameters) throws IOException {
        String token = generateAccessToken().getAccessToken();
        try (CloseableHttpClient httpClient = createHttpClient()) {
            String path = middlewareConfig.getUrlBase() + ProjectNameMW.ACH_ACCOUNTS.getName() + "/bs/v1/ach/ach-accounts/persons/" + personId + "/companies/" + personId;
            HttpGet request = httpGet(path, token, parameters);
            try (CloseableHttpResponse httpResponse = httpClient.execute(request)) {
                int statusCode = httpResponse.getStatusLine().getStatusCode();
                String jsonResponse = EntityUtils.toString(httpResponse.getEntity());
                if (statusCode == HttpStatus.SC_OK) {
                    return Util.stringToObject(jsonResponse, AchAccountMWResponse.class);
                } else {
                    AppError error = Util.mapProviderError(jsonResponse);
                    String empty = error.getCodeMiddleware();
                    if (Objects.equals(AppError.MDWAAM_004.getCodeMiddleware(), empty) || Objects.equals(AppError.MDWAAM_010.getCodeMiddleware(), empty)) {
                        return AchAccountMWResponse.builder()
                                .data(new ArrayList<>())
                                .build();
                    }
                    LOGGER.error(jsonResponse);
                    throw new GenericException(error.getMessage(), error.getHttpCode(), error.getCode());
                }
            }
        } catch (GenericException ex) {
            LOGGER.error(ex);
            throw ex;
        } catch (Exception e) {
            LOGGER.error(e);
            throw new GenericException(AppError.DEFAULT.getMessage(), AppError.DEFAULT.getHttpCode(), AppError.DEFAULT.getCode());
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
        return httpRequest;
    }

    @Override
    public QrListMWResponse getListQrGeneratePaidMW(QrListRequest request, Integer personId, Map<String, String> parameters) throws IOException {
        String token = generateAccessToken().getAccessToken();
        QrListMWRequest qrListMWRequest = QrListMWRequest.builder()
                .personId(String.valueOf(personId))
                .startDate(request.getFilters().getPeriod().getStart())
                .endDate(request.getFilters().getPeriod().getEnd())
                .build();
        String jsonRequest = Util.objectToString(qrListMWRequest);
        StringEntity entity = new StringEntity(jsonRequest);
        try (CloseableHttpClient httpClient = createHttpClient()) {
            String path = middlewareConfig.getUrlBase() + ProjectNameMW.ACH_ACCOUNTS.getName() + "/bs/v1/ach/transactions-qr";
            HttpPost httpRequest = getHttpPost(token, parameters, path, entity);
            try (CloseableHttpResponse httpResponse = httpClient.execute(httpRequest)) {
                int statusCode = httpResponse.getStatusLine().getStatusCode();
                String jsonResponse = EntityUtils.toString(httpResponse.getEntity());
                if (statusCode == HttpStatus.SC_OK) {
                    return Util.stringToObject(jsonResponse, QrListMWResponse.class);
                } else {
                    AppError error = Util.mapProviderError(jsonResponse);
                    String empty = error.getCodeMiddleware();
                    if (Objects.equals(AppError.MDWAAM_001.getCodeMiddleware(), empty)) {
                        return QrListMWResponse.builder()
                                .data(new ArrayList<>())
                                .build();
                    }
                    LOGGER.error(jsonResponse);
                    throw new GenericException(error.getMessage(), error.getHttpCode(), error.getCode());
                }
            }
        } catch (GenericException ex) {
            LOGGER.error(ex);
            throw ex;
        } catch (Exception e) {
            LOGGER.error(e);
            throw new GenericException(AppError.DEFAULT.getMessage(), AppError.DEFAULT.getHttpCode(), AppError.DEFAULT.getCode());
        }
    }
}
