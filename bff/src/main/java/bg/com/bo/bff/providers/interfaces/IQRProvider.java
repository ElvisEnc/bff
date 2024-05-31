package bg.com.bo.bff.providers.interfaces;

import bg.com.bo.bff.providers.dtos.request.QRCodeGenerateMWRequest;
import bg.com.bo.bff.providers.dtos.request.QRCodeRegenerateMWRequest;
import bg.com.bo.bff.providers.dtos.response.qr.QRCodeGenerateResponse;

import java.io.IOException;
import java.util.Map;

public interface IQRProvider {
    QRCodeGenerateResponse generate(QRCodeGenerateMWRequest request, Map<String, String> parameters) throws IOException;

    QRCodeGenerateResponse regenerate(QRCodeRegenerateMWRequest request, Map<String, String> parameters) throws IOException;

    QRCodeGenerateResponse decrypt(QRCodeRegenerateMWRequest request, Map<String, String> parameters) throws IOException;
}
