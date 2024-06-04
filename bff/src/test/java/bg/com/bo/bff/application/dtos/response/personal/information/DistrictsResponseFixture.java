package bg.com.bo.bff.application.dtos.response.personal.information;

import bg.com.bo.bff.application.dtos.response.apiface.DistrictsResponse;

import java.util.Collections;

public class DistrictsResponseFixture {
    public static DistrictsResponse withDefault() {
        return DistrictsResponse.builder()
                .data(Collections.singletonList(DistrictDetailFixture.withDefault()))
                .build();
    }
}
