package bg.com.bo.bff.application.dtos.response.user;

import bg.com.bo.bff.application.dtos.response.apiface.DepartmentsResponse;

import java.util.Collections;

public class DepartmentsResponseFixture {
    public static DepartmentsResponse withDefault() {
        return DepartmentsResponse.builder()
                .data(Collections.singletonList(DepartmentDetailFixture.withDefault()))
                .build();
    }
}
