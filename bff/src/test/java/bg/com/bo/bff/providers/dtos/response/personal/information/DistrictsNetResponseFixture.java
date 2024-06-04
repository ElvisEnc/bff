package bg.com.bo.bff.providers.dtos.response.personal.information;

import bg.com.bo.bff.providers.dtos.response.apiface.DepartmentNetDetail;
import bg.com.bo.bff.providers.dtos.response.apiface.DepartmentsNetResponse;
import bg.com.bo.bff.providers.dtos.response.apiface.DistrictNetDetail;
import bg.com.bo.bff.providers.dtos.response.apiface.DistrictsNetResponse;

import java.util.Arrays;

public class DistrictsNetResponseFixture {
    public static DistrictsNetResponse withDefault() {
        return DistrictsNetResponse.builder().result(
                        DistrictsNetResponse.DistrictData.builder()
                                .data(Arrays.asList(DistrictNetDetail.builder()
                                                .codeDistrict("01")
                                                .description("CHACHAPOYAS")
                                                .build(),
                                        DistrictNetDetail.builder()
                                                .codeDistrict("02")
                                                .description("BAGUA")
                                                .build(),
                                        DistrictNetDetail.builder()
                                                .codeDistrict("03")
                                                .description("BONGARA")
                                                .build()
                                ))
                                .build())
                .errorCode("0")
                .message("OK")
                .build();
    }
}