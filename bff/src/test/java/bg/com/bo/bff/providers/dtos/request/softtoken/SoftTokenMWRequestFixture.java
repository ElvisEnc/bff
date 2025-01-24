package bg.com.bo.bff.providers.dtos.request.softtoken;

import bg.com.bo.bff.providers.dtos.request.softtoken.mw.SoftTokenMWRequest;
import bg.com.bo.bff.providers.dtos.request.softtoken.mw.SoftTokenSentCodeMWRequest;

public class SoftTokenMWRequestFixture {

    public static SoftTokenMWRequest withDefault() {
        return SoftTokenMWRequest.builder()
                .codPerson(1212)
                .codApplication(1)
                .codCanal(1)
                .codLanguage(1)
                .imei("test")
                .ksBga("test")
                .didBga("test")
                .operatingSystem("test")
                .build();
    }

    public static SoftTokenSentCodeMWRequest withDefaultCode() {
        return SoftTokenSentCodeMWRequest.builder()
                .codPerson(1212)
                .codApplication(1)
                .codCanal(1)
                .codLanguage(1)
                .imei("test")
                .ksBga("test")
                .didBga("test")
                .operatingSystem("test")
                .email("test@test.com")
                .telephone("74125874")
                .build();
    }
}
