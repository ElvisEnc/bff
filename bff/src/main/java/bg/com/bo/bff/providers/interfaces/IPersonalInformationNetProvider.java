package bg.com.bo.bff.providers.interfaces;

import bg.com.bo.bff.application.dtos.response.user.EconomicActivityResponse;
import bg.com.bo.bff.application.dtos.response.user.PersonalResponse;

import java.io.IOException;
import java.util.Map;

public interface IPersonalInformationNetProvider {
    PersonalResponse getPersonalInformation(String personId, Map<String, String> parameter) throws IOException;
    EconomicActivityResponse getEconomicalActivity(Integer personId);
}
