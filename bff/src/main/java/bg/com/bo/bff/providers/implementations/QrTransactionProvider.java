package bg.com.bo.bff.providers.implementations;

import bg.com.bo.bff.application.config.MiddlewareConfig;
import bg.com.bo.bff.application.exceptions.GenericException;
import bg.com.bo.bff.application.exceptions.HandledException;
import bg.com.bo.bff.commons.converters.ErrorResponseConverter;
import bg.com.bo.bff.commons.enums.AppError;
import bg.com.bo.bff.commons.enums.CanalMW;
import bg.com.bo.bff.commons.enums.ProjectNameMW;
import bg.com.bo.bff.commons.utils.Util;
import bg.com.bo.bff.models.ClientToken;
import bg.com.bo.bff.models.interfaces.IHttpClientFactory;
import bg.com.bo.bff.providers.dtos.requests.qr.QRPaymentMWRequest;
import bg.com.bo.bff.providers.dtos.response.qr.QRPaymentMWResponse;
import bg.com.bo.bff.providers.interfaces.IQrTransactionProvider;
import bg.com.bo.bff.providers.interfaces.ITokenMiddlewareProvider;
import bg.com.bo.bff.providers.models.middleware.HeadersMW;
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
import java.util.Map;

@Service
public class QrTransactionProvider implements IQrTransactionProvider {
    private final ITokenMiddlewareProvider tokenMiddlewareProvider;
    private final MiddlewareConfig middlewareConfig;
    private final IHttpClientFactory httpClientFactory;


    private static final String QR_PAYMENT= "/bs/v1/transfer/qr";

    private static final Logger logger = LogManager.getLogger(IQrTransactionProvider.class.getName());

    public QrTransactionProvider(ITokenMiddlewareProvider tokenMiddlewareProvider, MiddlewareConfig middlewareConfig, IHttpClientFactory httpClientFactory) {
        this.tokenMiddlewareProvider = tokenMiddlewareProvider;
        this.middlewareConfig = middlewareConfig;
        this.httpClientFactory = httpClientFactory;
    }

    @Override
    public QRPaymentMWResponse qrPayment(QRPaymentMWRequest requestMW, Map<String, String> parameters) throws IOException {
        final String path= middlewareConfig.getUrlBase() + ProjectNameMW.QR_TRANSACTION_MANAGER.getName() +QR_PAYMENT;;
        final String jsonMapper = Util.objectToString(requestMW);
        ClientToken clientToken = tokenMiddlewareProvider.
                generateAccountAccessToken(ProjectNameMW.QR_TRANSACTION_MANAGER.getName(),
                        middlewareConfig.getClientQrTransactionManager(),
                        ProjectNameMW.QR_TRANSACTION_MANAGER.getHeaderKey()
                );
        final StringEntity entity = new StringEntity(jsonMapper);
        try (CloseableHttpClient httpClient = httpClientFactory.create()) {
            HttpPost httpPost = new HttpPost(path);
            httpPost.setHeaders(HeadersMW.getHeadersMW(parameters));
            httpPost.addHeader(HeadersMW.APP_ID.getName(), CanalMW.GANAMOVIL.getCanal());
            httpPost.addHeader(HeadersMW.AUT.getName(), "Bearer " + clientToken.getAccessToken());
            httpPost.setEntity(entity);
            ObjectMapper objectMapper = new ObjectMapper();
            try (CloseableHttpResponse httpResponse = httpClient.execute(httpPost)) {
                int statusCode = httpResponse.getStatusLine().getStatusCode();
                String jsonResponse = EntityUtils.toString(httpResponse.getEntity());
                if (statusCode == HttpStatus.SC_OK) {
                    QRPaymentMWResponse result = objectMapper.readValue(jsonResponse, QRPaymentMWResponse.class);
                    return result;
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
}
