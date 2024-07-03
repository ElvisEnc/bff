package bg.com.bo.bff.providers.implementations;

import bg.com.bo.bff.application.config.MiddlewareConfig;
import bg.com.bo.bff.commons.enums.ProjectNameMW;
import bg.com.bo.bff.commons.interfaces.IHttpClientFactory;
import bg.com.bo.bff.providers.dtos.request.qr.mw.QRPaymentMWRequest;
import bg.com.bo.bff.providers.dtos.response.qr.mw.QRPaymentMWResponse;
import bg.com.bo.bff.providers.interfaces.IQrTransactionProvider;
import bg.com.bo.bff.providers.interfaces.ITokenMiddlewareProvider;
import bg.com.bo.bff.providers.models.enums.middleware.qr.QRTransactionMiddlewareError;
import bg.com.bo.bff.providers.models.enums.middleware.qr.QRTransactionMiddlewareServices;
import bg.com.bo.bff.providers.models.middleware.HeadersMW;
import bg.com.bo.bff.providers.models.middleware.MiddlewareProvider;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Map;

@Service
public class QrTransactionProvider extends MiddlewareProvider<QRTransactionMiddlewareError> implements IQrTransactionProvider {

    public QrTransactionProvider(ITokenMiddlewareProvider tokenMiddlewareProvider, MiddlewareConfig middlewareConfig, IHttpClientFactory httpClientFactory) {
        super(ProjectNameMW.QR_TRANSACTION_MANAGER, QRTransactionMiddlewareError.class, tokenMiddlewareProvider, middlewareConfig, httpClientFactory, middlewareConfig.getClientQrTransactionManager());
    }

    @Override
    public QRPaymentMWResponse qrPayment(QRPaymentMWRequest requestMW, Map<String, String> parameters) throws IOException {
        String url = String.format("%s%s%s", middlewareConfig.getUrlBase(), ProjectNameMW.QR_TRANSACTION_MANAGER.getName(), QRTransactionMiddlewareServices.QR_PAYMENT.getServiceURL());
        return post(url, HeadersMW.getDefaultHeaders(parameters), requestMW, QRPaymentMWResponse.class);
    }
}
