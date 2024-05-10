package bg.com.bo.bff.providers.mappings.qr;

import bg.com.bo.bff.application.dtos.response.qr.QrGeneratedPaid;
import bg.com.bo.bff.providers.dtos.responses.qr.QrGeneratedPaidMW;

public interface IQrMapper {
    QrGeneratedPaid convert(QrGeneratedPaidMW mw);
}
