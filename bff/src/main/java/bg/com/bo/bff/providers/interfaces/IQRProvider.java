package bg.com.bo.bff.providers.interfaces;

import bg.com.bo.bff.providers.dtos.requests.QRCodeGenerateMWRequest;
import bg.com.bo.bff.providers.dtos.requests.QRCodeRegenerateMWRequest;
import bg.com.bo.bff.providers.dtos.responses.qr.QRCodeGenerateResponse;

import java.io.IOException;
import java.util.Map;

public interface IQRProvider {
    QRCodeGenerateResponse generate(QRCodeGenerateMWRequest request, Map<String, String> parameters) throws IOException;

    QRCodeGenerateResponse regenerate(QRCodeRegenerateMWRequest request, Map<String, String> parameters) throws IOException;
}
