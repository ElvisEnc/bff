package bg.com.bo.bff.providers.interfaces;

import bg.com.bo.bff.providers.dtos.request.qr.mw.QRPaymentMWRequest;
import bg.com.bo.bff.providers.dtos.response.qr.mw.QRPaymentMWResponse;


import java.io.IOException;
import java.util.Map;

public interface IQrTransactionProvider {
    QRPaymentMWResponse qrPayment(QRPaymentMWRequest requestMW, Map<String, String> parameter) throws IOException;
}
