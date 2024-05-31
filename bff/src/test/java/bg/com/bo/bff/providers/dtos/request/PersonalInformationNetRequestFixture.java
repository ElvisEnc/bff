package bg.com.bo.bff.providers.dtos.request;

import bg.com.bo.bff.providers.dtos.request.personal.information.PersonalInformationNetRequest;

public class PersonalInformationNetRequestFixture {
    public static PersonalInformationNetRequest withDefault() {
        return PersonalInformationNetRequest.builder()
                .intNumeroPersona("1487723")
                .pStrNroSesion("08052024093739235bebc7c04fddba")
                .build();
    }
}