package bg.com.bo.bff.application.dtos.response.personal.information;

import bg.com.bo.bff.application.dtos.response.apiface.DistrictDetail;

public class DistrictDetailFixture {
    public static DistrictDetail withDefault() {
        return DistrictDetail.builder()
                .id(123)
                .description("Description")
                .build();
    }
}