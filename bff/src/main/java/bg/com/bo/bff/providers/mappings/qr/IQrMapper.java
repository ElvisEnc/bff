package bg.com.bo.bff.providers.mappings.qr;

import bg.com.bo.bff.application.dtos.request.QRCodeGenerateRequest;
import bg.com.bo.bff.application.dtos.response.qr.QrGeneratedPaid;
import bg.com.bo.bff.providers.dtos.requests.QRCodeGenerateMWRequest;
import bg.com.bo.bff.providers.dtos.responses.qr.QrGeneratedPaidMW;

public interface IQrMapper {
    QrGeneratedPaid convert(QrGeneratedPaidMW mw);
    QRCodeGenerateMWRequest convert(QRCodeGenerateRequest request);
}
