package bg.com.bo.bff.providers.mappings.personal.information;

import bg.com.bo.bff.application.dtos.response.user.PersonalDetail;
import bg.com.bo.bff.application.dtos.response.user.PersonalResponse;
import bg.com.bo.bff.providers.dtos.request.personal.information.PersonalInformationNetRequest;
import bg.com.bo.bff.providers.dtos.response.personal.information.PersonalInformationNetResponse;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class PersonalInformationMapper implements IPersonalInformationMapper {

    @Override
    public PersonalInformationNetRequest mapperRequest (String personId) {
        return PersonalInformationNetRequest.builder()
                .intNumeroPersona(personId)
                .pStrNroSesion("10052024151318af42ae6fe0fd0f72")
                .build();
    }

    @Override
    public PersonalResponse convertResponse (PersonalInformationNetResponse response) {
        return PersonalResponse.builder()
                .maritalStatus(PersonalDetail.MaritalStatus.builder()
                        .status(response.getDataContent().getClientDataList().get(0).getMaritalStatus())
                        .spouseLastName(response.getDataContent().getClientDataList().get(0).getSpouseLastName())
                        .spouseName(response.getDataContent().getClientDataList().get(0).getSpouseName())
                        .build())
                .economicalActivity(PersonalDetail.EconomicalActivity.builder()
                        .type(response.getDataContent().getEconomicActivities().get(0).getIncomeSource())
                        .company(response.getDataContent().getEconomicActivities().get(0).getCompany())
                        .position(response.getDataContent().getEconomicActivities().get(0).getPosition())
                        .build())
                .personalData(PersonalDetail.PersonalData.builder()
                        .completeName(response.getDataContent().getClientDataList().get(0).getFullName())
                        .telephoneNumber(response.getDataContent().getClientDataList().get(0).getPhones())
                        .email(response.getDataContent().getClientDataList().get(0).getEmail())
                        .section(response.getDataContent().getClientDataList().get(0).getCityCode())
                        .dictrict(response.getDataContent().getClientDataList().get(0).getCity())
                        .zone(response.getDataContent().getClientDataList().get(0).getZone())
                        .address(response.getDataContent().getClientDataList().get(0).getNeighborhood())
                        .department(response.getDataContent().getClientDataList().get(0).getDepartment())
                        .floor(response.getDataContent().getClientDataList().get(0).getFloor())
                        .GPS(response.getDataContent().getClientDataList().get(0).getCoordinates())
                        .build())
                .references(response.getDataContent().getReferences().stream()
                        .map(reference -> PersonalDetail.Reference.builder()
                                .name(reference.getName())
                                .telephone(reference.getPhone())
                                .build())
                        .collect(Collectors.toList()))
                .build();
    }
}
