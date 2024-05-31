package bg.com.bo.bff.application.dtos.response;

import bg.com.bo.bff.providers.dtos.response.qr.QRCDataResponse;
import bg.com.bo.bff.providers.dtos.response.qr.QRCodeGenerateResponse;

public class QRCodeGenerateResponseFixture {
    public static QRCodeGenerateResponse withDefault(){
     return   QRCodeGenerateResponse.builder()
             .data(new QRCDataResponse("12121212121"))
             .build();
    }
}
