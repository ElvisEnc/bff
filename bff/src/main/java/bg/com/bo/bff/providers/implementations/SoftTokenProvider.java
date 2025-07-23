package bg.com.bo.bff.providers.implementations;

import bg.com.bo.bff.application.config.MiddlewareConfig;
import bg.com.bo.bff.commons.enums.config.provider.DeviceMW;
import bg.com.bo.bff.commons.enums.config.provider.ProjectNameMW;
import bg.com.bo.bff.commons.interfaces.IHttpClientFactory;
import bg.com.bo.bff.commons.utils.Util;
import bg.com.bo.bff.providers.dtos.request.softtoken.mw.*;
import bg.com.bo.bff.providers.dtos.response.generic.ApiDataResponse;
import bg.com.bo.bff.providers.dtos.response.softtoken.mw.*;
import bg.com.bo.bff.providers.interfaces.ISoftTokenProvider;
import bg.com.bo.bff.providers.interfaces.ITokenMiddlewareProvider;
import bg.com.bo.bff.providers.models.enums.middleware.softtoken.SoftTokenMiddlewareError;
import bg.com.bo.bff.providers.models.enums.middleware.softtoken.SoftTokenMiddlewareServices;
import bg.com.bo.bff.providers.models.middleware.HeadersMW;
import bg.com.bo.bff.providers.models.middleware.MiddlewareProvider;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class SoftTokenProvider extends MiddlewareProvider<SoftTokenMiddlewareError> implements ISoftTokenProvider {

    private final String baseUrl;
    private final HttpServletRequest httpServletRequest;

    public SoftTokenProvider(ITokenMiddlewareProvider tokenMiddlewareProvider, MiddlewareConfig middlewareConfig, IHttpClientFactory httpClientFactory, HttpServletRequest httpServletRequest) {
        super(ProjectNameMW.SOFT_TOKEN_MANAGER, SoftTokenMiddlewareError.class, tokenMiddlewareProvider, middlewareConfig, httpClientFactory, middlewareConfig.getClientSoftTokenManager());
        this.baseUrl = middlewareConfig.getUrlBase() + ProjectNameMW.SOFT_TOKEN_MANAGER.getName();
        this.httpServletRequest = httpServletRequest;
    }

    private String getDeviceId() {
        return HeadersMW.getSpecificHeader(HeadersMW.DEVICE_ID.getName(), httpServletRequest);
    }

    private String getDeviceName() {
        return HeadersMW.getSpecificHeader(DeviceMW.DEVICE_NAME.getCode(), httpServletRequest);
    }

    private void getRequest(SoftTokenMWRequest request){
        String deviceId = getDeviceId();
        request.setImei(deviceId);
        request.setDidBga(deviceId);
        request.setKsBga(deviceId);
    }

    private void getRequest(SoftTokenSentCodeMWRequest request){
        String deviceId = getDeviceId();
        request.setImei(deviceId);
        request.setDidBga(deviceId);
        request.setKsBga(deviceId);
    }

    private void getRequest(SoftTokenValidateCodeMWRequest request){
        String deviceId = getDeviceId();
        request.setImei(deviceId);
        request.setDidBga(deviceId);
        request.setKsBga(deviceId);
    }

    private void getRequest(SoftTokenValidateQuestionMWRequest request){
        String deviceId = getDeviceId();
        request.setImei(deviceId);
        request.setDidBga(deviceId);
        request.setKsBga(deviceId);
    }

    private void getRequest(SoftTokenRegistrationTokenMWRequest request){
        String deviceId = getDeviceId();
        request.setImei(deviceId);
        request.setDidBga(deviceId);
        request.setKsBga(deviceId);
    }

    @Override
    public SoftTokenWelcomeMWResponse getWelcomeMessage(SoftTokenMWRequest request) throws IOException {
        getRequest(request);
        request.setOperatingSystem(getDeviceName());
        String url = baseUrl + String.format(SoftTokenMiddlewareServices.GET_WELCOME_SOFT_TOKEN.getServiceURL());
        ApiDataResponse<SoftTokenWelcomeMWResponse> mwResponse = post(url, HeadersMW.getDefaultHeaders(httpServletRequest),request, ApiDataResponse.class);
        return Util.stringToObject(Util.objectToString(mwResponse.getData()), SoftTokenWelcomeMWResponse.class);
    }

    @Override
    public SoftTokenDataEnrollmentMWResponse getDataEnrollment(SoftTokenMWRequest request) throws IOException {
        getRequest(request);
        request.setOperatingSystem(getDeviceName());
        String url = baseUrl + String.format(SoftTokenMiddlewareServices.GET_DATA_ENROLLMENT_SOFT_TOKEN.getServiceURL());
        ApiDataResponse<SoftTokenDataEnrollmentMWResponse> mwResponse = post(url, HeadersMW.getDefaultHeaders(httpServletRequest),request, ApiDataResponse.class);
        return Util.stringToObject(Util.objectToString(mwResponse.getData()), SoftTokenDataEnrollmentMWResponse.class);
    }

    @Override
    public SoftTokenQuestionEnrollmentMWResponse getQuestionEnrollment(SoftTokenMWRequest request) throws IOException {
        getRequest(request);
        request.setOperatingSystem(getDeviceName());
        String url = baseUrl + String.format(SoftTokenMiddlewareServices.GET_QUESTION_ENROLLMENT_SOFT_TOKEN.getServiceURL());
        ApiDataResponse<SoftTokenQuestionEnrollmentMWResponse> mwResponse = post(url, HeadersMW.getDefaultHeaders(httpServletRequest),request, ApiDataResponse.class);
        return Util.stringToObject(Util.objectToString(mwResponse.getData()), SoftTokenQuestionEnrollmentMWResponse.class);
    }

    @Override
    public SoftTokenEnrollmentMWResponse getValidationEnrollment(SoftTokenMWRequest request) throws IOException {
        getRequest(request);
        String url = baseUrl + String.format(SoftTokenMiddlewareServices.GET_VALIDATE_ENROLLMENT_SOFT_TOKEN.getServiceURL());
        ApiDataResponse<SoftTokenEnrollmentMWResponse> mwResponse = post(url, HeadersMW.getDefaultHeaders(httpServletRequest),request, ApiDataResponse.class);
        return Util.stringToObject(Util.objectToString(mwResponse.getData()), SoftTokenEnrollmentMWResponse.class);
    }

    @Override
    public SoftTokenEnrollmentMWResponse postCodeEnrollment(SoftTokenSentCodeMWRequest mwRequest) throws IOException {
        getRequest(mwRequest);
        mwRequest.setOperatingSystem(getDeviceName());
        String url = baseUrl + String.format(SoftTokenMiddlewareServices.GET_VALIDATE_CODE_SECURITY_ENROLLMENT_SOFT_TOKEN.getServiceURL());
        ApiDataResponse<SoftTokenEnrollmentMWResponse> mwResponse = post(url, HeadersMW.getDefaultHeaders(httpServletRequest), mwRequest, ApiDataResponse.class);
        return Util.stringToObject(Util.objectToString(mwResponse.getData()), SoftTokenEnrollmentMWResponse.class);
    }
    @Override
    public SoftTokenEnrollmentMWResponse validateCodeEnrollment(SoftTokenValidateCodeMWRequest mwRequest) throws IOException {
        getRequest(mwRequest);
        mwRequest.setOperatingSystem(getDeviceName());
        String url = baseUrl + String.format(SoftTokenMiddlewareServices.POST_VALIDATE_CODE_SECURITY_ENROLLMENT_SOFT_TOKEN.getServiceURL());
        ApiDataResponse<SoftTokenEnrollmentMWResponse> mwResponse = post(url, HeadersMW.getDefaultHeaders(httpServletRequest), mwRequest, ApiDataResponse.class);
        return Util.stringToObject(Util.objectToString(mwResponse.getData()), SoftTokenEnrollmentMWResponse.class);
    }

    @Override
    public SoftTokenEnrollmentMWResponse validateQuestionSecurity(SoftTokenValidateQuestionMWRequest mwRequest) throws IOException {
        getRequest(mwRequest);
        mwRequest.setOperatingSystem(getDeviceName());
        String url = baseUrl + String.format(SoftTokenMiddlewareServices.POST_VALIDATE_QUESTION_SECURITY_ENROLLMENT_SOFT_TOKEN.getServiceURL());
        ApiDataResponse<SoftTokenEnrollmentMWResponse> mwResponse = post(url, HeadersMW.getDefaultHeaders(httpServletRequest), mwRequest, ApiDataResponse.class);
        return Util.stringToObject(Util.objectToString(mwResponse.getData()), SoftTokenEnrollmentMWResponse.class);
    }

    @Override
    public SoftTokenObtainParametersMWResponse getParameters(SoftTokenMWRequest mwRequest) throws IOException {
        getRequest(mwRequest);
        mwRequest.setOperatingSystem(getDeviceName());
        String url = baseUrl + String.format(SoftTokenMiddlewareServices.GET_PARAMETER_SOFT_TOKEN.getServiceURL());
        ApiDataResponse<SoftTokenObtainParametersMWResponse> mwResponse = post(url, HeadersMW.getDefaultHeaders(httpServletRequest), mwRequest, ApiDataResponse.class);
        return Util.stringToObject(Util.objectToString(mwResponse.getData()), SoftTokenObtainParametersMWResponse.class);
    }

    @Override
    public SoftTokenCodeTokenMWResponse postRegistrationToken(SoftTokenRegistrationTokenMWRequest mwRequest) throws IOException {
        getRequest(mwRequest);
        mwRequest.setOperatingSystem(getDeviceName());
        String url = baseUrl + String.format(SoftTokenMiddlewareServices.POST_REGISTRATION_TOKEN_ENROLLMENT_SOFT_TOKEN.getServiceURL());
        ApiDataResponse<SoftTokenCodeTokenMWResponse> mwResponse = post(url, HeadersMW.getDefaultHeaders(httpServletRequest), mwRequest, ApiDataResponse.class);
        return Util.stringToObject(Util.objectToString(mwResponse.getData()), SoftTokenCodeTokenMWResponse.class);
    }
    @Override
    public SoftTokenCodeTokenMWResponse getRegistrationValidation(SofTokenValidateMWRequest mwRequest) throws IOException {
        mwRequest.setDeviceId(getDeviceId());
        String url = baseUrl + String.format(SoftTokenMiddlewareServices.GET_ENROLLMENT_VALIDATE_SOFT_TOKEN.getServiceURL());
        ApiDataResponse<SoftTokenCodeTokenMWResponse> mwResponse = post(url, HeadersMW.getDefaultHeaders(httpServletRequest), mwRequest, ApiDataResponse.class);
        return Util.stringToObject(Util.objectToString(mwResponse.getData()), SoftTokenCodeTokenMWResponse.class);
    }

    @Override
    public SoftTokenGenerateTokenMWResponse postTokenGenerate(SofTokenGenerateTokenMWRequest mwRequest) throws IOException {
        mwRequest.setDeviceId(getDeviceId());
        String url = baseUrl + String.format(SoftTokenMiddlewareServices.POST_GENERATE_TOKEN_SOFT_TOKEN.getServiceURL());
        ApiDataResponse<SoftTokenGenerateTokenMWResponse> mwResponse = post(url, HeadersMW.getDefaultHeaders(httpServletRequest), mwRequest, ApiDataResponse.class);
        return Util.stringToObject(Util.objectToString(mwResponse.getData()), SoftTokenGenerateTokenMWResponse.class);
    }

    @Override
    public SoftTokenCodeTokenMWResponse postEnrollment(SofTokenEnrollmentMWRequest mwRequest) throws IOException {
        mwRequest.setDeviceId(getDeviceId());
        String url = baseUrl + String.format(SoftTokenMiddlewareServices.POST_ENROLLMENT_SOFT_TOKEN.getServiceURL());
        ApiDataResponse<SoftTokenCodeTokenMWResponse> mwResponse = post(url, HeadersMW.getDefaultHeaders(httpServletRequest), mwRequest, ApiDataResponse.class);
        return Util.stringToObject(Util.objectToString(mwResponse.getData()), SoftTokenCodeTokenMWResponse.class);
    }

    @Override
    public SoftTokenCodeTokenMWResponse validationToken(SoftTokenValidateTokenMWRequest mwRequest) throws IOException {
        String url = baseUrl + String.format(SoftTokenMiddlewareServices.POST_VALIDATE_SOFT_TOKEN.getServiceURL());
        ApiDataResponse<SoftTokenCodeTokenMWResponse> mwResponse = post(url, HeadersMW.getDefaultHeaders(httpServletRequest), mwRequest, ApiDataResponse.class);
        return Util.stringToObject(Util.objectToString(mwResponse.getData()), SoftTokenCodeTokenMWResponse.class);
    }
}
