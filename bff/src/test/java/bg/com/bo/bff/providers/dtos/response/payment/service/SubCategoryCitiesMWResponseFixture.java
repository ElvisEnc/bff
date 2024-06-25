package bg.com.bo.bff.providers.dtos.response.payment.service;

import bg.com.bo.bff.application.dtos.SubCategoryCitiesMWResponse;

import java.util.List;

public class SubCategoryCitiesMWResponseFixture {

    public static SubCategoryCitiesMWResponse withDefault(){
        List<SubCategoryCitiesMWResponse.CityMW> data = List.of(
                new SubCategoryCitiesMWResponse.CityMW(3, "COCHABAMBA"),
                new SubCategoryCitiesMWResponse.CityMW(7, "SANTA CRUZ")

        );
        return new SubCategoryCitiesMWResponse(data);
    }

    public static String errorMDWPSM004() {
        return "{\"code\":406,\"errorType\":\"Technical\",\"errorDetailResponse\":[{\"code\":\"MDWPSM-004\",\"description\":\"Not found cities\"}]}";
    }
}
