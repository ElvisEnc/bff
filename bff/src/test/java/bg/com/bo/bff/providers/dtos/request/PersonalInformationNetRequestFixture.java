package bg.com.bo.bff.providers.dtos.request;

import bg.com.bo.bff.providers.dtos.request.personal.information.ApiPersonalInformationNetRequest;

public class PersonalInformationNetRequestFixture {
    public static ApiPersonalInformationNetRequest withDefault() {
        return ApiPersonalInformationNetRequest.builder()
                .intNumeroPersona("1487723")
                .pStrNroSesion("08052024093739235bebc7c04fddba")
                .build();
    }
}