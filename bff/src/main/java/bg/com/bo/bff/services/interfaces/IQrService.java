package bg.com.bo.bff.services.interfaces;

import bg.com.bo.bff.application.dtos.request.QRCodeGenerateRequest;
import bg.com.bo.bff.application.dtos.request.QRCodeRegenerateRequest;
import bg.com.bo.bff.application.dtos.request.qr.QrListRequest;
import bg.com.bo.bff.application.dtos.response.qr.QrListResponse;
import bg.com.bo.bff.providers.dtos.responses.qr.QRCodeGenerateResponse;

import java.io.IOException;
import java.util.Map;


public interface IQrService {
    QrListResponse getQrGeneratedPaid(QrListRequest request, Integer personId, Map<String, String> parameters) throws IOException;
    QRCodeGenerateResponse generateQR(QRCodeGenerateRequest request, Map<String, String> parameters) throws IOException ;

    QRCodeGenerateResponse regenerateQR(QRCodeRegenerateRequest request, Map<String, String> parameter) throws IOException;
}






