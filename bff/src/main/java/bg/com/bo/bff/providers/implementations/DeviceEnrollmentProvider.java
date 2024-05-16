package bg.com.bo.bff.providers.implementations;

import bg.com.bo.bff.application.config.MiddlewareConfig;
import bg.com.bo.bff.application.dtos.request.Device;
import bg.com.bo.bff.application.exceptions.GenericException;
import bg.com.bo.bff.commons.enums.AppError;
import bg.com.bo.bff.commons.enums.CanalMW;
import bg.com.bo.bff.commons.enums.ProjectNameMW;
import bg.com.bo.bff.commons.utils.Util;
import bg.com.bo.bff.models.ClientToken;
import bg.com.bo.bff.models.interfaces.IHttpClientFactory;
import bg.com.bo.bff.providers.dtos.responses.ApiDataResponse;
import bg.com.bo.bff.providers.dtos.responses.login.DeviceEnrollmentMWResponse;
import bg.com.bo.bff.providers.interfaces.IDeviceEnrollmentProvider;
import bg.com.bo.bff.providers.interfaces.ITokenMiddlewareProvider;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Objects;

@Service
public class DeviceEnrollmentProvider implements IDeviceEnrollmentProvider {
    private IHttpClientFactory httpClientFactory;
    private final MiddlewareConfig middlewareConfig;
    ITokenMiddlewareProvider tokenMiddlewareProvider;

    private static final Logger LOGGER = LogManager.getLogger(TransferMiddlewareProvider.class.getName());

    private CloseableHttpClient createHttpClient() {
        return httpClientFactory.create();
    }

    public DeviceEnrollmentProvider(IHttpClientFactory httpClientFactory, MiddlewareConfig middlewareConfig, ITokenMiddlewareProvider tokenMiddlewareProvider) {
        this.httpClientFactory = httpClientFactory;
        this.middlewareConfig = middlewareConfig;
        this.tokenMiddlewareProvider = tokenMiddlewareProvider;
    }

    public ClientToken generateToken() throws IOException {
        return tokenMiddlewareProvider.generateAccountAccessToken(ProjectNameMW.LOGIN_MANAGER.getName(), middlewareConfig.getClientLogin(), ProjectNameMW.LOGIN_MANAGER.getHeaderKey());
    }

    public DeviceEnrollmentMWResponse makeValidateDevice(Device device, String token) {
        try (CloseableHttpClient httpClient = createHttpClient()) {
            String uniqueId = device.getUniqueId();
            String path = middlewareConfig.getUrlBase() + ProjectNameMW.LOGIN_MANAGER.getName() + "/bs/v1/device/validate-enrollment?deviceId=" + uniqueId;
            HttpGet request = new HttpGet(path);
            request.setHeader("Authorization", "Bearer " + token);
            request.setHeader("middleware-channel", CanalMW.GANAMOVIL.getCanal());
            request.setHeader("application-id", CanalMW.GANAMOVIL.getCanal());

            CloseableHttpResponse httpResponse = httpClient.execute(request);
            int statusCode = httpResponse.getStatusLine().getStatusCode();
            String jsonResponse = EntityUtils.toString(httpResponse.getEntity());
            if (statusCode == HttpStatus.SC_OK) {
                ApiDataResponse<DeviceEnrollmentMWResponse> response = Util.stringToObject(jsonResponse, ApiDataResponse.class);
                String text = Util.objectToString(response.getData());
                return Util.stringToObject(text, DeviceEnrollmentMWResponse.class);
            } else {
                LOGGER.error(jsonResponse);
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
            LOGGER.error(ex);
            throw ex;
        } catch (Exception e) {
            LOGGER.error(e);
            throw new GenericException(AppError.DEFAULT.getMessage(), AppError.DEFAULT.getHttpCode(), AppError.DEFAULT.getCode());
        }
    }
}
