package bg.com.bo.bff.providers.implementations;

import bg.com.bo.bff.application.config.MiddlewareConfig;
import bg.com.bo.bff.application.dtos.response.user.UpdateBiometricsResponse;
import bg.com.bo.bff.application.dtos.response.user.ContactResponse;
import bg.com.bo.bff.application.exceptions.GenericException;
import bg.com.bo.bff.commons.constants.CacheConstants;
import bg.com.bo.bff.commons.enums.config.provider.DeviceMW;
import bg.com.bo.bff.providers.dtos.request.encryption.EncryptInfo;
import bg.com.bo.bff.providers.dtos.request.login.mw.*;
import bg.com.bo.bff.providers.dtos.response.encryption.UserEncryptionKeys;
import bg.com.bo.bff.providers.dtos.response.login.mw.*;
import bg.com.bo.bff.providers.models.enums.middleware.login.LoginMiddlewareError;
import bg.com.bo.bff.providers.models.enums.middleware.login.LoginMiddlewareServices;
import bg.com.bo.bff.providers.models.middleware.DefaultMiddlewareError;
import bg.com.bo.bff.providers.models.middleware.HeadersMW;
import bg.com.bo.bff.commons.enums.config.provider.ProjectNameMW;
import bg.com.bo.bff.commons.utils.Util;
import bg.com.bo.bff.models.ClientToken;
import bg.com.bo.bff.providers.dtos.response.personal.information.UserContactResponse;
import bg.com.bo.bff.commons.interfaces.IHttpClientFactory;
import bg.com.bo.bff.providers.dtos.response.generic.ApiDataResponse;
import bg.com.bo.bff.providers.interfaces.ITokenMiddlewareProvider;
import bg.com.bo.bff.providers.interfaces.ILoginMiddlewareProvider;
import bg.com.bo.bff.mappings.providers.login.ILoginMapper;
import bg.com.bo.bff.providers.models.middleware.MiddlewareProvider;
import bg.com.bo.bff.providers.models.middleware.response.handler.ByMwErrorResponseHandler;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.log4j.Log4j2;
import org.apache.http.Header;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

@Log4j2
@Service
public class LoginMiddlewareProvider extends MiddlewareProvider<LoginMiddlewareError> implements ILoginMiddlewareProvider {
    private final String baseUrl;
    private final HttpServletRequest httpServletRequest;

    public LoginMiddlewareProvider(ITokenMiddlewareProvider tokenMiddlewareProvider, MiddlewareConfig middlewareConfig, IHttpClientFactory httpClientFactory, HttpServletRequest httpServletRequest) {
        super(ProjectNameMW.LOGIN_MANAGER, LoginMiddlewareError.class, tokenMiddlewareProvider, middlewareConfig, httpClientFactory, middlewareConfig.getClientLogin());
        this.baseUrl = middlewareConfig.getUrlBase() + ProjectNameMW.LOGIN_MANAGER.getName();
        this.httpServletRequest = httpServletRequest;
    }

    @Override
    public LoginFactorMWResponse validateFactorUser(LoginFactorMWRequest loginRequest) throws IOException {
        String url = baseUrl + LoginMiddlewareServices.VALIDATE_FACTOR.getServiceURL();
        Set<String> keyHeaders = Set.of(DeviceMW.JSON_DATA.getCode());
        Header[] headers = HeadersMW.getDefaultHeaders(httpServletRequest);
        Header[] newHeaders = HeadersMW.addSpecificHeaders(headers, httpServletRequest, keyHeaders);
        return post(url, newHeaders, loginRequest, LoginFactorMWResponse.class);
    }

    @Override
    public LoginCredentialMWResponse validateCredentials(LoginCredentialMWRequest loginRequest) throws IOException {
        String url = baseUrl + LoginMiddlewareServices.VALIDATE_CREDENTIALS.getServiceURL();
        Set<String> keyHeaders = Set.of(DeviceMW.JSON_DATA.getCode());
        Header[] headers = HeadersMW.getDefaultHeaders(httpServletRequest);
        Header[] newHeaders = HeadersMW.addSpecificHeaders(headers, httpServletRequest, keyHeaders);
        return post(url, newHeaders, loginRequest, LoginCredentialMWResponse.class);
    }

    @Override
    public LogoutMWResponse logout(LogoutMWRequest logoutMWRequest) throws IOException {
        String url = baseUrl + LoginMiddlewareServices.LOGOUT_SECURELY.getServiceURL();
        return post(url, HeadersMW.getDefaultHeaders(httpServletRequest), logoutMWRequest, LogoutMWResponse.class);
    }

    @Override
    public ChangePasswordMWResponse changePassword(ChangePasswordMWRequest mwRequest) throws IOException {
        String url = baseUrl + LoginMiddlewareServices.CHANGE_PASSWORD.getServiceURL();
        ApiDataResponse<ChangePasswordMWResponse> mwResponse = put(url, HeadersMW.getDefaultHeaders(httpServletRequest), mwRequest, ApiDataResponse.class);
        return Util.stringToObject(Util.objectToString(mwResponse.getData()), ChangePasswordMWResponse.class);
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
        } catch (Exception e) {
            log.error(e);
            throw new GenericException(DefaultMiddlewareError.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public DeviceEnrollmentMWResponse makeValidateDevice() throws IOException {
        String url = baseUrl + LoginMiddlewareServices.VALIDATE_ENROLLMENT.getServiceURL();
        ByMwErrorResponseHandler<ApiDataResponse> responseHandler = ByMwErrorResponseHandler.instance(LoginMiddlewareError.MDWLM_29);
        ApiDataResponse<DeviceEnrollmentMWResponse> mwResponse = get(url, HeadersMW.getDefaultHeaders(httpServletRequest), ApiDataResponse.class, responseHandler);
        if (mwResponse.getData() != null) {
            return Util.stringToObject(Util.objectToString(mwResponse.getData()), DeviceEnrollmentMWResponse.class);
        }
        return DeviceEnrollmentMWResponse.builder().statusCode("NOT_ENROLLED").build();
    }

    @Override
    public BiometricStatusMWResponse getBiometricsMW(Integer personId) throws IOException {
        String url = baseUrl  + String.format( LoginMiddlewareServices.GET_BIOMETRIC.getServiceURL(), personId);
        return get(url, HeadersMW.getDefaultHeaders(httpServletRequest), BiometricStatusMWResponse.class);
    }

    @Override
    public UpdateBiometricsResponse updateBiometricsMW(Integer personId, UpdateBiometricsMWRequest mwRequest) throws IOException {
        String url = baseUrl + String.format( LoginMiddlewareServices.UPDATE_BIOMETRIC.getServiceURL(), personId);
        ApiDataResponse<UpdateBiometricsResponse> mwResponse = post(url, HeadersMW.getDefaultHeaders(httpServletRequest), mwRequest, ApiDataResponse.class);
        return Util.stringToObject(Util.objectToString(mwResponse.getData()), UpdateBiometricsResponse.class);
    }

    @Cacheable(value = CacheConstants.ENCRYPTION_KEYS_CACHE_NAME)
    public UserEncryptionKeys getEncryptionKeys(EncryptInfo encodeInfo) throws IOException {
        String url = baseUrl + String.format( LoginMiddlewareServices.GET_KEYS.getServiceURL(), encodeInfo.getPersonId());
        ApiDataResponse<UserEncryptionKeys> mwResponse = get(url, HeadersMW.getDefaultHeaders(httpServletRequest), ApiDataResponse.class);
        return Util.stringToObject(Util.objectToString(mwResponse.getData()), UserEncryptionKeys.class);
    }
}
