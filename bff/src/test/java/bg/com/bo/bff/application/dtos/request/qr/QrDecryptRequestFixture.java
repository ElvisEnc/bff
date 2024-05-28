package bg.com.bo.bff.application.dtos.request.qr;

public class QrDecryptRequestFixture {
    public static QrDecryptRequest withDefault(){
        return QrDecryptRequest.builder()
                .data("data")
                .build();
    }
}
