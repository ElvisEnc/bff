package bg.com.bo.bff.providers.mappings.qr;

import bg.com.bo.bff.application.dtos.request.QRCodeGenerateRequest;
import bg.com.bo.bff.application.dtos.request.QRCodeRegenerateRequest;
import bg.com.bo.bff.application.dtos.request.qr.QrDecryptRequest;
import bg.com.bo.bff.application.dtos.response.qr.QrDecryptResponse;
import bg.com.bo.bff.application.dtos.request.qr.QRPaymentRequest;
import bg.com.bo.bff.application.dtos.response.qr.QrGeneratedPaid;
import bg.com.bo.bff.providers.dtos.request.QRCodeGenerateMWRequest;
import bg.com.bo.bff.providers.dtos.request.QRCodeRegenerateMWRequest;
import bg.com.bo.bff.providers.dtos.requests.qr.QRPaymentMWRequest;
import bg.com.bo.bff.providers.dtos.response.qr.QRCodeGenerateResponse;
import bg.com.bo.bff.providers.dtos.response.qr.QrGeneratedPaidMW;

public interface IQrMapper {
    QrGeneratedPaid convert(QrGeneratedPaidMW mw);

    QRCodeGenerateMWRequest convert(QRCodeGenerateRequest request);

    QRCodeRegenerateMWRequest convert(QRCodeRegenerateRequest request);

    QRCodeRegenerateMWRequest convertDecrypt(QrDecryptRequest request);

    QrDecryptResponse convertDecryptResponse(QRCodeGenerateResponse request);

    QRPaymentMWRequest convert(QRPaymentRequest request, String personId, String accountId);
}

