package bg.com.bo.bff.providers.implementations;

import bg.com.bo.bff.application.config.MiddlewareConfig;
import bg.com.bo.bff.application.exceptions.GenericException;
import bg.com.bo.bff.commons.enums.AppError;
import bg.com.bo.bff.commons.enums.CanalMW;
import bg.com.bo.bff.commons.enums.Headers;
import bg.com.bo.bff.commons.enums.ProjectNameMW;
import bg.com.bo.bff.commons.utils.Util;
import bg.com.bo.bff.models.ClientToken;
import bg.com.bo.bff.providers.dtos.requests.login.LoginMWCredendialDeviceRequest;
import bg.com.bo.bff.providers.dtos.requests.login.LoginMWCredentialRequest;
import bg.com.bo.bff.providers.dtos.requests.login.LoginMWFactorDeviceRequest;
import bg.com.bo.bff.providers.dtos.requests.login.LoginMWFactorRequest;
import bg.com.bo.bff.providers.dtos.responses.login.LoginMWCredentialResponse;
import bg.com.bo.bff.providers.dtos.responses.login.LoginMWFactorDataResponse;
import bg.com.bo.bff.providers.interfaces.ITokenMiddlewareProvider;
import bg.com.bo.bff.application.dtos.request.LoginRequest;
import bg.com.bo.bff.models.dtos.login.LoginValidationServiceResponse;
import bg.com.bo.bff.models.interfaces.IHttpClientFactory;
import bg.com.bo.bff.providers.dtos.responses.login.LoginMWFactorResponse;
import bg.com.bo.bff.providers.interfaces.ILoginMiddlewareProvider;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
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


    private static final Logger logger = LogManager.getLogger(LoginMiddlewareProvider.class.getName());

    public LoginMiddlewareProvider(ITokenMiddlewareProvider tokenMiddlewareProvider, MiddlewareConfig middlewareConfig, IHttpClientFactory httpClientFactory) {
        this.tokenMiddlewareProvider = tokenMiddlewareProvider;
        this.middlewareConfig = middlewareConfig;
        this.httpClientFactory = httpClientFactory;
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

            LoginMWCredentialRequest loginMWRequest = LoginMWCredentialRequest.builder().personId(data.getPersonId())
                    .password(loginRequest.getPassword())
                    .deviceData(loginMWCredendialDeviceRequest)
                    .idGeneratorUuid(data.getIdGeneratorUuid())
                    .loginType(data.getSecondFactor())
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
}
