package bg.com.bo.bff.application.dtos.response.user;

import bg.com.bo.bff.application.dtos.response.apiface.DepartmentDetail;

public class DepartmentDetailFixture {
    public static DepartmentDetail withDefault() {
        return DepartmentDetail.builder()
                .id(123)
                .description("Description")
                .build();
    }
}