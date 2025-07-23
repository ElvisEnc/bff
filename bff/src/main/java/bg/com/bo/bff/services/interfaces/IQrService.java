package bg.com.bo.bff.services.interfaces;

import bg.com.bo.bff.application.dtos.request.qr.QRCodeGenerateRequest;
import bg.com.bo.bff.application.dtos.request.qr.QRCodeRegenerateRequest;
import bg.com.bo.bff.application.dtos.request.qr.QRPaymentRequest;
import bg.com.bo.bff.application.dtos.request.qr.QrDecryptRequest;
import bg.com.bo.bff.application.dtos.request.qr.QrListRequest;
import bg.com.bo.bff.application.dtos.response.qr.QrDecryptResponse;
import bg.com.bo.bff.application.dtos.response.qr.QrListResponse;
import bg.com.bo.bff.providers.dtos.response.qr.mw.QRCodeGenerateResponse;
import bg.com.bo.bff.providers.dtos.response.qr.mw.QRPaymentMWResponse;

import java.io.IOException;
import java.util.Map;


public interface IQrService {
    QrListResponse getQrGeneratedPaid(QrListRequest request, String personId, Map<String, String> parameters) throws IOException;

    QRCodeGenerateResponse generateQR(QRCodeGenerateRequest request, Map<String, String> parameters) throws IOException;

    QRCodeGenerateResponse regenerateQR(QRCodeRegenerateRequest request, Map<String, String> parameter) throws IOException;

    QRPaymentMWResponse qrPayment(QRPaymentRequest request, String personId, String accountId, Map<String, String> parameter) throws IOException;

    QrDecryptResponse decryptQR(QrDecryptRequest request, Map<String, String> parameter) throws IOException;
}






