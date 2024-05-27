package bg.com.bo.bff.providers.mappings.personal.information;

import bg.com.bo.bff.application.dtos.response.user.PersonalResponse;
import bg.com.bo.bff.providers.dtos.requests.personal.information.PersonalInformationNetRequest;
import bg.com.bo.bff.providers.dtos.responses.personal.information.PersonalInformationNetResponse;

public interface IPersonalInformationMapper {
    PersonalInformationNetRequest mapperRequest(String personId);
    PersonalResponse convertResponse(PersonalInformationNetResponse response);
}
