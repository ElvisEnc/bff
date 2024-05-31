package bg.com.bo.bff.application.dtos.request;

import bg.com.bo.bff.providers.dtos.request.QRCodeRegenerateMWRequest;

public class QRCodeRegenerateMWRequestFixture {
    public static QRCodeRegenerateMWRequest withDefault(){
        return new QRCodeRegenerateMWRequest("ddsfs");
    }
}
