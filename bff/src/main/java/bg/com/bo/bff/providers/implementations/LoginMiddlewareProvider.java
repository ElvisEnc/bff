package bg.com.bo.bff.providers.implementations;

import bg.com.bo.bff.application.config.MiddlewareConfig;
import bg.com.bo.bff.application.dtos.request.ChangePasswordRequest;
import bg.com.bo.bff.application.dtos.response.GenericResponse;
import bg.com.bo.bff.application.exceptions.GenericException;
import bg.com.bo.bff.application.exceptions.HandledException;
import bg.com.bo.bff.commons.converters.IErrorResponse;
import bg.com.bo.bff.commons.converters.ChangePasswordErrorResponseConverter;
import bg.com.bo.bff.commons.converters.ErrorResponseConverter;
import bg.com.bo.bff.commons.enums.AppError;
import bg.com.bo.bff.commons.enums.CanalMW;
import bg.com.bo.bff.commons.enums.Headers;
import bg.com.bo.bff.commons.enums.ProjectNameMW;
import bg.com.bo.bff.commons.enums.response.user.UserControllerResponse;
import bg.com.bo.bff.commons.utils.Util;
import bg.com.bo.bff.models.ClientToken;
import bg.com.bo.bff.providers.dtos.requests.login.*;
import bg.com.bo.bff.providers.dtos.responses.login.LoginMWCredentialResponse;
import bg.com.bo.bff.providers.dtos.responses.login.LoginMWFactorDataResponse;
import bg.com.bo.bff.providers.interfaces.ITokenMiddlewareProvider;
import bg.com.bo.bff.application.dtos.request.LoginRequest;
import bg.com.bo.bff.models.dtos.login.LoginValidationServiceResponse;
import bg.com.bo.bff.models.interfaces.IHttpClientFactory;
import bg.com.bo.bff.providers.dtos.responses.login.LoginMWFactorResponse;
import bg.com.bo.bff.providers.interfaces.ILoginMiddlewareProvider;
import bg.com.bo.bff.providers.mappings.login.LoginMWMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class LoginMiddlewareProvider implements ILoginMiddlewareProvider {
    ITokenMiddlewareProvider tokenMiddlewareProvider;

    private final MiddlewareConfig middlewareConfig;

    private IHttpClientFactory httpClientFactory;
    private LoginMWMapper loginMWMapper;

    private static final Logger logger = LogManager.getLogger(LoginMiddlewareProvider.class.getName());

    public LoginMiddlewareProvider(ITokenMiddlewareProvider tokenMiddlewareProvider, MiddlewareConfig middlewareConfig, IHttpClientFactory httpClientFactory, LoginMWMapper loginMWMapper) {
        this.tokenMiddlewareProvider = tokenMiddlewareProvider;
        this.middlewareConfig = middlewareConfig;
        this.httpClientFactory = httpClientFactory;
        this.loginMWMapper = loginMWMapper;
    }


    private CloseableHttpClient createHttpClient() {
        return httpClientFactory.create();
    }

    public ClientToken tokenLogin() throws IOException {
        return tokenMiddlewareProvider.generateAccountAccessToken(ProjectNameMW.LOGIN_MANAGER.getName(), middlewareConfig.getClientLogin(), ProjectNameMW.LOGIN_MANAGER.getHeaderKey());
    }

    public LoginMWFactorResponse validateFactorUser(LoginRequest loginRequest, String ip, String token) throws IOException {
        try (CloseableHttpClient httpClient = createHttpClient()) {
            LoginMWFactorDeviceRequest loginMWDeviceFactorRequest = LoginMWFactorDeviceRequest.builder()
                    .deviceIp(ip)
                    .uniqueId(loginRequest.getDeviceIdentification().getUniqueId())
                    .build();
            LoginMWFactorRequest loginMWRequest = LoginMWFactorRequest.builder()
                    .codeTypeAuthentication(loginRequest.getType())
                    .factor(loginRequest.getUser())
                    .geoReference(loginRequest.getDeviceIdentification().getGeoPositionX() + "," + loginRequest.getDeviceIdentification().getGeoPositionY())
                    .deviceIdentification(loginMWDeviceFactorRequest)
                    .build();

            String path = middlewareConfig.getUrlBase() + ProjectNameMW.LOGIN_MANAGER.getName() + "/bs/v1/authentication/validate";
            HttpPost request = new HttpPost(path);
            ObjectMapper objectMapper = new ObjectMapper();
            String jsonMapper = objectMapper.writeValueAsString(loginMWRequest);
            StringEntity entity = new StringEntity(jsonMapper);
            request.setEntity(entity);
            request.setHeader(Headers.CONTENT_TYPE.getName(), Headers.APP_JSON.getName());
            request.setHeader(Headers.AUT.getName(), "Bearer " + token);
            request.setHeader(Headers.MW_CHA.getName(), CanalMW.GANAMOVIL.getCanal());
            request.setHeader(Headers.APP_ID.getName(), CanalMW.GANAMOVIL.getCanal());

            CloseableHttpResponse httpResponse = httpClient.execute(request);
            int statusCode = httpResponse.getStatusLine().getStatusCode();
            String jsonResponse = EntityUtils.toString(httpResponse.getEntity());
            if (statusCode == HttpStatus.SC_OK) {
                return objectMapper.readValue(jsonResponse, LoginMWFactorResponse.class);
            } else {
                logger.error(jsonResponse);
                AppError error = Util.mapProviderError(jsonResponse);
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

    public LoginValidationServiceResponse validateCredentials(LoginRequest loginRequest, String ip, String token, LoginMWFactorDataResponse data) throws IOException {
        try (CloseableHttpClient httpClient = createHttpClient()) {
            LoginMWCredendialDeviceRequest loginMWCredendialDeviceRequest = LoginMWCredendialDeviceRequest.builder()
                    .deviceIp(ip)
                    .deviceId(loginRequest.getDeviceIdentification().getUniqueId())
                    .deviceName(loginRequest.getDeviceIdentification().getSystemName())
                    .geoPositionX(loginRequest.getDeviceIdentification().getGeoPositionX())
                    .geoPositionY(loginRequest.getDeviceIdentification().getGeoPositionY())
                    .build();

            LoginMWCredentialRequest loginMWRequest = LoginMWCredentialRequest.builder()
                    .personId(data.getPersonId())
                    .password(loginRequest.getPassword())
                    .deviceData(loginMWCredendialDeviceRequest)
                    .idGeneratorUuid(data.getIdGeneratorUuid())
                    .loginType(loginRequest.getType())
                    .tokenFinger(data.getSecondFactor())
                    .appVersion(loginRequest.getAppVersion())
                    .build();

            String path = middlewareConfig.getUrlBase() + ProjectNameMW.LOGIN_MANAGER.getName() + "/bs/v1/users/validate-credentials";
            HttpPost request = new HttpPost(path);
            ObjectMapper objectMapper = new ObjectMapper();
            String jsonMapper = objectMapper.writeValueAsString(loginMWRequest);
            StringEntity entity = new StringEntity(jsonMapper);
            request.setEntity(entity);
            request.setHeader(Headers.CONTENT_TYPE.getName(), Headers.APP_JSON.getName());
            request.setHeader(Headers.AUT.getName(), "Bearer " + token);
            request.setHeader(Headers.MW_CHA.getName(), CanalMW.GANAMOVIL.getCanal());
            request.setHeader(Headers.APP_ID.getName(), CanalMW.GANAMOVIL.getCanal());

            CloseableHttpResponse httpResponse = httpClient.execute(request);
            int statusCode = httpResponse.getStatusLine().getStatusCode();
            String jsonResponse = EntityUtils.toString(httpResponse.getEntity());

            if (statusCode == HttpStatus.SC_OK) {
                LoginMWCredentialResponse loginMWCredentialResponse = objectMapper.readValue(jsonResponse, LoginMWCredentialResponse.class);
                String codError = loginMWCredentialResponse.getData().getCodError();
                switch (codError) {
                    case "0000": {
                        LoginValidationServiceResponse loginResult = new LoginValidationServiceResponse();
                        loginResult.setPersonId(data.getPersonId());
                        loginResult.setUserDeviceId(String.valueOf(loginMWCredentialResponse.getData().getUserDeviceId()));
                        loginResult.setRolePersonId(String.valueOf(loginMWCredentialResponse.getData().getRoleList().get(0).getRolePersonId()));
                        loginResult.setStatusCode("SUCCESS");
                        return loginResult;
                    }
                    default:
                        logger.error(jsonResponse);
                        throw new GenericException(AppError.DEFAULT.getMessage(), AppError.DEFAULT.getHttpCode(), AppError.DEFAULT.getCode());
                }
            } else {
                logger.error(jsonResponse);
                AppError error = Util.mapProviderError(jsonResponse);
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

    public GenericResponse changePassword(String personId, String ip, String deviceId, String deviceUniqueId, String rolePersonId, ChangePasswordRequest changePasswordRequest) throws IOException {
        ChangePasswordMWRequest changePasswordMWRequest = loginMWMapper.convert(changePasswordRequest);
        MWOwnerAccountRequest owner = new MWOwnerAccountRequest();
        owner.setPersonId(personId);
        owner.setUserDeviceId(deviceId);
        owner.setPersonRoleId(rolePersonId);
        changePasswordMWRequest.setOwnerAccount(owner);

        ClientToken clientToken = tokenLogin();

        try (CloseableHttpClient httpClient = createHttpClient()) {
            String path = middlewareConfig.getUrlBase() + ProjectNameMW.LOGIN_MANAGER.getName() + "/bs/v1/password/change";
            HttpPut request = new HttpPut(path);

            ObjectMapper objectMapper = new ObjectMapper();
            String jsonMapper = objectMapper.writeValueAsString(changePasswordMWRequest);
            StringEntity entity = new StringEntity(jsonMapper);
            request.setEntity(entity);
            request.setHeader(Headers.CONTENT_TYPE.getName(), Headers.APP_JSON.getName());
            request.setHeader(Headers.AUT.getName(), "Bearer " + clientToken.getAccessToken());
            request.setHeader(Headers.MW_CHA.getName(), CanalMW.GANAMOVIL.getCanal());
            request.setHeader(Headers.APP_ID.getName(), CanalMW.GANAMOVIL.getCanal());
            request.setHeader(Headers.DEVICE_ID.getName(), deviceUniqueId);
            request.setHeader(Headers.DEVICE_IP.getName(), "192.168.1.2");

            try (CloseableHttpResponse httpResponse = httpClient.execute(request)) {
                int statusCode = httpResponse.getStatusLine().getStatusCode();
                String jsonResponse = EntityUtils.toString(httpResponse.getEntity());
                if (statusCode == HttpStatus.SC_OK)
                    return GenericResponse.instance(UserControllerResponse.SUCCESS);
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
}
