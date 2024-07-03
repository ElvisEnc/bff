package bg.com.bo.bff.providers.dtos.response.personal.information;

import bg.com.bo.bff.providers.dtos.response.apiface.DepartmentNetDetail;
import bg.com.bo.bff.providers.dtos.response.apiface.DepartmentsNetResponse;
import bg.com.bo.bff.providers.dtos.response.apiface.DistrictNetDetail;
import bg.com.bo.bff.providers.dtos.response.apiface.DistrictsNetResponse;

import java.util.Arrays;

public class PersonalInformationNetResponseFixture {
    public static PersonalUpdateNetResponse withDefaultPersonalUpdateNetResponse() {
        return new PersonalUpdateNetResponse("COD000", "1", "Ejecucion Correcta");

    }

    public static DistrictsNetResponse withDefaultDistrictsNetResponse() {
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

    public static DepartmentsNetResponse withDefaultDepartmentsNetResponse() {
        return DepartmentsNetResponse.builder()
                .data(Arrays.asList(DepartmentNetDetail.builder()
                                .codeError("0")
                                .descriptionError("OK")
                                .codeResult(0)
                                .description("OK")
                                .messageApp("OK")
                                .messageUser("OK")
                                .sessionNumber("0")
                                .codeSquare(0)
                                .build(),
                        DepartmentNetDetail.builder()
                                .codeError("0")
                                .descriptionError("OK")
                                .codeResult(0)
                                .description("OK")
                                .messageApp("OK")
                                .messageUser("OK")
                                .sessionNumber("0")
                                .codeSquare(0)
                                .build())
                )
                .build();
    }
}
