package bg.com.bo.bff.providers.mappings.apiface;

import bg.com.bo.bff.application.dtos.response.apiface.DepartmentsResponse;
import bg.com.bo.bff.providers.dtos.response.apiface.DepartmentsNetResponse;

public interface IApiFaceMapper {
    DepartmentsResponse mapToDepartmentsResponse(DepartmentsNetResponse netResponse);
}
