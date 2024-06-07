package bg.com.bo.bff.providers.interfaces;

import bg.com.bo.bff.application.dtos.response.user.EconomicActivityResponse;
import bg.com.bo.bff.application.dtos.response.user.MaritalStatusResponse;
import bg.com.bo.bff.application.dtos.response.user.PersonalResponse;
import bg.com.bo.bff.providers.dtos.request.personal.information.DistrictsNetRequest;
import bg.com.bo.bff.providers.dtos.response.apiface.DistrictsNetResponse;

import java.io.IOException;
import java.util.Map;

public interface IPersonalInformationNetProvider {
    PersonalResponse getPersonalInformation(String personId, Map<String, String> parameter) throws IOException;

    EconomicActivityResponse getEconomicalActivity(Integer personId);

    DistrictsNetResponse getDistricts(DistrictsNetRequest request, Map<String, String> parameter) throws IOException;

    MaritalStatusResponse getMaritalStatuses();
}
