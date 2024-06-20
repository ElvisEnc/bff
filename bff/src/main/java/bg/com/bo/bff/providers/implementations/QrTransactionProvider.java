package bg.com.bo.bff.providers.implementations;

import bg.com.bo.bff.application.config.MiddlewareConfig;
import bg.com.bo.bff.commons.enums.CanalMW;
import bg.com.bo.bff.commons.enums.DeviceMW;
import bg.com.bo.bff.commons.enums.ProjectNameMW;
import bg.com.bo.bff.models.interfaces.IHttpClientFactory;
import bg.com.bo.bff.providers.dtos.requests.qr.QRPaymentMWRequest;
import bg.com.bo.bff.providers.dtos.response.qr.QRPaymentMWResponse;
import bg.com.bo.bff.providers.interfaces.IQrTransactionProvider;
import bg.com.bo.bff.providers.interfaces.ITokenMiddlewareProvider;
import bg.com.bo.bff.providers.models.enums.middleware.qr.QRTransactionMiddlewareError;
import bg.com.bo.bff.providers.models.enums.middleware.qr.QRTransactionMiddlewareServices;
import bg.com.bo.bff.providers.models.middleware.HeadersMW;
import bg.com.bo.bff.providers.models.middleware.MiddlewareProvider;
import org.apache.http.Header;
import org.apache.http.message.BasicHeader;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Map;

@Service
public class QrTransactionProvider extends MiddlewareProvider<QRTransactionMiddlewareError>  implements IQrTransactionProvider {

    public QrTransactionProvider(ITokenMiddlewareProvider tokenMiddlewareProvider, MiddlewareConfig middlewareConfig, IHttpClientFactory httpClientFactory) {
        super(ProjectNameMW.QR_TRANSACTION_MANAGER, QRTransactionMiddlewareError.class, tokenMiddlewareProvider, middlewareConfig, httpClientFactory, middlewareConfig.getClientQrTransactionManager());
    }



    @Override
    public QRPaymentMWResponse qrPayment(QRPaymentMWRequest requestMW, Map<String, String> parameters) throws IOException {
        String url = String.format("%s%s%s", middlewareConfig.getUrlBase(), ProjectNameMW.QR_TRANSACTION_MANAGER.getName(), QRTransactionMiddlewareServices.QR_PAYMENT.getServiceURL());
        Header[] headers =setHeaders(parameters);

        return post(url, headers,requestMW, QRPaymentMWResponse.class);
    }

    private static Header[] setHeaders(Map<String, String> parameters) {
        return new Header[]{
                new BasicHeader(HeadersMW.MW_CHA.getName(), CanalMW.GANAMOVIL.getCanal()),
                new BasicHeader(HeadersMW.APP_ID.getName(), CanalMW.GANAMOVIL.getCanal()),
                new BasicHeader(HeadersMW.CONTENT_TYPE.getName(), HeadersMW.APP_JSON.getName()),
                new BasicHeader(DeviceMW.DEVICE_ID.getCode(), parameters.get(DeviceMW.DEVICE_ID.getCode())),
                new BasicHeader(DeviceMW.DEVICE_IP.getCode(), parameters.get(DeviceMW.DEVICE_IP.getCode())),
                new BasicHeader(DeviceMW.DEVICE_NAME.getCode(), parameters.get(DeviceMW.DEVICE_NAME.getCode())),
                new BasicHeader(DeviceMW.GEO_POSITION_X.getCode(), parameters.get(DeviceMW.GEO_POSITION_X.getCode())),
                new BasicHeader(DeviceMW.GEO_POSITION_Y.getCode(), parameters.get(DeviceMW.GEO_POSITION_Y.getCode())),
                new BasicHeader(DeviceMW.APP_VERSION.getCode(), parameters.get(DeviceMW.APP_VERSION.getCode())),
        };
    }
}
