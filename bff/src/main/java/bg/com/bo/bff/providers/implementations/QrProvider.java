package bg.com.bo.bff.providers.implementations;

import bg.com.bo.bff.application.config.MiddlewareConfig;
import bg.com.bo.bff.providers.models.enums.middleware.qr.QRMiddlewareError;
import bg.com.bo.bff.providers.models.enums.middleware.qr.QRMiddlewareServices;
import bg.com.bo.bff.providers.models.middleware.HeadersMW;
import bg.com.bo.bff.commons.enums.config.provider.ProjectNameMW;
import bg.com.bo.bff.commons.interfaces.IHttpClientFactory;
import bg.com.bo.bff.providers.dtos.request.qr.mw.QRCodeGenerateMWRequest;
import bg.com.bo.bff.providers.dtos.request.qr.mw.QRCodeRegenerateMWRequest;
import bg.com.bo.bff.providers.dtos.response.qr.mw.QRCodeGenerateResponse;
import bg.com.bo.bff.providers.interfaces.IQRProvider;
import bg.com.bo.bff.providers.interfaces.ITokenMiddlewareProvider;
import bg.com.bo.bff.providers.models.middleware.MiddlewareProvider;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Map;

@Service
public class QrProvider extends MiddlewareProvider<QRMiddlewareError> implements IQRProvider {
    private final String baseUrl;

    public QrProvider(ITokenMiddlewareProvider tokenMiddlewareProvider, MiddlewareConfig middlewareConfig, IHttpClientFactory httpClientFactory) {
        super(ProjectNameMW.GENERATE_QR_MANAGER, QRMiddlewareError.class, tokenMiddlewareProvider, middlewareConfig, httpClientFactory, middlewareConfig.getClientGenerateQrManager());
        this.baseUrl = middlewareConfig.getUrlBase() + ProjectNameMW.GENERATE_QR_MANAGER.getName();
    }

    @Override
    public QRCodeGenerateResponse generate(QRCodeGenerateMWRequest request, Map<String, String> parameters) throws IOException {
        String url = baseUrl + QRMiddlewareServices.GENERATE.getServiceURL();
        return post(url, HeadersMW.getDefaultHeaders(parameters), request, QRCodeGenerateResponse.class);
    }

    @Override
    public QRCodeGenerateResponse regenerate(QRCodeRegenerateMWRequest request, Map<String, String> parameters) throws IOException {
        String url = baseUrl + QRMiddlewareServices.REGENERATE.getServiceURL();
        return post(url, HeadersMW.getDefaultHeaders(parameters), request, QRCodeGenerateResponse.class);
    }

    @Override
    public QRCodeGenerateResponse decrypt(QRCodeRegenerateMWRequest request, Map<String, String> parameters) throws IOException {
        String url = baseUrl + QRMiddlewareServices.DECRYPT.getServiceURL();
        return post(url, HeadersMW.getDefaultHeaders(parameters), request, QRCodeGenerateResponse.class);
    }
}
