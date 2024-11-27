package bg.com.bo.bff.providers.interfaces;

import bg.com.bo.bff.application.dtos.response.user.EconomicActivityResponse;
import bg.com.bo.bff.application.dtos.response.user.MaritalStatusResponse;
import bg.com.bo.bff.providers.dtos.request.personal.information.ApiPersonalInformationNetRequest;
import bg.com.bo.bff.providers.dtos.request.personal.information.DistrictsNetRequest;
import bg.com.bo.bff.providers.dtos.response.apiface.DistrictsNetResponse;
import bg.com.bo.bff.providers.dtos.request.personal.information.UpdatePersonalInformationNetRequest;
import bg.com.bo.bff.providers.dtos.response.personal.information.PersonalInformationNetResponse;
import bg.com.bo.bff.providers.dtos.response.personal.information.PersonalUpdateNetResponse;

import java.io.IOException;
import java.util.Map;

public interface IPersonalInformationNetProvider {
    PersonalInformationNetResponse getPersonalInformation(ApiPersonalInformationNetRequest request) throws IOException;

    EconomicActivityResponse getEconomicalActivity(Integer personId);

    DistrictsNetResponse getDistricts(DistrictsNetRequest request) throws IOException;

    MaritalStatusResponse getMaritalStatuses();

    PersonalUpdateNetResponse updatePersonalInformation(UpdatePersonalInformationNetRequest request) throws IOException;
}
