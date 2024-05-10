package bg.com.bo.bff.services.interfaces;

import bg.com.bo.bff.application.dtos.request.qr.QrListRequest;
import bg.com.bo.bff.application.dtos.response.qr.QrListResponse;

import java.io.IOException;
import java.util.Map;

public interface IQrService {
    QrListResponse getQrGeneratedPaid(QrListRequest request, Integer personId, Map<String, String> parameters) throws IOException;
}
