package bg.com.bo.bff.providers.implementations;

import bg.com.bo.bff.application.config.MiddlewareConfig;
import bg.com.bo.bff.application.dtos.request.ChangePasswordRequest;
import bg.com.bo.bff.application.dtos.request.LoginRequest;
import bg.com.bo.bff.application.dtos.response.GenericResponse;
import bg.com.bo.bff.application.dtos.response.user.ContactResponse;
import bg.com.bo.bff.application.dtos.response.user.PersonalResponse;
import bg.com.bo.bff.application.exceptions.GenericException;
import bg.com.bo.bff.application.exceptions.HandledException;
import bg.com.bo.bff.commons.HeadersMW;
import bg.com.bo.bff.commons.converters.ChangePasswordErrorResponseConverter;
import bg.com.bo.bff.commons.converters.ErrorResponseConverter;
import bg.com.bo.bff.commons.enums.*;
import bg.com.bo.bff.commons.converters.IErrorResponse;
import bg.com.bo.bff.commons.enums.AppError;
import bg.com.bo.bff.commons.enums.CanalMW;
import bg.com.bo.bff.commons.enums.DeviceMW;
import bg.com.bo.bff.commons.enums.Headers;
import bg.com.bo.bff.commons.enums.ProjectNameMW;
import bg.com.bo.bff.commons.enums.response.user.UserControllerResponse;
import bg.com.bo.bff.commons.utils.Util;
import bg.com.bo.bff.models.ClientToken;
import bg.com.bo.bff.models.UserContact;
import bg.com.bo.bff.models.dtos.login.LoginValidationServiceResponse;
import bg.com.bo.bff.models.interfaces.IHttpClientFactory;
import bg.com.bo.bff.providers.dtos.requests.login.ChangePasswordMWRequest;
import bg.com.bo.bff.providers.dtos.requests.login.LogoutMWRequest;
import bg.com.bo.bff.providers.dtos.requests.login.MWOwnerAccountRequest;
import bg.com.bo.bff.providers.dtos.responses.ApiDataResponse;
import bg.com.bo.bff.providers.dtos.responses.login.DeviceEnrollmentMWResponse;
import bg.com.bo.bff.providers.dtos.requests.login.*;
import bg.com.bo.bff.providers.dtos.responses.login.LoginCredentialMWResponse;
import bg.com.bo.bff.providers.dtos.responses.login.LoginFactorData;
import bg.com.bo.bff.providers.interfaces.ITokenMiddlewareProvider;
import bg.com.bo.bff.providers.dtos.responses.login.LoginFactorMWResponse;
import bg.com.bo.bff.providers.interfaces.ILoginMiddlewareProvider;
import bg.com.bo.bff.providers.mappings.login.ILoginMapper;
import bg.com.bo.bff.providers.mappings.login.LoginMWMapper;
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
        httpRequest.setHeader(Headers.AUT.getName(), "Bearer " + token);
        httpRequest.setHeader(Headers.MW_CHA.getName(), CanalMW.GANAMOVIL.getCanal());
        httpRequest.setHeader(Headers.APP_ID.getName(), ApplicationId.GANAMOVIL.getCode());
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
            request.setHeader(Headers.CONTENT_TYPE.getName(), Headers.APP_JSON.getName());
            request.setHeader(Headers.AUT.getName(), "Bearer " + token);
            request.setHeader(Headers.MW_CHA.getName(), CanalMW.GANAMOVIL.getCanal());
            request.setHeader(Headers.APP_ID.getName(), CanalMW.GANAMOVIL.getCanal());

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
    public GenericResponse changePassword(String personId,  String personRoleId, ChangePasswordRequest changePasswordRequest, Map<String,String> parameters) throws IOException {
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
            request.addHeader(Headers.AUT.getName(), "Bearer " + clientToken.getAccessToken());

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
            UserContact userContact = objectMapper.readValue(jsonContact, UserContact.class);
            String jsonResponse = Util.objectToString(userContact.getCompanyContactDetail());
            return Util.stringToObject(jsonResponse, ContactResponse.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public DeviceEnrollmentMWResponse makeValidateDevice( Map<String, String> parameter) throws IOException {
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
                String notEnrolled= error.getCodeMiddleware();
                if(Objects.equals(notEnrolled, AppError.MDWRLIB_0003.getCodeMiddleware())) {
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
}
