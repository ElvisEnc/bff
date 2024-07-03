package bg.com.bo.bff.providers.implementations;

import bg.com.bo.bff.application.config.MiddlewareConfig;
import bg.com.bo.bff.application.dtos.request.user.ChangePasswordRequest;
import bg.com.bo.bff.application.dtos.request.login.LoginRequest;
import bg.com.bo.bff.application.dtos.request.user.UpdateBiometricsRequest;
import bg.com.bo.bff.application.dtos.response.generic.GenericResponse;
import bg.com.bo.bff.application.dtos.response.user.UpdateBiometricsResponse;
import bg.com.bo.bff.application.dtos.response.user.ContactResponse;
import bg.com.bo.bff.application.exceptions.GenericException;
import bg.com.bo.bff.application.exceptions.HandledException;
import bg.com.bo.bff.commons.converters.ChangePasswordErrorResponseConverter;
import bg.com.bo.bff.commons.converters.ErrorResponseConverter;
import bg.com.bo.bff.commons.enums.*;
import bg.com.bo.bff.commons.converters.IErrorResponse;
import bg.com.bo.bff.commons.enums.AppError;
import bg.com.bo.bff.commons.enums.CanalMW;
import bg.com.bo.bff.commons.enums.DeviceMW;
import bg.com.bo.bff.providers.dtos.request.login.mw.*;
import bg.com.bo.bff.providers.dtos.response.login.mw.*;
import bg.com.bo.bff.providers.models.middleware.HeadersMW;
import bg.com.bo.bff.commons.enums.ProjectNameMW;
import bg.com.bo.bff.commons.enums.response.user.UserControllerResponse;
import bg.com.bo.bff.commons.utils.Util;
import bg.com.bo.bff.models.ClientToken;
import bg.com.bo.bff.providers.dtos.response.personal.information.UserContactResponse;
import bg.com.bo.bff.application.dtos.response.login.LoginValidationServiceResponse;
import bg.com.bo.bff.commons.interfaces.IHttpClientFactory;
import bg.com.bo.bff.providers.dtos.response.generic.ApiDataResponse;
import bg.com.bo.bff.providers.interfaces.ITokenMiddlewareProvider;
import bg.com.bo.bff.providers.interfaces.ILoginMiddlewareProvider;
import bg.com.bo.bff.mappings.providers.login.ILoginMapper;
import bg.com.bo.bff.mappings.providers.login.LoginMWMapper;
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
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Objects;

@Service
public class LoginMiddlewareProvider implements ILoginMiddlewareProvider {
    ITokenMiddlewareProvider tokenMiddlewareProvider;
    private final MiddlewareConfig middlewareConfig;
    private final IHttpClientFactory httpClientFactory;
    private final LoginMWMapper loginMWMapper;
    private final ILoginMapper mapper;

    private static final Logger logger = LogManager.getLogger(LoginMiddlewareProvider.class.getName());

    public LoginMiddlewareProvider(ITokenMiddlewareProvider tokenMiddlewareProvider, MiddlewareConfig middlewareConfig, IHttpClientFactory httpClientFactory, LoginMWMapper loginMWMapper, ILoginMapper mapper) {
        this.tokenMiddlewareProvider = tokenMiddlewareProvider;
        this.middlewareConfig = middlewareConfig;
        this.httpClientFactory = httpClientFactory;
        this.loginMWMapper = loginMWMapper;
        this.mapper = mapper;
    }


    private CloseableHttpClient createHttpClient() {
        return httpClientFactory.create();
    }

    private ClientToken tokenLogin() throws IOException {
        return tokenMiddlewareProvider.generateAccountAccessToken(ProjectNameMW.LOGIN_MANAGER.getName(), middlewareConfig.getClientLogin(), ProjectNameMW.LOGIN_MANAGER.getHeaderKey());
    }

    @Override
    public LoginFactorMWResponse validateFactorUser(LoginRequest loginRequest, Map<String, String> parameters) throws IOException {
        String token = tokenLogin().getAccessToken();
        try (CloseableHttpClient httpClient = createHttpClient()) {
            LoginFactorMWRequest loginMWRequest = LoginFactorMWRequest.builder()
                    .codeTypeAuthentication(loginRequest.getType())
                    .factor(loginRequest.getUser())
                    .build();

            String path = middlewareConfig.getUrlBase() + ProjectNameMW.LOGIN_MANAGER.getName() + "/bs/v1/authentication/validate";
            String jsonMapper = Util.objectToString(loginMWRequest);
            StringEntity entity = new StringEntity(jsonMapper);
            HttpPost request = getHttpPost(path, token, parameters, entity);
            request.setHeader(DeviceMW.JSON_DATA.getCode(), parameters.get(DeviceMW.JSON_DATA.getCode()));
            try (CloseableHttpResponse httpResponse = httpClient.execute(request)) {
                int statusCode = httpResponse.getStatusLine().getStatusCode();
                String jsonResponse = EntityUtils.toString(httpResponse.getEntity());
                if (statusCode == HttpStatus.SC_OK) {
                    return Util.stringToObject(jsonResponse, LoginFactorMWResponse.class);
                } else {
                    logger.error(jsonResponse);
                    AppError error = Util.mapProviderError(jsonResponse);
                    throw new GenericException(error.getMessage(), error.getHttpCode(), error.getCode());
                }
            }
        } catch (GenericException ex) {
            logger.error(ex);
            throw ex;
        } catch (Exception e) {
            logger.error(e);
            throw new GenericException(AppError.DEFAULT.getMessage(), AppError.DEFAULT.getHttpCode(), AppError.DEFAULT.getCode());
        }
    }

    @Override
    public LoginValidationServiceResponse validateCredentials(LoginRequest loginRequest, LoginFactorData data, Map<String, String> parameters) throws IOException {
        String token = tokenLogin().getAccessToken();
        LoginCredentialMWRequest loginMWRequest = mapper.mapperRequest(loginRequest, data);
        try (CloseableHttpClient httpClient = createHttpClient()) {
            String path = middlewareConfig.getUrlBase() + ProjectNameMW.LOGIN_MANAGER.getName() + "/bs/v1/users/validate-credentials";
            String jsonMapper = Util.objectToString(loginMWRequest);
            StringEntity entity = new StringEntity(jsonMapper);
            HttpPost request = getHttpPost(path, token, parameters, entity);
            request.setHeader(DeviceMW.JSON_DATA.getCode(), parameters.get(DeviceMW.JSON_DATA.getCode()));
            try (CloseableHttpResponse httpResponse = httpClient.execute(request)) {
                int statusCode = httpResponse.getStatusLine().getStatusCode();
                String jsonResponse = EntityUtils.toString(httpResponse.getEntity());
                if (statusCode == HttpStatus.SC_OK) {
                    LoginCredentialMWResponse mwResponse = Util.stringToObject(jsonResponse, LoginCredentialMWResponse.class);
                    return mapper.converResponse(data, mwResponse);
                } else {
                    logger.error(jsonResponse);
                    AppError error = Util.mapProviderError(jsonResponse);
                    throw new GenericException(error.getMessage(), error.getHttpCode(), error.getCode());
                }
            }
        } catch (GenericException ex) {
            logger.error(ex);
            throw ex;
        } catch (Exception e) {
            logger.error(e);
            throw new GenericException(AppError.DEFAULT.getMessage(), AppError.DEFAULT.getHttpCode(), AppError.DEFAULT.getCode());
        }
    }

    private HttpPost getHttpPost(String url, String token, Map<String, String> parameters, StringEntity entity) {
        HttpPost httpRequest = new HttpPost(url);
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
        httpRequest.setEntity(entity);
        return httpRequest;
    }

    @Override
    public GenericResponse logout(String deviceId, String deviceIp, String deviceName, String geoPositionX, String geoPositionY, String appVersion, LogoutMWRequest logoutMWRequest) throws IOException {
        String token = tokenLogin().getAccessToken();
        try (CloseableHttpClient httpClient = createHttpClient()) {
            String path = middlewareConfig.getUrlBase() + ProjectNameMW.LOGIN_MANAGER.getName() + "/bs/v1/users/log-logout-securely";
            HttpPost request = new HttpPost(path);
            ObjectMapper objectMapper = new ObjectMapper();
            String jsonMapper = objectMapper.writeValueAsString(logoutMWRequest);
            StringEntity entity = new StringEntity(jsonMapper);
            request.setEntity(entity);
            request.setHeader(HeadersMW.CONTENT_TYPE.getName(), HeadersMW.APP_JSON.getName());
            request.setHeader(HeadersMW.AUT.getName(), "Bearer " + token);
            request.setHeader(HeadersMW.MW_CHA.getName(), CanalMW.GANAMOVIL.getCanal());
            request.setHeader(HeadersMW.APP_ID.getName(), CanalMW.GANAMOVIL.getCanal());

            request.setHeader("device-id", deviceId);
            request.setHeader("device-ip", deviceIp);
            request.setHeader("device-name", deviceName);
            request.setHeader("geo-position-x", geoPositionX);
            request.setHeader("geo-position-y", geoPositionY);
            request.setHeader("app-version", appVersion);

            try (CloseableHttpResponse httpResponse = httpClient.execute(request)) {
                int statusCode = httpResponse.getStatusLine().getStatusCode();
                String jsonResponse = EntityUtils.toString(httpResponse.getEntity());
                if (statusCode == HttpStatus.SC_OK) {
                    return GenericResponse.builder()
                            .code("SUCCESS")
                            .message("Se cerró sesión satisfactoriamente")
                            .build();
                } else {
                    logger.error(jsonResponse);
                    AppError error = Util.mapProviderError(jsonResponse);
                    throw new GenericException(error.getMessage(), error.getHttpCode(), error.getCode());
                }
            }
        } catch (GenericException ex) {
            logger.error(ex);
            throw ex;
        } catch (Exception e) {
            logger.error(e);
            throw new HandledException(ErrorResponseConverter.GenericErrorResponse.DEFAULT, e);
        }
    }

    @Override
    public GenericResponse changePassword(String personId, String personRoleId, ChangePasswordRequest changePasswordRequest, Map<String, String> parameters) throws IOException {
        MWOwnerAccountRequest owner = loginMWMapper.convert(personId, parameters.get(DeviceMW.DEVICE_ID.getCode()), personRoleId);
        ChangePasswordMWRequest changePasswordMWRequest = loginMWMapper.convert(changePasswordRequest, owner);

        ClientToken clientToken = tokenLogin();

        try (CloseableHttpClient httpClient = createHttpClient()) {
            String path = middlewareConfig.getUrlBase() + ProjectNameMW.LOGIN_MANAGER.getName() + "/bs/v1/password/change";
            HttpPut request = new HttpPut(path);

            ObjectMapper objectMapper = new ObjectMapper();
            String jsonMapper = objectMapper.writeValueAsString(changePasswordMWRequest);
            StringEntity entity = new StringEntity(jsonMapper);
            request.setEntity(entity);
            request.setHeaders(HeadersMW.getHeadersMW(parameters));
            request.addHeader(HeadersMW.AUT.getName(), "Bearer " + clientToken.getAccessToken());

            try (CloseableHttpResponse httpResponse = httpClient.execute(request)) {
                int statusCode = httpResponse.getStatusLine().getStatusCode();
                String jsonResponse = EntityUtils.toString(httpResponse.getEntity());
                if (statusCode == HttpStatus.SC_OK) return GenericResponse.instance(UserControllerResponse.SUCCESS);
                else {
                    logger.error(jsonResponse);
                    IErrorResponse errorResponse = ChangePasswordErrorResponseConverter.INSTANCE.convert(jsonResponse);
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
    public ContactResponse getContactInfo() {
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonContact = null;
        try (InputStream file = getClass().getResourceAsStream("/files/ContactResponse.json")) {
            assert file != null;
            jsonContact = new String(file.readAllBytes());
            UserContactResponse userContact = objectMapper.readValue(jsonContact, UserContactResponse.class);
            String jsonResponse = Util.objectToString(userContact.getCompanyContactDetail());
            return Util.stringToObject(jsonResponse, ContactResponse.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public DeviceEnrollmentMWResponse makeValidateDevice(Map<String, String> parameter) throws IOException {
        String token = tokenLogin().getAccessToken();
        try (CloseableHttpClient httpClient = createHttpClient()) {

            String path = middlewareConfig.getUrlBase() + ProjectNameMW.LOGIN_MANAGER.getName() + "/bs/v1/device/validate-enrollment?deviceId=" + parameter.get(DeviceMW.DEVICE_ID.getCode());
            HttpGet request = new HttpGet(path);
            request.setHeaders(HeadersMW.getHeadersMW(parameter));
            request.addHeader("Authorization", "Bearer " + token);


            CloseableHttpResponse httpResponse = httpClient.execute(request);
            int statusCode = httpResponse.getStatusLine().getStatusCode();
            String jsonResponse = EntityUtils.toString(httpResponse.getEntity());
            if (statusCode == HttpStatus.SC_OK) {
                ApiDataResponse<DeviceEnrollmentMWResponse> response = Util.stringToObject(jsonResponse, ApiDataResponse.class);
                String text = Util.objectToString(response.getData());
                return Util.stringToObject(text, DeviceEnrollmentMWResponse.class);
            } else {
                logger.error(jsonResponse);
                AppError error = Util.mapProviderError(jsonResponse);
                String notEnrolled = error.getCodeMiddleware();
                if (Objects.equals(notEnrolled, AppError.MDWRLIB_0003.getCodeMiddleware())) {
                    DeviceEnrollmentMWResponse notEnrolledReponse = new DeviceEnrollmentMWResponse();
                    notEnrolledReponse.setStatusCode("NOT_ENROLLED");
                    return notEnrolledReponse;
                }
                throw new GenericException(error.getMessage(), error.getHttpCode(), error.getCode());
            }
        } catch (GenericException ex) {
            logger.error(ex);
            throw ex;
        } catch (Exception e) {
            logger.error(e);
            throw new GenericException(AppError.DEFAULT.getMessage(), AppError.DEFAULT.getHttpCode(), AppError.DEFAULT.getCode());
        }
    }

    @Override
    public BiometricStatusMWResponse getBiometricsMW(Integer personId, Map<String, String> parameter) throws IOException {
        String token = tokenLogin().getAccessToken();
        try (CloseableHttpClient httpClient = createHttpClient()) {
            String path = middlewareConfig.getUrlBase() + ProjectNameMW.LOGIN_MANAGER.getName() + "/bs/v1/users/biometric/" + personId;
            HttpGet request = httpGet(path, token, parameter);
            try (CloseableHttpResponse httpResponse = httpClient.execute(request)) {
                int statusCode = httpResponse.getStatusLine().getStatusCode();
                String jsonResponse = EntityUtils.toString(httpResponse.getEntity());
                if (statusCode == HttpStatus.SC_OK) {
                    return Util.stringToObject(jsonResponse, BiometricStatusMWResponse.class);
                } else {
                    AppError error = Util.mapProviderError(jsonResponse);
                    logger.error(jsonResponse);
                    throw new GenericException(error.getMessage(), error.getHttpCode(), error.getCode());
                }
            }
        } catch (GenericException ex) {
            logger.error(ex);
            throw ex;
        } catch (Exception e) {
            logger.error(e);
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
    public UpdateBiometricsResponse updateBiometricsMW(Integer personId, UpdateBiometricsRequest request, Map<String, String> parameter) throws IOException {
        String token = tokenLogin().getAccessToken();
        UpdateBiometricsMWRequest mwRequest = mapper.mapperUpdateBiometricRequest(request);
        try (CloseableHttpClient httpClient = createHttpClient()) {
            String path = middlewareConfig.getUrlBase() + ProjectNameMW.LOGIN_MANAGER.getName() + "/bs/v1/users/biometric/" + personId;
            String jsonMapper = Util.objectToString(mwRequest);
            StringEntity entity = new StringEntity(jsonMapper);
            HttpPost httpRequest = getHttpPost(path, token, parameter, entity);
            httpRequest.setHeader(DeviceMW.JSON_DATA.getCode(), parameter.get(DeviceMW.JSON_DATA.getCode()));
            try (CloseableHttpResponse httpResponse = httpClient.execute(httpRequest)) {
                int statusCode = httpResponse.getStatusLine().getStatusCode();
                String jsonResponse = EntityUtils.toString(httpResponse.getEntity());
                if (statusCode == HttpStatus.SC_OK) {
                    UpdateBiometricMWResponse mwResponse = Util.stringToObject(jsonResponse, UpdateBiometricMWResponse.class);
                    return UpdateBiometricsResponse.builder()
                            .personId(mwResponse.getData().getPersonId())
                            .build();
                } else {
                    logger.error(jsonResponse);
                    AppError error = Util.mapProviderError(jsonResponse);
                    throw new GenericException(error.getMessage(), error.getHttpCode(), error.getCode());
                }
            }
        } catch (GenericException ex) {
            logger.error(ex);
            throw ex;
        } catch (Exception e) {
            logger.error(e);
            throw new GenericException(AppError.DEFAULT.getMessage(), AppError.DEFAULT.getHttpCode(), AppError.DEFAULT.getCode());
        }
    }
}
