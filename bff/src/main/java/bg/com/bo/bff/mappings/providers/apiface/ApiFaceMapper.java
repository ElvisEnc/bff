package bg.com.bo.bff.mappings.providers.apiface;

import bg.com.bo.bff.application.dtos.response.user.apiface.DepartmentDetail;
import bg.com.bo.bff.application.dtos.response.user.apiface.DepartmentsResponse;
import bg.com.bo.bff.providers.dtos.response.apiface.DepartmentNetDetail;
import bg.com.bo.bff.providers.dtos.response.apiface.DepartmentsNetResponse;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ApiFaceMapper implements IApiFaceMapper {
    public DepartmentsResponse mapToDepartmentsResponse(DepartmentsNetResponse netResponse) {
        List<DepartmentDetail> dataList = new ArrayList<>();
        if (netResponse != null && netResponse.getData() != null) {
            for (DepartmentNetDetail departmentNetData : netResponse.getData()) {
                DepartmentDetail data = DepartmentDetail.builder()
                        .id(departmentNetData.getCodeSquare())
                        .description(departmentNetData.getDescription())
                        .build();
                dataList.add(data);
            }
        }
        return DepartmentsResponse.builder()
                .data(dataList)
                .build();
    }
}
