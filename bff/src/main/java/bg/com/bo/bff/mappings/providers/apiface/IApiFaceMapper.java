package bg.com.bo.bff.mappings.providers.apiface;

import bg.com.bo.bff.application.dtos.response.user.apiface.DepartmentsResponse;
import bg.com.bo.bff.providers.dtos.response.apiface.DepartmentsNetResponse;

public interface IApiFaceMapper {
    DepartmentsResponse mapToDepartmentsResponse(DepartmentsNetResponse netResponse);
}
