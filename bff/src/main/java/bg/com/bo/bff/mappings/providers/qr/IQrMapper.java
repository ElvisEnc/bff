package bg.com.bo.bff.mappings.providers.qr;

import bg.com.bo.bff.application.dtos.request.qr.QRCodeGenerateRequest;
import bg.com.bo.bff.application.dtos.request.qr.QRCodeRegenerateRequest;
import bg.com.bo.bff.application.dtos.request.qr.QrDecryptRequest;
import bg.com.bo.bff.application.dtos.response.qr.QrDecryptResponse;
import bg.com.bo.bff.application.dtos.request.qr.QRPaymentRequest;
import bg.com.bo.bff.application.dtos.response.qr.QrGeneratedPaid;
import bg.com.bo.bff.providers.dtos.request.qr.mw.QRCodeGenerateMWRequest;
import bg.com.bo.bff.providers.dtos.request.qr.mw.QRCodeRegenerateMWRequest;
import bg.com.bo.bff.providers.dtos.request.qr.mw.QRPaymentMWRequest;
import bg.com.bo.bff.providers.dtos.response.qr.mw.QRCodeGenerateResponse;
import bg.com.bo.bff.providers.dtos.response.ach.account.mw.QrGeneratedPaidMW;

public interface IQrMapper {
    QrGeneratedPaid convert(QrGeneratedPaidMW mw);

    QRCodeGenerateMWRequest convert(QRCodeGenerateRequest request);

    QRCodeRegenerateMWRequest convert(QRCodeRegenerateRequest request);

    QRCodeRegenerateMWRequest convertDecrypt(QrDecryptRequest request);

    QrDecryptResponse convertDecryptResponse(QRCodeGenerateResponse request);

    QRPaymentMWRequest convert(QRPaymentRequest request, String personId, String accountId);
}

