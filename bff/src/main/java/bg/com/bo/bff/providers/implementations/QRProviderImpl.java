package bg.com.bo.bff.providers.implementations;

import bg.com.bo.bff.application.config.MiddlewareConfig;
import bg.com.bo.bff.application.exceptions.GenericException;
import bg.com.bo.bff.application.exceptions.HandledException;
import bg.com.bo.bff.commons.converters.ErrorResponseConverter;
import bg.com.bo.bff.commons.enums.AppError;
import bg.com.bo.bff.commons.enums.CanalMW;
import bg.com.bo.bff.commons.enums.DeviceMW;
import bg.com.bo.bff.providers.models.middleware.HeadersMW;
import bg.com.bo.bff.commons.enums.ProjectNameMW;
import bg.com.bo.bff.commons.utils.Util;
import bg.com.bo.bff.models.ClientToken;
import bg.com.bo.bff.models.interfaces.IHttpClientFactory;
import bg.com.bo.bff.providers.dtos.request.QRCodeGenerateMWRequest;
import bg.com.bo.bff.providers.dtos.request.QRCodeRegenerateMWRequest;
import bg.com.bo.bff.providers.dtos.response.qr.QRCodeGenerateResponse;
import bg.com.bo.bff.providers.interfaces.IQRProvider;
import bg.com.bo.bff.providers.interfaces.ITokenMiddlewareProvider;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.Header;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.util.EntityUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Map;

@Service
public class QRProviderImpl implements IQRProvider {
    private final ITokenMiddlewareProvider tokenMiddlewareProvider;
    private final MiddlewareConfig middlewareConfig;
    private final IHttpClientFactory httpClientFactory;

    private static final String GENERATE = "/bs/v1/qrcode/generate";
    private static final String REGENERATE= "/bs/v1/qrcode/encrypt";
    private static final String DECRYPT= "/bs/v1/qrcode/decrypt";

    private static final Logger logger = LogManager.getLogger(QRProviderImpl.class.getName());

    public QRProviderImpl(ITokenMiddlewareProvider tokenMiddlewareProvider, MiddlewareConfig middlewareConfig, IHttpClientFactory httpClientFactory) {
        this.tokenMiddlewareProvider = tokenMiddlewareProvider;
        this.middlewareConfig = middlewareConfig;
        this.httpClientFactory = httpClientFactory;
    }

    @Override
    public QRCodeGenerateResponse generate(final QRCodeGenerateMWRequest request, final Map<String, String> parameters) throws IOException {

        final String jsonMapper = Util.objectToString(request);
        return getQrCodeGenerateResponse(parameters, jsonMapper, GENERATE);
    }

    @Override
    public QRCodeGenerateResponse regenerate(QRCodeRegenerateMWRequest request, Map<String, String> parameters) throws IOException {

        final String jsonMapper = Util.objectToString(request);
        return getQrCodeGenerateResponse(parameters, jsonMapper, REGENERATE);
    }

    @Override
    public QRCodeGenerateResponse decrypt(QRCodeRegenerateMWRequest request, Map<String, String> parameters) throws IOException {
        final String jsonMapper = Util.objectToString(request);
        return getQrCodeGenerateResponse(parameters, jsonMapper, DECRYPT);
    }

    private QRCodeGenerateResponse getQrCodeGenerateResponse(Map<String, String> parameters, String jsonMapper,String url) throws IOException {
        ClientToken clientToken = tokenMiddlewareProvider.
                generateAccountAccessToken(ProjectNameMW.GENERATE_QR_MANAGER.getName(),
                        middlewareConfig.getClientGenerateQrManager(),
                        ProjectNameMW.GENERATE_QR_MANAGER.getHeaderKey()
                );
        final StringEntity entity = new StringEntity(jsonMapper, ContentType.APPLICATION_JSON);
        try (CloseableHttpClient httpClient = httpClientFactory.create()) {

            String path = middlewareConfig.getUrlBase() + ProjectNameMW.GENERATE_QR_MANAGER.getName() + url;
            HttpPost httpPost = new HttpPost(path);

            httpPost.setHeaders(getHeadersMW(parameters));
            httpPost.addHeader(HeadersMW.APP_ID.getName(), CanalMW.GANAMOVIL.getCanal());
            httpPost.addHeader(HeadersMW.AUT.getName(), "Bearer " + clientToken.getAccessToken());
            httpPost.setEntity(entity);
            ObjectMapper objectMapper = new ObjectMapper();
            try (CloseableHttpResponse httpResponse = httpClient.execute(httpPost)) {
                int statusCode = httpResponse.getStatusLine().getStatusCode();
                String jsonResponse = EntityUtils.toString(httpResponse.getEntity());
                if (statusCode == HttpStatus.SC_OK) {
                    return objectMapper.readValue(jsonResponse, QRCodeGenerateResponse.class);
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

    private Header[] getHeadersMW(Map<String, String> parameters) {
        return new Header[]{
                new BasicHeader(DeviceMW.MIDDLEWARE_CHANNEL.getCode(), CanalMW.GANAMOVIL.getCanal()),
                new BasicHeader(DeviceMW.DEVICE_ID.getCode(), parameters.get(DeviceMW.DEVICE_ID.getCode())),
                new BasicHeader(DeviceMW.DEVICE_IP.getCode(), parameters.get(DeviceMW.DEVICE_IP.getCode())),
                new BasicHeader(DeviceMW.DEVICE_NAME.getCode(), parameters.get(DeviceMW.DEVICE_NAME.getCode())),
                new BasicHeader(DeviceMW.GEO_POSITION_X.getCode(), parameters.get(DeviceMW.GEO_POSITION_X.getCode())),
                new BasicHeader(DeviceMW.GEO_POSITION_Y.getCode(), parameters.get(DeviceMW.GEO_POSITION_Y.getCode())),
                new BasicHeader(DeviceMW.APP_VERSION.getCode(), parameters.get(DeviceMW.APP_VERSION.getCode())),
                new BasicHeader(HeadersMW.CONTENT_TYPE.getName(), HeadersMW.APP_JSON.getName())};
    }
}
