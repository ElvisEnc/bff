package bg.com.bo.bff.providers.mappings.personal.information;

import bg.com.bo.bff.application.dtos.response.apiface.DistrictsResponse;
import bg.com.bo.bff.application.dtos.response.user.EconomicActivityResponse;
import bg.com.bo.bff.application.dtos.response.user.PersonalResponse;
import bg.com.bo.bff.providers.dtos.request.personal.information.ApiPersonalInformationNetRequest;
import bg.com.bo.bff.providers.dtos.request.personal.information.DistrictsNetRequest;
import bg.com.bo.bff.providers.dtos.response.ProviderNetResponse;
import bg.com.bo.bff.providers.dtos.response.apiface.DistrictsNetResponse;
import bg.com.bo.bff.providers.dtos.response.personal.information.PersonalInformationNetResponse;

public interface IPersonalInformationMapper {
    ApiPersonalInformationNetRequest mapperRequest(String personId);

    PersonalResponse convertResponse(PersonalInformationNetResponse response);

    EconomicActivityResponse convertEconomicActivity(ProviderNetResponse netResponse);

    DistrictsNetRequest mapToDistrictRequest(String departmentId);

    DistrictsResponse mapToDistrictsResponse(DistrictsNetResponse netResponse);
}
